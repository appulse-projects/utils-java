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

/**
 * A {@code SizeUnit} represents digital information size at a given
 * unit of granularity and provides utility methods to convert across
 * the units.
 *
 * @since 1.13.0
 * @author Artem Labazin
 */
public enum SizeUnit {

  /**
   * Byte (B), 1 Byte.
   */
  BYTES {

    public long toBytes (long value) {
      return value;
    }

    public long toKilobytes (long value) {
      return value / (KILOBYTE / BYTE);
    }

    public long toMegabytes (long value) {
      return value / (MEGABYTE / BYTE);
    }

    public long toGigabytes (long value) {
      return value / (GIGABYTE / BYTE);
    }

    public long toTerabytes (long value) {
      return value / (TERABYTE / BYTE);
    }

    public long toPetabytes (long value) {
      return value / (PETABYTE / BYTE);
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toBytes(value);
    }
  },

  /**
   * Kilobyte (kB), 2^10 Byte = 1.024 Byte.
   */
  KILOBYTES {

    public long toBytes (long value) {
      return scale(value, KILOBYTE / BYTE, MAX / (KILOBYTE / BYTE));
    }

    public long toKilobytes (long value) {
      return value;
    }

    public long toMegabytes (long value) {
      return value / (MEGABYTE / KILOBYTE);
    }

    public long toGigabytes (long value) {
      return value / (GIGABYTE / KILOBYTE);
    }

    public long toTerabytes (long value) {
      return value / (TERABYTE / KILOBYTE);
    }

    public long toPetabytes (long value) {
      return value / (PETABYTE / KILOBYTE);
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toKilobytes(value);
    }
  },

  /**
   * Megabyte (MB), 2^20 Byte = 1.024 * 1.024 Byte = 1.048.576 Byte.
   */
  MEGABYTES {

    public long toBytes (long value) {
      return scale(value, MEGABYTE / BYTE, MAX / (MEGABYTE / BYTE));
    }

    public long toKilobytes (long value) {
      return scale(value, MEGABYTE / KILOBYTE, MAX / (MEGABYTE / KILOBYTE));
    }

    public long toMegabytes (long value) {
      return value;
    }

    public long toGigabytes (long value) {
      return value / (GIGABYTE / MEGABYTE);
    }

    public long toTerabytes (long value) {
      return value / (TERABYTE / MEGABYTE);
    }

    public long toPetabytes (long value) {
      return value / (PETABYTE / MEGABYTE);
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toMegabytes(value);
    }
  },

  /**
   * Gigabyte (GB),
   * 2^30 Byte = 1.024 * 1.024 * 1.024 Byte = 1.073.741.824 Byte.
   */
  GIGABYTES {

    public long toBytes (long value) {
      return scale(value, GIGABYTE / BYTE, MAX / (GIGABYTE / BYTE));
    }

    public long toKilobytes (long value) {
      return scale(value, GIGABYTE / KILOBYTE, MAX / (GIGABYTE / KILOBYTE));
    }

    public long toMegabytes (long value) {
      return scale(value, GIGABYTE / MEGABYTE, MAX / (GIGABYTE / MEGABYTE));
    }

    public long toGigabytes (long value) {
      return value;
    }

    public long toTerabytes (long value) {
      return value / (TERABYTE / GIGABYTE);
    }

    public long toPetabytes (long value) {
      return value / (PETABYTE / GIGABYTE);
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toGigabytes(value);
    }
  },

  /**
   * Terabyte (TB),
   * 2^40 Byte = 1.024 * 1.024 * 1.024 * 1.024 Byte = 1.099.511.627.776 Byte.
   */
  TERABYTES {

    public long toBytes (long value) {
      return scale(value, TERABYTE / BYTE, MAX / (TERABYTE / BYTE));
    }

    public long toKilobytes (long value) {
      return scale(value, TERABYTE / KILOBYTE, MAX / (TERABYTE / KILOBYTE));
    }

    public long toMegabytes (long value) {
      return scale(value, TERABYTE / MEGABYTE, MAX / (TERABYTE / MEGABYTE));
    }

    public long toGigabytes (long value) {
      return scale(value, TERABYTE / GIGABYTE, MAX / (TERABYTE / GIGABYTE));
    }

    public long toTerabytes (long value) {
      return value;
    }

    public long toPetabytes (long value) {
      return value / (PETABYTE / TERABYTE);
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toTerabytes(value);
    }
  },

  /**
   * Petabyte (PB),
   * 2^50 Byte = 1.024 * 1.024 * 1.024 * 1.024 * 1.024 Byte = 1.125.899.906.842.624 Byte.
   */
  PETABYTES {

    public long toBytes (long value) {
      return scale(value, PETABYTE / BYTE, MAX / (PETABYTE / BYTE));
    }

    public long toKilobytes (long value) {
      return scale(value, PETABYTE / KILOBYTE, MAX / (PETABYTE / KILOBYTE));
    }

    public long toMegabytes (long value) {
      return scale(value, PETABYTE / MEGABYTE, MAX / (PETABYTE / MEGABYTE));
    }

    public long toGigabytes (long value) {
      return scale(value, PETABYTE / GIGABYTE, MAX / (PETABYTE / GIGABYTE));
    }

    public long toTerabytes (long value) {
      return scale(value, PETABYTE / TERABYTE, MAX / (PETABYTE / TERABYTE));
    }

    public long toPetabytes (long value) {
      return value;
    }

    public long convert (long value, SizeUnit unit) {
      return unit.toPetabytes(value);
    }
  };

  static final long BYTE = 1L;
  static final long KILOBYTE = BYTE * 1024L;
  static final long MEGABYTE = KILOBYTE * 1024L;
  static final long GIGABYTE = MEGABYTE * 1024L;
  static final long TERABYTE = GIGABYTE * 1024L;
  static final long PETABYTE = TERABYTE * 1024L;

  static final long MAX = Long.MAX_VALUE;

  /**
   * Scale d by m, checking for overflow.
   * This has a short name to make above code more readable.
   */
  static long scale (long d, long m, long over) {
    if (d > over) {
      return Long.MAX_VALUE;
    }
    if (d < -over) {
      return Long.MIN_VALUE;
    }
    return d * m;
  }

  /**
   * Converts a {@code source} value with a specified {@link SizeUnit} to that instance.
   *
   * @param source the value to convert.
   * @param sourceUnit the value's size unit.
   *
   * @return converted value.
   */
  public long convert (long source, SizeUnit sourceUnit) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to bytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in bytes.
   */
  public long toBytes (long value) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to kilobytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in kilobytes.
   */
  public long toKilobytes (long value) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to megabytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in megabytes.
   */
  public long toMegabytes (long value) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to gigabytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in gigabytes.
   */
  public long toGigabytes (long value) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to terabytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in terabytes.
   */
  public long toTerabytes (long value) {
    throw new AbstractMethodError();
  }

  /**
   * Translates a value to petabytes.
   *
   * @param value the value to convert.
   *
   * @return converted value, in petabytes.
   */
  public long toPetabytes (long value) {
    throw new AbstractMethodError();
  }
}
