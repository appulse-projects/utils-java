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

import static java.util.Optional.ofNullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Different annotation helpers.
 *
 * @author Artem Labazin
 * @since 1.7.0
 */
public final class AnnotationUtils {

  /**
   * Find a single {@link Annotation} of {@code annotationType} on the
   * supplied {@link Class}, traversing its interfaces, annotations, and
   * superclasses if the annotation is not <em>directly present</em> on
   * the given class itself.
   * <p>
   * This method explicitly handles class-level annotations which are not
   * declared as {@link java.lang.annotation.Inherited inherited} <em>as well
   * as meta-annotations and annotations on interfaces</em>.
   * <p>
   * The algorithm operates as follows:
   * <ol>
   * <li>Search for the annotation on the given class and return it if found.
   * <li>Recursively search through all annotations that the given class declares.
   * <li>Recursively search through all interfaces that the given class declares.
   * <li>Recursively search through the superclass hierarchy of the given class.
   * </ol>
   * <p>
   * Note: in this context, the term <em>recursively</em> means that the search
   * process continues by returning to step #1 with the current interface,
   * annotation, or superclass as the class to look for annotations on.
   *
   * @param clazz the class to look for annotations on
   *
   * @param annotationType the annotation type to look for, both locally and as a meta-annotation
   *
   * @return the first matching annotation
   *
   * @since 1.7.0
   */
  public static <T extends Annotation> Optional<T> findAnnotation (Class<?> clazz, Class<T> annotationType) {
    T result = findAnnotation(clazz, annotationType, new HashSet<>());
    return ofNullable(result);
  }

  /**
   * Find a single {@link Annotation} of {@code annotationType} on the
   * supplied {@link AnnotatedElement}.
   * <p>
   * Meta-annotations will be searched if the annotation is not
   * <em>directly present</em> on the supplied element.
   * <p>
   * <strong>Warning</strong>: this method operates generically on
   * annotated elements. In other words, this method does not execute
   * specialized search algorithms for classes or methods. If you require
   * the more specific semantics of {@link #findAnnotation(Class, Class)}
   * or {@link #findAnnotation(Method, Class)}, invoke one of those methods
   * instead.
   *
   * @param annotatedElement the {@code AnnotatedElement} on which to find the annotation
   *
   * @param annotationType the annotation type to look for, both locally and as a meta-annotation
   *
   * @return the first matching annotation
   *
   * @since 1.7.0
   */
  public static <T extends Annotation> Optional<T> findAnnotation (AnnotatedElement annotatedElement,
                                                                   Class<T> annotationType
  ) {
    T result = findAnnotation(annotatedElement, annotationType, new HashSet<>());
    return ofNullable(result);
  }

  /**
   * Find a single {@link Annotation} of {@code annotationType} on the supplied
   * {@link Method}, traversing its super methods (i.e., from superclasses and
   * interfaces) if the annotation is not <em>directly present</em> on the given
   * method itself.
   * <p>
   * Correctly handles bridge {@link Method Methods} generated by the compiler.
   * <p>
   * Meta-annotations will be searched if the annotation is not
   * <em>directly present</em> on the method.
   * <p>
   * Annotations on methods are not inherited by default, so we need to handle
   * this explicitly.
   *
   * @param method the method to look for annotations on
   *
   * @param annotationType the annotation type to look for
   *
   * @return the first matching annotation
   *
   * @since 1.7.0
   */
  public static <T extends Annotation> Optional<T> findAnnotation (Method method, Class<T> annotationType) {
    T result = findAnnotation(method, annotationType, new HashSet<>());
    return ofNullable(result);
  }

  /**
   * Determine if the supplied {@link Annotation} is defined in the core JDK {@code java.lang.annotation} package.
   *
   * @param annotation the annotation to check
   *
   * @return {@code true} if the annotation is in the {@code java.lang.annotation} package
   *
   * @since 1.7.0
   */
  public static boolean isInJavaLangAnnotationPackage (Annotation annotation) {
    return annotation != null && isInJavaLangAnnotationPackage(annotation.annotationType());
  }

  /**
   * Determine if the {@link Annotation} with the supplied name is defined in the core JDK {@code java.lang.annotation} package.
   *
   * @param annotationType the annotation type to check
   *
   * @return {@code true} if the annotation is in the {@code java.lang.annotation} package
   *
   * @since 1.7.0
   */
  public static boolean isInJavaLangAnnotationPackage (Class<? extends Annotation> annotationType) {
    return annotationType != null && isInJavaLangAnnotationPackage(annotationType.getName());
  }

  /**
   * Determine if the {@link Annotation} with the supplied name is defined in the core JDK {@code java.lang.annotation} package.
   *
   * @param annotationType the name of the annotation type to check
   *
   * @return {@code true} if the annotation is in the {@code java.lang.annotation} package
   *
   * @since 1.7.0
   */
  public static boolean isInJavaLangAnnotationPackage (String annotationType) {
    return annotationType != null && annotationType.startsWith("java.lang.annotation");
  }

  private static <T extends Annotation> T findAnnotation (Method method,
                                                          Class<T> annotationType,
                                                          Set<Annotation> visited
  ) {
    T result = findAnnotation((AnnotatedElement) method, annotationType, visited);
    if (result != null) {
      return result;
    }
    Class<?> declaringClass = method.getDeclaringClass();
    return ofNullable(declaringClass.getSuperclass())
        .filter(it -> it != Object.class)
        .map(it -> findAnnotation(it, method, annotationType, visited))
        .filter(Objects::nonNull)
        .orElseGet(() -> Stream.of(declaringClass.getInterfaces())
            .map(it -> findAnnotation(it, method, annotationType, visited))
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null));
  }

  private static <T extends Annotation> T findAnnotation (Class<?> clazz,
                                                          Method method,
                                                          Class<T> annotationType,
                                                          Set<Annotation> visited
  ) {
    return Stream.of(clazz.getDeclaredMethods())
        .filter(it -> method.getName().equals(it.getName()))
        .filter(it -> Arrays.equals(method.getParameterTypes(), it.getParameterTypes()))
        .findAny()
        .map(it -> findAnnotation(it, annotationType, visited))
        .orElse(null);
  }

  private static <T extends Annotation> T findAnnotation (Class<?> clazz,
                                                          Class<T> annotationType,
                                                          Set<Annotation> visited
  ) {
    T result = findAnnotation((AnnotatedElement) clazz, annotationType, visited);
    if (result != null) {
      return result;
    }
    for (Class<?> type : clazz.getInterfaces()) {
      T annotation = findAnnotation(type, annotationType, visited);
      if (annotation != null) {
        return annotation;
      }
    }
    Class<?> superclass = clazz.getSuperclass();
    if (superclass == null || Object.class == superclass) {
      return null;
    }
    return findAnnotation(superclass, annotationType, visited);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Annotation> T findAnnotation (AnnotatedElement annotatedElement,
                                                          Class<T> annotationType,
                                                          Set<Annotation> visited
  ) {
    Annotation[] declaredAnnotations = annotatedElement.getDeclaredAnnotations();
    for (Annotation annotation : declaredAnnotations) {
      if (annotation.annotationType() == annotationType) {
        return (T) annotation;
      }
    }
    for (Annotation annotation : declaredAnnotations) {
      if (isInJavaLangAnnotationPackage(annotation) || !visited.add(annotation)) {
        continue;
      }
      T result = findAnnotation(annotation.annotationType(), annotationType, visited);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  private AnnotationUtils () {
  }
}
