/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import Logica.Procesos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
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
    int apuntador = 0;
    int terminados = 0;
    ArrayList <Procesos> procesos_ejecucion = new ArrayList <Procesos>();
    Procesos [] procesos;

    public EjecutarFrame(Procesos []a) {
        procesos = a;
    }
    
    
    
    private void init(){
          a  = new Display("Frame SRT");
    }
    
    private void tick(){
        //Logica Del Codigo
         if (apuntador<5){
                procesos_ejecucion.add(procesos[apuntador]);
                apuntador++;
            }
            if (procesos_ejecucion.get(0).getDuracion()==0){
                System.out.println("Se Termino El Proceso:"+procesos_ejecucion.get(0).getNombre());
                procesos_ejecucion.remove(0);
                terminados++;
            }
            if (procesos_ejecucion.size()>1){
                recorrerProcesos_Ejecucion();
            }   
             if (procesos_ejecucion.size()>0){
                  procesos_ejecucion.get(0).setDuracion(procesos_ejecucion.get(0).getDuracion()-1);
             }
    }
    
    public void recorrerProcesos_Ejecucion(){
        //ForEach para determinar proceso mas corto
        Procesos aux = null;
        Procesos aux2=null;
        for (Procesos procesos1 : procesos_ejecucion) {
            if (aux!=null){
                if (procesos1.getDuracion()<aux.getDuracion()){
                    aux=procesos1;
                }
            }else{
                aux=procesos1;
            }
        }
        if (procesos_ejecucion.get(0)!=aux){
            aux2=procesos_ejecucion.get(0);
            procesos_ejecucion.remove(0);
            procesos_ejecucion.add(aux2);
        }
        procesos_ejecucion.remove(aux);
        procesos_ejecucion.add(0, aux);
        
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
        while ((terminados<5)){
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
