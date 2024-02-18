package dao;

import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.dao.ProductDAOFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class ProductDAOFactoryTest {
    // This method has to fail if there are some error in productDAO creation
    @Test
    public void createProductDAOTest() {
        try {
            ProductDAOFactory prod = new ProductDAOFactory();
            ProductDAO p = prod.createProductDAO();
        } catch (IOException e) {
            Assertions.fail();
        }
    }
}
