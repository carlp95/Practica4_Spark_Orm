package Servicios;

import Entidades.CommentVote;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public CommentVote findByAuthorComment(CommentVote vote) {
        EntityManager em = getEntityManager();
        TypedQuery<CommentVote> query = em.createQuery("select cv from CommentVote cv where cv.comment.id = :comment_id and cv.author.username = :author_id", CommentVote.class);
        query.setParameter("comment_id", vote.getComment().getId());
        query.setParameter("author_id", vote.getAuthor().getUsername());

        List<CommentVote> results = query.getResultList();

        if (results.size() == 0) {
            return null;
        }
        else if (results.size() > 1) throw new NonUniqueResultException();

        return results.get(0);
    }


}
