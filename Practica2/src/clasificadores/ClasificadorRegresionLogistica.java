/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;

/**
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorRegresionLogistica extends Clasificador{
    
    
    
    private double tasaAprendizaje = 0.0001;
    private double[] pesos;
    private int ITERACIONES = 3000;

    
    public void setTasaAprendizaje(double tasaAprendizaje) {
        this.tasaAprendizaje = tasaAprendizaje;
    }

    public void setITERACIONES(int ITERACIONES) {
        this.ITERACIONES = ITERACIONES;
    }
    

    private double sigmoidal(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double regresionLogistica(double dato[]){
        
        double logist = 0.0;
        
        for (int i = 0; i < pesos.length-1; i++) {
            logist += pesos[i] * dato[i];
        }
        logist += pesos[pesos.length-1] * 1;
        return sigmoidal(logist);        
        
    }

    @Override
    public void entrenamiento(Datos datosTrain) {
        
        int tamanioAtributos = datosTrain.getCategorias().size();
        
        pesos = new double [tamanioAtributos+1]; 
        //Random rand = new Random();

        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = Math.random() * (0.5 - (-0.5)) + (-0.5);
        }

        for (int n = 0; n < ITERACIONES; n++) {

            for (double dato[] : datosTrain.getDatos()) {
                
                double clasePredicha = regresionLogistica(dato);
                double claseReal = dato[datosTrain.getCategorias().size()-1];
                
                for (int i = 0; i < tamanioAtributos; i++) {
                    pesos[i] = pesos[i] + tasaAprendizaje * (claseReal - clasePredicha) * dato[i];
                }
                pesos[tamanioAtributos] = pesos[tamanioAtributos] + tasaAprendizaje * (claseReal - clasePredicha) * 1;
            }
        } 
    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {

        ArrayList<Integer> resultados = new ArrayList<>();
        
        for(double dato[] : datosTest.getDatos()){
                       
            int resultado = (regresionLogistica(dato)>0.5)? 1 : 0;
            resultados.add(resultado);
        }
        
        return resultados;
    }


    /**
     * 
     * Main de uso del clasificador por regresion logistica:
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
        Clasificador clasificador = new ClasificadorRegresionLogistica();

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
        ArrayList<Double> resultados = Clasificador.validacion(
                part, d, clasificador, particion);
        for (Double resultado : resultados) {
            error += resultado;
        }
        error /= resultados.size();
        
        System.out.println(error);
    }
    
}
