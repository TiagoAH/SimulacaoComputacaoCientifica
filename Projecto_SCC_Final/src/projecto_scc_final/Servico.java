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
import java.util.*;

// Classe que representa um serviço com uma fila de espera associada

public abstract class Servico {
        
    protected int estado; // Vari�vel que regista o estado do servi�o: 0 - livre; 1 - ocupado
    protected int atendidos; // N�mero de clientes atendidos at� ao momento
    protected double temp_ult, soma_temp_esp, soma_temp_serv,temp_med_fila,comp_med_fila, utilizacao_serv; // Vari�veis para c�lculos estat�sticos

    protected double media_s,desvio_s;
    protected int n_maquinas;
    protected int semente;
    protected ArrayList<Double> listaTempos;

    protected Vector<Cliente> fila; // Fila de espera do servi�o
    protected Simulador s; // Refer�ncia para o simulador a que pertence o servi�o

    // Construtor
    Servico (Simulador s,double media,double desvio,int maquinas,int semente){
    	this.s = s;
        fila = new Vector <Cliente>(); // Cria fila de espera
        estado = 0; // Livre
        temp_ult = s.getInstante(); // Tempo que passou desde o �ltimo evento. Neste caso 0, porque a simula��o ainda n�o come�ou.
        atendidos = 0;  // Inicializa��o de vari�veis
        soma_temp_esp = 0;
        soma_temp_serv = 0;

        this.desvio_s = desvio;
        this.media_s = media;
        this.n_maquinas = maquinas;
        this.semente = semente;
        this.listaTempos=new ArrayList<>();
    }

    
    // M�todo que insere cliente (c) no servi�o
    public void insereServico (Cliente c){};          

    // M�todo que remove cliente do servi�o
   public void removeServico (){};
    
	// M�todo que calcula valores para estat�sticas, em cada passo da simula��o ou evento
    public void act_stats(){
        // Calcula tempo que passou desde o �ltimo evento
        double temp_desde_ult = s.getInstante() - temp_ult;
        // Actualiza vari�vel para o pr�ximo passo/evento
        temp_ult = s.getInstante();
        // Contabiliza tempo de espera na fila
        // para todos os clientes que estiveram na fila durante o intervalo
        soma_temp_esp += fila.size() * temp_desde_ult;
        // Contabiliza tempo de atendimento
        soma_temp_serv += estado*temp_desde_ult;

    }

	// M�todo que calcula valores finais estat�sticos
    public String relat(){return "";};
    
    public Vector<Cliente> getFila(){
        return this.fila;
    }
    public void printFila(){
        for (int i=0;i<fila.size();i++){
            System.out.println(fila.get(i).getId());
        }
    }
    public double getTempMedFila(){
        return this.temp_med_fila;
    }
    public double getCompMedFila(){
        return this.comp_med_fila;
    }
    public double getTaxaUtiliz(){
        return this.utilizacao_serv;
    }
    public int getAtendidos(){
        return this.atendidos;
    }
}