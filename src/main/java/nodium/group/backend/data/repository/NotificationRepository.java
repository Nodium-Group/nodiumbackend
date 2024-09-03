package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Notification;
import nodium.group.backend.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUser(User user);
}
