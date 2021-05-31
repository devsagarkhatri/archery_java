/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Archery;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 *
 * @author Sagar
 */
public class Main extends JFrame
{
    Main()
    {
        this.setTitle("Shadow Archer");
        this.setSize(1280,720);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);        
        Panel ui=new Panel();
        this.add(ui);
        ui.name=JOptionPane.showInputDialog(this,"Enter your name","Enter Username:", QUESTION_MESSAGE);          
        
        ui.requestFocusInWindow();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we)
            { 
                String ObjButtons[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?\n Your score will not be saved","A Shadow Archer",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                
                if(PromptResult==JOptionPane.YES_OPTION)
                {                     
                    dispose();
                    new ArcheryMenu();
                }
            }
        });
    }
    
    public static void main(String args[])
    {
        new Main();
    }
}
