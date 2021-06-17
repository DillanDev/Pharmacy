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
import models.Categories;
import models.CategoriesDao;
import models.DynamicCombobox;
import models.Tables;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDao categoryDao;
    private Administrador views;

    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories category, CategoriesDao categoryDao, Administrador views) {
        this.category = category;
        this.categoryDao = categoryDao;
        this.views = views;
        // Botón de registrar categorias
        this.views.btn_register_category.addActionListener(this);
        // Buscadpr
        this.views.txt_search_category.addKeyListener(this);
        // Obtener nombre de la categoría
        getCategoryName();
        // Auto completado
        AutoCompleteDecorator.decorate(views.cmb_product_category);
        // Escucha de los clics en los label
        this.views.jLabelCategories.addMouseListener(this);
    }

    //Registrar categoría
    @Override
    public void actionPerformed(ActionEvent e) {
        // Si presiona el botón de registrar
        if (e.getSource() == views.btn_register_category) {
            //Verificar si los campos estan vacios
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios ");
            } else {
                //Realizar la insercción
                category.setName(views.txt_category_name.getText().trim());
                //Llamar al método de registrar
                if (categoryDao.registerCategoryQuery(category)) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar clientes
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoría registrada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoría");
                }
            }
        }
    }

    //Listar categoría
    public void listAllCategories() {
        Tables color = new Tables();
        views.categories_table.setDefaultRenderer(views.categories_table.getColumnClass(0), color);
        List<Categories> list = categoryDao.listCategoriesQuery(views.txt_search_category.getText());
        model = (DefaultTableModel) views.categories_table.getModel();
        //Recorrer la lista
        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();

            model.addRow(row);
        }
        views.categories_table.setModel(model);
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
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");

    }

    //Buscador
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_category) {
            //Limpiar tabla
            cleanTable();
            //Listar clientes
            listAllCategories();
        }
    }

    //Método para mostrar el nombre del proveedor
    public void getCategoryName() {
        List<Categories> list = categoryDao.listCategoriesQuery(views.txt_search_category.getText());
        //Recorrer la lista
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            views.cmb_product_category.addItem(new DynamicCombobox(id, name));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
        } else if (e.getSource() == views.jLabelCategories) {
            views.jTabbedPane1.setSelectedIndex(4);
            // Limpiar tabla
            cleanTable();
            // Limpiar campos
            cleanFields();
            // Listar categorias
            listAllCategories();
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

}
