package com.example.MyBookShopApp.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//не работает с классами
@Retention(RetentionPolicy.RUNTIME)//видимость во время работы jvm
public @interface LogExecutionTime {

}
