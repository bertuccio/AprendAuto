

package datos;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 *  Almacena una correspondencia entre una categoría nominal y un identificador
 *  mediante una tabla hash. Diseñado como Singleton.
 * 
 * @author Adrián Lorenzo Mateo
 */
public class Diccionario {
    
    private static Diccionario miDiccionario;
    private  HashMap<String,Integer> clases;
    private  HashMap<String,Integer> atributos; 
    
    private Diccionario() { 
    
            clases = new HashMap<>();
            atributos = new HashMap<>();
    }
    
    public static Diccionario getInstance(){
        
        if(miDiccionario == null)
            miDiccionario = new Diccionario();
        
        return miDiccionario;
        
    }

    public  HashMap<String,Integer> getDiccionarioClases() {
        return clases;
    }
    
    public  HashMap<String,Integer> getDiccionarioAtributos() {
        return atributos;
    }
    
    
}
