/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gianluigi
 */
public class Tables extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(
    JTable jtable, Object o, boolean bln, boolean blnl, int row, int col){
        super.getTableCellRendererComponent(jtable, o, blnl, blnl, row, col);
        if (jtable.getValueAt(row, col).toString().equals("Sin stock")){
            setBackground(Color.red);
        }else{
            setBackground(Color.white);
        }
        return this;
    }
}