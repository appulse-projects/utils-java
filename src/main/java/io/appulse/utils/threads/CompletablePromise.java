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

import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * {@link Future} object wrapper to {@link CompletableFuture}.
 *
 * @param <T> the promis's generic type.
 *
 * @since 1.11.1
 * @author Artem Labazin
 */
@FieldDefaults(level = PRIVATE)
public class CompletablePromise<T> extends CompletableFuture<T> {

  Future<T> future;

  public CompletablePromise (@NonNull Future<T> future) {
    super();
    this.future = future;
    CompletablePromiseContext.getInstance().schedule(this::tryToComplete);
  }

  private void tryToComplete () {
    if (future.isDone()) {
      try {
        complete(future.get());
      } catch (InterruptedException e) {
        completeExceptionally(e);
      } catch (ExecutionException e) {
        completeExceptionally(e.getCause());
      }
      return;
    }

    if (future.isCancelled()) {
      cancel(true);
      return;
    }

    CompletablePromiseContext.getInstance().schedule(this::tryToComplete);
  }
}
