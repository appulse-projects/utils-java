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

package io.appulse.utils.threads.executor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@SuppressWarnings("PMD.DoNotUseThreads")
@FieldDefaults(level = PRIVATE, makeFinal = true)
abstract class ScheduledExecutorServiceWrapper extends ExecutorServiceWrapper implements ScheduledExecutorService {

  @Getter(PROTECTED)
  ScheduledExecutorService delegate;

  protected ScheduledExecutorServiceWrapper (ScheduledExecutorService delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  @Override
  public ScheduledFuture<?> schedule (Runnable command, long delay, TimeUnit unit) {
    return delegate.schedule(command, delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule (Callable<V> callable, long delay, TimeUnit unit) {
    return delegate.schedule(callable, delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate (Runnable command, long initialDelay, long period, TimeUnit unit) {
    return delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay (Runnable command, long initialDelay, long delay, TimeUnit unit) {
    return delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
  }
}
