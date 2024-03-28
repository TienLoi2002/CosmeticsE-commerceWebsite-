package doanjavanhom4.webbanmypham.global;

import doanjavanhom4.webbanmypham.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
