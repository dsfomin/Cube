package dsfomin.cube.conroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dsfomin.cube.domain.User;
import dsfomin.cube.domain.Views;
import dsfomin.cube.dto.MessagePageDto;
import dsfomin.cube.repo.UserDetailsRepo;
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
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserDetailsRepo userDetailsRepo;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    @Autowired
    public MainController(MessageService messageService, UserDetailsRepo userDetailsRepo, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userDetailsRepo = userDetailsRepo;

        ObjectMapper objectMapper = mapper.setConfig(mapper.getSerializationConfig());

        this.messageWriter = objectMapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper.writerWithView(Views.FullProfile.class);;
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            User userFromDb = userDetailsRepo.findById(user.getId()).get();
            model.addAttribute("profile", profileWriter.writeValueAsString(userFromDb));

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest page = PageRequest.of(0, messageService.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findAll(page);

            String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", messages);

            data.put("totalPages", messagePageDto.getTotalPages());
            data.put("currentPage", messagePageDto.getCurrentPage());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}