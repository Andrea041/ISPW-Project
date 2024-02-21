import com.example.codiceprogetto.logic.dao.ProductDAOCsv;
import com.example.codiceprogetto.logic.entities.Product;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductDAOCsvTest {
    @Test
    public void getProducts() throws IOException {
        List<Product> productList = new ProductDAOCsv().fetchAllProduct();

        productList.forEach(System.out::println);

        Assertions.assertNotNull(productList);
    }
}
