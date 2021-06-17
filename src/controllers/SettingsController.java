  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class SettingsController implements MouseListener{
    
    //Instanciar el panel de administración
    private Administrador views;

    public SettingsController(Administrador views) {
        this.views = views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
       if (e.getSource() == views.products_table) {
            int row = views.products_table.rowAtPoint(e.getPoint());
       }    
            else if (e.getSource() == views.jLabelSettings) {
            views.jTabbedPane1.setSelectedIndex(9);
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
        //Verificar en que label está el mouse
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProducts.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(255, 51, 51));
        }else if(e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(255, 51, 51));
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProducts.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(51, 51, 51));
        }else if(e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(51, 51, 51));
        }
    }
    
}
