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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.2.0
 */
public final class SocketUtils {

  public static Optional<Integer> findFreePort () {
    return findFreePort(1024, 65535);
  }

  public static Optional<Integer> findFreePort (int from, int to) {
    for (int port = from; port <= to; port++) {
      if (isPortAvailable(port)) {
        return of(port);
      }
    }
    return empty();
  }

  public static boolean isPortAvailable (int port) {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress("localhost", port), (int) SECONDS.toMillis(1));
      return false;
    } catch (IOException ex) {
      return true;
    }
  }

  @SneakyThrows
  public static byte[] read (@NonNull InputStream stream) {
    val outputStream = new ByteArrayOutputStream(32);
    val buffer = new byte[32];

    while (true) {
      val length = stream.read(buffer);
      if (length == -1) {
        break;
      }
      outputStream.write(buffer, 0, length);
    }

    return outputStream.toByteArray();
  }

  @SneakyThrows
  public static byte[] read (@NonNull Socket socket) {
    return read(socket.getInputStream());
  }

  @SneakyThrows
  public static byte[] read (@NonNull InputStream stream, int length) {
    if (length < 0) {
      throw new IndexOutOfBoundsException();
    }

    val result = new byte[length];
    int readed = 0;

    while (readed < length) {
      val count = stream.read(result, readed, length - readed);
      if (count < -1) {
        throw new EOFException();
      }
      readed += count;
    }

    return result;
  }

  @SneakyThrows
  public static byte[] read (@NonNull Socket socket, int length) {
    return read(socket.getInputStream(), length);
  }

  public static Bytes readBytes (@NonNull InputStream stream) {
    val result = read(stream);
    return Bytes.wrap(result);
  }

  @SneakyThrows
  public static Bytes readBytes (@NonNull Socket socket) {
    return readBytes(socket.getInputStream());
  }

  public static Bytes readBytes (@NonNull InputStream stream, int length) {
    val result = read(stream, length);
    return Bytes.wrap(result);
  }

  @SneakyThrows
  public static Bytes readBytes (@NonNull Socket socket, int fixedLength) {
    return readBytes(socket.getInputStream(), fixedLength);
  }

  private SocketUtils () {
  }
}
