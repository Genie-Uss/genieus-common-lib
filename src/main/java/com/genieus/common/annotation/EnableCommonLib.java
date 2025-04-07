package com.genieus.common.annotation;

import com.genieus.common.config.CommonConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CommonConfig.class)
public @interface EnableCommonLib {
}