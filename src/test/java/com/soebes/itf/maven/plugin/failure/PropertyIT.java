package com.soebes.itf.maven.plugin.failure;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.extension.SystemProperty;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
class PropertyIT {

  @MavenTest
  @MavenGoal("verify")
  @SystemProperty("skipTests")
  void goal_clean_skiptests (MavenExecutionResult result) {
    assertThat(result)
        .isSuccessful()
        .out()
        .info()
        .containsSubsequence(
            "--- maven-enforcer-plugin:3.0.0:enforce (enforce-maven) @ kata-fraction ---",
            "--- jacoco-maven-plugin:0.8.7:prepare-agent (default) @ kata-fraction ---",
            "--- maven-resources-plugin:3.2.0:resources (default-resources) @ kata-fraction ---",
            "--- maven-compiler-plugin:3.10.0:compile (default-compile) @ kata-fraction ---",
            "--- maven-resources-plugin:3.2.0:testResources (default-testResources) @ kata-fraction ---",
            "Using 'UTF-8' encoding to copy filtered resources.",
            "--- maven-compiler-plugin:3.10.0:testCompile (default-testCompile) @ kata-fraction ---",
            "--- maven-surefire-plugin:3.0.0-M5:test (default-test) @ kata-fraction ---",
            "Tests are skipped.",
            "--- maven-jar-plugin:3.2.2:jar (default-jar) @ kata-fraction ---",
            "--- maven-site-plugin:3.11.0:attach-descriptor (attach-descriptor) @ kata-fraction ---",
            "--- jacoco-maven-plugin:0.8.7:report (default) @ kata-fraction ---",
            "BUILD SUCCESS"
        );
    assertThat(result)
        .isSuccessful()
        .out()
        .warn().isEmpty();
  }

  @MavenTest
  @MavenGoal("clean")
  @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:failure")
  @SystemProperty(value = "executionException", content = "true")
  @SystemProperty(value = "exception", content = "This is the value of exception.")
  void goal_failure_execution(MavenExecutionResult result) {
    assertThat(result)
        .isFailure()
        .out()
        .info()
        .containsSubsequence(
            "Scanning for projects...",
            "-------------------< com.soebes.katas:kata-fraction >-------------------",
            "Building kata-fraction 1.0-SNAPSHOT",
            "--------------------------------[ jar ]---------------------------------",
            "--- maven-clean-plugin:3.1.0:clean (default-clean) @ kata-fraction ---",
            "--- itf-failure-plugin:0.1.0-SNAPSHOT:failure (default-cli) @ kata-fraction ---",
            "BUILD FAILURE"
        );
    assertThat(result)
        .isFailure()
        .out()
        .error()
        .contains("Failed to execute goal com.soebes.itf.examples.part.v" +
            ":itf-failure-plugin:0.1.0-SNAPSHOT:failure (default-cli) on project " +
            "kata-fraction: This is the value of exception. -> [Help 1]");
    assertThat(result)
        .isFailure()
        .out()
        .plain()
        .contains("Caused by: org.apache.maven.plugin.MojoExecutionException: This is the value of exception.");

  }

  @MavenTest
  @MavenGoal("clean")
  @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:failure")
  @SystemProperty(value = "failureException", content = "true")
  @SystemProperty(value = "exception", content = "This is the value of exception.")
  void goal_failure_failure_execution(MavenExecutionResult result) {
    assertThat(result)
        .isFailure()
        .out()
        .info()
        .containsSubsequence(
            "Scanning for projects...",
            "-------------------< com.soebes.katas:kata-fraction >-------------------",
            "Building kata-fraction 1.0-SNAPSHOT",
            "--------------------------------[ jar ]---------------------------------",
            "--- maven-clean-plugin:3.1.0:clean (default-clean) @ kata-fraction ---",
            "--- itf-failure-plugin:0.1.0-SNAPSHOT:failure (default-cli) @ kata-fraction ---",
            "BUILD FAILURE"
        );
    assertThat(result)
        .isFailure()
        .out()
        .error()
        .contains("Failed to execute goal com.soebes.itf.examples.part.v" +
            ":itf-failure-plugin:0.1.0-SNAPSHOT:failure (default-cli) on project " +
            "kata-fraction: This is the value of exception. -> [Help 1]");


    assertThat(result)
        .isFailure()
        .out()
        .plain()
        .contains("Caused by: org.apache.maven.plugin.MojoFailureException: This is the value of exception.");
  }

}