package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.Product;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void removeProductById(int id){
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(int id){
        return productRepository.findById(id);
    }

    public Product findById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public void updateProduct(Product book) {
        productRepository.save(book);
    }

}
