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
import models.Customers;
import models.CustomersDao;
import models.Tables;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customerDao;
    private Administrador views;
    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customer, CustomersDao customerDao, Administrador views) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.views = views;
        // Botón de registrar cliente
        this.views.btn_register_customer.addActionListener(this);
        // Botón de modificar cliente
        this.views.btn_update_customer.addActionListener(this);
        // Botón de eliminar cliente
        this.views.btn_delete_customer.addActionListener(this);
        // Buscador
        this.views.txt_search_customer.addKeyListener(this);
        // Escuchar clics de la tabla
        this.views.customers_table.addMouseListener(this);
         // Escucha de los clics en los label
        this.views.jLabelCustomers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            //Verificar si los campos estan vacios
            if (views.txt_customer_id.getText().equals("") || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("") || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                customer.setId(Integer.parseInt(views.txt_customer_id.getText()));
                customer.setFull_name(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());
                //Llamar al método de registrar
                if (customerDao.registerCustomerQuery(customer)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar clientes
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente");
                }
            }
        } else if (e.getSource() == views.btn_update_customer) {
            if (views.txt_customer_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_customer_id.getText().equals("")
                        && views.txt_customer_fullname.getText().equals("")
                        && views.txt_customer_address.getText().equals("")
                        && views.txt_customer_telephone.getText().equals("")
                        && views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "No ha modificado ningún campo");
                } else {
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText()));
                    customer.setFull_name(views.txt_customer_fullname.getText().trim());
                    customer.setAddress(views.txt_customer_address.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());
                    //Llamar a método de modificar
                    //Validar si todo ha salido bien 
                    if (customerDao.updateCustomerQuery(customer)) {
                        //Limpiar tabla
                        cleanTable();
                        //Limpiar campos
                        cleanFields();
                        //Listar clientes
                        listAllCustomers();
                        JOptionPane.showMessageDialog(null, "Datos del cliente modificados con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del cliente");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {

            int row = views.customers_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para eliminar");
            } else {
                int id = Integer.parseInt((String) views.customers_table.getValueAt(row, 0).toString());
                customerDao.deleteCustomerQuery(id);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar clientes
                listAllCustomers();
                JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
            }
        }
    }

    public void listAllCustomers() {
        Tables color = new Tables();
        views.customers_table.setDefaultRenderer(views.customers_table.getColumnClass(0), color);
        List<Customers> list = customerDao.listCustomerQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.customers_table.getModel();
        //Recorrer la lista
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();

            model.addRow(row);
        }
        views.customers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customers_table) {
            int row = views.customers_table.rowAtPoint(e.getPoint());
            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEnabled(false);
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jTabbedPane1.setSelectedIndex(1);
            // Limpiar tabla
            cleanTable();
            // Limpiar campos
            cleanFields();
            // Listar clientes
            listAllCustomers();
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

    //Limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    //Limpiar campos

    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_customer) {
            //Limpiar tabla
            cleanTable();
            //Listar clientes
            listAllCustomers();
        }
    }
}
