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

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import lombok.experimental.FieldDefaults;

@SuppressWarnings({
    "PMD.DoNotUseThreads",
    "PMD.ClassNamingConventions"
})
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class CompletablePromiseContext {

  static CompletablePromiseContext getInstance () {
    return LazyHolder.INSTANCE;
  }

  ScheduledExecutorService scheduler;

  CompletablePromiseContext () {
    scheduler = Executors.newSingleThreadScheduledExecutor();
  }

  void schedule (Runnable runnable) {
    scheduler.schedule(runnable, 1, MILLISECONDS);
  }

  private static class LazyHolder {

    static final CompletablePromiseContext INSTANCE = new CompletablePromiseContext();
  }
}
