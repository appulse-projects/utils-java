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

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.io.OutputStream;

import lombok.experimental.FieldDefaults;

/**
 * This class implements an output stream in which the data is
 * written into a Bytes. The buffer automatically grows as data
 * is written to it.
 * The data can be retrieved using <code>toByteArray()</code> and
 * <code>array()</code>.
 * <p>
 * Closing a <tt>BytesOutputStream</tt> has no effect. The methods in
 * this class can be called after the stream has been closed without
 * generating an <tt>IOException</tt>.
 *
 * @since 1.17.0
 * @author Artem Labazin
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BytesOutputStream extends OutputStream {

  Bytes buffer;

  /**
   * Creates a new byte array output stream. The buffer capacity is
   * initially 32 bytes, though its size increases if necessary.
   */
  public BytesOutputStream () {
    this(32);
  }

  /**
   * Creates a new byte array output stream, with a buffer capacity of
   * the specified size, in bytes.
   *
   * @param size the initial size.
   * @exception  IllegalArgumentException if size is negative.
   */
  public BytesOutputStream (int size) {
    super();
    if (size < 0) {
      throw new IllegalArgumentException("Negative initial size: " + size);
    }
    buffer = Bytes.resizableArray(size);
  }

  /**
   * The constructor wraps already existent {@link Bytes}.
   *
   * @param bytes the bytes buffer to wraps.
   */
  public BytesOutputStream (Bytes bytes) {
    super();
    buffer = bytes;
  }

  @Override
  public synchronized void write (int value) throws IOException {
    buffer.write1B(value);
  }

  @Override
  public synchronized void write (byte[] bytes, int offset, int length) {
    buffer.writeNB(bytes, offset, length);
  }

  /**
   * Resets the byte array output stream to zero, so that all currently
   * accumulated output in the output stream is discarded. The output
   * stream can be used again, reusing the already allocated buffer space.
   */
  public synchronized void reset () {
    buffer.reset();
  }

  /**
   * Returns the current size of the buffer.
   *
   * @return the number of valid bytes in this output stream.
   */
  public synchronized int size () {
    return buffer.writerIndex();
  }

  /**
   * Creates a newly allocated byte array. Its size is the current
   * size of this output stream and the valid contents of the buffer
   * have been copied into it.
   *
   * @return  the current contents of this output stream, as a byte array.
   * @see     java.io.ByteArrayOutputStream#size()
   */
  public synchronized byte[] toByteArray () {
    return buffer.arrayCopy();
  }

  /**
   * Returns the byte array that backs this buffer.
   * <p>
   * Modifications to this buffer's content may cause the
   * returned array's content to be modified, and vice versa.
   *
   * @return the array that backs this buffer
   */
  public byte[] array () {
    return buffer.array();
  }

  @Override
  public void close () {
    // no op
  }
}
