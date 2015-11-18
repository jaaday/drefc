/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.dao;

import br.com.melkran.drefc.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.melkran.drefc.model.Estado;
import br.com.melkran.drefc.model.FormaPagamento;
import br.com.melkran.drefc.model.Venda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author sephi_000
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado origem = venda.getOrigem();
            if (origem != null) {
                origem = em.getReference(origem.getClass(), origem.getId());
                venda.setOrigem(origem);
            }
            Estado destino = venda.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getId());
                venda.setDestino(destino);
            }
            FormaPagamento formaPagamentoId = venda.getFormaPagamentoId();
            if (formaPagamentoId != null) {
                formaPagamentoId = em.getReference(formaPagamentoId.getClass(), formaPagamentoId.getId());
                venda.setFormaPagamentoId(formaPagamentoId);
            }
            em.persist(venda);
            if (origem != null) {
                origem.getVendaCollection().add(venda);
                origem = em.merge(origem);
            }
            if (destino != null) {
                destino.getVendaCollection().add(venda);
                destino = em.merge(destino);
            }
            if (formaPagamentoId != null) {
                formaPagamentoId.getVendaCollection().add(venda);
                formaPagamentoId = em.merge(formaPagamentoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getId());
            Estado origemOld = persistentVenda.getOrigem();
            Estado origemNew = venda.getOrigem();
            Estado destinoOld = persistentVenda.getDestino();
            Estado destinoNew = venda.getDestino();
            FormaPagamento formaPagamentoIdOld = persistentVenda.getFormaPagamentoId();
            FormaPagamento formaPagamentoIdNew = venda.getFormaPagamentoId();
            if (origemNew != null) {
                origemNew = em.getReference(origemNew.getClass(), origemNew.getId());
                venda.setOrigem(origemNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getId());
                venda.setDestino(destinoNew);
            }
            if (formaPagamentoIdNew != null) {
                formaPagamentoIdNew = em.getReference(formaPagamentoIdNew.getClass(), formaPagamentoIdNew.getId());
                venda.setFormaPagamentoId(formaPagamentoIdNew);
            }
            venda = em.merge(venda);
            if (origemOld != null && !origemOld.equals(origemNew)) {
                origemOld.getVendaCollection().remove(venda);
                origemOld = em.merge(origemOld);
            }
            if (origemNew != null && !origemNew.equals(origemOld)) {
                origemNew.getVendaCollection().add(venda);
                origemNew = em.merge(origemNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getVendaCollection().remove(venda);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getVendaCollection().add(venda);
                destinoNew = em.merge(destinoNew);
            }
            if (formaPagamentoIdOld != null && !formaPagamentoIdOld.equals(formaPagamentoIdNew)) {
                formaPagamentoIdOld.getVendaCollection().remove(venda);
                formaPagamentoIdOld = em.merge(formaPagamentoIdOld);
            }
            if (formaPagamentoIdNew != null && !formaPagamentoIdNew.equals(formaPagamentoIdOld)) {
                formaPagamentoIdNew.getVendaCollection().add(venda);
                formaPagamentoIdNew = em.merge(formaPagamentoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getId();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            Estado origem = venda.getOrigem();
            if (origem != null) {
                origem.getVendaCollection().remove(venda);
                origem = em.merge(origem);
            }
            Estado destino = venda.getDestino();
            if (destino != null) {
                destino.getVendaCollection().remove(venda);
                destino = em.merge(destino);
            }
            FormaPagamento formaPagamentoId = venda.getFormaPagamentoId();
            if (formaPagamentoId != null) {
                formaPagamentoId.getVendaCollection().remove(venda);
                formaPagamentoId = em.merge(formaPagamentoId);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Venda> gerarRelatorio(FormaPagamento forma, boolean cpf, boolean cnpj, boolean nota) {
        EntityManager em = getEntityManager();
        try{        
            String sqlConsulta = "SELECT v FROM venda v WHERE v.forma_pagamento_id=:forma ";

            if(cpf){
                sqlConsulta += " AND v.cpf IS NOT NULL";
            }
            if(cnpj){
                sqlConsulta += " AND v.cnpj IS NOT NULL";
            }
            if(nota){
                sqlConsulta += " AND v.nota_fiscal IS NOT NULL";
            }
            
            TypedQuery<Venda> query = (TypedQuery<Venda>) em.createNativeQuery(sqlConsulta,Venda.class);
            query.setParameter("forma", forma);
            
            return query.getResultList();
            
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        
    }

}
