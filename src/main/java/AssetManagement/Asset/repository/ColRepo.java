package AssetManagement.Asset.repository;

import AssetManagement.Asset.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColRepo extends JpaRepository<College , Long> {

}
