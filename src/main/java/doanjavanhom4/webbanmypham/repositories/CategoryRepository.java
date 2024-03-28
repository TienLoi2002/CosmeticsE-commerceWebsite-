package doanjavanhom4.webbanmypham.repositories;

import doanjavanhom4.webbanmypham.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
    List<Category> search(String keyword);
}