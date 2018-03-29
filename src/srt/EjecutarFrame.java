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
import java.util.HashMap;
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
    HashMap diccionario = new HashMap();
    HashMap diccionario2= new HashMap();
    ArrayList <String> procesos_ejecutados= new ArrayList <String>();
    String actual;
    
    public EjecutarFrame(Procesos []a) {
        procesos = a;
        diccionario.put("A", Color.RED); diccionario.put("B", Color.ORANGE); diccionario.put("C", Color.PINK); diccionario.put("D", Color.WHITE); diccionario.put("E", Color.CYAN);
        diccionario2.put("A", 0);diccionario2.put("B", 100);diccionario2.put("C", 200);diccionario2.put("D", 300);diccionario2.put("E", 400);
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
                 System.out.println("Proceso Actual"+procesos_ejecucion.get(0).getNombre());
                 procesos_ejecutados.add(procesos_ejecucion.get(0).getNombre());
                 actual=procesos_ejecucion.get(0).getNombre();
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
        int ayu=0;
        x=730;
        while (ayu<procesos_ejecutados.size()){
            int posicion=0;
            g.setColor((Color) diccionario.get(procesos_ejecutados.get(ayu)));
            g.fillRect(x, (int) diccionario2.get(procesos_ejecutados.get(ayu)), 20, 20);
            for (int i=0;i<5;i++){
                g.setColor(Color.BLACK);
                if (posicion!=(int) diccionario2.get(procesos_ejecutados.get(ayu))){
                     g.fillRect(x, posicion, 20, 20);
                }
                posicion+=100;
            }
            ayu++;
            x-=30;
        }
    }
    
    
    @Override
    public void run() {
       init();
        while ((terminados<5)){
            tick();
            render();
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(EjecutarFrame.class.getName()).log(Level.SEVERE, null, ex);
           }
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
