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

package io.appulse.utils.threads.executor.builder;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import io.appulse.utils.threads.executor.ScheduledExecutorServiceWithClientTrace;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * The {@link ScheduledExecutorService}'s builder object.
 *
 * @since 1.5.0
 * @author Artem Labazin
 */
@SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
@FieldDefaults(level = PRIVATE)
public final class ScheduledExecutorServiceBuilder {

  private static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();

  int poolSize = 1;

  boolean continueExistingPeriodicTasksAfterShutdownPolicy;

  boolean executeExistingDelayedTasksAfterShutdownPolicy = true;

  boolean removeOnCancelPolicy;

  boolean useClientTrace;

  ThreadFactory threadFactory;

  RejectedExecutionHandler handler;

  public ScheduledExecutorServiceBuilder poolSize (int value) {
    if (value < 1) {
      throw new IllegalArgumentException("Pool size must be greater than 0");
    }
    this.poolSize = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder continueExistingPeriodicTasksAfterShutdownPolicy () {
    return continueExistingPeriodicTasksAfterShutdownPolicy(true);
  }

  public ScheduledExecutorServiceBuilder continueExistingPeriodicTasksAfterShutdownPolicy (boolean value) {
    this.continueExistingPeriodicTasksAfterShutdownPolicy = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder executeExistingDelayedTasksAfterShutdownPolicy () {
    return executeExistingDelayedTasksAfterShutdownPolicy(true);
  }

  public ScheduledExecutorServiceBuilder executeExistingDelayedTasksAfterShutdownPolicy (boolean value) {
    this.executeExistingDelayedTasksAfterShutdownPolicy = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder removeOnCancelPolicy () {
    return removeOnCancelPolicy(true);
  }

  public ScheduledExecutorServiceBuilder removeOnCancelPolicy (boolean value) {
    this.removeOnCancelPolicy = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder clientTrace () {
    return clientTrace(true);
  }

  public ScheduledExecutorServiceBuilder clientTrace (boolean value) {
    this.useClientTrace = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder threadFactory (@NonNull ThreadFactory value) {
    this.threadFactory = value;
    return this;
  }

  public ScheduledExecutorServiceBuilder handler (@NonNull RejectedExecutionHandler value) {
    this.handler = value;
    return this;
  }

  public ScheduledExecutorService build () {
    if (poolSize < 1) {
      throw new IllegalArgumentException("Pool size must be greater than 0");
    }

    threadFactory = ofNullable(threadFactory)
        .orElse(Executors.defaultThreadFactory());

    handler = ofNullable(handler)
        .orElse(DEFAULT_HANDLER);

    ScheduledThreadPoolExecutor result = new ScheduledThreadPoolExecutor(
        poolSize,
        threadFactory,
        handler
    );
    result.setContinueExistingPeriodicTasksAfterShutdownPolicy(continueExistingPeriodicTasksAfterShutdownPolicy);
    result.setExecuteExistingDelayedTasksAfterShutdownPolicy(executeExistingDelayedTasksAfterShutdownPolicy);
    result.setRemoveOnCancelPolicy(removeOnCancelPolicy);

    return useClientTrace
           ? new ScheduledExecutorServiceWithClientTrace(result)
           : result;
  }
}
