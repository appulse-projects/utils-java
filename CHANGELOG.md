# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

[Tags on this repository](https://github.com/appulse-projects/utils-java/tags)

## [Unreleased]

- Add more tests.
- Add `JavaDoc`.

## [1.2.0](https://github.com/appulse-projects/utils-java/releases/tag/1.2.0) - 2018-02-01

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
