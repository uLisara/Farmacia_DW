/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBJ;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modelo.Cliente;
import modelo.Trabajador;

/**
 *
 * @author jose2
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public List<Cliente> iniciarSesion(Cliente cli){
        String sql = "Select u from Cliente u where u.correocli = ?1 and u.contrasenacli = ?2";        Query query = getEntityManager().createQuery(sql);
        Query query1 = getEntityManager().createQuery(sql);
        query1.setParameter(1, cli.getCorreocli());
        query1.setParameter(2, cli.getContrasenacli());
        return query1.getResultList();
        
    }
    
    public List<Trabajador> iniciarSesionT(Trabajador t){
        String sql = "Select t from Trabajador t where t.correotra = ?1 and t.contrasenatra = ?2";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(1, t.getCorreotra());
        query.setParameter(2, t.getContrasenatra());
        return query.getResultList();
        
    }
}
