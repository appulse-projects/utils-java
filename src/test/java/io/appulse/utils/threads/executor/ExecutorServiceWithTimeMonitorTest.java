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

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import io.appulse.utils.test.TestAppender;
import io.appulse.utils.threads.AppulseExecutors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExecutorServiceWithTimeMonitorTest {

  @BeforeEach
  void before () {
    TestAppender.EVENTS.clear();
  }

  @Test
  void monitor () throws Exception {
    ExecutorService service = AppulseExecutors.newSingleThreadExecutor()
        .enableTimeLogging()
        .build();

    service.execute(() -> {
      try {
        SECONDS.sleep(3);
      } catch (Exception ex) {
      }
    });

    Future<?> future = service.submit(() -> {
      try {
        SECONDS.sleep(1);
      } catch (Exception ex) {
      }
    });

    future.get(5, SECONDS);

    service.shutdown();
    service.awaitTermination(3, SECONDS);

    val logs = TestAppender.EVENTS.stream()
        .map(ILoggingEvent::getFormattedMessage)
        .collect(joining("\n"));

    assertThat(logs).containsPattern("Task \\S+ spent \\d+ms in queue");
    assertThat(logs).containsPattern("Task \\S+ end after \\d+ms");
  }
}
