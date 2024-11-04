package com.product_trial.config;

/**
 * Exception thrown when a product with a specific identifier is not found.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProductNotFoundException with a detailed message.
     *
     * @param productId of the product that was not found.
     */
    public ProductNotFoundException(Long productId) {
        super(String.format("Le produit avec l'identifiant %d n'existe pas.", productId));
    }
}
