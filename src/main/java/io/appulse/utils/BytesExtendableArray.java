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

package io.appulse.utils;

import java.util.Arrays;

import lombok.val;

class BytesExtendableArray extends BytesFixedArray {

  /**
   * The maximum size of array to allocate.
   * Some VMs reserve some header words in an array.
   * Attempts to allocate larger arrays may result in
   * OutOfMemoryError: Requested array size exceeds VM limit
   */
  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  BytesExtendableArray () {
    this(16);
  }

  BytesExtendableArray (int initialSize) {
    super(initialSize);
  }

  @Override
  public boolean isResizable () {
    return true;
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    if (newIndex < readerIndex()) {
      throw new IndexOutOfBoundsException();
    }
    checkWriteBounds(newIndex, 1);
    writerIndex = newIndex;
    return this;
  }

  @Override
  protected void checkWriteBounds (int index, int length) {
    // if (index < readerIndex()) {
    //   throw new IndexOutOfBoundsException();
    // }
    val currentCapacity = capacity();
    val neededCapacity = index + length;
    if (currentCapacity >= neededCapacity) {
      return;
    }

    int newCapacity = currentCapacity * 2;
    if (newCapacity - neededCapacity < 0) {
      newCapacity = neededCapacity;
    }
    if (newCapacity - MAX_ARRAY_SIZE > 0) {
      newCapacity = neededCapacity > MAX_ARRAY_SIZE
                    ? Integer.MAX_VALUE
                    : MAX_ARRAY_SIZE;
    }
    buffer = Arrays.copyOf(buffer, newCapacity);
  }
}
