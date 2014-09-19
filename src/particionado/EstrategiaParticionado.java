

package particionado;

import java.util.ArrayList;

public interface EstrategiaParticionado {
    
    public String getNombreEstrategiaParticionado();
    public ArrayList<Particion> crearParticiones(int numDatos, int numParticiones);
}