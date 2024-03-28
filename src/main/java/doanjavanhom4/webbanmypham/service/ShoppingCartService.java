package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.CartItem;

import java.util.Collection;

public interface ShoppingCartService {
    void add(CartItem newItem);
    void remove(Integer id, String size);
    CartItem update(Integer productID, int quantity);
    void clear();
    double getAmount();
    int getCount();
    Collection<CartItem> getAllItems();
}
