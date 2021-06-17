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
import models.Employees;
import models.EmployeesDao;
import models.Tables;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class EmployeesController implements ActionListener, MouseListener, KeyListener {

    private Employees employee;
    private EmployeesDao employeeDao;
    private Administrador views;

    DefaultTableModel model = new DefaultTableModel();

    public EmployeesController(Employees employee, EmployeesDao employeeDao, Administrador views) {
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        // Botón de registrar empleado
        this.views.btn_register_employee.addActionListener(this);
        // Botón de modificar empleado
        this.views.btn_update_employee.addActionListener(this);
        // Botón de eliminar empleado
        this.views.btn_delete_employee.addActionListener(this);
        // Buscador
        this.views.txt_search_employee.addKeyListener(this);
        // Escucha de los clics en la tabla
        this.views.employees_table.addMouseListener(this);
        // Escucha los clics del label
        this.views.jLabelEmployees.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si presiona el botón de registrar
        if (e.getSource() == views.btn_register_employee) {
            //Verificar si los campos estan vacios
            if (views.txt_employee_id.getText().equals("")
                    || views.txt_employee_fullname.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_address.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios ");
            } else {
                //Realizar la insercción
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_rol.getSelectedItem().toString());
                //Llamar a método de registrar
                //Validar si todo ha salido bien 
                if (employeeDao.registerEmployeeQuery(employee)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar empleados
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el empleado");
                }
            }
            // Si presiona el botón de modificar
        } else if (e.getSource() == views.btn_update_employee) {
            //Verificar si los campos estan vacios
            if (views.txt_employee_id.getText().equals("")
                    && views.txt_employee_fullname.getText().equals("")
                    && views.txt_employee_username.getText().equals("")
                    && views.txt_employee_address.getText().equals("")
                    && views.txt_employee_telephone.getText().equals("")
                    && views.txt_employee_email.getText().equals("")
                    && views.cmb_rol.getSelectedItem().toString().equals("")
                    && String.valueOf(views.txt_employee_password.getPassword()).equals("")) {

                JOptionPane.showMessageDialog(null, "No ha realizado ningún cambio");
            } else {
                //Realizar la modificación
                employee.setId(Integer.parseInt(views.txt_employee_id.getText()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_rol.getSelectedItem().toString());
                //Llamar a método de modificar
                //Validar si todo ha salido bien 
                if (employeeDao.updateEmployeeQuery(employee)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar empleados
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Datos del empleado modificados con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del empleado");
                }
            }
        } else if (e.getSource() == views.btn_delete_employee) {

            int row = views.employees_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para eliminar");
            } else {
                int id = Integer.parseInt((String) views.employees_table.getValueAt(row, 0).toString());
                employeeDao.deleteEmployeeQuery(id);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar empleados
                listAllEmployees();
                JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
            }
        }
    }

    public void listAllEmployees() {
        Tables color = new Tables();
        views.employees_table.setDefaultRenderer(views.employees_table.getColumnClass(0), color);
        List<Employees> list = employeeDao.listEmployeesQuery(views.txt_search_employee.getText());
        model = (DefaultTableModel) views.employees_table.getModel();
        //Recorrer la lista
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getUsername();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getTelephone();
            row[5] = list.get(i).getEmail();
            row[6] = list.get(i).getRol();

            model.addRow(row);
        }
        views.employees_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.employees_table) {
            int row = views.employees_table.rowAtPoint(e.getPoint());
            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);
            views.txt_employee_id.setEnabled(false);
        } else if (e.getSource() == views.jLabelEmployees) {
            views.jTabbedPane1.setSelectedIndex(3);
            // Limpiar tabla
            cleanTable();
            // Limpiar campos
            cleanFields();
            // Listar clientes
            listAllEmployees();
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
        if (e.getSource() == views.txt_search_employee) {
            //Limpiar tabla
            cleanTable();
            //Listar empleados
            listAllEmployees();
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
        views.txt_employee_id.setText("");
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
    }
}
