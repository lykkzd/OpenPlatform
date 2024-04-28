package com.epicas.platform.log;

import java.lang.annotation.*;

/**
 * @author liuyang
 * @date 2023年10月09日 11:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EpicasLog {

    String value() default "";

    int[] paramIndex() default {};
}
