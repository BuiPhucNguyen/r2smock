package r2s.MockProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import r2s.MockProject.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
