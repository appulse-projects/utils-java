/*
 * Copyright 2018 the original author or authors.
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

package io.appulse.utils.threads.executor.builder;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory;
import static lombok.AccessLevel.PRIVATE;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
@FieldDefaults(level = PRIVATE)
public final class ForkJoinPoolBuilder {

  int parallelism;

  ForkJoinWorkerThreadFactory threadFactory;

  UncaughtExceptionHandler handler;

  boolean asyncMode = true;

  public ForkJoinPoolBuilder parallelism (int value) {
    if (value < 1) {
      throw new IllegalArgumentException("Parallelism must be greater than 0");
    }
    this.parallelism = value;
    return this;
  }

  public ForkJoinPoolBuilder threadFactory (@NonNull ForkJoinWorkerThreadFactory value) {
    this.threadFactory = value;
    return this;
  }

  public ForkJoinPoolBuilder handler (@NonNull UncaughtExceptionHandler value) {
    this.handler = value;
    return this;
  }

  public ForkJoinPoolBuilder enableAsyncMode () {
    return asyncMode(true);
  }

  public ForkJoinPoolBuilder asyncMode (boolean value) {
    this.asyncMode = value;
    return this;
  }

  public ForkJoinPool build () {
    if (parallelism < 1) {
      parallelism = Runtime.getRuntime().availableProcessors();
    }

    threadFactory = ofNullable(threadFactory)
        .orElse(defaultForkJoinWorkerThreadFactory);

    return new ForkJoinPool(
        parallelism,
        threadFactory,
        handler,
        asyncMode
    );
  }
}
