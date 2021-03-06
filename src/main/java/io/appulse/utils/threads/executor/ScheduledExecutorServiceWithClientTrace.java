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

package io.appulse.utils.threads.executor;

import java.util.concurrent.ScheduledExecutorService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * The {@link ScheduledExecutorService}'s wrapper for client's stack trace.
 *
 * @since 1.5.0
 * @author Artem Labazin
 */
@Slf4j
@SuppressWarnings("PMD.DoNotUseThreads")
public final class ScheduledExecutorServiceWithClientTrace extends ScheduledExecutorServiceWrapper {

  public ScheduledExecutorServiceWithClientTrace(ScheduledExecutorService delegate) {
    super(delegate);
  }

  @Override
  public void execute (@NonNull Runnable command) {
    val clientStack = new Exception("Client stack trace");
    val threadName = Thread.currentThread().getName();

    super.execute(() -> {
      try {
        command.run();
      } catch (Exception ex) {
        log.error("Exception {} in task submitted from thread {} here:",
                  ex, threadName, clientStack);
        throw ex;
      }
    });
  }
}
