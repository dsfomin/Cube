package dsfomin.cube.service;

import dsfomin.cube.domain.Message;
import dsfomin.cube.domain.User;
import dsfomin.cube.domain.UserSubscription;
import dsfomin.cube.domain.Views;
import dsfomin.cube.dto.EventType;
import dsfomin.cube.dto.MessagePageDto;
import dsfomin.cube.dto.MetaDto;
import dsfomin.cube.dto.ObjectType;
import dsfomin.cube.repo.MessageRepo;
import dsfomin.cube.repo.UserSubscriptionRepo;
import dsfomin.cube.util.WebSocketSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private static String URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static String IMG_PATTERN = "\\.(jpeg|png|gif|jpg)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMG_PATTERN, Pattern.CASE_INSENSITIVE);

    public final int MESSAGES_PER_PAGE = 5;

    private final MessageRepo messageRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, Message> webSocketSender;

    @Autowired
    public MessageService(MessageRepo messageRepo, UserSubscriptionRepo userSubscriptionRepo, WebSocketSender webSocketSender) {
        this.messageRepo = messageRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
        webSocketSender.accept(EventType.REMOVE, message);
    }

    public Message updateMessage(Message messageFromDb, Message message) throws IOException {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        fillMeta(messageFromDb);
        Message updatedMessage = messageRepo.save(messageFromDb);

        webSocketSender.accept(EventType.UPDATE, updatedMessage);

        return updatedMessage;
    }

    public Message createMessage(Message message, User user) throws IOException {
        message.setCreationTime(LocalDateTime.now());
        fillMeta(message);
        message.setAuthor(user);
        Message updatedMessage = messageRepo.save(message);

        webSocketSender.accept(EventType.CREATE, updatedMessage);

        return updatedMessage;
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> userChannels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        userChannels.add(user);

        Page<Message> page = messageRepo.findByAuthorIn(userChannels, pageable);
        return new MessagePageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }
}
