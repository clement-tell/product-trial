package com.product_trial.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.product_trial.dto.ProductCreateDto;
import com.product_trial.entity.ProductEntity;
import com.product_trial.dto.ProductUpdateDto;
import com.product_trial.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity containing the list of all products.
     *
     * @response 200 - Products successfully retrieved.
     * @response 500 - An internal server error occurred.
     */
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        LOGGER.info("All products successfully retrieved");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Creates a product.
     *
     * @param productCreateDto containing the product details for creation. Must be valid.
     *
     * @return ResponseEntity containing the created product.
     *
     * @response 201 - Product successfully created.
     * @response 400 - Invalid productCreateDto provided.
     * @response 500 - An internal server error occurred.
     */
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        ProductEntity createdProduct = productService.createProduct(productCreateDto);
        LOGGER.info("Product successfully created");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId of the product to retrieve.
     *
     * @return ResponseEntity containing the retrieved product.
     *
     * @response 200 Products successfully retrieved.
     * @response 404 Products not found.
     * @response 500 An internal server error occurred.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long productId) {
        ProductEntity product = productService.getProduct(productId);
        LOGGER.info("Product successfully retrieved");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     *
     * @param productId of the product to update.
     * @param productUpdateDto containing the product details for update. Must be valid.
     *
     * @return ResponseEntity containing the updated product.
     *
     * @response 200 - Product successfully updated.
     * @response 400 - Invalid productUpdateDto provided.
     * @response 404 - Products not found.
     * @response 500 - An internal server error occurred.
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductUpdateDto productUpdateDto) throws JsonMappingException {
        ProductEntity product = productService.updateProduct(productId, productUpdateDto);
        LOGGER.info("Products successfully updated");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Deletes an existing product.
     *
     * @param productId of the product to delete.
     *
     * @return ResponseEntity with no content.
     *
     * @response 204 - Product successfully deleted.
     * @response 404 - Products not found.
     * @response 500 - An internal server error occurred.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        LOGGER.info("Product successfully deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}