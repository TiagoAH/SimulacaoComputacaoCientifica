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
public class Transferencia extends Evento{
    
    Transferencia (double i, Simulador s,Servico servico,Cliente c){
        super(i, s,servico,c);
        
    }

    @Override
    void executa() {
        Servico ant = servico;
        Servico prox = s.getPagamento();
        
        ant.removeServico();
        prox.insereServico(this.c);
    }
    
    @Override
    public String toString(){
        return "Transferencia do cliente das bombas para a loja em:"+instante;
    }
}
