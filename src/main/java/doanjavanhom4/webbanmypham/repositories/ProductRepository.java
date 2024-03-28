package doanjavanhom4.webbanmypham.repositories;


import doanjavanhom4.webbanmypham.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByCategoryId(Integer categoryId);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:searchString%")
    Page<Product> searchProduct(String searchString, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    Page<Product> searchCategoryIsShirt(String keyword, Pageable pageable);
    @Query("SELECT p FROM Product p JOIN p.category ca WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR CONCAT(p.price, '') LIKE %:keyword% OR concat(p.id, '') LIKE %:keyword% OR ca.name LIKE %:keyword%")
    List<Product> searchProductAdmin(String keyword);

}