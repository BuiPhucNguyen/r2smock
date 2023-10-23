package r2s.MockProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import r2s.MockProject.entity.FeedbackProduct;

public interface FeedbackRepository extends JpaRepository<FeedbackProduct, Integer> {

//    @Query("SELECT f FROM feedback f WHERE f.star = :feedbackStar")
//    Product getFeedbackStar(@Param("feedbackStar") int feedbackStar);
//
//    Page<Product> findByStar(Integer star, Pageable pageable);

	@Query("SELECT f FROM FeedbackProduct f WHERE f.product.id = :productId order by f.star desc")
	Page<FeedbackProduct> getFeedbacksByProductId(@Param("productId") Integer productId, Pageable pageable);

	@Query("SELECT f FROM FeedbackProduct f WHERE f.star = :feedbackStar and f.product.id = :productId")
	Page<FeedbackProduct> getFeedbacksByStarAndProductId(@Param("productId") Integer productId, @Param("feedbackStar") Integer feedbackStar, Pageable pageable);
}
