/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledproject;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author eve
 */
public class ColorChooser extends JFrame {

    
    JPanel image_panel;
    Dimension screen_size;
    Image background_image = null;
    Robot robot;
    JLabel label;

    public ColorChooser(ArduinoComm arduinocomm) {
        
        screen_size = Toolkit.getDefaultToolkit().getScreenSize();  
        
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        this.setSize(screen_size.width, screen_size.height);
        
        image_panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background_image, 0, 0, null);
            }
        };
        image_panel.setPreferredSize(screen_size);
        
        this.add(image_panel);       
        
        
        JButton button = new JButton("Change LED Color");
        
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150,70));
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);        
        
        image_panel.add(button);
       
        button.setVisible(true);
        
 
        show();
        MyMouseListener mouselsn = new MyMouseListener(robot,button);
        this.addMouseListener(mouselsn);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                java.awt.Color color = mouselsn.getColor();
                arduinocomm.comm(color.getRed(),color.getGreen(),color.getBlue());
            }
        });
        
        
        
    }

    public void show() {
        try {
            // make the screenshot before showing the frame
            Rectangle rect = new Rectangle(0, 0,
                    (int) screen_size.getWidth(),
                    (int) screen_size.getHeight());
            this.robot = new Robot();
            background_image = robot.createScreenCapture(rect);
            super.show();
        } catch (AWTException ex) {
            System.out.println("exception creating screenshot:");
            ex.printStackTrace();
        }
    }

    



}
