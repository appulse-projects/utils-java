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

package io.appulse.utils.exception;

/**
 * Exception thrown when the Serialization process fails.
 * <p>
 * The original error is wrapped within this one.
 * <p>
 * #NotThreadSafe# because Throwable is not thread-safe.
 *
 * @since 1.8.0
 * @author Artem Labazin
 */
public class SerializationException extends RuntimeException {

  private static final long serialVersionUID = 8179675064137603549L;

  public SerializationException () {
    super();
  }

  public SerializationException (String message) {
    super(message);
  }

  public SerializationException (String message, Throwable cause) {
    super(message, cause);
  }

  public SerializationException (Throwable cause) {
    super(cause);
  }

  public SerializationException (String message,
                                 Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
