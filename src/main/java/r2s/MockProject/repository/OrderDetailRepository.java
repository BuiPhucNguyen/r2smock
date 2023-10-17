package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import r2s.MockProject.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
