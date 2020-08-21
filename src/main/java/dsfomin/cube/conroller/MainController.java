package dsfomin.cube.conroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dsfomin.cube.domain.User;
import dsfomin.cube.domain.Views;
import dsfomin.cube.dto.MessagePageDto;
import dsfomin.cube.repo.MessageRepo;
import dsfomin.cube.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter writer;

    @Autowired
    public MainController(MessageService messageService, ObjectMapper mapper) {
        this.messageService = messageService;

        this.writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest page = PageRequest.of(0, messageService.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findAll(page);

            String messages = writer.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", messages);

            data.put("totalPages", messagePageDto.getTotalPages());
            data.put("currentPage", messagePageDto.getCurrentPage());
        } else {
            model.addAttribute("messages", "[]");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}