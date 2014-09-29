/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 *  Mmm clase para modelar los datos (La clase datos contiene una matriz de 
 * doubles...cuando existen atributos NOMINALES, es decir, son atributos que
 * no son numeros !!! DEBERIA SER SINGLETON
 * 
 * @author eps
 */
public class Diccionario {
    
    private static Diccionario miDiccionario;
    private  HashMap<String,Integer> diccionario;
     
    
    private Diccionario() { 
    
            diccionario = new HashMap<>();
    }
    
    public static Diccionario getInstance(){
        
        if(miDiccionario == null)
            miDiccionario = new Diccionario();
        
        return miDiccionario;
        
    }

    public  HashMap<String,Integer> getDiccionario() {
        return diccionario;
    }
    
    
}
