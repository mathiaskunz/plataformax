/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plataformax.swingui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Mathias
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer {

    public Map<String,Integer> presenca;
    public int countIndex = 0;

    public MyListCellRenderer(HashMap<String,Integer> presenca) {
        this.presenca = presenca;
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setText(value.toString());
        
        if (presenca.get((String)value) == 1) {
            setBackground(Color.GREEN);
        }else{
            setBackground(Color.RED);
        }

        if (isSelected) {
            setBackground(getBackground().darker());
        }

        countIndex++;
        return this;
    }
}
