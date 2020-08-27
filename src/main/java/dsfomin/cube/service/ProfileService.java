package dsfomin.cube.service;

import dsfomin.cube.domain.User;
import dsfomin.cube.domain.UserSubscription;
import dsfomin.cube.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public User updateSubscription(User subscriber, User channel) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            channel.getSubscribers().add(new UserSubscription(channel, subscriber));
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepo.save(channel);
    }
}
