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
// Classe que representa a chegada de um cliente. Deriva de Evento.

public class Chegada extends Evento {
    
    private int semente;
    
    //Construtor
    Chegada (double i, Simulador s,Servico servico,int semente,Cliente c){
        super (i, s,servico,c);
        this.semente = semente;
    }

	// M�todo que executa as ac��es correspondentes � chegada de um cliente
    @Override
    void executa (){
        Servico serv;               
        if (((serv = s.getSelfService())==null)){
            double bomba_check;
            bomba_check = RandomGenerator.rand64(semente);
            if(bomba_check < 0.8){
                serv = s.getBombaGasolina();
            }
            else if(bomba_check >= 0.8){
                serv = s.getBombaGasoleo();
            }
        }
        
        //System.out.println(cliente.getId());
        
        // Coloca cliente no servi�o - na fila ou a ser atendido, conforme o caso
        
        
        // Agenda nova chegada para daqui a Aleatorio.exponencial(s.media_cheg) instantes
        double num = s.getInstante()+Aleatorio.exponencial(s.getMedia_cheg(),semente);
        
        Cliente cliente = new Cliente(num);
        s.getListaClientes().add1(cliente);
        
        // Coloca cliente no servi�o - na fila ou a ser atendido, conforme o caso
        serv.insereServico (cliente);
        
        s.insereEvento (new Chegada(num, s,serv,semente,cliente));
        
    }
    public double getInstante(){
        return this.instante;
    }
    // M�todo que descreve o evento.
    // Para ser usado na listagem da lista de eventos.
    @Override
    public String toString(){
         return "Chegada em " + instante;
    }
}