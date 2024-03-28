package doanjavanhom4.webbanmypham.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    private Integer star;
    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_comment_customer"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;
}
