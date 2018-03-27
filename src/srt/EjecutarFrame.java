/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc-smart
 */
public class EjecutarFrame implements Runnable{
    int x=0;
    private boolean flag=false;
    private Thread thread;
    private Display a;
    private BufferStrategy bs;
    private Graphics g;
    
    private void init(){
          a  = new Display("Frame SRT");
    }
    
    private void tick(){
        //Logica Del Codigo
    }
    
    private void render(){
        
        bs=a.getCanvas().getBufferStrategy();
        if (bs==null){
            a.getCanvas().createBufferStrategy(3);
            return;
        }
        
        g=bs.getDrawGraphics();
        g.clearRect(0, 0, 800, 600);
        
        dibujarCuadrados();
        dibujarProcesos();
        bs.show();
        g.dispose();
        if (x<730)x++;
    }
    
    
    private void dibujarProcesos(){
        Font font= new Font("Comic Sans MS",3,20);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Proceso A", 30, 30);
        g.setColor(Color.ORANGE);
        g.drawString("Proceso B", 30,120);
        g.setColor(Color.PINK);
        g.drawString("Proceso C", 30,220);
        g.setColor(Color.WHITE);
        g.drawString("Proceso D", 30,320);
        g.setColor(Color.CYAN);
        g.drawString("Proceso E", 30, 420);
    }
    
    private void dibujarCuadrados(){
         g.setColor(Color.red);
         g.fillRect(x, 0, 70, 70);
         g.setColor(Color.ORANGE);
         g.fillRect(x, 100, 70, 70);
         g.setColor(Color.PINK);
         g.fillRect(x, 200, 70, 70);
         g.setColor(Color.WHITE);
         g.fillRect(x, 300, 70, 70);
         g.setColor(Color.CYAN);
         g.fillRect(x, 400, 70, 70);   
    }
    
    
    @Override
    public void run() {
       init();
        while ((flag)){
            tick();
            render();
        }
        stop();
    }
    
    public synchronized void start(){
        if (flag){
            return;
        }
        flag=true;
        thread= new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        if (!flag)
            return;
        flag= false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            	e.printStackTrace();
        }
    }
    
    
    
}
