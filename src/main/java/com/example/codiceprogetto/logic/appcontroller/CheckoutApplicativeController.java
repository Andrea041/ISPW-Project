package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.AddressBean;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.CouponBean;
import com.example.codiceprogetto.logic.bean.ShippingBean;
import com.example.codiceprogetto.logic.dao.*;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.exception.AlreadyAppliedCouponException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.UUID;

public class CheckoutApplicativeController {
    public void checkCouponCode(CouponBean coupon) throws SQLException, AlreadyAppliedCouponException, DAOException {
        int discount;
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        if(cart.getDiscount() != 0)
            throw new AlreadyAppliedCouponException("You have already applied a coupon");

        discount = new CouponDAO().checkCode(coupon.getCode());
        new CartDAO().updateCartCoupon(discount, SessionUser.getInstance().getThisUser().getEmail());
    }

    public void removeCoupon() throws SQLException {
        new CartDAO().updateCartCoupon(0, SessionUser.getInstance().getThisUser().getEmail());
    }

    public void addShipping(ShippingBean shippingBean) throws SQLException {
        new CartDAO().updateCartShipping(shippingBean.getShippingValue(), SessionUser.getInstance().getThisUser().getEmail());
    }

    public CartBean calculateTotal(CartBean cartContent) throws DAOException, SQLException {
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        cartContent.setDiscount(-((cart.getTotal()/100) * cart.getDiscount()));
        cartContent.setTotal(cart.getTotal() + cartContent.getDiscount() + cart.getShipping());
        cartContent.setTax((cart.getTotal()/100) * 22);
        cartContent.setSubtotal(cart.getTotal() - cartContent.getTax());
        cartContent.setShipping(cart.getShipping());

        return cartContent;
    }

    public boolean checkCustomerAddress(String email) throws SQLException, DAOException {
        Customer customer;
        boolean checker = false;

        customer = new CustomerDAO().findCustomer(email);

        if(customer != null && customer.getAddress() != null)
            checker = true;

        return checker;
    }

    public void createOrder(AddressBean address, String email) throws SQLException, DAOException {
        Cart cart;
        DeliveryAddress deliveryAddress;
        double finalTotal;
        Customer customer;
        String orderId;

        cart = new CartDAO().retrieveCart(email);
        finalTotal = (cart.getTotal() - (cart.getTotal() * cart.getDiscount())/100) + cart.getShipping();

        if(address == null) {
            customer = new CustomerDAO().findCustomer(email);
            deliveryAddress = customer.getAddress();
        } else
            deliveryAddress = new DeliveryAddress(address.getState(),
                    address.getCity(),
                    address.getPhoneNumber(),
                    address.getName(),
                    address.getLastName(),
                    address.getAddress());

        orderId = UUID.randomUUID().toString();

        new OrderDAO().createOrder(email, finalTotal, orderId, deliveryAddress, cart.getProducts());
    }

    public void insertAddress(AddressBean address, String email) throws DAOException {
        new AddressDAO().insertAddress(email,
                                        address.getName(),
                                        address.getLastName(),
                                        address.getAddress(),
                                        address.getCity(),
                                        address.getState(),
                                        address.getPhoneNumber());
    }

    public void checkEmptyFieldAddress(AddressBean address) throws EmptyInputException {
        if(address.getState().isEmpty() ||
                address.getCity().isEmpty() ||
                address.getPhoneNumber().isEmpty() ||
                address.getName().isEmpty() ||
                address.getLastName().isEmpty() ||
                address.getAddress().isEmpty())
            throw new EmptyInputException("There are some empty input fields!");
    }
}
