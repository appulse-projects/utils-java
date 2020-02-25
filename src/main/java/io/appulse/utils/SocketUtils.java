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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * The utility class for working with TCP connections.
 *
 * @since 1.2.0
 * @author Artem Labazin
 */
public final class SocketUtils {

  public static Optional<Integer> findFreePort () {
    return findFreePort(1024, 65_535);
  }

  public static Optional<Integer> findFreePort (int from, int to) {
    for (int port = to; port >= from; port--) {
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
  public static byte[] read (@NonNull Socket socket) {
    return ReadBytesUtils.read(socket.getInputStream()).arrayCopy();
  }

  @SneakyThrows
  public static byte[] read (@NonNull Socket socket, int length) {
    return ReadBytesUtils.read(socket.getInputStream(), length).arrayCopy();
  }

  @SneakyThrows
  public static Bytes readBytes (@NonNull Socket socket) {
    return ReadBytesUtils.read(socket.getInputStream());
  }

  @SneakyThrows
  public static Bytes readBytes (@NonNull Socket socket, int fixedLength) {
    return ReadBytesUtils.read(socket.getInputStream(), fixedLength);
  }

  private SocketUtils () {
  }
}
