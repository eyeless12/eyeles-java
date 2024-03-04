package edu.java.bot.controller.validation;

import edu.java.bot.controller.validation.annotation.Link;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
public class LinkValidator implements ConstraintValidator<Link, String> {

    @Override
    public void initialize(Link constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String link, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        try {
            URL parsed = new URI(link).toURL();
            return true;
        } catch (MalformedURLException | URISyntaxException | IllegalArgumentException ex) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("The link is not correct")
                .addConstraintViolation();
            return false;
        }
    }
}
