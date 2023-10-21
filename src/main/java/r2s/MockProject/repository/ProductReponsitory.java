package r2s.MockProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import r2s.MockProject.entity.Product;

public interface ProductReponsitory extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Product getProductById(@Param("productId") int productId);
    
    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId and p.status = true and p.brand.status = true")
    Page<Product> getActiveProductByActiveBrand(@Param("brandId") int brandId, Pageable pageable);
    
    Page<Product> findByNameContainingIgnoreCaseAndStatusIsTrue(String name, Pageable pageable);
}
