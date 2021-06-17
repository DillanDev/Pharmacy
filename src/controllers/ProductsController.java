/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombobox;
import models.Products;
import models.ProductsDao;
import models.Tables;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class ProductsController implements ActionListener, MouseListener, KeyListener {

    private Products product;
    private ProductsDao productDao;
    private Administrador views;

    DefaultTableModel model = new DefaultTableModel();
     public ProductsController() {
      
    }

    public ProductsController(Products product, ProductsDao productDao, Administrador views) {
        this.product = product;
        this.productDao = productDao;
        this.views = views;
        // Botón de registrar producto
        this.views.btn_register_product.addActionListener(this);
        // Botón de modificar producto
        this.views.btn_update_product.addActionListener(this);
        // Botón de eliminar producto
        this.views.btn_delete_product.addActionListener(this);
        // Buscador
        this.views.txt_search_product.addKeyListener(this);
        // Escucha de los clics en la tabla
        this.views.products_table.addMouseListener(this);
        // Escucha de los clics en los label
        this.views.jLabelProducts.addMouseListener(this);
//        this.views.txt_purchase_product_code.addKeyListener(this);
        this.views.txt_purchase_quantity.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si presiona el botón de registrar
        if (e.getSource() == views.btn_register_product) {
            //Verificar si los campos estan vacios
            if (views.txt_product_id.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios ");
            } else {
                
                //Realizar la insercción
                product.setId(Integer.parseInt(views.txt_product_id.getText().trim()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText().trim()));
                //Obtener los id de categorias
                DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());

                //Llamar a método de registrar
                //Validar si todo ha salido bien 
                if (productDao.registerProductQuery(product)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar productos
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el producto");
                }
            }
            
            // Si presiona el botón de modificar
        } else if (e.getSource() == views.btn_update_product) {
            //Verificar si los campos estan vacios
            if (views.txt_product_id.getText().equals("")
                    && views.txt_product_name.getText().equals("")
                    && views.txt_product_description.getText().equals("")
                    && views.txt_product_unit_price.getText().equals("")
                    && views.cmb_product_category.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "No ha realizado ningún cambio");
            } else {
                //Realizar la modificación
                product.setId(Integer.parseInt(views.txt_product_id.getText().trim()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                //Obtener los id de categorias
                DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                //Llamar a método de modificar
                //Validar si todo ha salido bien 
                if (productDao.updateProductQuery(product)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar productos
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Datos del producto modificados con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del producto");
                }
            }
        } else if (e.getSource() == views.btn_delete_product) {

            int row = views.products_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para eliminar");
            } else {
                int id = Integer.parseInt((String) views.products_table.getValueAt(row, 0).toString());
                productDao.deleteProductQuery(id);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar productos
                listAllProducts();
                JOptionPane.showMessageDialog(null, "Producto eliminado con éxito");
            }
            
        }
    }

    public void listAllProducts() {
        Tables color = new Tables();
        views.products_table.setDefaultRenderer(views.products_table.getColumnClass(0), color);
        List<Products> list = productDao.listProductsQuery(views.txt_search_product.getText());
        model = (DefaultTableModel) views.products_table.getModel();
        //Recorrer la lista
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getDescription();
            row[3] = list.get(i).getUnit_price();
            row[4] = list.get(i).getProduct_quantity();
            row[5] = list.get(i).getCategory_id();

            model.addRow(row);
        }
        views.products_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.products_table) {
            int row = views.products_table.rowAtPoint(e.getPoint());
            views.txt_product_id.setText(views.products_table.getValueAt(row, 0).toString());
            product = productDao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            //Almacenar en cada uno de los campos
            views.txt_product_name.setText(product.getName());
            views.txt_product_description.setText(product.getDescription());
            views.txt_product_unit_price.setText("" + product.getUnit_price());
            views.cmb_product_category.setSelectedItem(new DynamicCombobox(product.getCategory_id(), product.getCategory_name()));
            views.btn_register_product.setEnabled(false);
        } else if (e.getSource() == views.jLabelProducts) {
            views.jTabbedPane1.setSelectedIndex(0);
            // Limpiar tabla
            cleanTable();
            // Limpiar campos
            cleanFields();
            // Listar producto
            listAllProducts();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if (e.getSource() == views.txt_purchase_product_code) {
//            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                if (views.txt_purchase_product_code.getText().equals("")) {
//                    JOptionPane.showMessageDialog(null, "Ingrese el código");
//                } else {
//                    int id = Integer.parseInt(views.txt_purchase_product_code.getText());
//                    product = productDao.searchCode(id);
//                    views.txt_purchase_product_name.setText(product.getName());
//                    views.txt_purchase_id.setText("" + product.getId());
//                    views.txt_purchase_price.setText("" + product.getUnit_price());
//                    views.txt_purchase_quantity.requestFocus();
//                }
//            }
//        }
 }

@Override
        public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_product) {
            //Limpiar tabla
            cleanTable();
            //Listar empleados
            listAllProducts();
        }
        
        if(e.getSource() == views.txt_purchase_quantity){
            int quantity;
            double price;
            if(views.txt_purchase_quantity.getText().equals("")){
                quantity = 1;
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_total_to_pay.setText(""+price);
            }else{
                quantity = Integer.parseInt(views.txt_purchase_quantity.getText()); 
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_total_to_pay.setText(""+quantity * price);
            }
        }
    }

    //Limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    //Limpiar campos
    public void cleanFields() {
        views.txt_product_id.setText("");
        views.txt_product_name.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unit_price.setText("");
        views.cmb_product_category.setSelectedIndex(0);
    }

}
