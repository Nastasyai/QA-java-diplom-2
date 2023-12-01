package helper;

import org.apache.commons.lang3.RandomStringUtils;
import user.User;

public class UserGeneration {
    public static User generic () {
        var createUser = new User("name", "password", "");
        return createUser;
    }

    public static User random() {
        return new User("name", "password", RandomStringUtils.randomAlphanumeric(5, 10)+"@yandex.ru");
    }
}
