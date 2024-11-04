package service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.product_trial.config.ProductNotFoundException;
import com.product_trial.dto.ProductCreateDto;
import com.product_trial.dto.ProductUpdateDto;
import com.product_trial.entity.ProductEntity;
import com.product_trial.repository.ProductRepository;
import com.product_trial.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTrialServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(new ProductEntity()));

        List<ProductEntity> response = productService.getAllProducts();

        assertEquals(1, response.size());
        assertEquals(new ProductEntity(), response.get(0));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testCreateProduct_FirstProduct() {
        when(productRepository.findTopByOrderByIdDesc()).thenReturn(null);

        productService.createProduct(this.getProductCreateDto());

        ArgumentCaptor<ProductEntity> productArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, times(1)).insert(productArgumentCaptor.capture());
        this.verifyCreatedProduct(productArgumentCaptor.getValue());
    }

    @Test
    void testCreateProduct_NotFirstProduct() {
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(56L);
        when(productRepository.findTopByOrderByIdDesc()).thenReturn(existingProduct);

        productService.createProduct(this.getProductCreateDto());

        ArgumentCaptor<ProductEntity> productArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, times(1)).insert(productArgumentCaptor.capture());
        this.verifyCreatedProduct(productArgumentCaptor.getValue());
        assertEquals(57L, productArgumentCaptor.getValue().getId());
    }

    @Test
    void testUpdateProduct() throws JsonMappingException {
        when(productRepository.findFirstById(anyLong())).thenReturn(this.getExistingProduct());

        productService.updateProduct(31L, this.getProductUpdateDto());

        ArgumentCaptor<ProductEntity> productArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        this.verifyUpdatedProduct(productArgumentCaptor.getValue());
    }

    @Test
    void testGetProduct_Found() {
        when(productRepository.findFirstById(anyLong())).thenReturn(this.getExistingProduct());

        ProductEntity response = productService.getProduct(31L);

        verify(productRepository, times(1)).findFirstById(anyLong());
        assertEquals(this.getExistingProduct(), response);
    }

    @Test
    void testGetProduct_NotFound() {
        when(productRepository.findFirstById(anyLong())).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(31L));

        verify(productRepository, times(1)).findFirstById(anyLong());
    }

    @Test
    void testDeleteProduct() {
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(56L);
        when(productRepository.findFirstById(anyLong())).thenReturn(existingProduct);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
    }

    private ProductEntity getExistingProduct() {
        return new ProductEntity(31L, "uah5op98d", "Ball", "This is a ball", 12F, "Sport", 14, 15, "ball.png", "FOE-O24-0Y4", "INSTOCK", Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), null, 4);
    }

    private ProductCreateDto getProductCreateDto() {
        return new ProductCreateDto("Ball", "This is a ball", 12F, "Sport", 15, "ball.png", "FOE-O24-0Y4", "INSTOCK");
    }

    private ProductUpdateDto getProductUpdateDto() {
        return new ProductUpdateDto("New Ball", "This is a new ball", 14F, "Basketball", 12, "basketball.png", "OUTOFSTOCK");
    }

    private void verifyCreatedProduct(ProductEntity createdProduct) {
        assertNotNull(createdProduct.getId());
        assertNotNull(createdProduct.getCode());
        assertEquals(9, createdProduct.getCode().length());
        assertEquals("Ball", createdProduct.getName());
        assertEquals("This is a ball", createdProduct.getDescription());
        assertEquals(12, createdProduct.getPrice());
        assertEquals("Sport", createdProduct.getCategory());
        assertEquals("ball.png", createdProduct.getImage());
        assertNotNull(createdProduct.getCreatedAt());
        assertNull(createdProduct.getUpdatedAt());
        assertEquals(15, createdProduct.getShellId());
        assertEquals("FOE-O24-0Y4", createdProduct.getInternalReference());
        assertEquals("INSTOCK", createdProduct.getInventoryStatus());
        assertNull(createdProduct.getRating());
        assertNull(createdProduct.getQuantity());
    }

    private void verifyUpdatedProduct(ProductEntity updatedProduct) {
        assertEquals(31L, updatedProduct.getId());
        assertEquals("uah5op98d", updatedProduct.getCode());
        assertEquals("New Ball", updatedProduct.getName());
        assertEquals("This is a new ball", updatedProduct.getDescription());
        assertEquals(14, updatedProduct.getPrice());
        assertEquals("Basketball", updatedProduct.getCategory());
        assertEquals(14, updatedProduct.getQuantity());
        assertEquals(12, updatedProduct.getShellId());
        assertEquals("basketball.png", updatedProduct.getImage());
        assertNotNull(updatedProduct.getCreatedAt());
        assertNotNull(updatedProduct.getUpdatedAt());
        assertEquals("FOE-O24-0Y4", updatedProduct.getInternalReference());
        assertEquals("OUTOFSTOCK", updatedProduct.getInventoryStatus());
        assertEquals(4, updatedProduct.getRating());
    }

}