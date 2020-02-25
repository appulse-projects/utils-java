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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import io.appulse.utils.exception.SerializationException;

import lombok.NonNull;
import lombok.val;

/**
 * Different serialization helpers.
 *
 * @since 1.8.0
 * @author Artem Labazin
 */
public final class SerializationUtils {

  /**
   * Serializes an {@code Object} to the specified stream.
   * <p>
   * The stream will be closed once the object is written. This avoids the need for a finally clause, and maybe also
   * exception handling, in the application code.
   * <p>
   * The stream passed in is not buffered internally within this method. This is the responsibility of your application
   * if desired.
   *
   * @param object the object to serialize to bytes, may be null
   *
   * @param outputStream the stream to write to, must not be null
   *
   * @throws NullPointerException if {@code outputStream} is {@code null}
   *
   * @throws SerializationException (runtime) if the serialization fails
   *
   * @since 1.8.0
   */
  public static void serialize (Serializable object, @NonNull OutputStream outputStream) {
    try (val objectOutputStream = new ObjectOutputStream(outputStream)) {
      objectOutputStream.writeObject(object);
    } catch (IOException ex) {
      throw new SerializationException(ex);
    }
  }

  /**
   * Serializes an {@code Object} to a byte array for storage/serialization.
   *
   * @param obj the object to serialize to bytes
   *
   * @return a byte[] with the converted Serializable
   *
   * @throws SerializationException (runtime) if the serialization fails
   *
   * @since 1.8.0
   */
  public static byte[] serialize (Serializable obj) {
    val byteArrayOutputStream = new ByteArrayOutputStream(512);
    serialize(obj, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  /**
   * Deserializes an {@code Object} from the specified stream.
   * <p>
   * The stream will be closed once the object is written. This avoids the need for a finally clause, and maybe also
   * exception handling, in the application code.
   * <p>
   * The stream passed in is not buffered internally within this method. This is the responsibility of your application
   * if desired.
   * <p>
   * If the call site incorrectly types the return value, a {@link ClassCastException} is thrown from the call site.
   * Without Generics in this declaration, the call site must type cast and can cause the same ClassCastException. Note
   * that in both cases, the ClassCastException is in the call site, not in this method.
   *
   * @param <T> the object type to be deserialized
   *
   * @param inputStream the serialized object input stream, must not be null
   *
   * @return the deserialized object
   *
   * @throws NullPointerException if {@code inputStream} is {@code null}
   *
   * @throws SerializationException (runtime) if the serialization fails
   *
   * @since 1.8.0
   */
  public static <T> T deserialize (@NonNull InputStream inputStream) {
    try (val objectInputStream = new ObjectInputStream(inputStream)) {
      @SuppressWarnings("unchecked")
      T obj = (T) objectInputStream.readObject();
      return obj;
    } catch (ClassNotFoundException | IOException ex) {
      throw new SerializationException(ex);
    }
  }

  /**
   * Deserializes a single {@code Object} from an array of bytes.
   * <p>
   * If the call site incorrectly types the return value, a {@link ClassCastException} is thrown from the call site.
   * Without Generics in this declaration, the call site must type cast and can cause the same ClassCastException. Note
   * that in both cases, the ClassCastException is in the call site, not in this method.
   *
   * @param <T> the object type to be deserialized
   *
   * @param objectData the serialized object, must not be null
   *
   * @return the deserialized object
   *
   * @throws NullPointerException if {@code objectData} is {@code null}
   *
   * @throws SerializationException (runtime) if the serialization fails
   *
   * @since 1.8.0
   */
  public static <T> T deserialize (@NonNull byte[] objectData) {
    return deserialize(new ByteArrayInputStream(objectData));
  }

  private SerializationUtils() {
  }
}
