package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class TypeConverter {
    protected String convertAddress(DeliveryAddress address) {
        return address.getName() + "," +
                address.getSurname() + "," +
                address.getAddress() + "," +
                address.getCity() + "," +
                address.getState() + "," +
                address.getPhoneNumber();
    }

    protected DeliveryAddress convertString(String address) {
        String[] elements = address.split(",");

        return new DeliveryAddress(elements[0],
                elements[1],
                elements[2],
                elements[3],
                elements[4],
                elements[5]
        );
    }

    protected String listConverter(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if(list == null)
            return null;

        for(Product product : list) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getPrice()).append(",").append(product.getImage()).append(",");
        }
        return stringBuilder.toString();
    }
    protected List<Product> stringConverter(String list) {
        List<Product> listProd = new ArrayList<>();
        String[] elements = list.split(",");

        for(int i = 0; i < elements.length; i += 5) {
            String name = elements[i];
            String id = elements[i + 1];
            int selectedUnits = Integer.parseInt(elements[i + 2]);
            double price = Double.parseDouble(elements[i + 3]);
            String image = elements[i + 4];

            listProd.add(new Product(name, id, selectedUnits, price, image));
        }
        return listProd;
    }
}
