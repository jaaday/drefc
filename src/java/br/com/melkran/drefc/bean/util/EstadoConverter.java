/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean.util;

import br.com.melkran.drefc.dao.EstadoJpaController;
import br.com.melkran.drefc.dao.FormaPagamentoJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Estado;
import br.com.melkran.drefc.model.FormaPagamento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "estado", forClass = Estado.class)
public class EstadoConverter implements Converter {

    private Estado estado = new Estado();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        EstadoJpaController dao = new EstadoJpaController(JPAUtil.EMF);
        return dao.findEstado(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        estado = (Estado) value;
        return String.valueOf(estado.getId());
    }
    
}