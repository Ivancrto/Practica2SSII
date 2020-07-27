package prac3.bbdd;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class dimHOSPITAL {
    @Id
    private int id;
    private String nombre;
    private int cpostal;
    private String autopista;
    private String gestor;
    @OneToMany(mappedBy = "hospital_id",
            cascade = CascadeType.ALL
    )
    private Collection<tablaHECHOS> tablaHECHOS;

    public dimHOSPITAL() {
    }

    public dimHOSPITAL(String nombre, int cpostal, String autopista, String gestor) {
        this.nombre = nombre;
        this.cpostal = cpostal;
        this.autopista = autopista;
        this.gestor = gestor;
    }

    public dimHOSPITAL(int id, String nombre, int cpostal, String autopista, String gestor) {
        this.id = id;
        this.nombre = nombre;
        this.cpostal = cpostal;
        this.autopista = autopista;
        this.gestor = gestor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCpostal() {
        return cpostal;
    }

    public void setCpostal(int cpostal) {
        this.cpostal = cpostal;
    }

    public String getAutopista() {
        return autopista;
    }

    public void setAutopista(String autopista) {
        this.autopista = autopista;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public Collection<prac3.bbdd.tablaHECHOS> getTablaHECHOS() {
        return tablaHECHOS;
    }

    public void setTablaHECHOS(Collection<prac3.bbdd.tablaHECHOS> tablaHECHOS) {
        this.tablaHECHOS = tablaHECHOS;
    }
}
