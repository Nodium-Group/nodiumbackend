package nodium.group.backend.service.impl;

import nodium.group.backend.data.models.Address;
import nodium.group.backend.data.models.Notification;
import nodium.group.backend.data.models.User;
import nodium.group.backend.data.repository.NotificationRepository;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.request.JobRequest;
import nodium.group.backend.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static nodium.group.backend.data.enums.Role.PROVIDER;

@Component
public class BackendNotificationService implements NotificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepo;
    public void notifyAllProvidersInLocation(String location, JobRequest jobRequest) {
        List<User> providersInLocation = userRepository.findAll().stream()
                .filter(this::isProvider)
                .filter(user -> isLocatedInLocation(user, location))
                .toList();

        providersInLocation.forEach(user -> sendNotification(user, jobRequest));
    }
    private boolean isProvider(User user) {
        return user.getRole() != null && user.getRole().contains(PROVIDER) &&
                user.getAddress() != null;
    }

    private boolean isLocatedInLocation(User user, String location) {
        Address address = user.getAddress();
        String state = address.getState();
        String lga = address.getLga();
        return (state != null && state.toLowerCase().contains(location.toLowerCase())) ||
                (lga != null && lga.toLowerCase().contains(location.toLowerCase()));
    }
    private void sendNotification(User user, JobRequest jobRequest) {
        Notification notification = new Notification();
        notification.setPurpose("Job Update");
        notification.setId(user.getId());
        notification.setDescription(
                String.format("""
                Amount : %s
                Location : %s
                Category : %s
                """,jobRequest.getAmount(),jobRequest.getLocation(),jobRequest.getCategory()));
        notification.setUser(user);
        notificationRepo.save(notification);
    }
}
