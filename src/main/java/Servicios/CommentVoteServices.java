package Servicios;

import Entidades.CommentVote;

public class CommentVoteServices extends Dao<CommentVote> {
    private static CommentVoteServices instance;

    private CommentVoteServices() {
        super(CommentVote.class);
    }

    public static CommentVoteServices getInstance() {
        if (instance == null) {
            instance = new CommentVoteServices();
        }
        return instance;
    }


}
