/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBJ;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Cliente;

/**
 *
 * @author jose2
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    @Override
    public Cliente iniciar(Cliente cli){
       Cliente cliente = null;
        try {
            String sql = "FROM Cliente c WHERE c.correocli =?1 and c.contrasenacli = ?2";
            Query query = em.createQuery(sql);
            query.setParameter(1, cli.getCorreocli());
            query.setParameter(2, cli.getContrasenacli());
            
            List<Cliente>lista = query.getResultList();
            if(!lista.isEmpty()){
                cliente = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
       
       return cliente;
    }
}
