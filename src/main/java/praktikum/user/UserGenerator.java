package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    private static String generatorRandomString() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }
    public static User genericUserRandom(){
        return new User(generatorRandomString()+"@yandex.ru", generatorRandomString(), generatorRandomString());

    }
}
