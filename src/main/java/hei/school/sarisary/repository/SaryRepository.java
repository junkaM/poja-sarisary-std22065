package hei.school.sarisary.repository;

import hei.school.sarisary.repository.model.OperationSary;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaryRepository extends JpaRepository<OperationSary, String> {
  @Override
  Optional<OperationSary> findById(String id);
}
