/*
 * This file is part of FastClasspathScanner.
 * 
 * Author: Luke Hutchison
 * 
 * Hosted at: https://github.com/lukehutch/fast-classpath-scanner
 * 
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Luke Hutchison
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.lukehutch.fastclasspathscanner.issues.issue151;

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Test;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.MethodInfo;

public class Issue151Test {
    @Test
    public void issue151Test() throws IOException {
        // Scans io.github.lukehutch.fastclasspathscanner.issues.issue146.CompiledWithJDK8,
        // which is in src/test/resources
        final String pkg = Issue151Test.class.getPackage().getName();
        final MethodInfo methodInfo = new FastClasspathScanner(pkg) //
                .enableMethodInfo() //
                .scan() //
                .getClassNameToClassInfo() //
                .get(Issue151Test.class.getName()) //
                .getMethodInfo("method") //
                .get(0);
        assertThat(methodInfo.toString()) //
                .isEqualTo("public void method(@" + ParamAnnotation0.class.getName() + " String, @"
                        + ParamAnnotation1.class.getName() + " @" + ParamAnnotation2.class.getName() + " String)");
    }

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    public static @interface ParamAnnotation0 {
    }

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    public static @interface ParamAnnotation1 {
    }

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    public static @interface ParamAnnotation2 {
    }

    public void method(@ParamAnnotation0 final String annotatedValue0,
            @ParamAnnotation1 @ParamAnnotation2 final String annotatedValue1) {
    }
}
