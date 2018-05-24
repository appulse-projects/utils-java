/*
 * Copyright 2018 the original author or authors.
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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 *
 * @author alabazin
 */
public class AnnotationUtilsTest {

  @Test
  public void testType1 () {
    String str = AnnotationUtils.findAnnotation(Child.class, ChildAnnotation.class)
        .map(ChildAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated parent type");
  }

  @Test
  public void testType2 () {
    String str = AnnotationUtils.findAnnotation(Child.class, ParentAnnotation.class)
        .map(ParentAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated child type");
  }

  @Test
  public void testType3 () {
    String str = AnnotationUtils.findAnnotation(Parent.class, ParentAnnotation.class)
        .map(ParentAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated annotation");
  }

  @Test
  public void testMethod1 () throws NoSuchMethodException {
    Method method = Child.class.getMethod("method");
    String str = AnnotationUtils.findAnnotation(method, ChildAnnotation.class)
        .map(ChildAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated parent method");
  }

  @Test
  public void testMethod2 () throws NoSuchMethodException {
    Method method = Child.class.getMethod("method");
    String str = AnnotationUtils.findAnnotation(method, ParentAnnotation.class)
        .map(ParentAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated child method");
  }

  @Test
  public void testMethod3 () throws NoSuchMethodException {
    Method method = Parent.class.getMethod("method");
    String str = AnnotationUtils.findAnnotation(method, ParentAnnotation.class)
        .map(ParentAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated annotation");
  }

  @Test
  public void testField1 () throws NoSuchFieldException {
    Field field = Child.class.getDeclaredField("field");
    String str = AnnotationUtils.findAnnotation(field, ChildAnnotation.class)
        .map(ChildAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated child field");
  }

  @Test
  public void testField2 () throws NoSuchFieldException {
    Field field = Child.class.getDeclaredField("field");
    String str = AnnotationUtils.findAnnotation(field, ParentAnnotation.class)
        .map(ParentAnnotation::value)
        .orElse("");

    assertThat(str).isEqualTo("annotated annotation");
  }

  @Target({ANNOTATION_TYPE, METHOD, FIELD, TYPE})
  @Retention(RUNTIME)
  public @interface ParentAnnotation {

    String value ();
  }

  @Target({ANNOTATION_TYPE, METHOD, FIELD, TYPE})
  @Retention(RUNTIME)
  @ParentAnnotation("annotated annotation")
  public @interface ChildAnnotation {

    String value ();
  }

  @ChildAnnotation("annotated parent type")
  public static class Parent {

    @ChildAnnotation("annotated parent method")
    public void method () {
    }
  }

  @ParentAnnotation("annotated child type")
  public static class Child extends Parent {

    @ChildAnnotation("annotated child field")
    String field;

    @Override
    @ParentAnnotation("annotated child method")
    public void method () {
    }
  }
}
