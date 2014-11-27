package clasificadores.genetico;

/**
 *
 * @author Andres
 */
public class MuestraGenetica {
    
    private double clase;
    private double[] args;
    
    /*Este constructor toma como clase el ultimo elemento*/
    public MuestraGenetica(double[] muestra){ 
        this.args = new double[muestra.length-1];
        this.clase = muestra[muestra.length-1];
        System.arraycopy( muestra, 0, this.args, 0, muestra.length-1 );
    }

    public double getClase() {
        return clase;
    }

    public void setClase(double clase) {
        this.clase = clase;
    }

    public double[] getArgs() {
        return args;
    }

    public void setArgs(double[] args) {
        this.args = args;
    }
    
    
    
}
