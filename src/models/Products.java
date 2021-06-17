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
public class Products {
    private int id;
    private String name;
    private String description;
    private double unit_price;
    private int product_quantity;
    private String created;
    private String updated;
    private int category_id;
    private String category_name;
     //Creación del constructor sin parámetros
    
    public Products() {
    }

    //Creación del constructor con parámetros

    public Products(int id, String name, String description, double unit_price, int product_quantity, String created, String updated, int category_id, String category_name) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.product_quantity = product_quantity;
        this.created = created;
        this.updated = updated;
        this.category_id = category_id;
        this.category_name = category_name;
    }


    //Creación de getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
