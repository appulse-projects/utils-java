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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.val;

/**
 * Different future helpers.
 *
 * @author Artem Labazin
 * @since 1.11.0
 */
public final class FutureUtils {

  /**
   * Creates {@link CompletableFuture} instance and completes it with specified exception.
   *
   * @param throwable the exception
   *
   * @return completed exceptionally {@link CompletableFuture} instance.
   */
  public static <T> CompletableFuture<T> completedExceptionally (Throwable throwable) {
    val future = new CompletableFuture<T>();
    future.completeExceptionally(throwable);
    return future;
  }

  /**
   * Returns first completed future, which didn't throw an exception.
   *
   * @param futures array of futures.
   *
   * @return first non-exceptional completed future.
   */
  public static <T> CompletableFuture<T> firstCompletedWithoutException (@NonNull CompletionStage<? extends T>... futures) {
    val stream = Stream.of(futures);
    return firstCompletedWithoutException(stream);
  }

  /**
   * Returns first completed future, which didn't throw an exception.
   *
   * @param futures list of futures.
   *
   * @return first non-exceptional completed future.
   */
  public static <T> CompletableFuture<T> firstCompletedWithoutException (@NonNull List<? extends CompletionStage<? extends T>> futures) {
    val stream = futures.stream();
    return firstCompletedWithoutException(stream);
  }

  // https://stackoverflow.com/a/34163913
  private static <T> CompletableFuture<T> firstCompletedWithoutException (Stream<? extends CompletionStage<? extends T>> stream) {
    val result = new CompletableFuture<T>();
    val complete = (Consumer<T>) result::complete;

    val futures = stream
        .map(it -> it.thenAccept(complete))
        .toArray(CompletableFuture<?>[]::new);

    CompletableFuture.allOf(futures)
        .exceptionally(ex -> {
          result.completeExceptionally(ex);
          return null;
        });

    return result;
  }

  private FutureUtils () {
    throw new UnsupportedOperationException();
  }
}
