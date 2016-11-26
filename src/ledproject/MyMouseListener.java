/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledproject;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author eve
 */
public class MyMouseListener implements MouseListener{
    
    private Robot robot;
    private JButton button;
    public MyMouseListener(Robot robot, JButton button){
        this.robot = robot;
        this.button = button;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
//        setSelectedColor(robot.getPixelColor(e.getX(), e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        setSelectedColor(robot.getPixelColor(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setSelectedColor(robot.getPixelColor(e.getX(), e.getY()));
//	this.setVisible(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public void setSelectedColor(Color color) {
        final Color c = color;
        button.setBackground(color);
    }
    
}
