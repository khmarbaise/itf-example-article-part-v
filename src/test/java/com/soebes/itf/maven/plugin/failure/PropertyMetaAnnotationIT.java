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
class PropertyMetaAnnotationIT {

  @MavenTest
  @MavenGoal("clean")
  @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:failure")
  @ExecutionException
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

}