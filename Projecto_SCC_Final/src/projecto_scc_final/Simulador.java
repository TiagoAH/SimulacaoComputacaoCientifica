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
public class Simulador {

    // Relógio de simulação - variável que contém o valor do tempo em cada instante
    private double instante;
    // Médias das distribuições de chegadas e de atendimento no serviço
    private double media_chegada;
    
    private int num_empregados;
    private int semente_chegada;
    // Número de clientes que vão ser atendidos
    private int n_clientes;
   
    private int aux_tipo = 0;
    // Serviço - pode haver mais do que um num simulador
    private Servico bombasGasolina;
    private Servico pagamento;
    private Servico bombasGasoleo;
    private Servico selfService;
    // Lista de eventos - onde ficam registados todos os eventos que vão ocorrer na simulação
    // Cada simulador só tem uma
    private ListaEventos lista;
    private ListaClientes listaclientes;

    // Construtor
    public Simulador(double med_ss,double desvio_ss,int n_maq_ss,int semente_ss,int semente_gasoleo, int semente_gasolina, int semente_pagamento, double med_cheg,double media_gasolina,double media_gasoleo,double media_pagamento,double desvio_gasolina,double desvio_gasoleo,double desvio_pagamento,int n_maquinas_gasolina,int n_maquinas_gasoleo,int n_maquinas_pag,int n_empregados, int num_cl,int semente_chegada) {
        // Inicialização de parâmetros do simulador
        this.media_chegada = med_cheg;     
        this.num_empregados = n_empregados;
        this.n_clientes = num_cl;
        this.semente_chegada = semente_chegada;                                      


        // Inicialização do relógio de simulação e dos serviços
        instante = 0;

        // Criação do serviço


        // Criação da lista de eventos
        lista = new ListaEventos(this);
        listaclientes = new ListaClientes();
        
        Cliente.setStaticId(1);
        Cliente c = new Cliente(this.getInstante());
        listaclientes.add1(c);
        
        //Se selfService selecionado agenda a chegada
        if (med_ss>0&&desvio_ss>=0&&n_maq_ss>0&&semente_ss>0){
            selfService = new SelfService(this,med_ss,desvio_ss,n_maq_ss,semente_ss);
            this.insereEvento(new Chegada(instante,this,selfService,semente_chegada,c));            
        }
        //caso contrário escolhe o tipo de serviço(Bomba Gasolina ou Bomba de Gasoleo) e agenda a chegada
        else{
            bombasGasolina = new BombasGasolina(this,media_gasolina,desvio_gasolina,n_maquinas_gasolina,semente_gasolina);
            bombasGasoleo = new BombasGasoleo(this,media_gasoleo,desvio_gasoleo,n_maquinas_gasoleo,semente_gasoleo);
            pagamento = new Pagamento (this,media_pagamento,desvio_pagamento,n_maquinas_pag,semente_pagamento);
            double decisao_bomba = RandomGenerator.rand64(semente_chegada);

            if(decisao_bomba <0.8){
                this.insereEvento (new Chegada(instante, this,bombasGasolina,semente_chegada,c));
            }
            else if(decisao_bomba >= 0.8){
                this.insereEvento (new Chegada(instante, this,bombasGasoleo,semente_chegada,c));
            }                
        }
        // Agendamento da primeira chegada
        // Se não for feito, o simulador não tem eventos para simular
		
    }

        // programa principal
        //public static void main(String[] args) {
        //    // Cria um simulador e
        //    Simulador s = new Simulador();
        //    // põe-o em marcha
        //    s.executa();
        //}

    // Método que insere o evento e1 na lista de eventos

    /**
     *
     * @param e1
     */
    protected void insereEvento (Evento e1){
            lista.insereEvento (e1);
    }
    
    // Calculo dos tempos médios dos serviços (BombaGasoleo, BombaGasolina e Pagamento)
    public String relat(){
        String texto_final = "\n--------------------- Resultados Goblais ---------------------\n";
        double temp_med_fila_final = (this.getBombaGasoleo().getTempMedFila()+this.getBombaGasolina().getTempMedFila()+this.getPagamento().getTempMedFila())/3;
        double comp_med_fila_final = (this.getBombaGasoleo().getCompMedFila()+this.getBombaGasolina().getCompMedFila()+this.getPagamento().getCompMedFila())/3;
        double utilizacao_serv_final = (this.getBombaGasoleo().getTaxaUtiliz()+this.getBombaGasolina().getTaxaUtiliz()+this.getPagamento().getTaxaUtiliz())/3;
        texto_final += "\nTempo médio de espera: "+temp_med_fila_final
        +"\nMédia do comp. médio da fila: "+comp_med_fila_final
        +"\nMédia da utilização do serviço: "+utilizacao_serv_final
        +"\nTempo de simulação: "+this.getInstante()
        +"\nNúmero de clientes atendidos: "+(this.getPagamento().getAtendidos()+this.getBombaGasoleo().getAtendidos()+this.getBombaGasolina().getAtendidos())
        +"\nNúmero de clientes nas filas: "+(this.getPagamento().getFila().size()+this.getBombaGasoleo().getFila().size()+this.getBombaGasolina().getFila().size())
        +"\nMédia do tempo de serviço de cada cliente: "+this.getLista().getMediaServico();
        return texto_final;
    }
    // Método que actualiza os valores estatísticos do simulador
    private void act_stats(){
        if (this.selfService==null){
            bombasGasolina.act_stats();
            pagamento.act_stats();
            bombasGasoleo.act_stats();
        }
        else{
            selfService.act_stats();
        }
    }
        
    // Método executivo do simulador
    public void executa (){
            Evento e1;
            // Se serviço não for selfService 
            if (this.getSelfService()==null){
                while (pagamento.getAtendidos() < n_clientes){
                        //lista.print();  // Mostra lista de eventos - desnecessário; é apenas informativo
                        e1 = (Evento)(lista.removeFirst());  // Retira primeiro evento (é o mais iminente) da lista de eventos
                        instante = e1.getInstante();         // Actualiza relógio de simulação
                        act_stats();                         // Actualiza valores estatísticos
                        e1.executa();                 // Executa evento                       
                }
            }
            else{
                while (pagamento.getAtendidos() < n_clientes){
                        //lista.print();  // Mostra lista de eventos - desnecessário; é apenas informativo
                        e1 = (Evento)(lista.removeFirst());  // Retira primeiro evento (é o mais iminente) da lista de eventos
                        instante = e1.getInstante();         // Actualiza relógio de simulação
                        act_stats();                         // Actualiza valores estatísticos
                        e1.executa();                 // Executa evento
                }
            }
            //relat();  // Apresenta resultados de simulação finais
    }

    // Método que devolve o instante de simulação corrente
    public double getInstante() {
        return this.instante;
    }
    
    public ListaClientes getLista(){
        return listaclientes;
    }
    
    // Método que devolve a média dos intervalos de chegada
    public double getMedia_cheg() {
        return media_chegada;
    }

    // Método que devolve a média dos tempos de serviço
    
    
    
    public Servico getPagamento(){
        return this.pagamento;
    }
    public Servico getBombaGasolina(){
        return this.bombasGasolina;
    }
    public Servico getBombaGasoleo(){
        return this.bombasGasoleo;
    }
    public Servico getSelfService(){
        return this.selfService;
    }
    public int getSizeLista(){
        return this.listaclientes.size();
    }
    public ListaClientes getListaClientes(){
        return this.listaclientes;
    }
}
