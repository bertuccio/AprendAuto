
package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import particionado.Particion;
/**
 * 
 * @author eps
 */
public class Datos {
    
    public enum TiposDeAtributos {Continuo, Nominal};

    ArrayList<TiposDeAtributos> tipoAtributos;
    double [][]datos;
    
    public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos) 
    {
        
    }
    public Datos extraeDatosTrain(Particion idx) 
    {
        return null;
    }
    public Datos extraeDatosTest(Particion idx) 
    {
        return null;
    }
    public static Datos cargaDeFichero(String nombreDeFichero) 
    {
        Datos dato = null;
        //quitar
        int linecounter = 0;
        try {
            
            Scanner sc = new Scanner(new File(nombreDeFichero));
           
            while(sc.hasNextLine())
            {
                linecounter++;
                String data [] = sc.nextLine().split(", ");
                
            }
            
            dato = new Datos(linecounter , /*/*/null);
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
}