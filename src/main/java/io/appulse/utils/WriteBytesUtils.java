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

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Locale.ENGLISH;

import java.io.File;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 * The helper methods for writing the data to different streams/channels/files.
 *
 * @author Artem Labazin
 * @since 1.14.0
 */
public final class WriteBytesUtils {

  public static int write (OutputStream outputStream, @NonNull byte[] buffer) {
    return write(outputStream, buffer, 0, buffer.length);
  }

  public static int write (OutputStream outputStream, byte[] buffer, int length) {
    return write(outputStream, buffer, 0, length);
  }

  @SneakyThrows
  public static int write (@NonNull OutputStream outputStream, @NonNull byte[] bytes, int offset, int length) {
    if (offset < 0 || offset >= bytes.length) {
      val msg = String.format(ENGLISH,
          "Invalid offset %d. The offset must be equal or greater than 0 and less than byte array length (%d)",
          offset, bytes.length
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
      val bufferSize = Math.min(remaining, 8192);
      outputStream.write(bytes, position, bufferSize);
      remaining -= bufferSize;
      position += bufferSize;
    }
    return position - offset;
  }

  public static int write (OutputStream outputStream, @NonNull ByteBuffer buffer) {
    return write(outputStream, buffer, buffer.remaining());
  }

  public static int write (@NonNull OutputStream outputStream, @NonNull ByteBuffer buffer, int length) {
    val bytes = buffer.array();
    val written = write(outputStream, bytes, buffer.position(), length);
    buffer.position(buffer.position() + written);
    return written;
  }

  public static int write (OutputStream outputStream, @NonNull Bytes buffer) {
    return write(outputStream, buffer, buffer.readableBytes());
  }

  public static int write (@NonNull OutputStream outputStream, @NonNull Bytes buffer, int length) {
    val bytes = buffer.array();
    val written = write(outputStream, bytes, buffer.readerIndex(), length);
    buffer.readerIndex(buffer.readerIndex() + written);
    return written;
  }

  public static int write (WritableByteChannel channel, @NonNull byte[] buffer) {
    return write(channel, buffer, 0, buffer.length);
  }

  public static int write (WritableByteChannel channel, byte[] buffer, int length) {
    return write(channel, buffer, 0, length);
  }

  @SneakyThrows
  public static int write (@NonNull WritableByteChannel channel, @NonNull byte[] bytes, int offset, int length) {
    if (offset < 0 || offset >= bytes.length) {
      val msg = String.format(ENGLISH,
          "Invalid offset %d. The offset must be equal or greater than 0 and less than byte array length (%d)",
          offset, bytes.length
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

    int totalWritten = 0;
    while (byteBuffer.hasRemaining()) {
      val written = channel.write(byteBuffer);
      if (written < 0) {
        break;
      }
      totalWritten += written;
    }
    return totalWritten;
  }

  public static int write (WritableByteChannel channel, @NonNull ByteBuffer buffer) {
    return write(channel, buffer, buffer.remaining());
  }

  public static int write (@NonNull WritableByteChannel channel, @NonNull ByteBuffer buffer, int length) {
    val bytes = buffer.array();
    val written = write(channel, bytes, buffer.position(), length);
    buffer.position(buffer.position() + written);
    return written;
  }

  public static int write (WritableByteChannel channel, @NonNull Bytes buffer) {
    return write(channel, buffer, buffer.readableBytes());
  }

  public static int write (@NonNull WritableByteChannel channel, @NonNull Bytes buffer, int length) {
    val bytes = buffer.array();
    val written = write(channel, bytes, buffer.readerIndex(), length);
    buffer.readerIndex(buffer.readerIndex() + written);
    return written;
  }

  public static int write (File file, @NonNull byte[] buffer) {
    return write(file, buffer, 0, buffer.length);
  }

  public static int write (File file, byte[] buffer, int length) {
    return write(file, buffer, 0, length);
  }

  public static int write (@NonNull File file, byte[] buffer, int offset, int length) {
    return write(file.toPath(), buffer, offset, length);
  }

  public static int write (File file, @NonNull ByteBuffer buffer) {
    return write(file, buffer, buffer.remaining());
  }

  public static int write (@NonNull File file, ByteBuffer buffer, int length) {
    return write(file.toPath(), buffer, length);
  }

  public static int write (File file, @NonNull Bytes buffer) {
    return write(file, buffer, buffer.readableBytes());
  }

  public static int write (@NonNull File file, Bytes buffer, int length) {
    return write(file.toPath(), buffer, length);
  }

  public static int write (Path path, @NonNull byte[] buffer) {
    return write(path, buffer, 0, buffer.length);
  }

  public static int write (Path path, byte[] buffer, int length) {
    return write(path, buffer, 0, length);
  }

  @SneakyThrows
  public static int write (@NonNull Path path, byte[] buffer, int offset, int length) {
    try (val channel = Files.newByteChannel(path, CREATE, WRITE)) {
      return write(channel, buffer, offset, length);
    }
  }

  public static int write (Path path, @NonNull ByteBuffer buffer) {
    return write(path, buffer, buffer.remaining());
  }

  @SneakyThrows
  public static int write (@NonNull Path path, ByteBuffer buffer, int length) {
    try (val channel = Files.newByteChannel(path, CREATE, WRITE)) {
      return write(channel, buffer, length);
    }
  }

  public static int write (Path path, @NonNull Bytes buffer) {
    return write(path, buffer, buffer.readableBytes());
  }

  @SneakyThrows
  public static int write (@NonNull Path path, Bytes buffer, int length) {
    try (val channel = Files.newByteChannel(path, CREATE, WRITE)) {
      return write(channel, buffer, length);
    }
  }

  private WriteBytesUtils () {
    throw new UnsupportedOperationException();
  }
}
