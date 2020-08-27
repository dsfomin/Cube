package dsfomin.cube.repo;

import dsfomin.cube.domain.User;
import dsfomin.cube.domain.UserSubscription;
import dsfomin.cube.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
