package dao;

import com.example.codiceprogetto.logic.dao.CustomerDAO;
import com.example.codiceprogetto.logic.exception.DAOException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class CustomerDAOTest {

    // This method has to fail cause the customer with this email does not exist
    @Test
    public void findCustomerTest() {
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            customerDAO.findCustomer("marcoRossi01@gmail.com");
        } catch (SQLException | DAOException ignored) {
            Assertions.fail();
        }
    }
}
