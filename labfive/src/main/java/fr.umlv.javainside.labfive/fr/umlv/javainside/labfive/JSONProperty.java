package fr.umlv.javainside.labfive;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JSONProperty {
	String alterativeName() default ""; 
}
