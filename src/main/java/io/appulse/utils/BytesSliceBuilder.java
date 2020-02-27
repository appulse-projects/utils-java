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

import static java.util.Locale.ENGLISH;
import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;

import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * The builder for creating a {@link Bytes} slice - a part of the {@link Bytes}.
 *
 * @since 1.18.0
 * @author Artem Labazin
 */
@FieldDefaults(level = PRIVATE)
public class BytesSliceBuilder {

  final Bytes original;

  int from;

  int to;

  BytesSliceBuilder (Bytes bytes) {
    original = bytes;
    to = bytes.capacity();
  }

  /**
   * Sets an index <b>from</b>, the beginning of the slice.
   *
   * @param index the <b>from</b> index for a slice.
   *
   * @return {@code this} object for chaining calls
   */
  public BytesSliceBuilder from (int index) {
    from = index;
    return this;
  }

  /**
   * Sets an index <b>to</b>, the ending of the slice.
   *
   * @param index the <b>to</b> index for a slice.
   *
   * @return {@code this} object for chaining calls
   */
  public BytesSliceBuilder to (int index) {
    to = index;
    return this;
  }

  /**
   * Sets a length of the slice. An index <b>to</b> equals <b>from</b>+<b>length</b>.
   *
   * @param length the length of the slice.
   *
   * @return {@code this} object for chaining calls
   */
  public BytesSliceBuilder length (int length) {
    to = from + length;
    return this;
  }

  /**
   * Creates a byte array slice from the builder.
   *
   * @return the byte array slice
   */
  public byte[] toByteArray () {
    verify();
    return Arrays.copyOfRange(original.array(), from, to);
  }

  /**
   * Creates a {@link Bytes} slice from the builder,
   * which <b>can</b> affects the original {@link Bytes}.
   *
   * @return the {@link Bytes} slice
   */
  public Bytes toMutableBytes () {
    verify();
    return new BytesSlice(original, from, to);
  }

  /**
   * Creates a {@link Bytes} slice from the builder,
   * which <b>can not</b> affects the original {@link Bytes}.
   *
   * @return the {@link Bytes} slice
   */
  public Bytes toReadOnlyBytes () {
    val result = toMutableBytes();
    return Bytes.readOnly(result);
  }

  /**
   * Creates a {@link Bytes} slice from the builder,
   * which is detached from the original {@link Bytes}.
   *
   * @return the {@link Bytes} slice
   */
  public Bytes toNewBytes () {
    val bytes = toByteArray();
    return Bytes.wrap(bytes);
  }

  private void verify () {
    if (from < 0) {
      val msg = "'from' index must be greater or equal to 0";
      throw new IndexOutOfBoundsException(msg);
    } else if (to > original.capacity()) {
      val msg = String.format(ENGLISH,
          "byte array has length %d, but 'to' index was set to %d",
          original.capacity(), to
      );
      throw new IndexOutOfBoundsException(msg);
    } else if (from > to) {
      val msg = String.format(ENGLISH,
          "value 'from' (%d) must be less or equal 'to' (%d)",
          from, to
      );
      throw new IllegalArgumentException(msg);
    }
  }
}
