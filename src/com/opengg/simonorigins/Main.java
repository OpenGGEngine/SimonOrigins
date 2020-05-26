package com.opengg.simonorigins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements KeyListener, MouseListener {
    public static GameState state;

    boolean[] keyCode = new boolean[600];

    static int WIDTH = 600, HEIGHT = 600;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simon Escape");
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);
        Main maindow = new Main();
        state = new GameState(1);
        state.panel = frame;
        frame.addKeyListener(maindow);
        frame.addMouseListener(maindow);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();System.exit(0);
            }
        });
        maindow.setVisible(true);
        frame.add(maindow);
        while(true){
            maindow.repaint();
        }

    }

    public Main(){
        setSize(WIDTH,HEIGHT);
    }

    public void update(float delta){
        float vel = 8f;
        float x = 0;
        float y = 0;
        if(keyCode[KeyEvent.VK_A]){
            x = -vel;
            state.right = false;
        }
        if(keyCode[KeyEvent.VK_D]){
            x = vel;
            state.right = true;
        }
        if(keyCode[KeyEvent.VK_W]){
            y = -vel;
        }
        if(keyCode[KeyEvent.VK_S]){
            y = vel;
        }
        state.player.shooting = keyCode[KeyEvent.VK_SPACE];

        state.player.velocity = new Vec2(x,y);
        state.update(delta);
    }
    @Override
    public void paintComponent(Graphics g) {
        update(0.016f);
        state.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCode[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        state.held = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        state.held = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
