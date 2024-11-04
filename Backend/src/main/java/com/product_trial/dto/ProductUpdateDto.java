package com.product_trial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for updating a product.
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUpdateDto {

    private String name;
    private String description;
    private Float price;
    private String category;
    private Integer shellId;
    private String image;
    @Pattern(regexp = "^(INSTOCK|LOWSTOCK|OUTOFSTOCK)$", message = "La valeur doit être INSTOCK, LOWSTOCK ou OUTOFSTOCK")
    private String inventoryStatus;
}