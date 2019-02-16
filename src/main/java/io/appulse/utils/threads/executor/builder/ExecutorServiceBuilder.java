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

package io.appulse.utils.threads.executor.builder;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.appulse.utils.threads.executor.ExecutorServiceWithClientTrace;
import io.appulse.utils.threads.executor.ExecutorServiceWithTimeMonitor;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@SuppressWarnings({
    "PMD.DoNotUseThreads",
    "PMD.AvoidLiteralsInIfCondition",
    "PMD.CyclomaticComplexity",
    "PMD.NPathComplexity"
})
@FieldDefaults(level = PRIVATE)
public final class ExecutorServiceBuilder {

  private static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();

  int corePoolSize;

  int maxPoolSize;

  long keepAliveTime;

  TimeUnit unit;

  BlockingQueue<Runnable> queue;

  int queueLimit;

  boolean clientTrace;

  boolean timeLogging;

  ThreadFactory threadFactory;

  RejectedExecutionHandler handler;

  public ExecutorServiceBuilder corePoolSize (int value) {
    this.corePoolSize = value;
    return this;
  }

  public ExecutorServiceBuilder maxPoolSize (int value) {
    this.maxPoolSize = value;
    return this;
  }

  public ExecutorServiceBuilder keepAliveTime (long value) {
    this.keepAliveTime = value;
    return this;
  }

  public ExecutorServiceBuilder unit (@NonNull TimeUnit value) {
    this.unit = value;
    return this;
  }

  public ExecutorServiceBuilder queue (@NonNull BlockingQueue<Runnable> value) {
    this.queue = value;
    return this;
  }

  public ExecutorServiceBuilder queueLimit (int value) {
    this.queueLimit = value;
    queue = null;
    return this;
  }

  public ExecutorServiceBuilder enableClientTrace () {
    return clientTrace(true);
  }

  public ExecutorServiceBuilder clientTrace (boolean value) {
    this.clientTrace = value;
    return this;
  }

  public ExecutorServiceBuilder enableTimeLogging () {
    return timeLogging(true);
  }

  public ExecutorServiceBuilder timeLogging (boolean value) {
    this.timeLogging = value;
    return this;
  }

  public ExecutorServiceBuilder threadFactory (@NonNull ThreadFactory value) {
    this.threadFactory = value;
    return this;
  }

  public ExecutorServiceBuilder handler (@NonNull RejectedExecutionHandler value) {
    this.handler = value;
    return this;
  }

  public ExecutorService build () {
    if (corePoolSize < 0) {
      throw new IllegalArgumentException("Core pool size must be greater or equals than 0");
    }

    if (maxPoolSize <= 0) {
      throw new IllegalArgumentException("Max pool size must be greater than 0");
    } else if (maxPoolSize < corePoolSize) {
      maxPoolSize = corePoolSize;
    }

    if (keepAliveTime < 0L) {
      throw new IllegalArgumentException("Keep alive time must be greater than 0");
    }

    if (queueLimit < 0) {
      throw new IllegalArgumentException("Queue limit must be greater than 0");
    } else if (queueLimit > 0 && queue != null) {
      throw new IllegalArgumentException("I couldn't set queue limit and queue simultaneously");
    } else if (queueLimit > 0) {
      queue = new LinkedBlockingQueue<>(queueLimit);
    } else if (queue == null) {
      queue = new LinkedBlockingQueue<>();
    }

    threadFactory = ofNullable(threadFactory)
        .orElse(Executors.defaultThreadFactory());

    handler = ofNullable(handler)
        .orElse(DEFAULT_HANDLER);

    ExecutorService result = new ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        unit,
        queue,
        threadFactory,
        handler
    );

    if (clientTrace) {
      result = new ExecutorServiceWithClientTrace(result);
    }

    if (timeLogging) {
      result = new ExecutorServiceWithTimeMonitor(result);
    }
    return result;
  }
}
