
package projecto_scc_final;

/**
 *
 * @author user
 */
public class BombasGasolina extends Servico {
    BombasGasolina(Simulador s,double media,double desvio,int n_maquinas,int semente){
        super(s,media,desvio,n_maquinas,semente);
    }
    
    @Override
    public void insereServico (Cliente c){              
        if (this.listaTempos.isEmpty()){
            double[] w = Aleatorio.gaussiana(this.media_s,this.desvio_s,this.semente); 
            this.listaTempos.add(w[0]);
            this.listaTempos.add(w[1]);           
        }    
        if (estado < n_maquinas){
            estado ++;            
            s.insereEvento(new Transferencia(s.getInstante()+this.listaTempos.remove(0),s,this,c));
        }
        else{
            fila.addElement(c);
        }               
    }
    
    @Override
    public void removeServico (){
               
        atendidos++; // Regista que acabou de atender + 1 cliente
        if (fila.isEmpty()){
            estado --;
        } // Se a fila est� vazia, liberta o servi�o
        else { // Se n�o,
             // vai buscar pr�ximo cliente � fila de espera e
            Cliente c = (Cliente)fila.firstElement();
            fila.removeElementAt(0);

            if (this.listaTempos.isEmpty()){
                double[] w = Aleatorio.gaussiana(this.media_s,this.desvio_s,this.semente);
                this.listaTempos.add(w[0]);
                this.listaTempos.add(w[1]);               
            } 

            // agenda a sua saida para daqui a s.getMedia_serv() instantes
            
            //System.out.println(this.listaTempos.get(0));
            s.insereEvento(new Transferencia(s.getInstante()+this.listaTempos.remove(0),s,this,c));                                                  
        }                              
    }
    
    @Override
    public String relat(){
        String texto_final = "\n-------------------- Resultados gasolina ----------------\n";
        // Tempo m�dio de espera na fila
        temp_med_fila = soma_temp_esp / (atendidos+fila.size());
        // Comprimento m�dio da fila de espera
        // s.getInstante() neste momento � o valor do tempo de simula��o,
        // uma vez que a simula��o come�ou em 0 e este m�todo s� � chamdo no fim da simula��o
        comp_med_fila = soma_temp_esp / s.getInstante();
        // Tempo m�dio de atendimento no servi�o
        utilizacao_serv = (soma_temp_serv / s.getInstante())/this.n_maquinas;
        double finalTemp_serv = (soma_temp_serv/atendidos)/this.n_maquinas;
        // Apresenta resultados
        
        texto_final += "\nTempo médio de espera: "+temp_med_fila
        +"\nComp. médio da fila: "+comp_med_fila
        +"\nUtilização do serviço: "+utilizacao_serv
        +"\nTempo de simulação: "+s.getInstante()
        +"\nNúmero de clientes atendidos: "+atendidos
        +"\nNúmero de clientes na fila: "+fila.size()
        +"\nTempo médio de serviço: "+finalTemp_serv;
        
        return texto_final;
    }
}
