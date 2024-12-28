package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.ProductNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.ProductDTOMapper;
import com.noisy.rrssProject.model.dto.request.ProductRequest;
import com.noisy.rrssProject.model.dto.response.ProductPageResponse;
import com.noisy.rrssProject.model.dto.response.ProductResponse;
import com.noisy.rrssProject.repository.ProductRepository;
import com.noisy.rrssProject.model.entity.Merchant;
import com.noisy.rrssProject.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;
    private final CategoryService categoryService;
    private final MerchantService merchantService;

    public ProductService(ProductRepository productRepository, ProductDTOMapper productDTOMapper, CategoryService categoryService, MerchantService merchantService) {
        this.productRepository = productRepository;
        this.productDTOMapper = productDTOMapper;
        this.categoryService = categoryService;
        this.merchantService = merchantService;
    }

    public ProductResponse getProductById(Long id){
        return productRepository.findById(id).map(productDTOMapper).orElseThrow(()->
                new ProductNotFoundException("Product could not find by id: "+id));
    }
    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product could not find by id: "+id));
    }
    public List<ProductResponse> getAllProducts() {
        return productRepository.
                findAll().
                stream().
                map(productDTOMapper).
                collect(Collectors.toList());
    }

    public List<ProductResponse> getAllProductsByCategoryName(String name) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().getName().equals(name))
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }
    public Product createProduct(ProductRequest product) {
        return productRepository.save(productDTOMapper.ProductRequestToProduct(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findProductById(id);
        if(request.name()!= null) product.setName(request.name());
        if(request.categoryName()!= null) product.setCategory(categoryService.getCategoryByName(request.categoryName()));
        if(request.description()!= null) product.setDescription(request.description());
        if(request.stock() != product.getStock() && request.stock() >=0) product.setStock(product.getStock()); // TODO: Buraya bakilacak.
        if(!request.merchantId().equals(product.getMerchant().getId())) product.setMerchant(merchantService.findMerchantById(request.merchantId()));
        if(request.price() != product.getPrice()) product.setPrice(request.price());
        product = productRepository.save(product);
        return productDTOMapper.apply(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductResponse getProductMerchantNameAndProductName(String merchantName, String productName) {
        Merchant merchant = merchantService.findMerchantByName(merchantName);

        Product res = merchant.getProducts().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst().orElseThrow(()->new ProductNotFoundException("Product is not founded"));
        return productDTOMapper.apply(res);

    }

    public List<ProductResponse> getAllProductsByMerchantName(String name) {
        return productRepository.findAll().stream()
                .filter(product -> product.getMerchant().getName().equals(name))
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }

    public ProductPageResponse getPaginatedByName(int pageNo, int size, String name, Function<Product, String> nameExtractor) {
        Pageable pageable = PageRequest.of(pageNo, size);

        Page<Product> page = productRepository.findAll(pageable);

        List<ProductResponse> content = page.getContent().stream()
                .filter(product -> nameExtractor.apply(product).equals(name))
                .map(productDTOMapper)
                .collect(Collectors.toList());

        return getProductPageResponse(content, page);
    }

    public ProductPageResponse getPaginatedByCategoryName(int pageNo, int size, String categoryName) {
        return getPaginatedByName(pageNo, size, categoryName, product -> product.getCategory().getName());
    }

    public ProductPageResponse getPaginatedByMerchantName(int pageNo, int size, String merchantName) {
        return getPaginatedByName(pageNo, size, merchantName, product -> product.getMerchant().getName());
    }


    public ProductPageResponse getPaginatedAll(int pageNo, int size)
    {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> content = page.getContent();
        List<ProductResponse> productResponseList =content.stream().map(productDTOMapper).collect(Collectors.toList());

        pageable.getPageNumber();

        return getProductPageResponse(productResponseList, page);
    }

    private ProductPageResponse getProductPageResponse(List<ProductResponse> content, Page<Product> page) {
        ProductPageResponse productPageResponse = new ProductPageResponse();
        productPageResponse.setContent(content);
        productPageResponse.setPageNo(page.getNumber());
        productPageResponse.setPageSize(page.getSize());
        productPageResponse.setTotalElements(page.getTotalElements());
        productPageResponse.setTotalPages(page.getTotalPages());
        productPageResponse.setLast(page.isLast());
        return productPageResponse;
    }

}
