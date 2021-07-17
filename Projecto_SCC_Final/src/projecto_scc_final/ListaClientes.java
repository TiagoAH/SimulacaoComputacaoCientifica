/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto_scc_final;

import java.util.ArrayList;

/**
 *
 * @author PauloAlexandredosSan
 */
public class ListaClientes {
    
    private ArrayList<Cliente> clientes;
    
    ListaClientes(){
        clientes = new ArrayList<Cliente>();
    }
    public int size(){
        return this.clientes.size();
    }
    public void add1(Cliente c){
        this.clientes.add(c);
    }
    
    public double getTempoEntrada(int id){
        if(!clientes.isEmpty()){
            for(int i = 0;i<clientes.size();i++){
                if(id == clientes.get(i).getId()){
                    return clientes.get(i).getTempoEntrada();
                }
            }
        }
        else{
            System.out.println("[ListaClientes.getTempoEntrada()] Lista de clientes vazia");
        }
        System.out.println("[ListaClientes.getTempoEntrada()] Cliente não encontrado");
        return 0;
    }
    public void setTempoServico(double tempoServico, Cliente c){
        if (!clientes.isEmpty()){
            for (int i=0;i<clientes.size();i++){
                if (c.getId()==clientes.get(i).getId()){
                    clientes.get(i).setTempoServico(tempoServico);
                }
            }
        }
        else{
            System.out.println("[ListaClientes.setTempoServico]Lista de clientes vazias");
        }
    }
    
    public double getMediaServico(){
        if(!clientes.isEmpty()){
            double total=0;
            int numClientes = clientes.size();
            for(int i = 0;i<clientes.size();i++){
                if (clientes.get(i).getTempoServico()==0){
                    numClientes--;
                }
                total += clientes.get(i).getTempoServico();
            }
            return total/(numClientes);
        }
        return 0;
    }
    public String toString(){
        String clientesNaLista = "";
        for (int i=0;i<clientes.size();i++){
            clientesNaLista += "Tempo de entrada: "+clientes.get(i).getTempoEntrada()+" Tempo de serviço: "+clientes.get(i).getTempoServico()+"\n ";
        }
        return clientesNaLista;
    }
}
