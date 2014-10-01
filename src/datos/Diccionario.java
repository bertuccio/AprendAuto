

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
