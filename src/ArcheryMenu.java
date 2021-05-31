/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Archery;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JOptionPane.*;

/**
 *
 * @author Sagar
 */
class menuPane extends JPanel {
    private BufferedImage pic;
    menuPane()
    {
        init();
    }
       
    public void init()
    {   try
        {
            pic = ImageIO.read(new File("src/Archery/menu.jpg"));        
        }catch(Exception e)
        {
            System.out.println("Image not loaded");
        }
    }
    
    @Override
    public void paintComponent(Graphics g)    
    {
        super.paintComponent(g);
        g.drawImage(pic, 0, 0, 960,720, this);
    }    
}






public class ArcheryMenu extends JFrame {
    public ArcheryMenu()
    {
        this.setTitle("Shadow Archer");
        this.setSize(1325,720);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        menuPane mp=new menuPane();
        buttons bt=new buttons();
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.X_AXIS));
        mp.setPreferredSize(new Dimension(960,720));
        bt.setPreferredSize(new Dimension(445,720));
        
        this.add(mp);
        this.add(bt);
        //this.pack();
        bt.setVisible(true);
        mp.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);               
        this.setVisible(true);
    }
    
    public static void main(String args[])
    {
        new ArcheryMenu();
    }
}
