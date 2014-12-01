package clasificadores;

import clasificadores.genetico.Entorno;
import clasificadores.genetico.evaluacion.Evaluador;
import clasificadores.genetico.evaluacion.FitnessFunction;
import clasificadores.genetico.poblacion.individuo.Individuo;
import clasificadores.genetico.poblacion.individuo.mutacion.Mutacion;
import clasificadores.genetico.poblacion.individuo.mutacion.MutacionClase;
import clasificadores.genetico.poblacion.individuo.mutacion.MutacionGen;
import clasificadores.genetico.recombinacion.Recombinacion;
import clasificadores.genetico.recombinacion.RecombinacionSimple;
import clasificadores.genetico.selecciones.Seleccion;
import clasificadores.genetico.selecciones.SeleccionRuleta;
import datos.Datos;
import datos.Diccionario;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;


/**
 * Clasificador que utiliza un clasificador Genetico
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorGenetico extends Clasificador{

    
    private final double tolerance = 0.001;
    private final int epoch = 1000;
    private final int maxIndividuos = 100;
    private final int maxReglasIni = 20;
    private final double probMutation = 0.1;
    private final double probRecombine = 0.6;
    private final Evaluador evaluator = new FitnessFunction();
    
    private int maxGeneraciones;
    private Entorno entorno;
    
    /*Metodos publicos*/
    @Override
    public void entrenamiento(Datos datosTrain) {
        
        double lastKing =  0;
        
        int nClases = Diccionario.getInstance().getDiccionarioClases().size();
        
        int nAtributos = datosTrain.getCategorias().size() - 1;
        int rangoAtributos = Diccionario.getInstance().getDiccionarioAtributos().size();
        
        this.entorno = new Entorno(maxIndividuos,nClases,nAtributos,rangoAtributos,maxReglasIni,probMutation,probRecombine,evaluator,datosTrain,null);
        
        
        ArrayList<Mutacion> mutaciones = new ArrayList<>();
        mutaciones.add(new MutacionClase());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        mutaciones.add(new MutacionGen());
        
        this.entorno.setMutaciones(mutaciones);
        
        ArrayList<Recombinacion> recombinaciones = new ArrayList<>();
        recombinaciones.add(new RecombinacionSimple());
        
        this.entorno.setCruces(recombinaciones);
        
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        selecciones.add(new SeleccionRuleta());
        
        this.entorno.setSelecciones(selecciones);
    
        for (int i = 0; i< this.epoch; i++){
          this.entorno.epoch();
            if (lastKing < this.entorno.getKing()){
                System.out.println(this.entorno.getKing());
            }
            lastKing = this.entorno.getKing();
        }
        
    }

    
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        this.entorno.setEntornoTesteo(datosTest);
        return this.entorno.test();
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
        Integer particion = 90;

        //EstrategiaParticionado part = new DivisionPorcentual();
        EstrategiaParticionado part = new ValidacionCruzada();
        Clasificador clasificador = new ClasificadorGenetico();

        /*for (int i = 0; i < args.length; i++) {
            
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
        }*/
        Datos d = Datos.cargaDeFichero("scale.data");

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
