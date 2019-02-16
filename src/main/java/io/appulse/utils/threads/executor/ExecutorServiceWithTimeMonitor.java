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

import java.util.concurrent.ExecutorService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@Slf4j
@SuppressWarnings("PMD.DoNotUseThreads")
public final class ExecutorServiceWithTimeMonitor extends ExecutorServiceWrapper {

  public ExecutorServiceWithTimeMonitor (ExecutorService delegate) {
    super(delegate);
  }

  @Override
  public void execute (@NonNull Runnable command) {
    val submitTime = System.currentTimeMillis();
    super.execute(() -> {
      long startTime = System.currentTimeMillis();
      long queueDuration = startTime - submitTime;
      log.debug("Task {} spent {}ms in queue", command, queueDuration);
      try {
        command.run();
      } finally {
        long endTime = System.currentTimeMillis() - startTime;
        log.debug("Task {} end after {}ms", command, endTime);
      }
    });
  }
}
