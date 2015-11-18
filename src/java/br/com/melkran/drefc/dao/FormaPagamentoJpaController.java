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
import br.com.melkran.drefc.model.Compra;
import br.com.melkran.drefc.model.FormaPagamento;
import java.util.ArrayList;
import java.util.Collection;
import br.com.melkran.drefc.model.Venda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class FormaPagamentoJpaController implements Serializable {

    public FormaPagamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaPagamento formaPagamento) {
        if (formaPagamento.getCompraCollection() == null) {
            formaPagamento.setCompraCollection(new ArrayList<Compra>());
        }
        if (formaPagamento.getVendaCollection() == null) {
            formaPagamento.setVendaCollection(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : formaPagamento.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getId());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            formaPagamento.setCompraCollection(attachedCompraCollection);
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : formaPagamento.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getId());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            formaPagamento.setVendaCollection(attachedVendaCollection);
            em.persist(formaPagamento);
            for (Compra compraCollectionCompra : formaPagamento.getCompraCollection()) {
                FormaPagamento oldFormaPagamentoIdOfCompraCollectionCompra = compraCollectionCompra.getFormaPagamentoId();
                compraCollectionCompra.setFormaPagamentoId(formaPagamento);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldFormaPagamentoIdOfCompraCollectionCompra != null) {
                    oldFormaPagamentoIdOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldFormaPagamentoIdOfCompraCollectionCompra = em.merge(oldFormaPagamentoIdOfCompraCollectionCompra);
                }
            }
            for (Venda vendaCollectionVenda : formaPagamento.getVendaCollection()) {
                FormaPagamento oldFormaPagamentoIdOfVendaCollectionVenda = vendaCollectionVenda.getFormaPagamentoId();
                vendaCollectionVenda.setFormaPagamentoId(formaPagamento);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldFormaPagamentoIdOfVendaCollectionVenda != null) {
                    oldFormaPagamentoIdOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldFormaPagamentoIdOfVendaCollectionVenda = em.merge(oldFormaPagamentoIdOfVendaCollectionVenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaPagamento formaPagamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaPagamento persistentFormaPagamento = em.find(FormaPagamento.class, formaPagamento.getId());
            Collection<Compra> compraCollectionOld = persistentFormaPagamento.getCompraCollection();
            Collection<Compra> compraCollectionNew = formaPagamento.getCompraCollection();
            Collection<Venda> vendaCollectionOld = persistentFormaPagamento.getVendaCollection();
            Collection<Venda> vendaCollectionNew = formaPagamento.getVendaCollection();
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getId());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            formaPagamento.setCompraCollection(compraCollectionNew);
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getId());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
            vendaCollectionNew = attachedVendaCollectionNew;
            formaPagamento.setVendaCollection(vendaCollectionNew);
            formaPagamento = em.merge(formaPagamento);
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    compraCollectionOldCompra.setFormaPagamentoId(null);
                    compraCollectionOldCompra = em.merge(compraCollectionOldCompra);
                }
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    FormaPagamento oldFormaPagamentoIdOfCompraCollectionNewCompra = compraCollectionNewCompra.getFormaPagamentoId();
                    compraCollectionNewCompra.setFormaPagamentoId(formaPagamento);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldFormaPagamentoIdOfCompraCollectionNewCompra != null && !oldFormaPagamentoIdOfCompraCollectionNewCompra.equals(formaPagamento)) {
                        oldFormaPagamentoIdOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldFormaPagamentoIdOfCompraCollectionNewCompra = em.merge(oldFormaPagamentoIdOfCompraCollectionNewCompra);
                    }
                }
            }
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setFormaPagamentoId(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    FormaPagamento oldFormaPagamentoIdOfVendaCollectionNewVenda = vendaCollectionNewVenda.getFormaPagamentoId();
                    vendaCollectionNewVenda.setFormaPagamentoId(formaPagamento);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldFormaPagamentoIdOfVendaCollectionNewVenda != null && !oldFormaPagamentoIdOfVendaCollectionNewVenda.equals(formaPagamento)) {
                        oldFormaPagamentoIdOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldFormaPagamentoIdOfVendaCollectionNewVenda = em.merge(oldFormaPagamentoIdOfVendaCollectionNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaPagamento.getId();
                if (findFormaPagamento(id) == null) {
                    throw new NonexistentEntityException("The formaPagamento with id " + id + " no longer exists.");
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
            FormaPagamento formaPagamento;
            try {
                formaPagamento = em.getReference(FormaPagamento.class, id);
                formaPagamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaPagamento with id " + id + " no longer exists.", enfe);
            }
            Collection<Compra> compraCollection = formaPagamento.getCompraCollection();
            for (Compra compraCollectionCompra : compraCollection) {
                compraCollectionCompra.setFormaPagamentoId(null);
                compraCollectionCompra = em.merge(compraCollectionCompra);
            }
            Collection<Venda> vendaCollection = formaPagamento.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setFormaPagamentoId(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            em.remove(formaPagamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaPagamento> findFormaPagamentoEntities() {
        return findFormaPagamentoEntities(true, -1, -1);
    }

    public List<FormaPagamento> findFormaPagamentoEntities(int maxResults, int firstResult) {
        return findFormaPagamentoEntities(false, maxResults, firstResult);
    }

    private List<FormaPagamento> findFormaPagamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaPagamento.class));
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

    public FormaPagamento findFormaPagamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaPagamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaPagamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaPagamento> rt = cq.from(FormaPagamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
