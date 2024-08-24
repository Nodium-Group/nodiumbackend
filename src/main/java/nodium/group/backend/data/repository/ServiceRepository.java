package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
