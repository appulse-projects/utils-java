# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

[Tags on this repository](https://github.com/appulse-projects/utils-java/tags)

## [Unreleased]

- Add more tests.
- Add `JavaDoc`.

## [1.11.0](https://github.com/appulse-projects/utils-java/releases/tag/1.11.0) - 2019-02-14

### Added

- [FutureUtils](./src/main/java/io/appulse/utils/threads/FutureUtils.java) helper class;
- [Tests](./src/test/java/io/appulse/utils/threads/FutureUtilsTests.java) for `FutureUtils`.

### Changed

- Updated `pom` dependencies;
- Fixed copyright year.

## [1.10.0](https://github.com/appulse-projects/utils-java/releases/tag/1.10.0) - 2018-09-25

### Added

- `FifoCache` implementation;
- `ResourceUtils.getResourceUrls` function for flexible searching resource files;
- `ResourceUtils.get*Content` functions for extracting resource files content;
- A test for checking `ResourceUtils.getResourceUrls` and `ResourceUtils.get*Content` functions.

### Changed

- `ResourceUtils.getResource` are deprecated, use `ResourceUtils.get*Content` functions;
- `LruCache` and `FifoCache` moved to `io.appulse.utils.cache` package.

## [1.9.0](https://github.com/appulse-projects/utils-java/releases/tag/1.9.0) - 2018-09-11

### Added

- Created `LruCache` implementation.

## [1.8.0](https://github.com/appulse-projects/utils-java/releases/tag/1.8.0) - 2018-05-25

Created SerializationUtils helper class and RoundRobin collection wrapper.

### Added

- `SerializationUtils` helper class.
- `SerializationUtils`.`serialize` method for converting object to a byte array.
- `SerializationUtils`.`deserialize` method for creating object from an array of bytes.
- `SerializationException` exception thrown when the Serialization process fails.
- `RoundRobin` collection wrapper.

## [1.7.0](https://github.com/appulse-projects/utils-java/releases/tag/1.7.0) - 2018-05-24

Created AnnotationUtils helper class.

### Added

- `AnnotationUtils` helper class.
- `AnnotationUtils`.`findAnnotation` method for deep searching annotations.
- `AnnotationUtils`.`isInJavaLangAnnotationPackage` method for determining user's/java annotations.

## [1.6.0](https://github.com/appulse-projects/utils-java/releases/tag/1.6.0) - 2018-05-22

Created ExceptionUtils helper class.

### Added

- `ExceptionUtils` helper class.
- Add `ExceptionUtils`.`softException` wrapper for sneaky throws exceptions.

## [1.5.2](https://github.com/appulse-projects/utils-java/releases/tag/1.5.2) - 2018-04-22

Speed-up unsigned functions execution.

### Changed

- byte/short/int unsigned functions became more fast.
- Catched exception in `ExecutorServiceWithClientTraceTest` rethrows again.

## [1.5.1](https://github.com/appulse-projects/utils-java/releases/tag/1.5.1) - 2018-04-07

Add fancy logging for `ExecutorServiceWithClientTraceTest`.

### Changed

- No more re-throw exception in `ExecutorServiceWithClientTraceTest`.

## [1.5.0](https://github.com/appulse-projects/utils-java/releases/tag/1.5.0) - 2018-03-22

Added tests and threads helpers.

### Added

- Threads helpers: executor wrappers, customizable thread factory and executor builders.
- Test helpers.

## [1.3.3](https://github.com/appulse-projects/utils-java/releases/tag/1.3.3) - 2018-03-15

Small refactoring.

### Changed

- Updated dependencies.
- Corrected codestyle files.

## [1.3.2](https://github.com/appulse-projects/utils-java/releases/tag/1.3.2) - 2018-02-14

Minor release with simple bug fix.

### Changed

- Redesigned `Bytes.put(index, byte)` logic, now it moves `limit`.

## [1.3.1](https://github.com/appulse-projects/utils-java/releases/tag/1.3.1) - 2018-02-09

Minor release with unsigned numbers support.

### Added

- Byte arrays conversion to unsigned byte/short/int.
- Unsigned tests.

### Changed

- Exceptions in tests have full stack traces.

## [1.3.0](https://github.com/appulse-projects/utils-java/releases/tag/1.3.0) - 2018-02-01

Add resource utils class and made small refactorign.

### Added

- `ResourceUtils` which contains resource related functions.

### Changed

- Renamed `BytesUtil` to `BytesUtils`.
- Moved methods for reading `InputStream` to `BytesUtils`.

## [1.2.1](https://github.com/appulse-projects/utils-java/releases/tag/1.2.1) - 2018-02-01

Add socket utils class, with help functions related to sockets work.

### Added

- Method `array(int)` in `Bytes` for getting `byte[]` with offset.
- Added port related functions to `StreamReader`.

### Changed

- Renamed `StreamReader` to `SocketUtils`.

## [1.1.0](https://github.com/appulse-projects/utils-java/releases/tag/1.1.0) - 2018-01-30

### Added

- `StreamReader` for effective reading `InputStream`.

## [1.0.4](https://github.com/appulse-projects/utils-java/releases/tag/1.0.4) - 2018-01-28

### Changed

- Refactored `Bytes` interface.

### Removed

- Netty's dependency.

## [1.0.3](https://github.com/appulse-projects/utils-java/releases/tag/1.0.3) - 2018-01-28

### Added

- New `Bytes` methods.

## [1.0.2](https://github.com/appulse-projects/utils-java/releases/tag/1.0.2) - 2018-01-28

### Changed

- Speed up `BytesUtil` methods.

## [1.0.1](https://github.com/appulse-projects/utils-java/releases/tag/1.0.1) - 2018-01-27

### Added

- `ReflectionUtils`;
- More tests.

### Changed

- Optimize `ByteUtil` speed.
- Refactored `Bytes` interface.

## [1.0.0](https://github.com/appulse-projects/utils-java/releases/tag/1.0.0) - 2018-01-26

Initial release.

### Added

- Bytes wrapper with growing capacity;
- Primitives to bytes converter and vice versa.
