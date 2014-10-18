/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;

/**
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorRegresionLogistica extends Clasificador{
    
    
    
    private double tasaAprendizaje = 0.0001;
    private double[] pesos;
    private int ITERACIONES = 1000;

    
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
        
        double logist = .0;
        
        for (int i = 0; i < pesos.length; i++) {
            logist += pesos[i] * dato[i];
        }
        return sigmoidal(logist);        
        
    }

    @Override
    public void entrenamiento(Datos datosTrain) {
        
        pesos = new double [datosTrain.getCategorias().size()-1];
        
        for (int n = 0; n < ITERACIONES; n++) {

            for (double dato[] : datosTrain.getDatos()) {
                
                double clasePredicha = regresionLogistica(dato);
                double claseReal = dato[datosTrain.getCategorias().size()-1];
                
                for (int i = 0; i < pesos.length; i++) {
                    pesos[i] = pesos[i] + tasaAprendizaje * (claseReal - clasePredicha) * dato[i];
                }
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
    
}
