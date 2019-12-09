/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appulse.utils.threads;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

class FutureUtilsTests {

  @Test
  void completedExceptionally () {
    val future = FutureUtils.completedExceptionally(new RuntimeException());

    assertThat(future)
        .isCompletedExceptionally();

    assertThatThrownBy(() -> future.get())
        .isInstanceOf(ExecutionException.class)
        .hasCauseInstanceOf(RuntimeException.class);
  }

  @Test
  @SneakyThrows
  void firstCompletedWithoutException1 () {
    List<CompletableFuture<String>> futures = Arrays.asList(
        CompletableFuture.supplyAsync(() -> {
          throw new RuntimeException("failing immediately");
        }),
        CompletableFuture.supplyAsync(() -> {
          // delayed to demonstrate that the solution will wait for all completions
          // to ensure it doesn't miss a possible successful computation
          LockSupport.parkNanos(SECONDS.toNanos(5));
          throw new RuntimeException("failing later");
        })
    );

    CompletableFuture<String> result = FutureUtils.firstCompletedWithoutException(futures);

    SECONDS.sleep(6);

    assertThat(result)
        .isCompletedExceptionally();

    assertThatThrownBy(() -> result.get())
        .isInstanceOf(ExecutionException.class)
        .hasCauseInstanceOf(RuntimeException.class)
        .hasMessage("java.lang.RuntimeException: failing immediately");
  }

  @Test
  @SneakyThrows
  void firstCompletedWithoutException2 () {
    List<CompletableFuture<String>> futures = Arrays.asList(
        CompletableFuture.supplyAsync(() -> {
          LockSupport.parkNanos(SECONDS.toNanos(10));
          return "with 10s delay";
        }),
        CompletableFuture.supplyAsync(() -> {
          throw new RuntimeException("failing immediately"); }
        ),
        CompletableFuture.supplyAsync(() -> {
          LockSupport.parkNanos(SECONDS.toNanos(5));
          return "with 5s delay";
        })
    );

    CompletableFuture<String> result = FutureUtils.firstCompletedWithoutException(futures);

    SECONDS.sleep(6);

    assertThat(result)
        .isCompletedWithValue("with 5s delay");
  }
}
