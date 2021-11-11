package pl.filipwlodarczyk.SpringSecurity.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@Slf4j
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        if(!email.contains("@")) {
            log.error("Email must have @!");
            return false;
        } else if(email.length() < 10) {
            log.error("Email is to short!");
            return false;
        } else {
            return true;
        }
    }
}
