package dsfomin.cube.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import dsfomin.cube.domain.Message;
import dsfomin.cube.domain.User;
import dsfomin.cube.domain.Views;
import dsfomin.cube.dto.MessagePageDto;
import dsfomin.cube.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;

    private final static int MESSAGES_PER_PAGE = 5;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@PageableDefault(size = MESSAGES_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(
            @RequestBody Message message,
            @AuthenticationPrincipal User user
    ) throws IOException {
        return messageService.createMessage(message, user);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message
    ) throws IOException {
        return messageService.updateMessage(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.deleteMessage(message);
    }
}