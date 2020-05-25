package com.opengg.simonorigins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JPanel implements KeyListener {
    public static GameState state = new GameState();
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
        long prevTime = System.nanoTime();
        while(true){
            long time = System.nanoTime();
            maindow.repaintUp((time-prevTime)/(float)(1e9));
            prevTime = time;
        }

    }

    public Main(){
        setSize(600,600);
    }
    public void repaintUp(float delta){
        state.player.velocity = new Vec2(0,0);
        if(keyCode[KeyEvent.VK_LEFT]){
            state.player.velocity = new Vec2(-20f,0);
        }else if(keyCode[KeyEvent.VK_RIGHT]){
            state.player.velocity = new Vec2(20f,0);
        }else if(keyCode[KeyEvent.VK_UP]){
            state.player.velocity = new Vec2(0,-20f);
        }else if(keyCode[KeyEvent.VK_DOWN]){
            state.player.velocity = new Vec2(0,20f);
        }
        this.paintComponent(this.getGraphics());
        state.update(delta);
    }
    @Override
    public void paintComponent(Graphics g) {
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
