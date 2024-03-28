package doanjavanhom4.webbanmypham.entities;


import doanjavanhom4.webbanmypham.common.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 10, message = "Số điện thoại chỉ được 10 số")
    @NotBlank(message = "Số điện thoại không được để trống! ")
    private String phone;

    @NotBlank(message = "Email không được để trống! ")
    @Size(max = 50, message = "Email không quá 50 ký tự")
    private String email;
    @NotBlank(message = "Password không được để trống")
    private String password;
    @Size(max = 50, message = "Tên không quá 50 ký tự")
    @NotBlank(message = "Your name is required")
    private String name;

    private Integer gender;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;
    @OneToMany(mappedBy = "customer",cascade=CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orders;
}
