

package datos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

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
    
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    
}
