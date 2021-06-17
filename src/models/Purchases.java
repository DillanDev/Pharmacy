/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author gianluigi
 */
public class Purchases {

    private int id;
    private String purchase_date;
    private double purchase_price;
    private int purchase_amount;
    private int product_id;
    private String product_name;
    private int supplier_id;
    private String supplier_name;

// Constructor sin parámetros
    public Purchases() {
    }

    //Constructor con parámetros
    public Purchases(int id, String purchase_date, double purchase_price, int purchase_amount, int product_id, String product_name, int supplier_id, String supplier_name) {
        this.id = id;
        this.purchase_date = purchase_date;
        this.purchase_price = purchase_price;
        this.purchase_amount = purchase_amount;
        this.product_id = product_id;
        this.product_name = product_name;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
    }
    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(int purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

}
