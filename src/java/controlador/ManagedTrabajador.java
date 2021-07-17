
package controlador;

import EBJ.TrabajadorFacadeLocal;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Tipotrabajador;
import modelo.Trabajador;

@ManagedBean
@SessionScoped
public class ManagedTrabajador {
    @EJB
    private TrabajadorFacadeLocal trabFacade;
    private List<Trabajador> listaTraba;
    private List<Trabajador> usuTra;
    private Trabajador traba;
    private Tipotrabajador tipoTra;

    public List<Trabajador> getListaTraba() {
        this.listaTraba = trabFacade.findAll();
        return listaTraba;
    }

    public void setListaTraba(List<Trabajador> listaTraba) {
        this.listaTraba = listaTraba;
    }
    
    public List<Trabajador> getUsuTra() {
        return usuTra;
    }

    public void setUsuTra(List<Trabajador> usuTra) {
        this.usuTra = usuTra;
    }

    public Trabajador getTraba() {
        return traba;
    }

    public void setTraba(Trabajador traba) {
        this.traba = traba;
    }

    public Tipotrabajador getTipoTra() {
        return tipoTra;
    }

    public void setTipoTra(Tipotrabajador tipoTra) {
        this.tipoTra = tipoTra;
    }
    
    @PostConstruct
    public void init(){
        this.traba = new Trabajador();
        this.tipoTra = new Tipotrabajador();
    }
    
    public void guardar(){
        this.traba.setIdTipotrabajador(tipoTra);
        this.trabFacade.create(traba);
    }
    public void eliminar(Trabajador t){
        this.trabFacade.remove(t);
    }
    
    public void cargarDatos(Trabajador t){
        this.tipoTra.setIdtipotra(t.getIdTipotrabajador().getIdtipotra());
        this.traba = t;
    }
    
    public void modificar(){
        this.traba.setIdTipotrabajador(tipoTra);
        this.trabFacade.edit(traba);
    }

    public String validarTrabajador(){
        String irPagina = null;
        usuTra = trabFacade.iniciarSesionT(traba);
        if(!usuTra.isEmpty()){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("trabajador", traba);
            irPagina = "indexTrabajador";
            Iterator i = usuTra.iterator();
            while(i.hasNext()){
                traba = (Trabajador)i.next();
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"UsuarioInvalido","Aviso"));
        }
        return irPagina;
    }
    
    
}
