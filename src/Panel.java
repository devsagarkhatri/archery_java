/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Archery;

import gameMenu.DBConnection;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author Sagar
 */
public class Panel extends JPanel implements KeyListener
{
    private BufferedImage bg,archer,arrow;
    int x=50,y=100,div=20,xdiv=10,slide=80,arrowx=0,flag=0;
    int count=0;
    int i=0,score=0;
    public String name;   
    String url="jdbc:odbc:Driver={SQL Server};server=GIDEON;DATABASE=Distraction";
    
    
    
    Panel()
    {
        this.addKeyListener(this);       
       setFocusable(true);
       
    }
    
    Panel(String name)
    {
       this.addKeyListener(this);       
       setFocusable(true);
       this.name=name;
    }
    
    @Override
    public void paint(Graphics g1d)
    {
       super.paint(g1d);        
       Graphics2D g=(Graphics2D) g1d;
       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);       
       g.setColor(Color.BLUE);              
       g.drawRect(50, 50, 200, 200);
       try
       {
           bg = ImageIO.read(new File("src/Archery/bg.png"));
           archer=ImageIO.read(new File("src/Archery/archer1.png"));
           arrow=ImageIO.read(new File("src/Archery/arrow.png"));
       }catch(Exception e){}
       
       g.drawImage(bg,0,0,1280,692, this);
       g.drawImage(arrow, 790-arrowx, 340, 100, 10, this);//arrow hai real
       g.drawImage(archer,800,190, this);
       g.setColor(Color.WHITE);
      // g.drawLine(800, 345, 900, 345);//arrow hai
       
       g.drawLine(1110, 0, 1110, 720);
       Font f=new Font("arial",35,38);
       g.setFont(f);
       g.drawString("SCORE", 1120, 40);
       Font f1=new Font("arial",35,32);
       g.setFont(f1);
       g.drawString("+"+score, 1120, 65);
       g.drawString("ARROWS", 1120, 190);
       g.drawRect(1115,200,155,400);
       
       g.setColor(Color.yellow);
       g.fillRect(x, y+slide, 10, 30);
       g.setColor(Color.red);
       g.fillRect(x-xdiv, y-div+slide, 10, 30+2*div);
       g.setColor(Color.blue);
       g.fillRect(x-2*xdiv, y-2*div+slide, 10, 30+4*div);
       g.setColor(Color.LIGHT_GRAY);
       g.fillRect(x-3*xdiv, y-3*div+slide, 10, 30+6*div);
       g.setColor(Color.GRAY);
       g.fillRect(x-4*xdiv, y-4*div+slide, 10, 30+8*div);
       
       if(slide<720)
       {
           slide+=3;           
       }
       else
       {
           slide=-215;
           
       }
       
        if(790-arrowx>20 && flag==1)
        {
            arrowx+=10;                                        
        }
        else
        {
            flag=0;
            arrowx=0;
        }
       
       //arrows left sectio0n
       i=count*25;
       while(i<350)
       {                
            g.drawImage(arrow, 1145, 240+i, 100, 10, this);
            i=i+25;
       }
       
       
       
       //timer class scope here
       if(count>=14)
       {
            count=0;     
                  
            PreparedStatement pst ;
           try {
                
               Connection con=DriverManager.getConnection(url); 
               try {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
               pst=con.prepareStatement("Select * from SinglePlayer where Player_Name= ?");
               pst.setString(1, name);
               ResultSet rs=pst.executeQuery();
               if(rs.next())
               {
                    rs.close();
                    pst.close();
                    pst = con.prepareStatement("update SinglePlayer set Score= ? where Player_Name= ?");                    
                    pst.setString(1, String.valueOf(score));
                    pst.setString(2,name);
                    pst.executeUpdate();
               }
               else
               {
                    rs.close();
                    pst.close();
                    pst = con.prepareStatement("insert into SinglePlayer values( ?,?,? )");
                    pst.setString(1,"Archery");
                    pst.setString(2,name);
                    pst.setString(3, String.valueOf(score));
                    pst.executeUpdate();
               }
               System.out.println(name);
           } catch (SQLException ex) {
               Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(this,"Not able to Connect to Database" +ex.getMessage(), "Shadow Archer", WARNING_MESSAGE); 
           }
            JOptionPane.showMessageDialog(this,name+", Your game is Over\n Your Score is "+score+"\n Database is Updated.", "Shadow Archer", WARNING_MESSAGE); 
            ((Frame)this.getTopLevelAncestor()).dispose();  
            new ArcheryMenu();
       }
       
       
       //logic board
       if(one().intersects(arrowrect()))
       {
           score+=100;
           flag=0;
           //1g.drawString("+100", 1120, 65);
       }
       else if(two().intersects(arrowrect()))
       {
           score+=80;
           flag=0;
           //g.drawString("+60", 1120, 65);
       }
       else if(three().intersects(arrowrect()))
       {
           score+=60;
           flag=0;
           //g.drawString("+60", 1120, 65);
       }
       else if(four().intersects(arrowrect()))
       {
           score+=40;
           flag=0;
           //g.drawString("+40", 1120, 65);
       }
       else if(five().intersects(arrowrect()))
       {
           score+=20;
           flag=0;
           //g.drawString("+20", 1120, 65);
       }
       
       repaint();
       
    }///end paoint method
    
    
    public Rectangle arrowrect()
    {
        return new Rectangle(790-arrowx, 340, 100, 10);
    }
    public Rectangle one()
    {
        return new Rectangle(x, y+slide, 10, 30);
    }    
    
    public Rectangle two()
    {
        return new Rectangle(x-xdiv, y-div+slide, 10, 30+2*div);
    }
    
    public Rectangle three()
    {
        return new Rectangle(x-2*xdiv, y-2*div+slide, 10, 30+4*div);
    }
    
    public Rectangle four()
    {
        return new Rectangle(x-3*xdiv, y-3*div+slide, 10, 30+6*div);
    }
    
    public Rectangle five()
    {
        return new Rectangle(x-4*xdiv, y-4*div+slide, 10, 30+8*div);
    }
     
    
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
       switch(e.getKeyCode())
       {
           case KeyEvent.VK_SPACE :
           case KeyEvent.VK_ENTER :if(790-arrowx>50)
                                    {   
                                        
                                        flag=1;
                                        if(count<14)
                                        {
                                            count++;
                                        }
                                    }
                                    else
                                    {                                     
                                        flag=0;
                                    }
                                    break;
           case KeyEvent.VK_ESCAPE: String ObjButtons[] = {"Yes","No"};
                                    int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?\n Your score will not be saved","A Shadow Archer",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                                    
                                    if(PromptResult==JOptionPane.YES_OPTION)
                                    {                     
                                        ((Frame)this.getTopLevelAncestor()).dispose();
                                        new ArcheryMenu();
                                    }
       }
    }

    @Override
    public void keyReleased(KeyEvent e){}
    
}
