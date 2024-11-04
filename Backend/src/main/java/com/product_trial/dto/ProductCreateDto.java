package com.product_trial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for creating a product.
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCreateDto {

    @NotNull(message = "Le nom doit être renseigné")
    private String name;
    private String description;
    @NotNull(message = "Le prix doit être renseigné")
    private Float price;
    private String category;
    private Integer shellId;
    private String image;
    private String internalReference;
    @Pattern(regexp = "^(INSTOCK|LOWSTOCK|OUTOFSTOCK)$", message = "La valeur doit être INSTOCK, LOWSTOCK ou OUTOFSTOCK")
    private String inventoryStatus;
}