package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Review;
import nodium.group.backend.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select u from Review u where u.reviewee =: user")
    List<Review> findAllByReviewee(User user);
}
