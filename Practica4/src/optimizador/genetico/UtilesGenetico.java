package optimizador.genetico;

import java.util.Random;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class UtilesGenetico {
    
    public static int coinDrop() {
        Random rg = new Random();
        int bin = rg.nextInt(2);
        return bin;

    }
    
    public static int randomNumber(int n) {
        if (n <= 0) return 0;
        Random rg = new Random();
        int num = rg.nextInt(n+1);
        return num;
    } 
    
    public static int randomRangedNumber(int min, int max) {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        
        return randomNum;
    }    
    public static double randomRangedNumberDecimal(int min, int max) {
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        
        return randomNum/ 100.0;
    }     

}
