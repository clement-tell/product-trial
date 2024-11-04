package com.product_trial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * Represents a product entity in the database.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductEntity {

    @Transient
    @JsonIgnore
    private static final Random random = new Random();

    @Id
    private Long id;
    private String code;
    private String name;
    private String description;
    private Float price;
    private String category;
    private Integer quantity;
    private Integer shellId;
    private String image;
    private String internalReference;
    private String inventoryStatus;
    private Date createdAt;
    private Date updatedAt;
    private Integer rating;

    /**
     * Initializes a new product with the specified ID and generates a unique code.
     * Also sets the creation date to the current date.
     *
     * @param id of the product.
     */
    public void init(Long id) {
        this.setId(id);
        this.generateCode();
        this.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * Updates the updated date of the product to the current date.
     */
    public void update() {
        this.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * Generates a random code for the product.
     */
    private void generateCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        int length = 9;
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < 9; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        this.setCode(result.toString());
    }
}
