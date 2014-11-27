package clasificadores;

import clasificadores.genetico.Entorno;
import datos.Datos;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;


/**
 * Clasificador que utiliza un modelo KNN
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorGenetico extends Clasificador{

    private int maxGeneraciones;
    private Entorno entorno;
    
    /*Metodos publicos*/
    @Override
    public void entrenamiento(Datos datosTrain) {
        
    }

    
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        return null;
    }

    /**
     * 
     * Main de uso del clasificador Genetico:
     * USO:
     *  Parametros disponibles:
     *      -input file: entrada de datos
     *      -cruzada: particionado con validacion cruzada
     *      -partition: particionado porcentual
     *      -laplace: utilizar suavizado de laplace
     *      -debug: activar trazas de debug          
     * 
     * @param args cadena de opciones "-input file [-cruzada|-partition] [-debug]"
     */
    public static void main(String[] args) {

        String inputFile = "input";
        Integer particion = 5;

        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador clasificador = new ClasificadorGenetico();

        for (int i = 0; i < args.length; i++) {
            
            if (args[i].compareTo("-input") == 0) {
                
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {

                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-particion") == 0) {
                
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);

        double error = 0;
        ArrayList<Double> resultados = Clasificador.validacion(part, d, clasificador, particion);
        for (Double resultado : resultados) {
            error += resultado;
        }
        error /= resultados.size();
        
        System.out.println(error);
    }

    public int getMaxGeneraciones() {
        return maxGeneraciones;
    }

    public void setMaxGeneraciones(int maxGeneraciones) {
        this.maxGeneraciones = maxGeneraciones;
    }

    public Entorno getEntorno() {
        return entorno;
    }

    public void setEntorno(Entorno entorno) {
        this.entorno = entorno;
    }
    
}
