package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import r2s.MockProject.entity.Brand;

public interface BrandReponsitory extends JpaRepository<Brand, Integer> {
    @Query("SELECT b FROM Brand b WHERE b.id = :brandId")
    Brand getBrandById(@Param("brandId") int brandId);
}
