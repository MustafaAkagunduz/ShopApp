package com.noisy.rrssProject.services;


import com.noisy.rrssProject.model.dto.response.ProductPageResponse;
import com.noisy.rrssProject.model.dto.response.ProductResponse;
import com.noisy.rrssProject.repository.ProductRepository;
import com.noisy.rrssProject.model.dto.mapper.ProductDTOMapper;
import com.noisy.rrssProject.model.dto.request.ProductRequest;
import com.noisy.rrssProject.model.entity.Category;
import com.noisy.rrssProject.model.entity.Merchant;
import com.noisy.rrssProject.model.entity.Product;
import com.noisy.rrssProject.service.CategoryService;
import com.noisy.rrssProject.service.MerchantService;
import com.noisy.rrssProject.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class ProductServiceTest {


    private ProductService productService;
    private ProductRepository productRepository;
    private  ProductDTOMapper productDTOMapper;
    private CategoryService categoryService;
    private MerchantService merchantService;
    @BeforeEach
    void setUp() {
        productDTOMapper = Mockito.mock(ProductDTOMapper.class);
        categoryService = Mockito.mock(CategoryService.class);
        merchantService = Mockito.mock(MerchantService.class);
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository,productDTOMapper,categoryService,merchantService);

    }

    // henuz hata var
    @Test
    void whencreateProductByIdIsCalledWithValid_itShouldReturnProduct() {

        ProductRequest response = new ProductRequest("Item12",
                "this is a description",100,"Category",
                1l,100);
        Product product = getValidProduct();
        Product result = productService.createProduct(response);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, product);

    }


    // henuz hata var
    @Test
    void whenUpdateProductIsCalledWithValid_itShouldReturnProductResponse() {

        ProductRequest response = new ProductRequest("Item12",
                "this is a description",100,"Category",
                1l,100);
        ProductResponse product = getValidProductResponse();
        ProductResponse result = productService.updateProduct(2l,response);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, product);

    }

    //test is passed.
    @Test
    void whenGetProductByMerchantNameIsCalled_itShouldReturnProductResponseList(){
        List<ProductResponse> result = productService.getAllProductsByMerchantName("Furkan");
//        List<ProductResponse> productList =  new List<ProductResponse>();
        Assert.assertNotNull(result);
        for(ProductResponse response: result){
            Assert.assertNotNull(response.merchantName());
            Assert.assertEquals(response.merchantName(), "Furkan");
        }
    }

    //test is passed.
    @Test
    void whenGetPaginatedByMerchantNameIsCalled_itShouldReturnProductPageResponse(){
        int pageNumber = 0;
        int pageSize = 3;

        Product product = getValidProduct();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = createMockedPage(pageSize, productList);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(page);

        ProductPageResponse result =
                productService.getPaginatedByMerchantName(0,3,"Furkan");
//        List<ProductResponse> productList =  new List<ProductResponse>();

        Assert.assertNotNull(result);
        Assert.assertTrue( result.getPageSize() <= 3);
        Assert.assertTrue( result.getPageNo() == 0);

        List<ProductResponse> resultContent = result.getContent();
        for(ProductResponse response: resultContent){
            Assert.assertNotNull(response.merchantName());
            Assert.assertEquals(response.merchantName(), "Furkan");
        }
    }

    //gecemedi page null donuyor sebebi tespit edemedim?
    @Test
    void whenGetPaginatedByCategoryNameIsCalled_itShouldReturnProductPageResponse(){
        int pageNumber = 0;
        int pageSize = 3;

        Product product = getValidProduct();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = createMockedPage(pageSize, productList);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(page);

        ProductPageResponse result =
                productService.getPaginatedByCategoryName(0,3,"Category");
//        List<ProductResponse> productList =  new List<ProductResponse>();

        Assert.assertNotNull(result);
        Assert.assertTrue( result.getPageSize() <= 3);
        Assert.assertTrue( result.getPageNo() == 0);
        Assert.assertNotNull(result.getContent().get(0));

        List<ProductResponse> resultContent = result.getContent();
        for(ProductResponse response: resultContent){
            Assert.assertNotNull(response.categoryName());
            Assert.assertEquals(response.categoryName(), "Category");
        }
    }
    private  Product getValidProduct() {
        Product product = new Product();
        product.setStock(10);
        product.setName("Item12");
        product.setDescription("This is an item");
        product.setPrice(100);
        product.setAvailable(true);
        product.setMerchant(getValidMerchant());
        product.setCategory(getValidCategory());
        return product;
    }
    private Merchant getValidMerchant(){
        Merchant merchant = new Merchant();
        merchant.setName("Furkan");
        merchant.setEmail("furkan@gmail.com");
        merchant.setPassword("furkan123");
        merchant.setId(2L);
        return merchant;
    }
    private Category getValidCategory(){
        Category category = new Category();
        category.setId(2L);
        category.setName("Category");
        return category;
    }

    private ProductResponse getValidProductResponse() {
        ProductResponse response = new ProductResponse(2l,"Item12",
                "this is a description",100,
                "Category", "Taha",100,true);
        return response;
    }

    private Page<Product> createMockedPage(int size, List<Product> responseList) {
        return new Page<Product>() {
            @NotNull
            @Override
            public Iterator<Product> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return size;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Product> getContent() {
                return responseList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
                return null;
            }

            // Implement other methods if needed (e.g., getContent(), getSize(), etc.)
        };
    }

}