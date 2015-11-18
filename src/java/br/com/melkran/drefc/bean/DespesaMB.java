/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.DespesaJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Despesa;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sephi_000
 */
public class DespesaMB {

    /**
     * Creates a new instance of DespesaMB
     */
private DespesaJpaController dao;
    private Despesa despesa;
    private String estado;
    private List<Despesa> filteredDespesas;
    
    public DespesaMB() {
        dao = new DespesaJpaController(JPAUtil.EMF);
        despesa = new Despesa();
        estado = "INSERIR";
    }
    
    public void acao(){
        try {
            switch (estado) {
                case "INSERIR":
                    inserir();
                    break;
                case "ATUALIZAR":
                    atualizar();
                    break;
            }
            despesa = new Despesa();
        } catch (Exception e) {
        }
    }
    
    public void inserir(){
        try {
            dao.create(despesa);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void atualizar(){
        try {
            dao.edit(despesa);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void remover(Despesa ven){
        try {
            dao.destroy(ven.getId());
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }
    
    public List<Despesa> getDespesas(){
        return dao.findDespesaEntities();
    }
    
    public void onRowSelect(SelectEvent event) {
        this.setDespesa((Despesa) event.getObject());
        estado = "ATUALIZAR";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
