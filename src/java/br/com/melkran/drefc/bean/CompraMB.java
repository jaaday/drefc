/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.CompraJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Compra;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sephi_000
 */
public class CompraMB {

    /**
     * Creates a new instance of CompraMB
     */
    private CompraJpaController dao;
    private Compra compra;
    private String estado;
    private List<Compra> filteredCompras;
    
    public CompraMB() {
        dao = new CompraJpaController(JPAUtil.EMF);
        compra = new Compra();
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
            compra = new Compra();
        } catch (Exception e) {
        }
    }
    
    public void inserir(){
        try {
            dao.create(compra);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void atualizar(){
        try {
            dao.edit(compra);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void remover(Compra ven){
        try {
            dao.destroy(ven.getId());
        } catch (Exception e) {
        }
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    
    public List<Compra> getCompras(){
        return dao.findCompraEntities();
    }
    
    public void onRowSelect(SelectEvent event) {
        this.setCompra((Compra) event.getObject());
        estado = "ATUALIZAR";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
