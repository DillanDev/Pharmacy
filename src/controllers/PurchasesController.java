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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Purchases;
import models.PurchasesDao;
import views.Administrador;

/**
 *
 * @author gianluigi
 */
public class PurchasesController implements ActionListener, MouseListener, KeyListener {

    private Purchases purchase;
    private PurchasesDao purchaseDao;
    private Administrador views;

    DefaultTableModel model = new DefaultTableModel();

    public PurchasesController(Purchases purchase, PurchasesDao purchaseDao, Administrador views) {
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.views = views;
//        this.views.txt_purchase_product_code.addKeyListener(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
//       
//        if (e.getSource()== views.txt_purchase_product_code) {
//            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                if (views.txt_purchase_product_code.getText().equals("")) {
//                    JOptionPane.showMessageDialog(null, "Ingrese el código");
//                } else {
//                    int id = Integer.parseInt(views.txt_purchase_product_code.getText());
//                    purchase = purchaseDao.searchCode(id);
//                    views.txt_purchase_product_name.setText(purchase.getProduct_name());
//                    views.txt_purchase_id.setText("" + purchase.getId());
//                    views.txt_purchase_price.setText(""+purchase.getPurchase_price());
//                    views.txt_purchase_quantity.requestFocus();
//                }
//            }
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
