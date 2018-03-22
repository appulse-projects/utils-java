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

package io.appulse.utils.threads;

import static java.lang.Thread.NORM_PRIORITY;
import static java.util.Locale.ROOT;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.5.0
 */
@SuppressWarnings("PMD.DoNotUseThreads")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class AppulseThreadFactory implements ThreadFactory {

  ThreadFactory parent;

  int priority;

  Optional<String> name;

  AtomicLong threadCount;

  Optional<Boolean> daemon;

  Optional<UncaughtExceptionHandler> uncaughtExceptionHandler;

  @Builder
  public AppulseThreadFactory (String name, Boolean daemon, Integer priority, ThreadFactory parent,
                               UncaughtExceptionHandler uncaughtExceptionHandler
  ) {
    this.name = ofNullable(name);
    threadCount = this.name
        .filter(it -> it.contains("%d"))
        .map(it -> new AtomicLong(1))
        .orElse(null);

    this.daemon = ofNullable(daemon);
    this.uncaughtExceptionHandler = ofNullable(uncaughtExceptionHandler);

    this.priority = ofNullable(priority)
        .orElse(NORM_PRIORITY);

    this.parent = ofNullable(parent)
        .orElse(Executors.defaultThreadFactory());
  }

  @Override
  public Thread newThread (@NonNull Runnable runnable) {
    val thread = parent.newThread(runnable);
    thread.setPriority(priority);

    name.map(it -> ofNullable(threadCount)
        .map(counter -> String.format(ROOT, it, counter.getAndIncrement()))
        .orElse(it)
    )
        .ifPresent(thread::setName);

    daemon.ifPresent(thread::setDaemon);
    uncaughtExceptionHandler.ifPresent(thread::setUncaughtExceptionHandler);
    return thread;
  }
}
