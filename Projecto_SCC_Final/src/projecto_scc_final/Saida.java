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
// Classe que representa a saída de um cliente. Deriva de Evento.

public class Saida extends Evento {
        
	//Construtor
    Saida (double i, Simulador s,Servico servico,Cliente c){
        super(i, s,servico,c);
    }

    // Metodo que executa as acçoes correspondentes á saída de um cliente
    @Override
    void executa (){
        
        double instanteChegada = s.getLista().getTempoEntrada(c.getId());
        //System.out.println(instanteChegada+" "+c.getId());
        double tempoServico = s.getInstante()-instanteChegada;
        s.getLista().setTempoServico(tempoServico,c);
        //System.out.println(s.getInstante()+"-"+c.getTempoEntrada()+"="+c.getTempoServico());
        // Retira cliente do serviço
        servico.removeServico();
    }

    // Metodo que descreve o evento.
    // Para ser usado na listagem da lista de eventos.
    public String toString(){
         return "Sa�da em " + instante;
    }


}