package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by raytine on 2017/8/14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ViewByIds {
    int value();
}
