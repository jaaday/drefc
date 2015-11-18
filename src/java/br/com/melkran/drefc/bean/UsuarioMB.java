/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.UsuarioJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Usuario;

/**
 *
 * @author sephi_000
 */
public class UsuarioMB {

    /**
     * Creates a new instance of UsuarioMB
     */
    private UsuarioJpaController dao;
    private Usuario usuario;
    
    public UsuarioMB() {
        dao = new UsuarioJpaController(JPAUtil.EMF);
        usuario = new Usuario();
    }
    
    public String entrar(){
        if(dao.encontrarUsuario(usuario)){
            return "index";
        } else {
            return "login";
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
