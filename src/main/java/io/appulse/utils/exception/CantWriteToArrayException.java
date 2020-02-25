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

package io.appulse.utils.exception;

import static java.util.Locale.ENGLISH;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * An exception throws when a byte array write process fails.
 *
 * @since 1.11.2
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CantWriteToArrayException extends IllegalArgumentException {

  private static final long serialVersionUID = 6322363184620773240L;

  @NonNull
  byte[] array;

  int from;

  int length;

  @Override
  public String getMessage () {
    return String.format(
        ENGLISH,
        "Can't write to an array from %d to %d indexes, because actual array's length is %d",
        from, from + length - 1, array.length
    );
  }
}
