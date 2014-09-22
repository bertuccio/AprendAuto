
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
        Datos datosTrain = new Datos(idx.getIndicesTrain().size(),this.tipoAtributos);
        int i = 0;
        for(Integer j : idx.getIndicesTrain()) {
            for(int z=0; z<this.tipoAtributos.size(); z++)
                datosTrain.datos[i][z] = this.datos[j][z];
            i++;
        }
          
        return datosTrain;
    }
    public Datos extraeDatosTest(Particion idx) 
    {
        Datos datosTest = new Datos(idx.getIndicesTest().size(),this.tipoAtributos);
        int i = 0;
        for(Integer j : idx.getIndicesTest()) {
            for(int z=0; z<this.tipoAtributos.size(); z++)
                datosTest.datos[i][z] = this.datos[j][z];
            i++;
        }
          
        return datosTest;
    }
    public static Datos cargaDeFichero(String nombreDeFichero) {
        
        Datos datos = null;
        Diccionario diccionario = new Diccionario();
        int contadorNominales = 0;
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
            
                if(tipoAtrib.compareTo(TiposDeAtributos.Continuo.toString()) 
                        == 0)
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
                                TiposDeAtributos.Nominal)) {
                            
                            if(!diccionario.asd.containsKey(inputData[j]))
                                
                                diccionario.asd.put(inputData[j], 
                                        contadorNominales);
                            
                            datos.datos[i][j] = diccionario.asd.get(inputData[j]);
                            
                        }
                        else
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