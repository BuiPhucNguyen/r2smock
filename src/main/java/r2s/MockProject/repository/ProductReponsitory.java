package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import r2s.MockProject.entity.Product;

public interface ProductReponsitory extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Product getProductById(@Param("productId") int productId);
}
