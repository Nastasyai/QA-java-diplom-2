package helper;

import org.apache.commons.lang3.RandomStringUtils;
import user.CreateUser;

public class UserGeneration {
    public static CreateUser getUser() {
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String email = RandomStringUtils.randomAlphabetic(10) + "@example.ru";
        return new CreateUser(name, email, password);
    }
}
