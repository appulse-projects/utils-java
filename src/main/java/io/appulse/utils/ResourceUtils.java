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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 * The set of helper methods for working with classpath resources.
 *
 * @since 1.3.0
 * @author Artem Labazin
 */
public final class ResourceUtils {

  @Deprecated
  public static Optional<String> getResource (@NonNull String name) {
    return getResource(name, UTF_8);
  }

  @Deprecated
  public static Optional<String> getResource (@NonNull String name, @NonNull Charset charset) {
    return getTextContent(name, charset);
  }

  /**
   * Returns bytes content of a resource file by its URL.
   *
   * @param url adress to a resource.
   *
   * @return file's bytes representation, if exists, or empty.
   *
   * @since 1.10.0
   */
  public static Optional<byte[]> getBytesContent (@NonNull URL url) {
    try {
      InputStream inputStream = url.openStream();
      byte[] bytes = ReadBytesUtils.read(inputStream).arrayCopy();
      return of(bytes);
    } catch (Exception ex) {
      return empty();
    }
  }

  /**
   * Returns text content of a resource file by its URL.
   *
   * @param url adress to a resource.
   *
   * @param charset text content charset.
   *
   * @return file's text representation, if exists, or empty.
   *
   * @since 1.10.0
   */
  public static Optional<String> getTextContent (@NonNull URL url, @NonNull Charset charset) {
    return getBytesContent(url)
        .map(it -> new String(it, charset));
  }

  /**
   * Returns a concrete resource content by its name.
   *
   * @param name file's name
   *
   * @return file's text representation, if exists, or empty.
   *
   * @since 1.10.0
   */
  public static Optional<String> getTextContent (@NonNull String name) {
    return getTextContent(name, UTF_8);
  }

  /**
   * Returns a concrete resource content by its name.
   *
   * @param name file's name
   *
   * @param charset text content charset.
   *
   * @return file's text representation, if exists, or empty.
   *
   * @since 1.10.0
   */
  public static Optional<String> getTextContent (@NonNull String name, @NonNull Charset charset) {
    val classLoader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(name);
    if (inputStream == null) {
      inputStream = ResourceUtils.class.getResourceAsStream(name);
      if (inputStream == null) {
        return empty();
      }
    }

    val bytes = ReadBytesUtils.read(inputStream).arrayCopy();
    val string = new String(bytes, charset);
    return of(string);
  }

  /**
   * Returns text content of a resource file by its URL.
   *
   * @param url adress to a resource.
   *
   * @return file's text representation, if exists, or empty.
   *
   * @since 1.10.0
   */
  public static Optional<String> getTextContent (@NonNull URL url) {
    return getTextContent(url, UTF_8);
  }

  /**
   * Searches resource files in specific folder and by glob-pattern.
   * <p>
   * It uses glob-like pattern sysytem...honestly speaking only '*' and '?' signs.
   *
   * @param folder where to search the files
   *
   * @param glob files name glob-pattern
   *
   * @return the URLs of found files
   *
   * @since 1.10.0
   */
  public static List<URL> getResourceUrls (@NonNull String folder, @NonNull String glob) {
    Pattern pattern = of(glob)
        .map(Pattern::quote)
        .map(it -> it.replace("*", "\\E.*\\Q"))
        .map(it -> it.replace("?", "\\E.\\Q"))
        .map(it -> '^' + it + '$')
        .map(Pattern::compile)
        .orElseThrow(RuntimeException::new);

    return getResourceUrls(folder, pattern);
  }

  /**
   * Searches resource files in specific folder and by pattern.
   *
   * @param folder where to search the files
   *
   * @param pattern files name pattern
   *
   * @return the URLs of found files
   *
   * @since 1.10.0
   */
  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  @SneakyThrows
  public static List<URL> getResourceUrls (@NonNull String folder, @NonNull Pattern pattern) {
    List<URL> result = new LinkedList<>();

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> urls = classLoader.getResources(folder);

    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      if (url == null) {
        continue;
      }

      switch (url.getProtocol()) {
      case "file":
        toFile(url)
            .map(file -> file.isFile()
                         ? new File[]{ file }
                         : file.listFiles()
            )
            .filter(Objects::nonNull)
            .ifPresent(files -> Stream.of(files)
                .filter(it -> pattern.matcher(it.getName()).matches())
                .map(it -> {
                  try {
                    return it.toURI().toURL();
                  } catch (Exception ex) {
                    return null;
                  }
                })
                .filter(Objects::nonNull)
                .forEach(result::add));
        break;
      case "jar":
        String dirname = of(folder)
            .filter(it -> it.endsWith("/"))
            .orElseGet(() -> folder + '/');

        String path = url.getPath();
        String jarPath = path.substring(5, path.indexOf('!'));

        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, UTF_8.name()))) {
          jar.stream()
              .map(JarEntry::getName)
              .filter(it -> it.startsWith(dirname))
              .filter(it -> pattern.matcher(it).matches())
              .map(it -> classLoader.getResource(it))
              .forEach(result::add);
        }
        break;
      default:
        throw new UnsupportedOperationException("unsupported protocol " + url.getProtocol());
      }
    }

    return result;
  }

  private static Optional<File> toFile (URL url) throws URISyntaxException {
    URI uri = url.toURI();
    try {
      return of(new File(uri));
    } catch (Exception ex1) {
      try {
        return of(new File(uri.getPath()));
      } catch (Exception ex2) {
        return empty();
      }
    }
  }

  private ResourceUtils () {
  }
}
