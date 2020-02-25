/*
 * Copyright 2020 the original author or authors.
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;

import org.junit.jupiter.api.Test;

class AppulseThreadFactoryTest {

  @Test
  void simpleName () throws Exception {
    CompletableFuture<String> future = new CompletableFuture<>();

    AppulseThreadFactory.builder()
        .name("popa")
        .build()
        .newThread(() -> future.complete(Thread.currentThread().getName()))
        .start();

    assertThat(future.get(1, SECONDS))
        .isEqualTo("popa");
  }

  @Test
  void nameWithCount () throws Exception {
    ThreadFactory factory = AppulseThreadFactory.builder()
        .name("popa-%d")
        .build();

    CompletableFuture<String> future1 = new CompletableFuture<>();
    factory.newThread(() -> future1.complete(Thread.currentThread().getName()))
        .start();

    assertThat(future1.get(1, SECONDS))
        .isEqualTo("popa-1");

    CompletableFuture<String> future2 = new CompletableFuture<>();
    factory.newThread(() -> future2.complete(Thread.currentThread().getName()))
        .start();

    assertThat(future2.get(1, SECONDS))
        .isEqualTo("popa-2");
  }
}
