package de.markuskfrank.cryptocur.main.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AccountCellRenderer extends DefaultTableCellRenderer{
 
	
	    public AccountCellRenderer() {
	    }

	
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {

	        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
	        if(row % 2 == 0){
				cell.setBackground(Color.WHITE);
	        }else {
				cell.setBackground(Color.lightGray);
			}
	        return cell;
	    }
}
