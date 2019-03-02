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

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
class AppulseExecutorsTests {

  @Test
  void cachedWithQueueThreadPool () {
    val queue = new LinkedBlockingQueue<Runnable>(3);

    val executor = (ThreadPoolExecutor) AppulseExecutors.newCachedThreadPool()
        .corePoolSize(4)
        .maxPoolSize(4)
        .keepAliveTime(100L)
        .unit(MILLISECONDS)
        .queue(queue)
        .build();

    val scheduled = AppulseExecutors.newScheduledThreadPool().build();
    scheduled.scheduleAtFixedRate(
        () -> {
          log.info("queue - {}, threads - {}", queue.size(), executor.getActiveCount());
        },
        0, 100, MILLISECONDS
    );

    val futures = IntStream.range(0, 7)
        .mapToObj(_i -> new MyRunnable())
        .map(it -> {
          val future = CompletableFuture.runAsync(it, executor);
          try {
            MILLISECONDS.sleep(50);
          } catch (InterruptedException ex) { }
          return future;
        })
        .toArray(CompletableFuture<?>[]::new);

    CompletableFuture.allOf(futures).join();
    scheduled.shutdown();
    executor.shutdown();
  }

  @Slf4j
  static class MyRunnable implements Runnable {

    @Override
    @SneakyThrows
    public void run () {
      log.info("hello");
      MILLISECONDS.sleep(100L);
    }
  }
}
