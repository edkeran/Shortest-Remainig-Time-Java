/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;

/**
 *
 * @author reide
 */
public class Calcular {
    int apuntador = 0;
    int terminados = 0;
    ArrayList <Procesos> procesos_ejecucion = new ArrayList <Procesos>();
    Procesos [] procesos;
    public Calcular(Procesos []a){
        procesos = a;
    }
    
    public void Ejecutar(){
        while(terminados<5){
            if (apuntador<5){
                procesos_ejecucion.add(procesos[apuntador]);
                apuntador++;
            }
            if (procesos_ejecucion.get(0).duracion==0){
                System.out.println("Se Termino El Proceso:"+procesos_ejecucion.get(0).getNombre());
                procesos_ejecucion.remove(0);
                terminados++;
            }
            if (procesos_ejecucion.size()>1){
                recorrerProcesos_Ejecucion();
            }   
           //Principal
           
                //if(procesos_ejecucion.get(0).duracion > procesos_ejecucion.get(procesos_ejecucion.size()-1).duracion){
                  //  Procesos aux;
                   // aux=procesos_ejecucion.get(0);
                   // procesos_ejecucion.remove(0);
                   // procesos_ejecucion.add(aux);
                //}
             if (procesos_ejecucion.size()>0){
                procesos_ejecucion.get(0).duracion--;
             }
            
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

    
}
