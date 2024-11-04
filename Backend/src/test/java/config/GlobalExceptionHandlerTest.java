package config;

import com.product_trial.config.GlobalExceptionHandler;
import com.product_trial.config.ProductNotFoundException;
import com.product_trial.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleProductNotFoundException() {
        ProductNotFoundException ex = new ProductNotFoundException(12L);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleProductNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertNotNull(response.getBody().get("message"));
        assertEquals("Le produit avec l'identifiant 12 n'existe pas.", response.getBody().get("message"));
    }

    @Test
    void testHandleValidationExceptions() throws NoSuchMethodException {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objet");
        bindingResult.addError(new FieldError("monObjet", "name", "Le nom doit être renseigné"));
        MethodParameter methodParameter = new MethodParameter(ProductEntity.class.getDeclaredMethod("init", Long.class), 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertNotNull(response.getBody().get("name"));
        assertEquals("Le nom doit être renseigné", response.getBody().get("name"));
    }

    @Test
    void testHandleGlobalException() {
        Exception ex = new Exception();

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleGlobalException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertNotNull(response.getBody().get("message"));
        assertEquals("An error has occurred, please try again later.", response.getBody().get("message"));
    }

}