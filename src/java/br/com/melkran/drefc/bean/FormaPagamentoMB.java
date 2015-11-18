/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.FormaPagamentoJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.FormaPagamento;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class FormaPagamentoMB {

    /**
     * Creates a new instance of FormaPagamentoMB
     */
    private FormaPagamentoJpaController dao;
    private FormaPagamento formaPagamento;
    
    public FormaPagamentoMB() {
        dao = new FormaPagamentoJpaController(JPAUtil.EMF);
        formaPagamento = new FormaPagamento();
    }
    
    public void inserir(){
        try {
            dao.create(formaPagamento);
            formaPagamento = new FormaPagamento();
        } catch (Exception e) {
        }
    }
    
    public void remover(FormaPagamento forPagt){
        try {
            dao.destroy(forPagt.getId());
        } catch (Exception e) {
        }
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public List<FormaPagamento> getFormas(){
        return dao.findFormaPagamentoEntities();
    }
}
