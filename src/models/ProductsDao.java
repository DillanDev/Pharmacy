/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gianluigi
 */
public class ProductsDao {

    //Instanciamos la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar producto
    public boolean registerProductQuery(Products product) {

        String query = "INSERT INTO products (id, name, description, unit_price, product_quantity, created, updated, category_id) VALUES (?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getId());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5, 0);
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.setInt(8, product.getCategory_id());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto ");
            return false;
        }
    }

    //Listar productos
    public List listProductsQuery(String value) {

        List<Products> list_products = new ArrayList();

        String query = "SELECT * FROM products";
        String query_search_product = "SELECT * FROM products WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }

            //Recorrer 
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                list_products.add(product);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_products;
    }
    

//Modificar productos
    public boolean updateProductQuery(Products product) {

        String query = "UPDATE products SET name = ?, description = ?, unit_price = ?, category_id = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getUnit_price());
            pst.setInt(4, product.getCategory_id());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el producto " + e.toString());
            return false;
        }
    }

//Eliminar empleaado
    public boolean deleteProductQuery(int id) {
        String query = "DELETE FROM products WHERE id=" + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
    
    // Buscar producto
    public Products searchProduct(int id){
        String query = "SELECT pro.*, cat.name AS name_category FROM products pro INNER JOIN categories cat ON pro.category_id=cat.id WHERE pro.id = ?";
        
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));   
                product.setCategory_id(rs.getInt("category_id"));     
                product.setCategory_name(rs.getString("name_category"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
     
    // Buscar código
//    public Products searchCode(int id) {
//        String query = "SELECT * FROM products WHERE id = ?";
//
//        Products product = new Products();
//        try {
//            conn = cn.getConnection();
//            pst = conn.prepareStatement(query);
//            pst.setInt(1, id);
//            rs = pst.executeQuery();
//            if (rs.next()) {
//                product.setId(rs.getInt("id"));
//                product.setName(rs.getString("name"));
//                product.setUnit_price(rs.getDouble("unit_price"));
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }
//        return product;
//    }
}
