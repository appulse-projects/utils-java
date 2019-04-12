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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Comparator.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadBytesUtilsTests {

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
  void read () {
    withInputStream(inputStream -> {
      val buffer = ReadBytesUtils.read(inputStream);

      assertThat(buffer.writerIndex()).isEqualTo(CONTENT.length);
      assertThat(buffer.arrayCopy()).isEqualTo(CONTENT);
    });
    withInputStream(inputStream -> {
      val length = 3;
      val buffer = ReadBytesUtils.read(inputStream, length);

      assertThat(buffer.writerIndex()).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.readByte()).isEqualTo(CONTENT[i]);
      }
    });
  }

  @Test
  void readInputStream () {
    withInputStream(inputStream -> {
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(inputStream, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer).isEqualTo(CONTENT);
    });
    withInputStream(inputStream -> {
      val length = 3;
      val buffer = new byte[length];
      val readed = ReadBytesUtils.read(inputStream, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i]);
      }
    });
    withInputStream(inputStream -> {
      val offset = 2;
      val length = 3;
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(inputStream, buffer, offset, length);

      assertThat(readed).isEqualTo(length);
      for (int i = offset; i < offset + length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i - offset]);
      }
    });

    withInputStream(inputStream -> {
      val buffer = ByteBuffer.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(inputStream, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withInputStream(inputStream -> {
      val length = 3;
      val buffer = ByteBuffer.allocate(length);
      val readed = ReadBytesUtils.read(inputStream, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });

    withInputStream(inputStream -> {
      val buffer = Bytes.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(inputStream, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withInputStream(inputStream -> {
      val length = 3;
      val buffer = Bytes.allocate(length);
      val readed = ReadBytesUtils.read(inputStream, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });
  }

  @Test
  void readChannel () {
    withChannel(channel -> {
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(channel, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer).isEqualTo(CONTENT);
    });
    withChannel(channel -> {
      val length = 3;
      val buffer = new byte[length];
      val readed = ReadBytesUtils.read(channel, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i]);
      }
    });
    withChannel(channel -> {
      val offset = 2;
      val length = 3;
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(channel, buffer, offset, length);

      assertThat(readed).isEqualTo(length);
      for (int i = offset; i < offset + length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i - offset]);
      }
    });

    withChannel(channel -> {
      val buffer = ByteBuffer.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(channel, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withChannel(channel -> {
      val length = 3;
      val buffer = ByteBuffer.allocate(length);
      val readed = ReadBytesUtils.read(channel, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });

    withChannel(channel -> {
      val buffer = Bytes.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(channel, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withChannel(channel -> {
      val length = 3;
      val buffer = Bytes.allocate(length);
      val readed = ReadBytesUtils.read(channel, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });
  }

  @Test
  void readPath () {
    withPath(path -> {
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(path, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer).isEqualTo(CONTENT);
    });
    withPath(path -> {
      val length = 3;
      val buffer = new byte[length];
      val readed = ReadBytesUtils.read(path, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i]);
      }
    });
    withPath(path -> {
      val offset = 2;
      val length = 3;
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(path, buffer, offset, length);

      assertThat(readed).isEqualTo(length);
      for (int i = offset; i < offset + length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i - offset]);
      }
    });

    withPath(path -> {
      val buffer = ByteBuffer.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(path, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withPath(path -> {
      val length = 3;
      val buffer = ByteBuffer.allocate(length);
      val readed = ReadBytesUtils.read(path, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });

    withPath(path -> {
      val buffer = Bytes.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(path, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withPath(path -> {
      val length = 3;
      val buffer = Bytes.allocate(length);
      val readed = ReadBytesUtils.read(path, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });
  }

  @Test
  void readFile () {
    withFile(file -> {
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(file, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer).isEqualTo(CONTENT);
    });
    withFile(file -> {
      val length = 3;
      val buffer = new byte[length];
      val readed = ReadBytesUtils.read(file, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i]);
      }
    });
    withFile(file -> {
      val offset = 2;
      val length = 3;
      val buffer = new byte[CONTENT.length];
      val readed = ReadBytesUtils.read(file, buffer, offset, length);

      assertThat(readed).isEqualTo(length);
      for (int i = offset; i < offset + length; i++) {
        assertThat(buffer[i]).isEqualTo(CONTENT[i - offset]);
      }
    });

    withFile(file -> {
      val buffer = ByteBuffer.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(file, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withFile(file -> {
      val length = 3;
      val buffer = ByteBuffer.allocate(length);
      val readed = ReadBytesUtils.read(file, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });

    withFile(file -> {
      val buffer = Bytes.allocate(CONTENT.length);
      val readed = ReadBytesUtils.read(file, buffer);

      assertThat(readed).isEqualTo(CONTENT.length);
      assertThat(buffer.array()).isEqualTo(CONTENT);
    });
    withFile(file -> {
      val length = 3;
      val buffer = Bytes.allocate(length);
      val readed = ReadBytesUtils.read(file, buffer, length);

      assertThat(readed).isEqualTo(length);
      for (int i = 0; i < length; i++) {
        assertThat(buffer.array()[i]).isEqualTo(CONTENT[i]);
      }
    });
  }

  @SneakyThrows
  private void withInputStream (ConsumerWithThrowable<InputStream> consumer) {
    val path = createFile();
    try (val inputStream = Files.newInputStream(path, READ)) {
      consumer.consume(inputStream);
    }
  }

  @SneakyThrows
  private void withChannel (ConsumerWithThrowable<ReadableByteChannel> consumer) {
    val path = createFile();
    try (val channel = Files.newByteChannel(path, READ)) {
      consumer.consume(channel);
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
    val path = FOLDER.resolve(UUID.randomUUID().toString());
    Files.write(path, CONTENT, CREATE, WRITE);
    return path;
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
}
