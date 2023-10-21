package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import r2s.MockProject.entity.FeedbackProduct;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import r2s.MockProject.entity.Product;

public interface FeedbackRepository extends JpaRepository<FeedbackProduct, Integer> {

//    @Query("SELECT f FROM feedback f WHERE f.star = :feedbackStar")
//    Product getFeedbackStar(@Param("feedbackStar") int feedbackStar);
//
//    Page<Product> findByStar(Integer star, Pageable pageable);
}
