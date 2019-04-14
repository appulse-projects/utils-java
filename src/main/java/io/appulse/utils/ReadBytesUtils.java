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

import static java.nio.file.StandardOpenOption.READ;
import static java.util.Locale.ENGLISH;

import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 * The helper methods for reading different streams/channels/files to buffers.
 *
 * @author Artem Labazin
 * @since 1.14.0
 */
public final class ReadBytesUtils {

  /**
   * Reads all bytes from the stream till the end of the stream.
   *
   * @param inputStream the input data stream
   *
   * @return readed bytes wrappend in {@link Bytes} instance.
   */
  @SneakyThrows
  public static Bytes read (@NonNull InputStream inputStream) {
    val buffer = Bytes.resizableArray(64);

    while (true) {
      val readed = inputStream.read(buffer.array(), buffer.writerIndex(), buffer.writableBytes());
      if (readed == -1) {
        break;
      }
      buffer.writerIndex(buffer.writerIndex() + readed);
      if (!buffer.isWritable()) {
        buffer.capacity(buffer.capacity() * 2);
      }
    }
    return buffer;
  }

  /**
   * Reads all bytes from the stream till the end of the stream or
   * requested length reached.
   *
   * @param inputStream the input data stream
   *
   * @param length the maximum number of bytes will be readed
   *
   * @return readed bytes wrappend in {@link Bytes} instance.
   */
  @SneakyThrows
  public static Bytes read (@NonNull InputStream inputStream, int length) {
    if (length < 0) {
      val msg = String.format(ENGLISH, "Invalid length %d. The length must be greater or equal 0", length);
      throw new IndexOutOfBoundsException(msg);
    }

    val buffer = Bytes.allocate(length);
    int offset = 0;
    int remaining = length;

    while (remaining > 0) {
      val readed = inputStream.read(buffer.array(), offset, remaining);
      if (readed == -1) {
        break;
      }
      offset += readed;
      remaining -= readed;
    }
    buffer.writerIndex(offset);
    return buffer;
  }

