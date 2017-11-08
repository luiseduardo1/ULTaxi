package ca.ulaval.glo4003.ultaxi.http.authentication.filtering;

import ca.ulaval.glo4003.ultaxi.domain.user.Role;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@NameBinding
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {

    Role[] value() default {};
}
