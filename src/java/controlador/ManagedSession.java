
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Cliente;
import modelo.Trabajador;

@ManagedBean
@SessionScoped
public class ManagedSession {
    private Cliente cliente;
    private Trabajador trabajador;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    public void verificarSessionT(){
       trabajador = (Trabajador)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
       if(trabajador == null){
           try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("loginT.xhtml");
           } catch (IOException ex) {
               Logger.getLogger(ManagedSession.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    public void verificarSessionC(){
       cliente = (Cliente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
       if(cliente == null){
           try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
           } catch (IOException ex) {
               Logger.getLogger(ManagedSession.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
    public void cerrarSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ManagedSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void init(){
        cliente = new Cliente();
        trabajador = new Trabajador();
    }

}
