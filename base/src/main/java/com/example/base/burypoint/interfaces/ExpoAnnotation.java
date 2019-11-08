package com.example.base.burypoint.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aspectj 埋点注解
 *
 */
public interface ExpoAnnotation {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface ExposeGoods {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface ExposeGoodsEnd {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface ExposeGoodsActivityEnd {
    }

}
