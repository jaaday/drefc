/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.bean;

import br.com.melkran.drefc.dao.VendaJpaController;
import br.com.melkran.drefc.dao.util.JPAUtil;
import br.com.melkran.drefc.model.Venda;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sephi_000
 */
public class VendaMB {

    /**
     * Creates a new instance of VendaMB
     */
    private VendaJpaController dao;
    private Venda venda;
    private String estado;
    private List<Venda> filteredVendas;
    private float valorMin;
    private float valorMax;
    private boolean cpf;
    private boolean cnpj;
    private boolean nota;
    private List<Venda> relatorioLista;

    public VendaMB() {
        dao = new VendaJpaController(JPAUtil.EMF);
        venda = new Venda();
        estado = "INSERIR";
    }

    public void acao() {
        try {
            switch (estado) {
                case "INSERIR":
                    inserir();
                    break;
                case "ATUALIZAR":
                    atualizar();
                    break;
            }
            venda = new Venda();
        } catch (Exception e) {
        }
    }
    
    public void relatorio(){
        this.relatorioLista = dao.gerarRelatorio(venda.getFormaPagamentoId(), cpf, cnpj, nota);
        setCpf(false);
        setCnpj(false);
        setNota(false);
    }

    public void inserir() {
        try {
            dao.create(venda);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }

    public void atualizar() {
        try {
            dao.edit(venda);
            estado = "INSERIR";
        } catch (Exception e) {
        }
    }

    public void remover(Venda ven) {
        try {
            dao.destroy(ven.getId());
        } catch (Exception e) {
        }
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getVendas() {
        return dao.findVendaEntities();
    }

    public void onRowSelect(SelectEvent event) {
        this.setVenda((Venda) event.getObject());
        estado = "ATUALIZAR";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getValorMin() {
        return valorMin;
    }

    public void setValorMin(float valorMin) {
        this.valorMin = valorMin;
    }

    public float getValorMax() {
        return valorMax;
    }

    public void setValorMax(float valorMax) {
        this.valorMax = valorMax;
    }

    public boolean isCpf() {
        return cpf;
    }

    public void setCpf(boolean cpf) {
        this.cpf = cpf;
    }

    public boolean isCnpj() {
        return cnpj;
    }

    public void setCnpj(boolean cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isNota() {
        return nota;
    }

    public void setNota(boolean nota) {
        this.nota = nota;
    }

    public List<Venda> getRelatorioLista() {
        return relatorioLista;
    }

    public void setRelatorioLista(List<Venda> relatorioLista) {
        this.relatorioLista = relatorioLista;
    }

}
