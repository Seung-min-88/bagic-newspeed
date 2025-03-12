package org.example.bagicnewspeed.domain.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 너 어디 붙을래? > 파라미터요
@Retention(RetentionPolicy.RUNTIME) // 너 언제까지 살아 있을래? > 그래도 역활을 할려면 동작시에는 살아있어야죠..(런타임)
public @interface Auth {
}
