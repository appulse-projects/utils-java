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

package io.appulse.utils;

import java.util.LinkedList;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Just a regular {@link LinkedList}, but with limited capacity.
 *
 * @param <T> the list's element type.
 *
 * @since 1.15.0
 * @author Artem Labazin
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class LimitedQueue<T> extends LinkedList<T> {

  private static final long serialVersionUID = -5465432595539481983L;

  long capacity;

  @Override
  public boolean add (T value) {
    if (size() >= capacity) {
      return false;
    }
    return super.add(value);
  }

  /**
   * Tells if the list is full or not.
   *
   * @return {@code true} if maximum size reached, {@code false} otherwise
   */
  public boolean isFull () {
    return size() < capacity;
  }
}
