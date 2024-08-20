package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
}
