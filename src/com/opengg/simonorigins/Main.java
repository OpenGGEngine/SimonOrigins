package com.opengg.simonorigins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JPanel implements KeyListener {
    GameState state = new GameState();
    boolean[] keyCode = new boolean[512];
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simon Escape");
        frame.setSize(600,600);
        frame.setVisible(true);
        Main maindow = new Main();
        frame.addKeyListener(maindow);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();System.exit(0);
            }
        });
        maindow.setVisible(true);
        frame.add(maindow);
        while(true){
            maindow.repaintUp();
        }

    }

    public Main(){
        setSize(600,600);
    }
    public void repaintUp(){
        if(keyCode[KeyEvent.VK_LEFT]){
            state.posX+=0.1;
        }else if(keyCode[KeyEvent.VK_RIGHT]){
            state.posX-=0.1;
        }else if(keyCode[KeyEvent.VK_UP]){
            state.posY-=0.1;
        }else if(keyCode[KeyEvent.VK_DOWN]){
            state.posY+=0.1;
        }
        this.paintComponent(this.getGraphics());
    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawRect(0,0,20,20);
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
}
