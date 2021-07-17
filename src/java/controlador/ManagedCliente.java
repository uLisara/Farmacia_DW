
package controlador;

import EBJ.ClienteFacadeLocal;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Cliente;
import modelo.Distrito;

@ManagedBean
@SessionScoped
public class ManagedCliente {
    @EJB
    private ClienteFacadeLocal clienteFacade;
    private List<Cliente> listaCliente;
    private List<Cliente> usu;
    private Cliente cli;
    private Distrito dis;

    public List<Cliente> getListaCliente() {
        this.listaCliente = clienteFacade.findAll();
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }
    
    public List<Cliente> getUsu() {
        return usu;
    }

    public void setUsu(List<Cliente> usu) {
        this.usu = usu;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Distrito getDis() {
        return dis;
    }

    public void setDis(Distrito dis) {
        this.dis = dis;
    }
    
    @PostConstruct
    public void init(){
        this.cli = new Cliente();
        this.dis = new Distrito();
    }
    
    public void guardar(){
        this.cli.setIdDistritocli(dis);
        this.clienteFacade.create(cli);
    }
    
    public void eliminar(Cliente c){
        this.clienteFacade.remove(c);
    }
    
    public void cargarDatos(Cliente c){
        this.dis.setIdDistrito(c.getIdDistritocli().getIdDistrito());
        this.cli = c;
    }
    
    public void modificar(){
        this.cli.setIdDistritocli(dis);
        this.clienteFacade.edit(cli);
    }

    public String validarCliente(){
        String irPagina = null;
        usu = clienteFacade.iniciarSesion(cli);
        if(!usu.isEmpty()){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", cli);
            irPagina = "indexProducto";
            Iterator i = usu.iterator();
            while(i.hasNext()){
                cli = (Cliente)i.next();
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"UsuarioInvalido","Aviso"));
        }
        return irPagina;
    }
    
    
}
