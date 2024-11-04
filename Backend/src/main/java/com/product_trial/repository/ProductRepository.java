package com.product_trial.repository;

import com.product_trial.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing {@link ProductEntity} objects in the MongoDB database.
 */
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    /**
     * Retrieves the first product entity with the specified ID.
     *
     * @param productId of the product to retrieve
     *
     * @return the found ProductEntity, or null if no product with the specified ID exists
     */
    ProductEntity findFirstById(Long productId);

    /**
     * Retrieves the most recently added product entity, based on the highest ID.
     *
     * @return the latest ProductEntity, or null if no products exist
     */
    ProductEntity findTopByOrderByIdDesc();
}
