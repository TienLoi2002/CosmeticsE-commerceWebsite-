package doanjavanhom4.webbanmypham.entities;

import lombok.Data;

@Data
public class CartItem {
    private Integer productId;
    private String name;
    private double price;
    private String img;
    private int quantity;
    private String size;


}
