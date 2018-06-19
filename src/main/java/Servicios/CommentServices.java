package Servicios;

import Entidades.Comment;

public class CommentServices extends Dao<Comment> {
    private static CommentServices instance;

    private CommentServices() {
        super(Comment.class);
    }

    public static CommentServices getInstance() {
        if (instance == null) {
            instance = new CommentServices();
        }
        return instance;
    }
}
