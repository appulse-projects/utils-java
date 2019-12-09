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

package io.appulse.utils.threads.executor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The {@link ExecutorService} wrapper, which delegates all methods execution.
 *
 * @since 1.5.0
 * @author Artem Labazin
 */
@SuppressWarnings("PMD.DoNotUseThreads")
@RequiredArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PRIVATE, makeFinal = true)
abstract class ExecutorServiceWrapper extends AbstractExecutorService {

  @NonNull
  @Getter(PROTECTED)
  ExecutorService delegate;

  @Override
  public void execute (Runnable command) {
    delegate.execute(command);
  }

  @Override
  public void shutdown () {
    delegate.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow () {
    return delegate.shutdownNow();
  }

  @Override
  public boolean isShutdown () {
    return delegate.isShutdown();
  }

  @Override
  public boolean isTerminated () {
    return delegate.isTerminated();
  }

  @Override
  public boolean awaitTermination (long timeout, TimeUnit unit) throws InterruptedException {
    return delegate.awaitTermination(timeout, unit);
  }
}
