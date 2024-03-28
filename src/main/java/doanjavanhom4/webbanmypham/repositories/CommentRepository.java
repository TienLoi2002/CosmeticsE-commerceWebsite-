package doanjavanhom4.webbanmypham.repositories;

import doanjavanhom4.webbanmypham.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByProductId(Integer productId);

    @Query("SELECT c FROM Comment c JOIN c.customer cu JOIN c.product pr WHERE c.content LIKE %:keyword% OR cu.name LIKE %:keyword% OR CONCAT(c.star, '') LIKE %:keyword% OR concat(pr.id, '') LIKE  %:keyword% OR pr.name LIKE  %:keyword%")
    Page<Comment> searchComment(String keyword, Pageable pageable);
    @Query("SELECT COUNT(c.id) FROM Comment c JOIN c.product p where p.id = :productId")
    Integer getTotalCommentsForProduct(@Param("productId") Integer productId);
    @Query("SELECT AVG(c.star) FROM  Comment c WHERE c.product.id = :productId")
    Double getAverageRatingForProduct(@Param("productId") Integer productId);
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.product.id = :productId AND c.star = :star")
    Integer getCountByRating(@Param("productId") Integer productId, @Param("star") int star);

}

