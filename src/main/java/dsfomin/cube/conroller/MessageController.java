package dsfomin.cube.conroller;

import dsfomin.cube.domain.Message;
import dsfomin.cube.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> getList() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message postMessage(@RequestBody Message message) {
        message.setCreationTime(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message putMessage(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {

        BeanUtils.copyProperties(message, messageFromDb, "id");

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}

