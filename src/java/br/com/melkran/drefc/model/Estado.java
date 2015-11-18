/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findById", query = "SELECT e FROM Estado e WHERE e.id = :id"),
    @NamedQuery(name = "Estado.findByAliquotaIcms", query = "SELECT e FROM Estado e WHERE e.aliquotaIcms = :aliquotaIcms"),
    @NamedQuery(name = "Estado.findByUf", query = "SELECT e FROM Estado e WHERE e.uf = :uf")})
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "aliquota_icms")
    private BigInteger aliquotaIcms;
    @Column(name = "uf")
    private String uf;
    @OneToMany(mappedBy = "origem")
    private Collection<Compra> compraCollection;
    @OneToMany(mappedBy = "destino")
    private Collection<Compra> compraCollection1;
    @OneToMany(mappedBy = "origem")
    private Collection<Venda> vendaCollection;
    @OneToMany(mappedBy = "destino")
    private Collection<Venda> vendaCollection1;

    public Estado() {
    }

    public Estado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getAliquotaIcms() {
        return aliquotaIcms;
    }

    public void setAliquotaIcms(BigInteger aliquotaIcms) {
        this.aliquotaIcms = aliquotaIcms;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection1() {
        return compraCollection1;
    }

    public void setCompraCollection1(Collection<Compra> compraCollection1) {
        this.compraCollection1 = compraCollection1;
    }

    @XmlTransient
    public Collection<Venda> getVendaCollection() {
        return vendaCollection;
    }

    public void setVendaCollection(Collection<Venda> vendaCollection) {
        this.vendaCollection = vendaCollection;
    }

    @XmlTransient
    public Collection<Venda> getVendaCollection1() {
        return vendaCollection1;
    }

    public void setVendaCollection1(Collection<Venda> vendaCollection1) {
        this.vendaCollection1 = vendaCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.melkran.drefc.model.Estado[ id=" + id + " ]";
    }
    
}
