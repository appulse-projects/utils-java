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

package io.appulse.utils;

import static lombok.AccessLevel.PRIVATE;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * Round robin collection wrapper.
 *
 * @author Artem Labazin
 * @since 1.8.0
 */
@FieldDefaults(level = PRIVATE)
public final class RoundRobin<T> {

  final AtomicInteger position = new AtomicInteger(0);

  final T[] elements;

  volatile int size;

  @SuppressWarnings("unchecked")
  public RoundRobin (@NonNull T... elements) {
    if (elements.length == 0) {
      throw new IllegalArgumentException("Elements array is empty");
    }
    this.elements = elements;
    size = elements.length;
  }

  @SuppressWarnings("unchecked")
  public RoundRobin (@NonNull Collection<T> collection) {
    if (collection.isEmpty()) {
      throw new IllegalArgumentException("Elements collection is empty");
    }
    size = collection.size();

    Class<?> elementType = collection.iterator()
        .next()
        .getClass();

    this.elements = (T[]) Array.newInstance(elementType, size);

    Iterator<T> iterator = collection.iterator();
    int counter = 0;
    while (iterator.hasNext()) {
      elements[counter] = iterator.next();
      counter++;
    }
  }

  public T getNext () {
    int index = getNextIndex();
    return elements[index];
  }

  private int getNextIndex () {
    while (true) {
      int current = position.get();
      int next = current + 1;
      if (next >= size) {
        next = 0;
      }
      if (position.compareAndSet(current, next)) {
        return current;
      }
    }
  }
}
