
package controlador;

import EBJ.DetallepedidoFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Detallepedido;
import modelo.Producto;

@ManagedBean
@SessionScoped
public class ManagedDetallePedido {
    @EJB
    private DetallepedidoFacadeLocal detalleFacade;
    private List<Detallepedido> listDetalle;
    private List<Detallepedido> listCarrito;
    private Detallepedido detalle;
    private Producto prod;
    private int spin;
    private double total;
    
    
    public List<Detallepedido> getListDetalle() {
        this.listDetalle = detalleFacade.findAll();
        return listDetalle;
    }

    public void setListDetalle(List<Detallepedido> listDetalle) {
        this.listDetalle = listDetalle;
    }
    
    public List<Detallepedido> getListCarrito() {
        return listCarrito;
    }

    public void setListCarrito(List<Detallepedido> listCarrito) {
        this.listCarrito = listCarrito;
    }
    
    public Detallepedido getDetalle() {
        return detalle;
    }

    public void setDetalle(Detallepedido detalle) {
        this.detalle = detalle;
    }

    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }
    
    public int getSpin() {
        return spin;
    }

    public void setSpin(int spin) {
        this.spin = spin;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    @PostConstruct
    public void init(){
        this.detalle = new Detallepedido();
        this.prod = new Producto();
    }
    
    public void guardar(){
        this.detalle.setIdproductodetallepe(prod);
        this.detalleFacade.create(detalle);
    }
    
    public void eliminar(Detallepedido d){
        this.detalleFacade.remove(d);
    }
    
    public void cargarDatos(Detallepedido d){
        this.prod.setIdpro(d.getIdproductodetallepe().getIdpro());
        this.detalle = d;
    }
    
    public void modificar(){
        this.detalle.setIdproductodetallepe(prod);
        this.detalleFacade.edit(detalle);
    }
    public double calcularSubTotal(Producto p){
        double multi;
        multi=p.getPreciopro()*getSpin();
        return multi;
    }
    
    public void guardarCarrito(Producto p){
        System.out.println(getSpin());
        this.detalle.setCantidaddetellepe(getSpin());
        this.detalle.setIdproductodetallepe(p);
        this.detalle.setTotaldetallepe(calcularSubTotal(p));
        this.detalleFacade.create(detalle);
    }
    
    public boolean buscarProducto(Producto p){
        int pos=-1;
        for(int i=0; i<listCarrito.size();i++){
            if(listCarrito.get(i).getIdproductodetallepe().getIdpro().equals(p.getIdpro())){
                listCarrito.get(i).setCantidaddetellepe(listCarrito.get(i).getCantidaddetellepe()+1);
                pos=i;
            }
        }
        if(pos!=-1)
            return true;
        else
            return false;
    }
    
    public List<Detallepedido> agregaCarrito(Producto p){
        if(listCarrito ==  null)
            listCarrito = new ArrayList<>();
        boolean res = buscarProducto(p);
        if(res==false){
            detalle = new Detallepedido();
            detalle.setIdproductodetallepe(p);
            detalle.setTotaldetallepe(calcularSubTotal(p));
            detalle.setCantidaddetellepe(getSpin());
            detalleFacade.create(detalle);
            int x =detalleFacade.count()+12;
            System.out.println(x);
            detalle.setIddetallepe(x);
            //detalle.setIddetallepe(1);
            listCarrito.add(detalle);
            
            setSpin(0);
            
            
        }
        total=calcularTotalPagar();
        return listCarrito;
    }
    
    public double calcularTotalPagar(){
        double suma=0;
        for(int i=0;i<listCarrito.size();i++)
            suma=suma+(listCarrito.get(i).getTotaldetallepe());
        return suma;
    }
    
    public List<Detallepedido> eliminarProductoCarrito(int id){
        if(listCarrito ==  null)
            listCarrito = new ArrayList<>();
        for (int i = 0; i < listCarrito.size(); i++) {
            if(listCarrito.get(i).getIdproductodetallepe().getIdpro()==id)
                listCarrito.remove(i);
        }
        total=calcularTotalPagar();
        return listCarrito;
    }

    

    

    
}
