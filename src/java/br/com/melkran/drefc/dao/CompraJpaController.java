/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.melkran.drefc.dao;

import br.com.melkran.drefc.dao.exceptions.NonexistentEntityException;
import br.com.melkran.drefc.model.Compra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.melkran.drefc.model.Estado;
import br.com.melkran.drefc.model.FormaPagamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sephi_000
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado origem = compra.getOrigem();
            if (origem != null) {
                origem = em.getReference(origem.getClass(), origem.getId());
                compra.setOrigem(origem);
            }
            Estado destino = compra.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getId());
                compra.setDestino(destino);
            }
            FormaPagamento formaPagamentoId = compra.getFormaPagamentoId();
            if (formaPagamentoId != null) {
                formaPagamentoId = em.getReference(formaPagamentoId.getClass(), formaPagamentoId.getId());
                compra.setFormaPagamentoId(formaPagamentoId);
            }
            em.persist(compra);
            if (origem != null) {
                origem.getCompraCollection().add(compra);
                origem = em.merge(origem);
            }
            if (destino != null) {
                destino.getCompraCollection().add(compra);
                destino = em.merge(destino);
            }
            if (formaPagamentoId != null) {
                formaPagamentoId.getCompraCollection().add(compra);
                formaPagamentoId = em.merge(formaPagamentoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getId());
            Estado origemOld = persistentCompra.getOrigem();
            Estado origemNew = compra.getOrigem();
            Estado destinoOld = persistentCompra.getDestino();
            Estado destinoNew = compra.getDestino();
            FormaPagamento formaPagamentoIdOld = persistentCompra.getFormaPagamentoId();
            FormaPagamento formaPagamentoIdNew = compra.getFormaPagamentoId();
            if (origemNew != null) {
                origemNew = em.getReference(origemNew.getClass(), origemNew.getId());
                compra.setOrigem(origemNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getId());
                compra.setDestino(destinoNew);
            }
            if (formaPagamentoIdNew != null) {
                formaPagamentoIdNew = em.getReference(formaPagamentoIdNew.getClass(), formaPagamentoIdNew.getId());
                compra.setFormaPagamentoId(formaPagamentoIdNew);
            }
            compra = em.merge(compra);
            if (origemOld != null && !origemOld.equals(origemNew)) {
                origemOld.getCompraCollection().remove(compra);
                origemOld = em.merge(origemOld);
            }
            if (origemNew != null && !origemNew.equals(origemOld)) {
                origemNew.getCompraCollection().add(compra);
                origemNew = em.merge(origemNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getCompraCollection().remove(compra);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getCompraCollection().add(compra);
                destinoNew = em.merge(destinoNew);
            }
            if (formaPagamentoIdOld != null && !formaPagamentoIdOld.equals(formaPagamentoIdNew)) {
                formaPagamentoIdOld.getCompraCollection().remove(compra);
                formaPagamentoIdOld = em.merge(formaPagamentoIdOld);
            }
            if (formaPagamentoIdNew != null && !formaPagamentoIdNew.equals(formaPagamentoIdOld)) {
                formaPagamentoIdNew.getCompraCollection().add(compra);
                formaPagamentoIdNew = em.merge(formaPagamentoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getId();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            Estado origem = compra.getOrigem();
            if (origem != null) {
                origem.getCompraCollection().remove(compra);
                origem = em.merge(origem);
            }
            Estado destino = compra.getDestino();
            if (destino != null) {
                destino.getCompraCollection().remove(compra);
                destino = em.merge(destino);
            }
            FormaPagamento formaPagamentoId = compra.getFormaPagamentoId();
            if (formaPagamentoId != null) {
                formaPagamentoId.getCompraCollection().remove(compra);
                formaPagamentoId = em.merge(formaPagamentoId);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
