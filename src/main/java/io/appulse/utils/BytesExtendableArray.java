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

  BytesExtendableArray () {
    super(16);
  }

  @Override
  public boolean isExtendable () {
    return true;
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    if (newIndex < readerIndex()) {
      throw new IndexOutOfBoundsException();
    }
    while (newIndex > capacity()) {
      extendBuffer();
    }
    writerIndex = newIndex;
    return this;
  }

  @Override
  public byte[] array () {
    return Arrays.copyOfRange(buffer, 0, writerIndex);
  }

  @Override
  protected void checkWriteBounds (int index, int length) {
    if (index < readerIndex()) {
      throw new IndexOutOfBoundsException();
    }
    if (index + length > capacity()) {
      extendBuffer();
    }
  }

  private void extendBuffer () {
    val newBuffer = new byte[capacity() * 2];
    System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
    buffer = newBuffer;
  }
}
