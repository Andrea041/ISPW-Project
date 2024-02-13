package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.AddressBean;
import com.example.codiceprogetto.logic.bean.ShippingBean;
import com.example.codiceprogetto.logic.dao.CustomerDAO;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.CouponBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.CouponDAO;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.AlreadyAppliedCouponException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;

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

        if(customer.getAddress() != null)
            checker = true;

        return checker;
    }

    public void createOrder(AddressBean address, String email) throws DAOException, SQLException {
        Cart cart;
        DeliveryAddress deliveryAddress;
        double finalTotal;

        cart = new CartDAO().retrieveCart(email);
        finalTotal = (cart.getTotal() - (cart.getTotal() * cart.getDiscount())/100) + cart.getShipping();

        if(address == null)
            deliveryAddress = new CustomerDAO().fetchAddress(email);
        else
            deliveryAddress = new DeliveryAddress(address.getState(),
                                                    address.getCity(),
                                                    address.getCountry(),
                                                    address.getPhoneNumber(),
                                                    address.getName(),
                                                    address.getLastName(),
                                                    address.getAddress());

        new OrderDAO().createOrder(email, finalTotal, deliveryAddress, cart.getProducts());
    }

    public void insertAddress(AddressBean address, String email) throws SQLException, EmptyInputException {
        if(address.getState().isEmpty() ||
                address.getCity().isEmpty() ||
                address.getCountry().isEmpty() ||
                address.getPhoneNumber().isEmpty() ||
                address.getName().isEmpty() ||
                address.getLastName().isEmpty() ||
                address.getAddress().isEmpty())
            throw new EmptyInputException("There are some empty fields!");

        DeliveryAddress deliveryAddress = new DeliveryAddress(address.getState(),
                                                                address.getCity(),
                                                                address.getCountry(),
                                                                address.getPhoneNumber(),
                                                                address.getName(),
                                                                address.getLastName(),
                                                                address.getAddress());

        new CustomerDAO().insertAddress(deliveryAddress, email);
    }

    public boolean checkPendingOrder(String email) throws SQLException {
        String status;

        status = new OrderDAO().checkOrderStatus(email);

        if(status == null)
            return false;

        return status.equals(OrderStatus.NEW.getId());
    }
}
