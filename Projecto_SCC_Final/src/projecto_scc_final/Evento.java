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
// Classe de onde vão ser derivados todos os eventos.
// Contém apenas os atributos e métodos comuns a todos os eventos.
// Por isso é uma classe abstracta. Não haverá instâncias desta classe num simulador.

public abstract class Evento {

	protected double instante;  // Instante de ocorr�ncia do evento
	protected Simulador s;      // Simulador onde ocorre o evento
        protected Servico servico;  // Servico associado ao evento
        protected Cliente c;
	//Construtor
    Evento (double i, Simulador s,Servico servico,Cliente c){
        instante = i;
        this.s = s;
        this.servico = servico;
        this.c = c;
    }

	// M�todo que determina se o evento corrente ocorre primeiro, ou n�o, do que o evento e1
	// Se sim, devolve true; se n�o, devolve false
	// Usado para ordenar por ordem crescente de instantes de ocorr�ncia a lista de eventos do simulador
    public boolean menor (Evento e1){
        return (instante < e1.instante);
    }

	// M�todo que executa um evento; a ser definido em cada tipo de evento
    abstract void executa ();

    // M�todo que devolve o instante de ocorr�ncia do evento
    public double getInstante() {
        return instante;
    }
}