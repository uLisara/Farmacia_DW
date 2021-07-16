/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBJ;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Trabajador;

/**
 *
 * @author jose2
 */
@Stateless
public class TrabajadorFacade extends AbstractFacade<Trabajador> implements TrabajadorFacadeLocal {

    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrabajadorFacade() {
        super(Trabajador.class);
    }
    
    @Override
    public Trabajador iniciarS(Trabajador traba){
        Trabajador trabajador = null;
        try {
            String sql = "FROM Trabajador t WHERE t.correotra =?1 and t.contrasenatra = ?2";
            Query query = em.createQuery(sql);
            query.setParameter(1, traba.getCorreotra());
            query.setParameter(2, traba.getContrasenatra());
            
            List<Trabajador>lista = query.getResultList();
            if(!lista.isEmpty()){
                trabajador = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        
        
        
        return trabajador;
    }
    
}
