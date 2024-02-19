package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAOCsv implements ProductDAO {
    private final File fd;
    private static final String CSV_FILE_NAME = "productCSV.csv";
    private static final int INDEX_PRODUCT_NAME = 0;
    private static final int INDEX_PRODUCT_ID = 1;
    private static final int INDEX_PRICE = 2;
    private static final int INDEX_PROD_IMAGE = 3;

    public ProductDAOCsv() throws IOException {
        this.fd = new File(CSV_FILE_NAME);

        if(!fd.exists()) {
            throw new IOException(CSV_FILE_NAME + " file does not exist");
        }
    }

    @Override
    public Product fetchProduct(String prodID) {
        List<Product> productList = fetchAllProduct();

        Optional<Product> prod = productList.stream().filter(product -> product.getId().equals(prodID)).findFirst();

        return prod.orElse(null);
    }

    @Override
    public List<Product> fetchAllProduct() {
        return getProducts();
    }

    private List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] recordString;

            while((recordString = csvReader.readNext()) != null) {
                String productName = recordString[INDEX_PRODUCT_NAME];
                String productID = recordString[INDEX_PRODUCT_ID];
                double productPrice = Double.parseDouble(recordString[INDEX_PRICE]);
                String productImage = recordString[INDEX_PROD_IMAGE];

                Product product = new Product(productName, productID, productPrice, productImage);

                productList.add(product);
            }
        } catch (FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid file descriptor");
        } catch (IOException | CsvValidationException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error reading CSV file");
        }

        return productList;
    }
}
