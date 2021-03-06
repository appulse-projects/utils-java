# Overview

[![build_status](https://travis-ci.org/appulse-projects/utils-java.svg?branch=master)](https://travis-ci.org/appulse-projects/utils-java)
[![maven_central](https://maven-badges.herokuapp.com/maven-central/io.appulse/utils-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.appulse/utils-java)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Common utility classes for Appulse projects.

## Development

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

For building the project you need only a [Java compiler](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

> **IMPORTANT:** the project requires Java version starting from **8**

And, of course, you need to clone the project from GitHub:

```bash
$> git clone https://github.com/appulse-projects/utils-java
$> cd utils-java
```

### Building

For building routine automation, I am using [maven](https://maven.apache.org).

To build the project, do the following:

```bash
$> mvn clean compile
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 10.522 s
[INFO] Finished at: 2019-05-19T15:31:22+03:00
[INFO] Final Memory: 43M/512M
[INFO] ------------------------------------------------------------------------
```

### Running the tests

To run the project's test, do the following:

```bash
$> mvn clean test
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
...
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 26, Failures: 0, Errors: 0, Skipped: 0
[INFO]
...
```

Also, if you do `package` or `install` goals, the tests launch automatically.

## Deploy

To deploy the project in Maven Central, use the following command:

```bash
$> ./mvnw \
    -DskipTests \
    -Dmaven.test.skip=true \
    -Dfindbugs.skip=true \
    -Dpmd.skip=true \
    -Dcheckstyle.skip \
    -Dmaven.javadoc.skip=false \
    --settings .settings.xml \
    deploy -B
```

It maybe usefull to import `gpg`'s secret keys and ownertrust from somewhere:

```bash
$> echo "${GPG_SECRET_KEYS}" | base64 --decode | "${GPG_EXECUTABLE}" --batch --passphrase "${GPG_PASSPHRASE}" --import
...
$> echo "${GPG_OWNERTRUST}" | base64 --decode | "${GPG_EXECUTABLE}" --batch --passphrase "${GPG_PASSPHRASE}" --import-ownertrust
...
```

## Built With

* [Java](http://www.oracle.com/technetwork/java/javase) - is a systems and applications programming language

* [Lombok](https://projectlombok.org) - is a java library that spicing up your java

* [Junit](http://junit.org/junit4/) - is a simple framework to write repeatable tests

* [AssertJ](http://joel-costigliola.github.io/assertj/) - AssertJ provides a rich set of assertions, truly helpful error messages, improves test code readability

* [Maven](https://maven.apache.org) - is a software project management and comprehension tool

## Changelog

To see what has changed in recent versions of the project, see the [changelog](./CHANGELOG.md) file.

## Contributing

Please read [contributing](./CONTRIBUTING.md) file for details on my code of conduct, and the process for submitting pull requests to me.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/appulse-projects/utils-java/tags).

## Authors

* **[Artem Labazin](https://github.com/xxlabaza)** - creator and the main developer

## License

This project is licensed under the Apache License 2.0 License - see the [license](./LICENSE) file for details
