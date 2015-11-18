/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean.util;

import br.com.melkran.drefc.dao.FormaPagamentoJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.FormaPagamento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "formaPagamento", forClass = FormaPagamento.class)
public class FormaConverter implements Converter {

    private FormaPagamento formaPagamento = new FormaPagamento();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        FormaPagamentoJpaController dao = new FormaPagamentoJpaController(JPAUtil.EMF);
        return dao.findFormaPagamento(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        formaPagamento = (FormaPagamento) value;
        return String.valueOf(formaPagamento.getId());
    }
    
}