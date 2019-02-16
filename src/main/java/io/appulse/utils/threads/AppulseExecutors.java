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

import static java.lang.Integer.MAX_VALUE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SynchronousQueue;

import io.appulse.utils.threads.executor.builder.ExecutorServiceBuilder;
import io.appulse.utils.threads.executor.builder.ForkJoinPoolBuilder;
import io.appulse.utils.threads.executor.builder.ScheduledExecutorServiceBuilder;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@SuppressWarnings("PMD.ClassNamingConventions")
public final class AppulseExecutors {

  public static ExecutorServiceBuilder newFixedThreadPool (int threads) {
    return new ExecutorServiceBuilder()
        .corePoolSize(threads)
        .maxPoolSize(threads)
        .keepAliveTime(0L)
        .unit(MILLISECONDS);
  }

  public static ForkJoinPoolBuilder newWorkStealingPool () {
    return new ForkJoinPoolBuilder()
        .parallelism(Runtime.getRuntime().availableProcessors())
        .threadFactory(ForkJoinPool.defaultForkJoinWorkerThreadFactory)
        .asyncMode(true);
  }

  public static ExecutorServiceBuilder newSingleThreadExecutor () {
    return new ExecutorServiceBuilder()
        .corePoolSize(1)
        .maxPoolSize(1)
        .keepAliveTime(0L)
        .unit(MILLISECONDS);
  }

  public static ExecutorServiceBuilder newCachedThreadPool () {
    return new ExecutorServiceBuilder()
        .corePoolSize(0)
        .maxPoolSize(MAX_VALUE)
        .keepAliveTime(1L)
        .unit(MINUTES)
        .queue(new SynchronousQueue<>());
  }

  public static ScheduledExecutorServiceBuilder newScheduledThreadPool () {
    return new ScheduledExecutorServiceBuilder();
  }

  private AppulseExecutors () {
  }
}
