package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
