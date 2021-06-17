/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
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
import models.Suppliers;
import models.SuppliersDao;
import models.Tables;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private Administrador views;

    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, Administrador views) {
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.views = views;
        this.views = views;
        // Botón de registrar proveedor
        this.views.btn_register_supplier.addActionListener(this);
        // Botón de modificar proveedor
        this.views.btn_update_supplier.addActionListener(this);
        // Botón de eliminar proveedor
        this.views.btn_delete_supplier.addActionListener(this);
        // Buscador
        this.views.txt_search_supplier.addKeyListener(this);
        // Escucha de los clics en la tabla
        this.views.suppliers_table.addMouseListener(this);
        // Obtener nombre del proveedor
        getSupplierName();   
        // Escucha los clics de los label
        this.views.jLabelSuppliers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si presiona el botón de registrar
        if (e.getSource() == views.btn_register_supplier) {
            //Verificar si los campos estan vacios
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_supplier_country.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios ");
            } else {
                //Realizar la insercción
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCountry(views.cmb_supplier_country.getSelectedItem().toString());

                //Llamar a método de registrar
                //Validar si todo ha salido bien 
                if (supplierDao.registerSupplierQuery(supplier)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar proveedor
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al proveedor");
                }
            }
            // Si presiona el botón de modificar
        } else if (e.getSource() == views.btn_update_supplier) {
            //Verificar si los campos estan vacios
            if (views.txt_supplier_name.getText().equals("")
                    && views.txt_supplier_description.getText().equals("")
                    && views.txt_supplier_address.getText().equals("")
                    && views.txt_supplier_telephone.getText().equals("")
                    && views.txt_supplier_email.getText().equals("")
                    && views.cmb_supplier_country.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "No ha realizado ningún cambio");
            } else {
                //Realizar la modificación
                supplier.setId(Integer.parseInt(views.txt_supplier_id.getText()));
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCountry(views.cmb_supplier_country.getSelectedItem().toString());

                //Llamar a método de modificar
                //Validar si todo ha salido bien 
                if (supplierDao.updateSupplierQuery(supplier)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar proveedor
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Datos del proveedor modificados con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del proveedor");
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {

            int row = views.suppliers_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt((String) views.suppliers_table.getValueAt(row, 0).toString());
                supplierDao.deleteSupplierQuery(id);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar proveedor
                listAllSuppliers();
                JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito");
            }
        }
    }

    public void listAllSuppliers() {
        Tables color = new Tables();
        views.suppliers_table.setDefaultRenderer(views.suppliers_table.getColumnClass(0), color);
        List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
        model = (DefaultTableModel) views.suppliers_table.getModel();
        //Recorrer la lista
        Object[] row = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getDescription();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getTelephone();
            row[5] = list.get(i).getEmail();
            row[6] = list.get(i).getCountry();

            
            model.addRow(row);
        }
        views.suppliers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliers_table) {
            int row = views.suppliers_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_country.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());

            
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEnabled(false);
        }else if (e.getSource() == views.jLabelSuppliers) {
            views.jTabbedPane1.setSelectedIndex(2);
            // Limpiar tabla
            cleanTable();
            // Limpiar campos
            cleanFields();
            // Listar producto
            listAllSuppliers();
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_supplier) {
            //Limpiar tabla
            cleanTable();
            //Listar proveedor
            listAllSuppliers();
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
        views.txt_supplier_id.setText("");
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_supplier_country.setSelectedIndex(0);
    }

    //Método para mostrar el nombre del proveedor
    public void getSupplierName() {
        List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
        //Recorrer la lista
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            //views.cmb_product_supplier.addItem(new DynamicCombobox(id, name));
        }
    }
}
