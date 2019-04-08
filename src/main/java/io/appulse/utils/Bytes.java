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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * This interface provides an abstract view for a primitive byte
 * arrays ({@code byte[]}).
 *
 * @author Artem Labazin
 * @since 1.12.0
 */
@SuppressWarnings({
    "PMD.ExcessiveClassLength",
    "PMD.LinguisticNaming"
})
public interface Bytes {

  /**
   * Wraps a byte array into a new {@link Bytes} object.
   * <p>
   * Modifications to this array's content will cause the wrapped
   * buffer's content to be modified, and vice versa.
   *
   * @param bytes the value to wrap
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes wrap (byte[] bytes) {
    return new BytesFixedArray(bytes);
  }

  /**
   * Wraps a {@link ByteBuffer} buffer into a new {@link Bytes} object.
   * <p>
   * Modifications to this buffer's content will cause the wrapped
   * buffer's content to be modified, and vice versa.
   *
   * @param buffer the value to wrap
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes wrap (ByteBuffer buffer) {
    return new BytesByteBuffer(buffer);
  }

  /**
   * Copies a byte array into a new {@link Bytes} object.
   * <p>
   * Modifications to this array's content will not cause the wrapped
   * buffer's content to be modified, and vice versa.
   *
   * @param bytes the value to copy
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes copy (byte[] bytes) {
    return BytesFixedArray.copy(bytes);
  }

  /**
   * Copies a {@link ByteBuffer} buffer into a new {@link Bytes} object.
   * <p>
   * Modifications to this buffer's content will not cause the wrapped
   * buffer's content to be modified, and vice versa.
   *
   * @param buffer the value to copy
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes copy (ByteBuffer buffer) {
    return BytesByteBuffer.copy(buffer);
  }

  /**
   * Create a new {@link Bytes} instance with a fixed size content
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes allocate (int size) {
    return new BytesFixedArray(size);
  }

  /**
   * Creates a new {@link Bytes} instance with a resizable content.
   *
   * @return the new {@link Bytes} instance
   */
  static Bytes resizableArray () {
    return new BytesExtendableArray();
  }

  /**
   * Creates a new {@link Bytes} instance with a resizable content.
   *
   * @param initialSize the initial buffer's size
   *
   * @return the new {@link Bytes} instance
   *
   * @since 1.13.0
   */
  static Bytes resizableArray (int initialSize) {
    return new BytesExtendableArray(initialSize);
  }

  /**
   * Tells if the buffer is resizable or not.
   *
   * @return {@code true} if the buffer could be extandable
   *         {@code false} otherwise
   */
  boolean isResizable ();

  /**
   * Sets the specified byte array at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code bytes.length}
   * in this buffer.
   *
   * @param bytes the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code bytes.length}
   */
  Bytes writeNB (byte[] bytes);

  /**
   * Sets the specified byte array at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code bytes.length}-{@code offset}
   * in this buffer.
   *
   * @param bytes the value to write
   *
   * @param offset the first index of the bytes
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than
   *         {@code bytes.length}-{@code offset}
   */
  Bytes writeNB (byte[] bytes, int offset);

  /**
   * Sets the specified byte array at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code length}
   * in this buffer.
   *
   * @param bytes the value to write
   *
   * @param offset the first index of the bytes
   *
   * @param length the number of bytes to read
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code length}
   */
  Bytes writeNB (byte[] bytes, int offset, int length);

  /**
   * Sets the specified string at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code string.length()}
   * in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code bytes.length()}
   */
  Bytes writeNB (String value);

