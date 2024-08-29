package com.productservice.repository;


import com.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT p.id, p.name, p.category_id, p.description,p.price FROM product p WHERE ((?1 <> '' AND (p.name LIKE CONCAT('%',?1,'%') OR p.description LIKE CONCAT('%',?1,'%')  )) OR ?1 = '')  AND (?2 = '' OR p.category_id = ?2)", nativeQuery = true)
    Page<Product> getAllProducts(String keyword, int categoryId, Pageable pageable) throws Exception;
}
