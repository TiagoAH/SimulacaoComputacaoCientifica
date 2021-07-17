/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto_scc_final;


/**
 *
 * @author PauloAlexandredosSan
 */
// Classe para geração de números aleatórios segundos várias distribuições
// Apenas a distribuição exponencial negativa está definida

public class Aleatorio {

	// Gera um número segundo uma distribuição exponencial negativa de média m
    static double exponencial (double m,int semente){
        return (-m*Math.log(RandomGenerator.rand64(semente)));
            
    }
    static double[] gaussiana (double m,double desvio,int semente){
        double w;
        double v1,v2,y1,y2;
        double[] x = new double[2];
       
        do{
            do{

            double u1 = RandomGenerator.rand64(semente);
            double u2 = RandomGenerator.rand64(semente);
            v1 = 2*u1 - 1;
            v2 = 2*u2 - 1;
            w = Math.pow(v1,2)+Math.pow(v2,2);

            }while(w>1);

            y1 = v1* Math.sqrt((-2*Math.log(w)))/w;
            y2 = v2* Math.sqrt((-2*Math.log(w)))/w;
            
            x[0] = m + desvio*y1;
            x[1] = m + desvio*y2;

        }while(x[0]<0||x[1]<0);
        
        return x;
    }

}

