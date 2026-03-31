package com.johnteacher.shoppingcart.service.product;

import com.johnteacher.shoppingcart.dto.ProductDto;
import com.johnteacher.shoppingcart.model.Product;
import com.johnteacher.shoppingcart.request.AddProductRequest;
import com.johnteacher.shoppingcart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    public List<ProductDto> getConvertedProducts(List<Product> products);
    public ProductDto convertToDto(Product product);
}
