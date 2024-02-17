package com.example.codiceprogetto.logic.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAOFactory {
    public ProductDAO createProductDAO() throws IOException {
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = new FileInputStream("src/main/java/com/example/codiceprogetto/logic/utils/configuration.properties");

            properties.load(input);
        } catch (FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("File db.properties not found, %s", e));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (input != null) input.close();
        }

        String categoryDaoType = properties.getProperty("PRODUCTS_DAO_TYPE");

        return switch (categoryDaoType) {
            case "jdbc" -> new ProductDAOJdbc();
            case "csv" -> new ProductDAOCsv();
            default -> throw new IOException("Configuration file error");
        };
    }
}
