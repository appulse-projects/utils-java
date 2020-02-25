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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Comparator.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteBytesUtilsTests {

  private static final Path FOLDER = Paths.get("./test-folder");

  private static final byte[] CONTENT = "Hello world".getBytes(UTF_8);

  @BeforeEach
  void beforeEach () {
    clearFolder();
  }

  @AfterEach
  void afterEach () {
    clearFolder();
  }

  @Test
  void writeOutputStream () {
    withOutputStream((path, outputStream) -> {
      val written = WriteBytesUtils.write(outputStream, CONTENT);
      outputStream.flush();

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withOutputStream((path, outputStream) -> {
      val length = 5;
      val written = WriteBytesUtils.write(outputStream, CONTENT, length);
      outputStream.flush();

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
    withOutputStream((path, outputStream) -> {
      val offset = 3;
      val length = 5;
      val written = WriteBytesUtils.write(outputStream, CONTENT, offset, length);
      outputStream.flush();

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, offset, offset + length);
      assertThat(expected).isEqualTo(Files.readAllBytes(path));
    });

    withOutputStream((path, outputStream) -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val written = WriteBytesUtils.write(outputStream, buffer);
      outputStream.flush();

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withOutputStream((path, outputStream) -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(outputStream, buffer, length);
      outputStream.flush();

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });

    withOutputStream((path, outputStream) -> {
      val buffer = Bytes.wrap(CONTENT);
      val written = WriteBytesUtils.write(outputStream, buffer);
      outputStream.flush();

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withOutputStream((path, outputStream) -> {
      val buffer = Bytes.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(outputStream, buffer, length);
      outputStream.flush();

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
  }

  @Test
  void writeChannel () {
    withChannel((path, channel) -> {
      val written = WriteBytesUtils.write(channel, CONTENT);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withChannel((path, channel) -> {
      val length = 5;
      val written = WriteBytesUtils.write(channel, CONTENT, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
    withChannel((path, channel) -> {
      val offset = 3;
      val length = 5;
      val written = WriteBytesUtils.write(channel, CONTENT, offset, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, offset, offset + length);
      assertThat(expected).isEqualTo(Files.readAllBytes(path));
    });

    withChannel((path, channel) -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val written = WriteBytesUtils.write(channel, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withChannel((path, channel) -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(channel, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });

    withChannel((path, channel) -> {
      val buffer = Bytes.wrap(CONTENT);
      val written = WriteBytesUtils.write(channel, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withChannel((path, channel) -> {
      val buffer = Bytes.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(channel, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
  }

  @Test
  void writePath () {
    withPath(path -> {
      val written = WriteBytesUtils.write(path, CONTENT);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withPath(path -> {
      val length = 5;
      val written = WriteBytesUtils.write(path, CONTENT, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
    withPath(path -> {
      val offset = 3;
      val length = 5;
      val written = WriteBytesUtils.write(path, CONTENT, offset, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, offset, offset + length);
      assertThat(expected).isEqualTo(Files.readAllBytes(path));
    });

    withPath(path -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val written = WriteBytesUtils.write(path, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withPath(path -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(path, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });

    withPath(path -> {
      val buffer = Bytes.wrap(CONTENT);
      val written = WriteBytesUtils.write(path, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(path));
    });
    withPath(path -> {
      val buffer = Bytes.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(path, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(path).length).isEqualTo(length);
      assertThat(Files.readAllBytes(path)).isEqualTo(expected);
    });
  }

  @Test
  void writeFile () {
    withFile(file -> {
      val written = WriteBytesUtils.write(file, CONTENT);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(file.toPath()));
    });
    withFile(file -> {
      val length = 5;
      val written = WriteBytesUtils.write(file, CONTENT, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(file.toPath()).length).isEqualTo(length);
      assertThat(Files.readAllBytes(file.toPath())).isEqualTo(expected);
    });
    withFile(file -> {
      val offset = 3;
      val length = 5;
      val written = WriteBytesUtils.write(file, CONTENT, offset, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, offset, offset + length);
      assertThat(expected).isEqualTo(Files.readAllBytes(file.toPath()));
    });

    withFile(file -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val written = WriteBytesUtils.write(file, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(file.toPath()));
    });
    withFile(file -> {
      val buffer = ByteBuffer.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(file, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(file.toPath()).length).isEqualTo(length);
      assertThat(Files.readAllBytes(file.toPath())).isEqualTo(expected);
    });

    withFile(file -> {
      val buffer = Bytes.wrap(CONTENT);
      val written = WriteBytesUtils.write(file, buffer);

      assertThat(CONTENT).hasSize(written);
      assertThat(CONTENT).isEqualTo(Files.readAllBytes(file.toPath()));
    });
    withFile(file -> {
      val buffer = Bytes.wrap(CONTENT);
      val length = 5;
      val written = WriteBytesUtils.write(file, buffer, length);

      assertThat(written).isEqualTo(length);

      val expected = Arrays.copyOfRange(CONTENT, 0, length);
      assertThat(Files.readAllBytes(file.toPath()).length).isEqualTo(length);
      assertThat(Files.readAllBytes(file.toPath())).isEqualTo(expected);
    });
  }

  @SneakyThrows
  private void withOutputStream (BiconsumerWithThrowable<Path, OutputStream> consumer) {
    val path = createFile();
    try (val outputStream = Files.newOutputStream(path, WRITE)) {
      consumer.consume(path, outputStream);
    }
  }

  @SneakyThrows
  private void withChannel (BiconsumerWithThrowable<Path, WritableByteChannel> consumer) {
    val path = createFile();
    try (val channel = Files.newByteChannel(path, WRITE)) {
      consumer.consume(path, channel);
    }
  }

  @SneakyThrows
  private void withPath (ConsumerWithThrowable<Path> consumer) {
    val path = createFile();
    consumer.consume(path);
  }

  @SneakyThrows
  private void withFile (ConsumerWithThrowable<File> consumer) {
    val path = createFile().toFile();
    consumer.consume(path);
  }

  @SneakyThrows
  private Path createFile () {
    val name = UUID.randomUUID().toString();
    val path = FOLDER.resolve(name);
    return Files.createFile(path);
  }

  @SneakyThrows
  private void clearFolder () {
    Files.createDirectories(FOLDER);
    if (Files.notExists(FOLDER)) {
      return;
    }

    Files.walk(FOLDER)
        .sorted(reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    Files.createDirectories(FOLDER);
  }

  interface ConsumerWithThrowable<T> {

    @SuppressWarnings("checkstyle:IllegalThrows")
    void consume (T value) throws Throwable;
  }

  interface BiconsumerWithThrowable<A, B> {

    @SuppressWarnings("checkstyle:IllegalThrows")
    void consume (A a, B b) throws Throwable;
  }
}
