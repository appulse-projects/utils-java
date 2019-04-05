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

/**
 * A {@code SizeUnit} represents digital information size at a given
 * unit of granularity and provides utility methods to convert across
 * the units.
 *
 * @author Artem Labazin
 * @since 1.13.0
 */
public enum SizeUnit {

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

  public long convert (long source, SizeUnit sourceUnit) {
    throw new AbstractMethodError();
  }

  public long toBytes (long value) {
    throw new AbstractMethodError();
  }

  public long toKilobytes (long value) {
    throw new AbstractMethodError();
  }

  public long toMegabytes (long value) {
    throw new AbstractMethodError();
  }

  public long toGigabytes (long value) {
    throw new AbstractMethodError();
  }

  public long toTerabytes (long value) {
    throw new AbstractMethodError();
  }

  public long toPetabytes (long value) {
    throw new AbstractMethodError();
  }
}
