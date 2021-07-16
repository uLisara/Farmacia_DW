
package controlador;

import EBJ.ProductoFacadeLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Producto;
import modelo.Subcategoria;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


@ManagedBean
@SessionScoped
public class ManagedProducto {
    @EJB
    private ProductoFacadeLocal proFacade;
    private List<Producto> listaPro;
    private Producto pro;
    private Subcategoria sub;
    String ruta = "C:\\Users\\jose2\\OneDrive\\Documentos\\NetBeansProjects\\UNICO PROYECTO WEB\\Proyecto\\web\\imagenes\\";

    public List<Producto> getListaPro() {
        this.listaPro = proFacade.findAll();
        return listaPro;
    }

    public void setListaPro(List<Producto> listaPro) {
        this.listaPro = listaPro;
    }

    public Producto getPro() {
        return pro;
    }

    public void setPro(Producto pro) {
        this.pro = pro;
    }

    public Subcategoria getSub() {
        return sub;
    }

    public void setSub(Subcategoria sub) {
        this.sub = sub;
    }
    
    @PostConstruct
    public void init(){
        this.pro = new Producto();
        this.sub = new Subcategoria();
    }
    
    public void guardar(){
        this.pro.setIdSubcategoria(sub);
        this.proFacade.create(pro);
    }
    
    public void cargarArchivo(FileUploadEvent event){
        int read=0;
        byte[] bytes = new byte[1024];
        try {
            UploadedFile uploadedFile = event.getFile();
            String nombreArchivo = uploadedFile.getFileName();
            File file = new File(ruta, nombreArchivo);
            pro.setImagen(ruta+nombreArchivo);
            OutputStream out = new FileOutputStream(file);
            InputStream in = uploadedFile.getInputstream();
            while((read = in.read(bytes))!=-1){
                out.write(bytes, 0, read);
            }
            out.flush();
            in.close();
            out.close();
        } catch (FileNotFoundException e){
            //Logger.getLogger(ManagedProducto.class.getName()).log(Level.ERROR, null,e);
        }catch (IOException e) {
        } 
    }
    
    public void eliminar(Producto p){
        this.proFacade.remove(p);
    }
    
    public void cargarDatos(Producto p){
        this.sub.setIdSub(p.getIdSubcategoria().getIdSub());
        this.pro = p;
    }
    
    public void modificar(){
        this.pro.setIdSubcategoria(sub);
        this.proFacade.edit(pro);
    }
    
    
}
