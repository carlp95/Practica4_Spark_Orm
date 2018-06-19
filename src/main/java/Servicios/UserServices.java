package Servicios;

import Entidades.User;

public class UserServices extends Dao<User> {
    private static UserServices instance;

    private UserServices() {
        super(User.class);
    }

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }
}
