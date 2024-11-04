package com.product_trial.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_trial.config.ProductNotFoundException;
import com.product_trial.dto.ProductCreateDto;
import com.product_trial.dto.ProductUpdateDto;
import com.product_trial.entity.ProductEntity;
import com.product_trial.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products.
     */
    public List<ProductEntity> getAllProducts() {
        return this.productRepository.findAll();
    }

    /**
     * Creates a new product based on the provided ProductCreateDto.
     *
     * @param productCreateDto containing product creation details.
     *
     * @return the created ProductEntity.
     */
    public ProductEntity createProduct(ProductCreateDto productCreateDto) {
        ProductEntity product = new ObjectMapper().convertValue(productCreateDto, ProductEntity.class);
        ProductEntity lastProduct = this.productRepository.findTopByOrderByIdDesc();
        product.init(lastProduct == null ? 1 : lastProduct.getId() + 1);
        return this.productRepository.insert(product);
    }

    /**
     * Updates an existing product with the provided ProductUpdateDto.
     *
     * @param productId of the product to update.
     * @param productUpdateDto containing updated product details.
     *
     * @return the updated ProductEntity.
     *
     * @throws JsonMappingException if there is an error during JSON mapping.
     */
    public ProductEntity updateProduct(Long productId, ProductUpdateDto productUpdateDto) throws JsonMappingException {
        ProductEntity product = this.getProduct(productId);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        product = mapper.updateValue(product, productUpdateDto);
        product.update();
        return this.productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId of the product to retrieve.
     *
     * @return the found ProductEntity.
     *
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public ProductEntity getProduct(Long productId) {
        ProductEntity product = this.productRepository.findFirstById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        } else {
            return product;
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId of the product to delete.
     */
    public void deleteProduct(Long productId) {
        ProductEntity product = this.getProduct(productId);
        this.productRepository.delete(product);
    }
}
