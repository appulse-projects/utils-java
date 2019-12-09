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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import io.appulse.utils.exception.SerializationException;

import lombok.val;
import org.junit.jupiter.api.Test;

class SerializationUtilsTest {

  @Test
  void testSerializeStream () throws Exception {
    HashMap<Object, Object> map = new HashMap<>(2);
    map.put("foo", "foo");
    map.put("bar", 7);

    val streamTest = new ByteArrayOutputStream();
    SerializationUtils.serialize(map, streamTest);

    val streamReal = new ByteArrayOutputStream();
    val objectOutputStream = new ObjectOutputStream(streamReal);
    objectOutputStream.writeObject(map);
    objectOutputStream.flush();
    objectOutputStream.close();

    val testBytes = streamTest.toByteArray();
    val realBytes = streamReal.toByteArray();

    assertThat(testBytes.length).isEqualTo(realBytes.length);
    assertThat(realBytes).isEqualTo(testBytes);
  }

  @Test
  void testSerializeStreamUnserializable () throws Exception {
    HashMap<Object, Object> map = new HashMap<>(2);
    map.put("foo", "foo");
    map.put("bar", 7);
    map.put(new Object(), new Object());

    val streamTest = new ByteArrayOutputStream();
    assertThatThrownBy(() -> SerializationUtils.serialize(map, streamTest))
        .isExactlyInstanceOf(SerializationException.class);
  }

  @Test
  void testSerializeStreamNullObj () throws Exception {
    val streamTest = new ByteArrayOutputStream();
    SerializationUtils.serialize(null, streamTest);

    val streamReal = new ByteArrayOutputStream();
    val objectOutputStream = new ObjectOutputStream(streamReal);
    objectOutputStream.writeObject(null);
    objectOutputStream.flush();
    objectOutputStream.close();

    val testBytes = streamTest.toByteArray();
    val realBytes = streamReal.toByteArray();

    assertThat(testBytes.length).isEqualTo(realBytes.length);
    assertThat(realBytes).isEqualTo(testBytes);
  }

  @Test
  void testSerializeStreamObjNull () throws Exception {
    assertThatThrownBy(() -> SerializationUtils.serialize(new HashMap<>(), null))
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  void testSerializeStreamNullNull () throws Exception {
    assertThatThrownBy(() -> SerializationUtils.serialize(null, null))
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  void testSerializeException () throws Exception {
    val outputStream = new OutputStream() {

      @Override
      public void write (final int arg0) throws IOException {
        throw new IOException("popa");
      }
    };

    assertThatThrownBy(() -> SerializationUtils.serialize(new HashMap<>(), outputStream))
        .isExactlyInstanceOf(SerializationException.class)
        .hasMessage("java.io.IOException: popa");
  }

  @Test
  void testDeserializeBytes () throws Exception {
    HashMap<Object, Object> map = new HashMap<>(2);
    map.put("foo", "foo");
    map.put("bar", 7);

    val byteArrayOutputStream = new ByteArrayOutputStream();
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(map);
    objectOutputStream.flush();
    objectOutputStream.close();

    val result = SerializationUtils.deserialize(byteArrayOutputStream.toByteArray());
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(HashMap.class);
    assertThat(result == map).isFalse();

    HashMap<?, ?> testMap = (HashMap<?, ?>) result;
    assertThat(testMap.get("foo")).isEqualTo(map.get("foo"));
    assertThat(testMap.get("foo") == map.get("foo")).isFalse();

    assertThat(testMap.get("bar")).isEqualTo(map.get("bar"));
    assertThat(testMap.get("bar") == map.get("bar")).isFalse();

    assertThat(testMap).isEqualTo(map);
  }

  @Test
  void testDeserializeBytesOfNull () throws IOException {
    val byteArrayOutputStream = new ByteArrayOutputStream();
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(null);
    objectOutputStream.flush();
    objectOutputStream.close();

    val result = SerializationUtils.deserialize(byteArrayOutputStream.toByteArray());
    assertThat(result).isNull();
  }

  @Test
  void testDeserializeBytesNull () {
    assertThatThrownBy(() -> SerializationUtils.deserialize((byte[]) null))
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  void testDeserializeBytesBadStream () {
    assertThatThrownBy(() -> SerializationUtils.deserialize(new byte[0]))
        .isExactlyInstanceOf(SerializationException.class);
  }
}
