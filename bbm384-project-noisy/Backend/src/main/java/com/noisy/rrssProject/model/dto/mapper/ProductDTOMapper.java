package com.noisy.rrssProject.model.dto.mapper;

import com.noisy.rrssProject.model.dto.request.ProductRequest;
import com.noisy.rrssProject.model.dto.response.ProductResponse;
import com.noisy.rrssProject.model.entity.Product;
import com.noisy.rrssProject.service.CategoryService;
import com.noisy.rrssProject.service.MerchantService;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductResponse> {

    private final CategoryService categoryService;
    private final MerchantService merchantService;

    public ProductDTOMapper(CategoryService categoryService, MerchantService merchantService) {
        this.categoryService = categoryService;
        this.merchantService = merchantService;
    }

    @Override
    public ProductResponse apply(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName(),
                product.getMerchant().getName(),
                product.getStock(),
                product.isAvailable(),
                product.getImage()
        );
    }

    public Product ProductRequestToProduct(ProductRequest request){
        Product product = new Product();
        product.setAvailable(true);
        product.setCategory(categoryService.getCategoryByName(request.categoryName()));
        product.setMerchant(merchantService.findMerchantById(request.merchantId()));
        product.setCustomers(new HashSet<>());
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setReviews(new HashSet<>());
        product.setStock(request.stock());
        product.setImage(request.image());
        return product;
    }

}
