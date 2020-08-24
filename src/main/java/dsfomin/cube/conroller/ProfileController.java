package dsfomin.cube.conroller;

import dsfomin.cube.domain.User;
import dsfomin.cube.domain.Views;
import dsfomin.cube.service.ProfileService;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User updateSubscription(@AuthenticationPrincipal User user, @PathVariable("channelId") User channel) {
        return user.equals(channel) ? channel : profileService.updateSubscription(user, channel);
    }

}
