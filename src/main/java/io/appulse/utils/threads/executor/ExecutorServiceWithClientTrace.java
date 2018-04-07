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
public final class ExecutorServiceWithClientTrace extends ExecutorServiceWrapper {

  private static final String EXCEPTION_SEPARATOR = "------ submitted from: ------";

  public ExecutorServiceWithClientTrace (ExecutorService delegate) {
    super(delegate);
  }

  @Override
  public void execute (@NonNull Runnable command) {
    Exception clientStack = new Exception("Client stack trace");
    val threadName = Thread.currentThread().getName();

    super.execute(() -> {
      try {
        command.run();
      } catch (Exception ex) {
        log.error("Exception during task execution submitted from thread '{}'",
                  threadName, merge(clientStack, ex));
      }
    });
  }

  private Throwable merge (Exception local, Exception remote) {
    Throwable result = remote;

    val remoteStackTrace = remote.getStackTrace();
    val localStackTrace = local.getStackTrace();

    val newStackTrace = new StackTraceElement[localStackTrace.length + remoteStackTrace.length];
    System.arraycopy(remoteStackTrace, 0, newStackTrace, 0, remoteStackTrace.length);

    newStackTrace[remoteStackTrace.length] = new StackTraceElement(
        EXCEPTION_SEPARATOR,
        "",
        "",
        -1
    );
    System.arraycopy(localStackTrace, 1, newStackTrace, remoteStackTrace.length + 1, localStackTrace.length - 1);

    result.setStackTrace(newStackTrace);
    return result;
  }
}