  /**
   * Reads all bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (InputStream inputStream, @NonNull byte[] buffer) {
    return read(inputStream, buffer, 0, buffer.length);
  }

  /**
   * Reads all bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (InputStream inputStream, @NonNull Bytes buffer) {
    return read(inputStream, buffer, buffer.writableBytes());
  }

  /**
   * Reads all bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (InputStream inputStream, @NonNull ByteBuffer buffer) {
    return read(inputStream, buffer, buffer.remaining());
  }

  /**
   * Reads the bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (InputStream inputStream, byte[] buffer, int length) {
    return read(inputStream, buffer, 0, length);
  }

  /**
   * Reads the bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull InputStream inputStream, @NonNull Bytes buffer, int length) {
    val readed = read(inputStream, buffer.array(), buffer.writerIndex(), length);
    buffer.writerIndex(buffer.writerIndex() + readed);
    return readed;
  }

  /**
   * Reads the bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (@NonNull InputStream inputStream, @NonNull ByteBuffer buffer, int length) {
    val readed = read(inputStream, buffer.array(), buffer.position(), length);
    buffer.position(buffer.position() + readed);
    return readed;
  }

  /**
   * Reads the bytes from the stream to the specified buffer.
   *
   * @param inputStream the input data stream
   *
   * @param bytes the buffer into which the data is read
   *
   * @param offset the start offset in the <code>buffer</code> at which the data is written
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull InputStream inputStream, @NonNull byte[] bytes, int offset, int length) {
    if (offset < 0 || offset >= bytes.length) {
      val msg = String.format(ENGLISH,
          "Invalid offset %d. The offset must be equal or greater than 0 and less than byte array length",
          offset
      );
      throw new IndexOutOfBoundsException(msg);
    }
    if (length < 0) {
      val msg = String.format(ENGLISH, "Invalid length %d. The length must be greater or equal 0", length);
      throw new IndexOutOfBoundsException(msg);
    }

    int position = offset;
    int remaining = Math.min(bytes.length - offset, length);
    while (remaining > 0) {
      val readed = inputStream.read(bytes, position, remaining);
      if (readed < 0) {
        break;
      }
      position += readed;
      remaining -= readed;
    }
    return position - offset;
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (ReadableByteChannel channel, @NonNull byte[] buffer) {
    return read(channel, buffer, 0, buffer.length);
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (ReadableByteChannel channel, byte[] buffer, int length) {
    return read(channel, buffer, 0, length);
  }

  /**
   * Reads the bytes from the stream to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param bytes the buffer into which the data is read
   *
   * @param offset the start offset in the <code>buffer</code> at which the data is written
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull ReadableByteChannel channel, @NonNull byte[] bytes, int offset, int length) {
    if (offset < 0 || offset >= bytes.length) {
      val msg = String.format(ENGLISH,
          "Invalid offset %d. The offset must be equal or greater than 0 and less than byte array length",
          offset
      );
      throw new IndexOutOfBoundsException(msg);
    }
    if (length < 0) {
      val msg = String.format(ENGLISH, "Invalid length %d. The length must be greater or equal 0", length);
      throw new IndexOutOfBoundsException(msg);
    }

    val byteBuffer = ByteBuffer.wrap(bytes);
    byteBuffer.position(offset);
    val limit = Math.min(offset + length, bytes.length);
    byteBuffer.limit(limit);

    int totalReaded = 0;
    while (byteBuffer.hasRemaining()) {
      val readed = channel.read(byteBuffer);
      if (readed < 0) {
        break;
      }
      totalReaded += readed;
    }

    return totalReaded;
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (ReadableByteChannel channel, @NonNull ByteBuffer buffer) {
    return read(channel, buffer, buffer.remaining());
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull ReadableByteChannel channel, @NonNull ByteBuffer buffer, int length) {
    val readed = read(channel, buffer.array(), length);
    buffer.position(buffer.position() + readed);
    return readed;
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (ReadableByteChannel channel, @NonNull Bytes buffer) {
    return read(channel, buffer, buffer.writableBytes());
  }

  /**
   * Reads all bytes from the channel to the specified buffer.
   *
   * @param channel the input data channel
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull ReadableByteChannel channel, @NonNull Bytes buffer, int length) {
    val readed = read(channel, buffer.array(), buffer.writerIndex(), length);
    buffer.writerIndex(buffer.writerIndex() + readed);
    return readed;
  }

  /**
   * Reads all bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (File file, @NonNull byte[] buffer) {
    return read(file, buffer, 0, buffer.length);
  }

  /**
   * Reads all bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (Path path, @NonNull byte[] buffer) {
    return read(path, buffer, 0, buffer.length);
  }

  /**
   * Reads all bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (File file, byte[] buffer, int length) {
    return read(file, buffer, 0, length);
  }

  /**
   * Reads all bytes from the file to the specified buffer.
   *
   * @param path the path to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (Path path, byte[] buffer, int length) {
    return read(path, buffer, 0, length);
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param offset the start offset in the <code>buffer</code> at which the data is written
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (@NonNull File file, byte[] buffer, int offset, int length) {
    return read(file.toPath(), buffer, offset, length);
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param offset the start offset in the <code>buffer</code> at which the data is written
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull Path path, @NonNull byte[] buffer, int offset, int length) {
    try (val channel = Files.newByteChannel(path, READ)) {
      return read(channel, buffer, offset, length);
    }
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (File file, @NonNull Bytes buffer) {
    return read(file, buffer, buffer.writableBytes());
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (Path path, @NonNull Bytes buffer) {
    return read(path, buffer, buffer.writableBytes());
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull File file, Bytes buffer, int length) {
    return read(file.toPath(), buffer, length);
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull Path path, @NonNull Bytes buffer, int length) {
    try (val channel = Files.newByteChannel(path, READ)) {
      return read(channel, buffer, length);
    }
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (File file, @NonNull ByteBuffer buffer) {
    return read(file, buffer, buffer.remaining());
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (Path path, @NonNull ByteBuffer buffer) {
    return read(path, buffer, buffer.remaining());
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param file the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  public static int read (@NonNull File file, ByteBuffer buffer, int length) {
    return read(file.toPath(), buffer, length);
  }

  /**
   * Reads the bytes from the file to the specified buffer.
   *
   * @param path the file to read
   *
   * @param buffer the buffer into which the data is read
   *
   * @param length the maximum number of bytes to read
   *
   * @return the total number of bytes read into the buffer.
   */
  @SneakyThrows
  public static int read (@NonNull Path path, @NonNull ByteBuffer buffer, int length) {
    try (val channel = Files.newByteChannel(path, READ)) {
      return read(channel, buffer, length);
    }
  }

  private ReadBytesUtils () {
    throw new UnsupportedOperationException();
  }
}