  /**
   * Sets the specified string at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code string.length()}
   * in this buffer.
   *
   * @param value the value to write
   *
   * @param charset the string's charset
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code bytes.length()}
   */
  Bytes writeNB (String value, Charset charset);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (byte value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (short value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (int value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (long value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (float value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (double value);

  /**
   * Sets the specified byte at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 1} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 1}
   */
  Bytes write1B (char value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (byte value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (short value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (int value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (long value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (float value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (double value);

  /**
   * Sets the specified short at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 2} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 2}
   */
  Bytes write2B (char value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (byte value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (short value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (int value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (long value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (float value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (double value);

  /**
   * Sets the specified int at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 4} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 4}
   */
  Bytes write4B (char value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (byte value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (short value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (int value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (long value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (float value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (double value);

  /**
   * Sets the specified long at the current {@code writerIndex}
   * and increases the {@code writerIndex} by {@code 8} in this buffer.
   *
   * @param value the value to write
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.writableBytes} is less than {@code 8}
   */
  Bytes write8B (char value);

  /**
   * Sets the specified byte array at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param bytes the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes setNB (int index, byte[] bytes);

  /**
   * Sets the specified byte array at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param bytes the value for set
   *
   * @param offset the first index of the value
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes setNB (int index, byte[] bytes, int offset);

  /**
   * Sets the specified byte array at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param bytes the value for set
   *
   * @param offset the first index of the value
   *
   * @param length the number of bytes to set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes setNB (int index, byte[] bytes, int offset, int length);

  /**
   * Sets the specified string at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes setNB (int index, String value);

  /**
   * Sets the specified string at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @param charset the string's charset
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes setNB (int index, String value, Charset charset);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, byte value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, short value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, int value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, long value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, float value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, double value);

  /**
   * Sets the specified byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  Bytes set1B (int index, char value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, byte value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, short value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, int value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, long value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, float value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, double value);

  /**
   * Sets the specified 2 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  Bytes set2B (int index, char value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, byte value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, short value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, int value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, long value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, float value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, double value);

  /**
   * Sets the specified 4 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  Bytes set4B (int index, char value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, byte value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, short value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, int value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, long value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, float value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, double value);

  /**
   * Sets the specified 8 byte value at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index to which the byte will be set
   *
   * @param value the value for set
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  Bytes set8B (int index, char value);

  /**
   * Gets a byte at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 1} in this buffer.
   *
   * @return the byte value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 1}
   */
  byte readByte ();

  /**
   * Gets an unsigned byte at the current {@code readerIndex} and
   * increases the {@code readerIndex} by {@code 1} in this buffer.
   *
   * @return the unsigned byte value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 1}
   */
  short readUnsignedByte ();

  /**
   * Gets a short at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 2} in this buffer.
   *
   * @return the short value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 2}
   */
  short readShort ();

  /**
   * Gets an unsigned short at the current {@code readerIndex} and
   * increases the {@code readerIndex} by {@code 2} in this buffer.
   *
   * @return the unsigned short value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 2}
   */
  int readUnsignedShort ();

  /**
   * Gets a int at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 4} in this buffer.
   *
   * @return the int value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 4}
   */
  int readInt ();

  /**
   * Gets an unsigned int at the current {@code readerIndex} and
   * increases the {@code readerIndex} by {@code 4} in this buffer.
   *
   * @return the unsigned int value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 4}
   */
  long readUnsignedInt ();

  /**
   * Gets a long at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 8} in this buffer.
   *
   * @return the long value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 8}
   */
  long readLong ();

  /**
   * Gets an unsigned long at the current {@code readerIndex} and
   * increases the {@code readerIndex} by {@code 8} in this buffer.
   *
   * @return the unsigned long value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 8}
   */
  BigInteger readUnsignedLong ();

  /**
   * Gets a float at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 4} in this buffer.
   *
   * @return the float value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 4}
   */
  float readFloat ();

  /**
   * Gets a double at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 8} in this buffer.
   *
   * @return the double value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 8}
   */
  double readDouble ();

  /**
   * Gets a char at the current {@code readerIndex} and increases
   * the {@code readerIndex} by {@code 2} in this buffer.
   *
   * @return the char value
   *
   * @throws IndexOutOfBoundsException
   *         if {@code this.readableBytes} is less than {@code 2}
   */
  char readChar ();

  /**
   * Reads this buffer's data to a newly created byte array starting at
   * the current {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the readed bytes (= {@code dst.length}).
   *
   * @return the readed byte array
   */
  byte[] readBytes ();

  /**
   * Reads this buffer's data to a newly created byte array starting at
   * the current {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the transferred bytes (= {@code length}).
   *
   * @param length the number of bytes to transfer
   *
   * @return the readed byte array
   *
   * @throws IndexOutOfBoundsException
   *         if {@code length} is greater than {@code this.readableBytes}
   */
  byte[] readBytes (int length);

  /**
   * Reads this buffer's data to the specified destination starting at
   * the current {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the readed bytes (= {@code destination.length}).
   *
   * @param destination the byte array to read into
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if {@code destination.length} is greater than {@code this.readableBytes}
   */
  Bytes readBytes (byte[] destination);

  /**
   * Reads this buffer's data to the specified destination starting at
   * the current {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the readed bytes (= {@code length}).
   *
   * @param destination the byte array to read into
   *
   * @param offset the first index of the destination
   *
   * @param length the number of bytes to read
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code offset} is less than {@code 0},
   *         if {@code length} is greater than {@code this.readableBytes}, or
   *         if {@code offset + length} is greater than {@code destination.length}
   */
  Bytes readBytes (byte[] destination, int offset, int length);

  /**
   * Reads this buffer's data to a string starting at the current
   * {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the readed bytes (= {@code dst.length}).
   *
   * @return the readed string
   */
  String readString ();

  /**
   * Reads this buffer's data to a string starting at the current
   * {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the readed bytes (= {@code dst.length}).
   *
   * @param charset the string's charset
   *
   * @return the readed string
   */
  String readString (Charset charset);

  /**
   * Reads this buffer's data to a string starting at the current
   * {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the transferred bytes (= {@code length}).
   *
   * @param length the number of bytes to transfer
   *
   * @return the readed string
   *
   * @throws IndexOutOfBoundsException
   *         if {@code length} is greater than {@code this.readableBytes}
   */
  String readString (int length);

  /**
   * Reads this buffer's data to a string starting at the current
   * {@code readerIndex} and increases the {@code readerIndex}
   * by the number of the transferred bytes (= {@code length}).
   *
   * @param length the number of bytes to transfer
   *
   * @param charset the string's charset
   *
   * @return the readed string
   *
   * @throws IndexOutOfBoundsException
   *         if {@code length} is greater than {@code this.readableBytes}
   */
  String readString (int length, Charset charset);

  /**
   * Gets a byte at the specified absolute {@code index} in this buffer.
   * This method does not modify {@code readerIndex} or {@code writerIndex} of
   * this buffer.
   *
   * @param index the index from which the byte will be read
   *
   * @return the byte at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  byte getByte (int index);

  /**
   * Gets an unsigned byte at the specified absolute {@code index} in this
   * buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the unsigned byte will be read
   *
   * @return the unsigned byte at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 1} is greater than {@code this.capacity}
   */
  short getUnsignedByte (int index);

  /**
   * Gets a 16-bit short integer at the specified absolute {@code index} in
   * this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the short will be read
   *
   * @return the short at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  short getShort (int index);

  /**
   * Gets an unsigned 16-bit short integer at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the unsigned short will be read
   *
   * @return the unsigned short at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  int getUnsignedShort (int index);

  /**
   * Gets a 32-bit integer at the specified absolute {@code index} in
   * this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the int will be read
   *
   * @return the int at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  int getInt (int index);

  /**
   * Gets an unsigned 32-bit integer at the specified absolute {@code index}
   * in this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the unsigned int will be read
   *
   * @return the unsigned int at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  long getUnsignedInt (int index);

  /**
   * Gets a 64-bit long integer at the specified absolute {@code index} in
   * this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the long will be read
   *
   * @return the long at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  long getLong (int index);

  /**
   * Gets a unsigned 64-bit long integer at the specified absolute {@code index} in
   * this buffer. This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the unsigned long will be read
   *
   * @return the unsigned long at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  BigInteger getUnsignedLong (int index);

  /**
   * Gets a 32-bit floating point number at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the float will be read
   *
   * @return the float at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 4} is greater than {@code this.capacity}
   */
  float getFloat (int index);

  /**
   * Gets a 64-bit floating point number at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the double will be read
   *
   * @return the double at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 8} is greater than {@code this.capacity}
   */
  double getDouble (int index);

  /**
   * Gets a 2-byte UTF-16 character at the specified absolute
   * {@code index} in this buffer. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the char will be read
   *
   * @return the char at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  char getChar (int index);

  /**
   * Returns a byte array from the specified absolute {@code index}
   * in this buffer till the end of it. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the byte array will be read
   *
   * @return the byte array at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  byte[] getBytes (int index);

  /**
   * Returns a byte array from the specified absolute {@code index}
   * in this buffer till the {@code index}+{@code length} index.
   * This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the byte array will be read
   *
   * @param length the number of bytes to returns
   *
   * @return the byte array at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  byte[] getBytes (int index, int length);

  /**
   * Returns a string from the specified absolute {@code index}
   * in this buffer till the end of it. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the string will be read
   *
   * @return the string at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  String getString (int index);

  /**
   * Returns a string from the specified absolute {@code index}
   * in this buffer till the end of it. This method does not modify
   * {@code readerIndex} or {@code writerIndex} of this buffer.
   *
   * @param index the index from which the string will be read
   *
   * @param charset the string's charset
   *
   * @return the string at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  String getString (int index, Charset charset);

  /**
   * Returns a string from the specified absolute {@code index}
   * in this buffer till the {@code index}+{@code length} index.
   * This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the string will be read
   *
   * @param length the number of bytes to returns
   *
   * @return the string at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  String getString (int index, int length);

  /**
   * Returns a string from the specified absolute {@code index}
   * in this buffer till the {@code index}+{@code length} index.
   * This method does not modify {@code readerIndex} or
   * {@code writerIndex} of this buffer.
   *
   * @param index the index from which the string will be read
   *
   * @param length the number of bytes to returns
   *
   * @param charset the string's charset
   *
   * @return the string at the given index
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code index} is less than {@code 0} or
   *         {@code index + 2} is greater than {@code this.capacity}
   */
  String getString (int index, int length, Charset charset);

  /**
   * Returns the number of bytes (octets) this buffer can contain.
   */
  int capacity ();

  /**
   * Extends or reduces the buffer's capacity.
   *
   * @param bytes the new buffer's capacity
   */
  void capacity (int bytes);

  /**
   * Returns the {@code writerIndex} of this buffer.
   *
   * @return {@code writerIndex}
   */
  int writerIndex ();

  /**
   * Sets the {@code writerIndex} of this buffer.
   *
   * @param newIndex a new index value
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code writerIndex} is
   *            less than {@code this.readerIndex} or
   *            greater than {@code this.capacity}
   */
  Bytes writerIndex (int newIndex);

  /**
   * Returns the number of writable bytes which is equal to
   * {@code (this.capacity - this.writerIndex)}.
   *
   * @return the number of writable bytes
   */
  int writableBytes ();

  /**
   * Returns {@code true} if and only if {@code (this.capacity - this.writerIndex)}
   * is greater than {@code 0}.
   *
   * @return {@code true} if it is possible to write more bytes,
   *         {@code false} otherwise
   */
  boolean isWritable ();

  /**
   * Returns {@code true} if and only if this buffer has enough room to allow writing
   * the specified number of elements.
   *
   * @param size the specified number bytes need to write
   *
   * @return {@code true} if it is possible to write the specified number of bytes,
   *         {@code false} otherwise
   */
  boolean isWritable (int size);

  /**
   * Returns the {@code readerIndex} of this buffer.
   *
   * @return {@code readerIndex}
   */
  int readerIndex ();

  /**
   * Sets the {@code readerIndex} of this buffer.
   *
   * @param newIndex a new index value
   *
   * @return {@code this} object for chaining calls
   *
   * @throws IndexOutOfBoundsException
   *         if the specified {@code readerIndex} is
   *            less than {@code 0} or
   *            greater than {@code this.writerIndex}
   */
  Bytes readerIndex (int newIndex);

  /**
   * Returns the number of readable bytes which is equal to
   * {@code (this.writerIndex - this.readerIndex)}.
   *
   * @return the number of readable bytes
   */
  int readableBytes ();

  /**
   * Returns {@code true} if and only if {@code (this.writerIndex - this.readerIndex)}
   * is greater than {@code 0}.
   *
   * @return {@code true} if it is possible to read more,
   *         {@code false} otherwise
   */
  boolean isReadable ();

  /**
   * Returns {@code true} if and only if this buffer contains equal to or more than
   * the specified number of elements.
   *
   * @param size the specified number bytes need to read
   *
   * @return {@code true} if it is possible to read the specified number of bytes,
   *         {@code false} otherwise
   */
  boolean isReadable (int size);

  /**
   * Sets the {@code readerIndex} and {@code writerIndex} of this buffer to
   * {@code 0}.
   *
   * @return {@code this} object for chaining calls
   */
  Bytes reset ();

  /**
   * Returns the byte array that backs this buffer.
   * <p>
   * Modifications to this buffer's content may cause the
   * returned array's content to be modified, and vice versa.
   *
   * @return the array that backs this buffer
   */
  byte[] array ();

  /**
   * Returns the copy of range the buffer's back byte array from
   * <b>0</b> till <b>writeIndex</t>.
   * <p>
   * Modifications to this buffer's content may NOT cause the
   * returned array's content to be modified, and vice versa.
   *
   * @return the copy of the array that backs this buffer
   */
  byte[] arrayCopy ();
}
