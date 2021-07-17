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
// Classe que representa um cliente
// Como são indistintos neste exemplo, está vazia

public class Cliente {
    private int id;
    private double tempoServico;
    private double tempoEntrada;
    private static int idAux = 1;
    
    
    Cliente(double tempo_entrada){
        this.tempoEntrada = tempo_entrada;
        this.id = Cliente.idAux;
        Cliente.idAux++;        
        
    }
    public void setTempoServico(double inst){
        this.tempoServico = inst;
    }
    public int getId(){
        return id;
    }
    public double getTempoEntrada(){
        return tempoEntrada;
    }
    public double getTempoServico(){
        return tempoServico;
    }
    static void setStaticId(int num){
        Cliente.idAux = num;
    }
}

