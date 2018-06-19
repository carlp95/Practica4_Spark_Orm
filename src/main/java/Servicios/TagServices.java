package Servicios;

import Entidades.Tag;   

public class TagServices extends Dao<Tag> {
    private static TagServices instance;

    private TagServices() {
        super(Tag.class);
    }

    public static TagServices getInstance() {
        if (instance == null) {
            instance = new TagServices();
        }
        return instance;
    }
}
