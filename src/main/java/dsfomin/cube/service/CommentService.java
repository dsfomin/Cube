package dsfomin.cube.service;

import com.fasterxml.jackson.annotation.JsonView;
import dsfomin.cube.domain.Comment;
import dsfomin.cube.domain.Message;
import dsfomin.cube.domain.User;
import dsfomin.cube.domain.Views;
import dsfomin.cube.dto.EventType;
import dsfomin.cube.dto.ObjectType;
import dsfomin.cube.repo.CommentRepo;
import dsfomin.cube.util.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> webSocketSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WebSocketSender webSocketSender) {
        this.commentRepo = commentRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepo.save(comment);

        webSocketSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}