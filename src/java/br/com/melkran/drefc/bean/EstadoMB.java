/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.EstadoJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Estado;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sephi_000
 */
public class EstadoMB {

    /**
     * Creates a new instance of EstadoMB
     */
    private EstadoJpaController dao;
    private Estado estado;
    private String acao;
    
    public EstadoMB() {
        dao = new EstadoJpaController(JPAUtil.EMF);
        estado = new Estado();
        acao = "INSERIR";
    }
    
    public void acao(){
        try {
            switch (acao) {
                case "INSERIR":
                    inserir();
                    break;
                case "ATUALIZAR":
                    atualizar();
                    break;
            }
estado = new Estado();
        } catch (Exception e) {
        }
    }
    
    public void inserir(){
        try {
            dao.create(estado);
            acao = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void atualizar(){
        try {
            dao.edit(estado); 
            acao = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void remover(Estado est){
        try {
            dao.destroy(est.getId());
        } catch (Exception e) {
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public List<Estado> getEstados(){
        return dao.findEstadoEntities();
    }
    
    public void onRowSelect(SelectEvent event) {
        this.setEstado((Estado) event.getObject());
        acao = "ATUALIZAR";
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }
}
