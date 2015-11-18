/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.EmpresaJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Empresa;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sephi_000
 */
public class EmpresaMB {

    /**
     * Creates a new instance of EmpresaMB
     */
    private EmpresaJpaController dao;
    private Empresa empresa;
    private String estado;
    private List<Empresa> filteredEmpresas;
    
    public EmpresaMB() {
        dao = new EmpresaJpaController(JPAUtil.EMF);
        empresa = new Empresa();
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
            empresa = new Empresa();
        } catch (Exception e) {
        }
    }
    
    public void inserir(){
        try {
            dao.create(empresa);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void atualizar(){
        try {
            dao.edit(empresa);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }
    
    public void remover(Empresa emp){
        try {
            dao.destroy(emp.getId());
        } catch (Exception e) {
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public List<Empresa> getEmpresas(){
        return dao.findEmpresaEntities();
    }
    
    public void onRowSelect(SelectEvent event) {
        this.setEmpresa((Empresa) event.getObject());
        estado = "ATUALIZAR";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
