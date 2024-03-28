package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<String, CartItem> shoppingCart = new HashMap<>();
    @Autowired
    HttpSession session;
    @Override
    public void add(CartItem newItem) {
//        System.out.println(newItem);


        if (shoppingCart.values().size() == 0) {
            shoppingCart.put(newItem.getProductId() + newItem.getSize(), newItem);
        } else {
            CartItem cartItem = shoppingCart.values().
                    stream().filter(e -> e.getProductId() == newItem.getProductId()
                            && e.getSize().equals(newItem.getSize())).findAny().orElse(null);
            System.out.println(cartItem);

            if (cartItem == null) {

                shoppingCart.put(newItem.getProductId() + newItem.getSize(), newItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            }
        }
//        session.setAttribute("cart",shoppingCart);

    }


    @Override
    public void remove(Integer id,String size) {

//      session.setAttribute("cart",null);
        CartItem cartItem = shoppingCart.values().
                stream().filter(e -> e.getProductId() == id
                        && e.getSize().equals(size)).findAny().orElse(null);
        shoppingCart.remove(id+cartItem.getSize());
    }

    @Override
    public CartItem update(Integer productID, int quantity) {
        CartItem cartItem = shoppingCart.get(productID);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public void clear() {
        shoppingCart.clear();
    }

    @Override
    public double getAmount() {
        return shoppingCart.values().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
    }

    @Override
    public int getCount() {
        return shoppingCart.values().size();
    }

    @Override
    public Collection<CartItem> getAllItems() {
        return shoppingCart.values();
    }

}
