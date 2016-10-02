/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.progressbar.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author deivid
 */
@ManagedBean
@ViewScoped
public class ExemploBean implements Serializable {

    //valor entre 0 a 100 que sera usado no progressbar
    private Integer progresso;
    //mensagem de notas sendo processadas ou canceladas 
    private String mensagem;
    //quantidade de notas que o usuario informou
    private Integer quantidadeNotas;
    //notas processadas
    private List<String> notas;

    /**
     * valores padrao de quantidade e notas
     */
    @PostConstruct
    public void init() {
        notas = new ArrayList<>();
        quantidadeNotas = 100;
    }

    /**
     * cria uma mensagem que sera exibida para usuario atraves do componente 
     * p:messages
     * @param texto mensagem informada
     */
    private void criarMensagem(String texto) {
        
        FacesMessage msg = new FacesMessage(texto);
        FacesContext.getCurrentInstance().addMessage("", msg);
    }

    //reseta o progresso e a mensagem do progressbar
    private void resetarProgresso() {
        progresso = 0;
        mensagem = "";
    }

    /**
     * atualiza o progresso
     * @param i posicao da nota na lista
     */
    private void atualizarProgresso(int i) {
        //calculo para o percentual do processo em relacao a quantidade de notas
        progresso = (i * 100) / quantidadeNotas;
        try {
            Thread.sleep(200);
        } catch (Exception ex) {
            criarMensagem("erro ");
        }
    }

    /**
     * processa as notas,podendo ser adicionadas ou canceladas
     * @param acao 1 = notas serao processadas 2 = notas serao canceladas
     */
    public void processarNotas(int acao) {
        try {
            resetarProgresso();
            switch (acao) {
                //processar notas
                case 1:
                    notas = new ArrayList<>();
                    for (int i = 0; i <= quantidadeNotas; i++) {
                        notas.add("nota" + (i + 1));
                        mensagem = "processando a nota" + (i + 1) + " de " 
                                + quantidadeNotas;
                        atualizarProgresso(i);
                    }
                    break;
                    //cancelando notas
                case 2:
                    for (int i = 0; i <= quantidadeNotas; i++) {
                        mensagem = "cancelando a nota" + (i + 1) + " de "
                                + quantidadeNotas;
                        atualizarProgresso(i);
                    }
                    notas = new ArrayList<>();
                    break;
                    //processando notas gerando um erro na metade da operacao
                case 3:
                    notas = new ArrayList<>();
                    for (int i = 0; i <= quantidadeNotas; i++) {
                        notas.add("nota" + (i + 1));
                        mensagem = "processando a nota" + (i + 1) + " de " 
                                + quantidadeNotas;

                        if (i == (quantidadeNotas / 2)) {
                            notas = new ArrayList<>();
                            throw new Exception("erro ao processar notas");                             
                        }
                        atualizarProgresso(i);
                    }
                   break;
                default:
                    break;
            }
            criarMensagem("todas as " + quantidadeNotas + "  notas foram " 
                    + (acao == 1 ? "processadas" : "canceladas"));
            resetarProgresso();
        } catch (Exception e) {
            criarMensagem("erro :");
        }
    }

    public Integer getProgresso() {
        if (progresso == null) {
            progresso = 0;
        }
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }

    public String getMensagem() {
        if (mensagem == null) {
            mensagem = "";
        }
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getQuantidadeNotas() {
        return quantidadeNotas;
    }

    public void setQuantidadeNotas(Integer quantidadeNotas) {
        this.quantidadeNotas = quantidadeNotas;
    }

    public List<String> getNotas() {
        return notas;
    }

    public void setNotas(List<String> notas) {
        this.notas = notas;
    }

}
