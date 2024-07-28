package net.nvsoftware.internProductService.repository;

import net.nvsoftware.internProductService.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
