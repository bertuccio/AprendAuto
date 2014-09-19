
package datos;

import java.util.ArrayList;
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
        return null;
    }
}