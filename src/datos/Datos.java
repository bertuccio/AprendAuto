
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

    /* Tantos elementos en el array como columnas tenga el dataset*/
    ArrayList<TiposDeAtributos> tipoAtributos;
    double [][]datos;
    
    public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos) 
    {
        tipoAtributos = tipos;
        datos = new double[numDatos][tipos.size()];
        
    }
    public Datos extraeDatosTrain(Particion idx) 
    {
        return null;
    }
    public Datos extraeDatosTest(Particion idx) 
    {
        return null;
    }
    public static Datos cargaDeFichero(String nombreDeFichero) {
        
        Datos datos = null;

        try {
            
            ArrayList <TiposDeAtributos> tipoAtributos = new ArrayList<>();
            
            Scanner sc = new Scanner(new File(nombreDeFichero));
            
            /*Primera linea del fichero, numero de datos*/
            int numDatos = Integer.parseInt(sc.nextLine());
            
            /*Segunda linea, nombre de los campos*/
            sc.nextLine();
            
            /*Tercera linea, tipos de atributos: Nominal o Continuo*/
            String data [] = sc.nextLine().split(",");
            
            for(String tipoAtrib : data) {
            
                if(tipoAtrib.compareTo("C") == 0)
                    tipoAtributos.add(TiposDeAtributos.Continuo);
                
                else
                    tipoAtributos.add(TiposDeAtributos.Nominal);
            }   
            
            datos = new Datos(numDatos,tipoAtributos);
            
            /*Resto de filas, conjunto de datos separados por comas*/
            for(int i=0; (i<numDatos && sc.hasNextLine()); i++) {
            
                String inputData [] = sc.nextLine().split(",");
                
                /* control de errores */
                if(inputData.length == tipoAtributos.size()) {
                    
                    for(int j=0; j<inputData.length; j++) {
                        
                        /* ¿QUE HACEMOS CON LOS NOMINALES? */
                        if(tipoAtributos.get(j).equals(
                                TiposDeAtributos.Nominal))
                            inputData[j] = "0";
                        
                        datos.datos[i][j] = Double.parseDouble(inputData[j]);
                    }

                }
                
            }
  
            return datos;
            
        } catch (Exception ex) {
            
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return datos;
        }
        
    }
}