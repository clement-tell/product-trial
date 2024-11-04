package controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.product_trial.controller.ProductController;
import com.product_trial.dto.ProductCreateDto;
import com.product_trial.dto.ProductUpdateDto;
import com.product_trial.entity.ProductEntity;
import com.product_trial.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTrialControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(new ProductEntity()));

        ResponseEntity<List<ProductEntity>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(new ProductEntity(), response.getBody().get(0));
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(ProductCreateDto.class))).thenReturn(new ProductEntity());
        ProductCreateDto productCreateDto = new ProductCreateDto("Ball", "This is a ball", 12F, "Sport", 3, "ball.png", "FOE-O24-0Y4", "INSTOCK");

        ResponseEntity<ProductEntity> response = productController.createProduct(productCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new ProductEntity(), response.getBody());
        verify(productService, times(1)).createProduct(any(ProductCreateDto.class));
    }

    @Test
    void testGetProduct() {
        when(productService.getProduct(anyLong())).thenReturn(new ProductEntity());

        ResponseEntity<ProductEntity> response = productController.getProduct(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new ProductEntity(), response.getBody());
        verify(productService, times(1)).getProduct(anyLong());
    }

    @Test
    void testUpdateProduct() throws JsonMappingException {
        when(productService.updateProduct(anyLong(), any(ProductUpdateDto.class))).thenReturn(new ProductEntity());

        ResponseEntity<ProductEntity> response = productController.updateProduct(1L, this.getProductUpdateDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new ProductEntity(), response.getBody());
        verify(productService, times(1)).updateProduct(anyLong(), any(ProductUpdateDto.class));
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private ProductUpdateDto getProductUpdateDto() {
        return new ProductUpdateDto("New Ball", "This is a new ball", 14F, "Basketball", 2,"basketball.png", "OUTOFSTOCK");
    }

}