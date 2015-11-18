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
import br.com.melkran.drefc.model.Estado;
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
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getCompraCollection() == null) {
            estado.setCompraCollection(new ArrayList<Compra>());
        }
        if (estado.getCompraCollection1() == null) {
            estado.setCompraCollection1(new ArrayList<Compra>());
        }
        if (estado.getVendaCollection() == null) {
            estado.setVendaCollection(new ArrayList<Venda>());
        }
        if (estado.getVendaCollection1() == null) {
            estado.setVendaCollection1(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : estado.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getId());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            estado.setCompraCollection(attachedCompraCollection);
            Collection<Compra> attachedCompraCollection1 = new ArrayList<Compra>();
            for (Compra compraCollection1CompraToAttach : estado.getCompraCollection1()) {
                compraCollection1CompraToAttach = em.getReference(compraCollection1CompraToAttach.getClass(), compraCollection1CompraToAttach.getId());
                attachedCompraCollection1.add(compraCollection1CompraToAttach);
            }
            estado.setCompraCollection1(attachedCompraCollection1);
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : estado.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getId());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            estado.setVendaCollection(attachedVendaCollection);
            Collection<Venda> attachedVendaCollection1 = new ArrayList<Venda>();
            for (Venda vendaCollection1VendaToAttach : estado.getVendaCollection1()) {
                vendaCollection1VendaToAttach = em.getReference(vendaCollection1VendaToAttach.getClass(), vendaCollection1VendaToAttach.getId());
                attachedVendaCollection1.add(vendaCollection1VendaToAttach);
            }
            estado.setVendaCollection1(attachedVendaCollection1);
            em.persist(estado);
            for (Compra compraCollectionCompra : estado.getCompraCollection()) {
                Estado oldOrigemOfCompraCollectionCompra = compraCollectionCompra.getOrigem();
                compraCollectionCompra.setOrigem(estado);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldOrigemOfCompraCollectionCompra != null) {
                    oldOrigemOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldOrigemOfCompraCollectionCompra = em.merge(oldOrigemOfCompraCollectionCompra);
                }
            }
            for (Compra compraCollection1Compra : estado.getCompraCollection1()) {
                Estado oldDestinoOfCompraCollection1Compra = compraCollection1Compra.getDestino();
                compraCollection1Compra.setDestino(estado);
                compraCollection1Compra = em.merge(compraCollection1Compra);
                if (oldDestinoOfCompraCollection1Compra != null) {
                    oldDestinoOfCompraCollection1Compra.getCompraCollection1().remove(compraCollection1Compra);
                    oldDestinoOfCompraCollection1Compra = em.merge(oldDestinoOfCompraCollection1Compra);
                }
            }
            for (Venda vendaCollectionVenda : estado.getVendaCollection()) {
                Estado oldOrigemOfVendaCollectionVenda = vendaCollectionVenda.getOrigem();
                vendaCollectionVenda.setOrigem(estado);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldOrigemOfVendaCollectionVenda != null) {
                    oldOrigemOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldOrigemOfVendaCollectionVenda = em.merge(oldOrigemOfVendaCollectionVenda);
                }
            }
            for (Venda vendaCollection1Venda : estado.getVendaCollection1()) {
                Estado oldDestinoOfVendaCollection1Venda = vendaCollection1Venda.getDestino();
                vendaCollection1Venda.setDestino(estado);
                vendaCollection1Venda = em.merge(vendaCollection1Venda);
                if (oldDestinoOfVendaCollection1Venda != null) {
                    oldDestinoOfVendaCollection1Venda.getVendaCollection1().remove(vendaCollection1Venda);
                    oldDestinoOfVendaCollection1Venda = em.merge(oldDestinoOfVendaCollection1Venda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Collection<Compra> compraCollectionOld = persistentEstado.getCompraCollection();
            Collection<Compra> compraCollectionNew = estado.getCompraCollection();
            Collection<Compra> compraCollection1Old = persistentEstado.getCompraCollection1();
            Collection<Compra> compraCollection1New = estado.getCompraCollection1();
            Collection<Venda> vendaCollectionOld = persistentEstado.getVendaCollection();
            Collection<Venda> vendaCollectionNew = estado.getVendaCollection();
            Collection<Venda> vendaCollection1Old = persistentEstado.getVendaCollection1();
            Collection<Venda> vendaCollection1New = estado.getVendaCollection1();
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getId());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            estado.setCompraCollection(compraCollectionNew);
            Collection<Compra> attachedCompraCollection1New = new ArrayList<Compra>();
            for (Compra compraCollection1NewCompraToAttach : compraCollection1New) {
                compraCollection1NewCompraToAttach = em.getReference(compraCollection1NewCompraToAttach.getClass(), compraCollection1NewCompraToAttach.getId());
                attachedCompraCollection1New.add(compraCollection1NewCompraToAttach);
            }
            compraCollection1New = attachedCompraCollection1New;
            estado.setCompraCollection1(compraCollection1New);
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getId());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
            vendaCollectionNew = attachedVendaCollectionNew;
            estado.setVendaCollection(vendaCollectionNew);
            Collection<Venda> attachedVendaCollection1New = new ArrayList<Venda>();
            for (Venda vendaCollection1NewVendaToAttach : vendaCollection1New) {
                vendaCollection1NewVendaToAttach = em.getReference(vendaCollection1NewVendaToAttach.getClass(), vendaCollection1NewVendaToAttach.getId());
                attachedVendaCollection1New.add(vendaCollection1NewVendaToAttach);
            }
            vendaCollection1New = attachedVendaCollection1New;
            estado.setVendaCollection1(vendaCollection1New);
            estado = em.merge(estado);
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    compraCollectionOldCompra.setOrigem(null);
                    compraCollectionOldCompra = em.merge(compraCollectionOldCompra);
                }
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    Estado oldOrigemOfCompraCollectionNewCompra = compraCollectionNewCompra.getOrigem();
                    compraCollectionNewCompra.setOrigem(estado);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldOrigemOfCompraCollectionNewCompra != null && !oldOrigemOfCompraCollectionNewCompra.equals(estado)) {
                        oldOrigemOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldOrigemOfCompraCollectionNewCompra = em.merge(oldOrigemOfCompraCollectionNewCompra);
                    }
                }
            }
            for (Compra compraCollection1OldCompra : compraCollection1Old) {
                if (!compraCollection1New.contains(compraCollection1OldCompra)) {
                    compraCollection1OldCompra.setDestino(null);
                    compraCollection1OldCompra = em.merge(compraCollection1OldCompra);
                }
            }
            for (Compra compraCollection1NewCompra : compraCollection1New) {
                if (!compraCollection1Old.contains(compraCollection1NewCompra)) {
                    Estado oldDestinoOfCompraCollection1NewCompra = compraCollection1NewCompra.getDestino();
                    compraCollection1NewCompra.setDestino(estado);
                    compraCollection1NewCompra = em.merge(compraCollection1NewCompra);
                    if (oldDestinoOfCompraCollection1NewCompra != null && !oldDestinoOfCompraCollection1NewCompra.equals(estado)) {
                        oldDestinoOfCompraCollection1NewCompra.getCompraCollection1().remove(compraCollection1NewCompra);
                        oldDestinoOfCompraCollection1NewCompra = em.merge(oldDestinoOfCompraCollection1NewCompra);
                    }
                }
            }
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setOrigem(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Estado oldOrigemOfVendaCollectionNewVenda = vendaCollectionNewVenda.getOrigem();
                    vendaCollectionNewVenda.setOrigem(estado);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldOrigemOfVendaCollectionNewVenda != null && !oldOrigemOfVendaCollectionNewVenda.equals(estado)) {
                        oldOrigemOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldOrigemOfVendaCollectionNewVenda = em.merge(oldOrigemOfVendaCollectionNewVenda);
                    }
                }
            }
            for (Venda vendaCollection1OldVenda : vendaCollection1Old) {
                if (!vendaCollection1New.contains(vendaCollection1OldVenda)) {
                    vendaCollection1OldVenda.setDestino(null);
                    vendaCollection1OldVenda = em.merge(vendaCollection1OldVenda);
                }
            }
            for (Venda vendaCollection1NewVenda : vendaCollection1New) {
                if (!vendaCollection1Old.contains(vendaCollection1NewVenda)) {
                    Estado oldDestinoOfVendaCollection1NewVenda = vendaCollection1NewVenda.getDestino();
                    vendaCollection1NewVenda.setDestino(estado);
                    vendaCollection1NewVenda = em.merge(vendaCollection1NewVenda);
                    if (oldDestinoOfVendaCollection1NewVenda != null && !oldDestinoOfVendaCollection1NewVenda.equals(estado)) {
                        oldDestinoOfVendaCollection1NewVenda.getVendaCollection1().remove(vendaCollection1NewVenda);
                        oldDestinoOfVendaCollection1NewVenda = em.merge(oldDestinoOfVendaCollection1NewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            Collection<Compra> compraCollection = estado.getCompraCollection();
            for (Compra compraCollectionCompra : compraCollection) {
                compraCollectionCompra.setOrigem(null);
                compraCollectionCompra = em.merge(compraCollectionCompra);
            }
            Collection<Compra> compraCollection1 = estado.getCompraCollection1();
            for (Compra compraCollection1Compra : compraCollection1) {
                compraCollection1Compra.setDestino(null);
                compraCollection1Compra = em.merge(compraCollection1Compra);
            }
            Collection<Venda> vendaCollection = estado.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setOrigem(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            Collection<Venda> vendaCollection1 = estado.getVendaCollection1();
            for (Venda vendaCollection1Venda : vendaCollection1) {
                vendaCollection1Venda.setDestino(null);
                vendaCollection1Venda = em.merge(vendaCollection1Venda);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
