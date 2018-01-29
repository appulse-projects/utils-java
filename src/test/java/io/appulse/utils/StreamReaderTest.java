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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Artem Labazin
 * @since 1.1.0
 */
public class StreamReaderTest {

  @Test
  public void read () throws Exception {
    byte[] expected = "Hello world".getBytes();

    assertThat(StreamReader.read(new CustomInputStream(expected)))
        .isEqualTo(expected);
  }

  @Test
  public void readWithLength () throws Exception {
    byte[] bytes = "Hello world".getBytes();
    byte[] expected = Arrays.copyOfRange(bytes, 0, 2);

    assertThat(StreamReader.read(new CustomInputStream(bytes), 2))
        .isEqualTo(expected);
  }

  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class CustomInputStream extends InputStream {

    final byte[] bytes;

    int index;

    CustomInputStream (byte[] bytes) {
      this.bytes = new byte[bytes.length + 1];
      int middle = bytes.length / 2;
      System.arraycopy(bytes, 0, this.bytes, 0, middle);
      this.bytes[middle] = -1;
      System.arraycopy(bytes, middle, this.bytes, middle + 1, bytes.length - middle);
    }

    @Override
    public int read () throws IOException {
      if (index >= bytes.length) {
        return -1;
      }
      return bytes[index++];
    }
  }
}
