
package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    private ArrayList<TiposDeAtributos> tipoAtributos;
    private double [][]datos;
    private ArrayList<String> categorias;
    
    
    public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos) 
    {
        tipoAtributos = tipos;
        datos = new double[numDatos][tipos.size()];
        categorias = new ArrayList<>();
        
    }

    public ArrayList<TiposDeAtributos> getTipoAtributos() {
        return tipoAtributos;
    }

    public double[][] getDatos() {
        return datos;
    }

    public ArrayList<String> getCategorias() {
        return categorias;
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
    
    static public void shuffleArray(double[][] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            double a[] = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    
    
    public static Datos cargaDeFichero(String nombreDeFichero) {
        
        Datos datos = null;
        Diccionario diccionario = Diccionario.getInstance();
        Integer contadorNominales = 0;
        try {
            
            ArrayList <TiposDeAtributos> tipoAtributos = new ArrayList<>();
            
            Scanner sc = new Scanner(new File(nombreDeFichero));
            
            /*Primera linea del fichero, numero de datos*/
            int numDatos = Integer.parseInt(sc.nextLine());
            
            
            /*Segunda linea, nombre de los campos*/
            String[] categorias = sc.nextLine().split(",");
             
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
            
            datos.categorias.addAll(Arrays.asList(categorias));
            
            /*Resto de filas, conjunto de datos separados por comas*/
            for(int i=0; (i<numDatos && sc.hasNextLine()); i++) {
            
                String inputData [] = sc.nextLine().split(",");
                
                /* control de errores */
                if(inputData.length == tipoAtributos.size()) {
                    
                    for(int j=0; j<inputData.length; j++) {
                        
                        /* ¿QUE HACEMOS CON LOS NOMINALES? */
                        if(tipoAtributos.get(j).equals(
                                TiposDeAtributos.Nominal)) {
                            
                            if(!diccionario.getDiccionario().containsKey(inputData[j])) {
                                
                                diccionario.getDiccionario().put(inputData[j], contadorNominales);
                            
                                datos.datos[i][j] = contadorNominales++;
                               
                            }                              
                                
                            else
                                datos.datos[i][j] = diccionario.getDiccionario().get(inputData[j]);
                            
                            
                        }
                        else
                            datos.datos[i][j] = Double.parseDouble(inputData[j]);
                    }

                }
                
            }
        
        Datos.shuffleArray(datos.datos);
        
        for (int i = 0; i< numDatos;i++){
            System.out.println();
            for (int j = 0; j< datos.datos[0].length ; j++)
                System.out.print(datos.datos[i][j]+" ");
        }
      
        return datos;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return datos;
        } catch (NumberFormatException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return datos;
        }
    }
}