package prac3.bbdd;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class dimTIEMPO {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;
    private String fecha;
    private int dia;
    private int mes;
    private int anio;
    private int cuatrim;
    private String disemana;
    private int esfinde;
    @OneToMany(mappedBy = "fechaIngreso_id",
            cascade = CascadeType.ALL
    )
    private Collection<tablaHECHOS> tablaHECHOS;

    public dimTIEMPO(String fecha, int dia, int mes, int anio, int cuatrim, String disemana, int esfinde) {
        this.fecha = fecha;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.cuatrim = cuatrim;
        this.disemana = disemana;
        this.esfinde = esfinde;
    }

    public dimTIEMPO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return anio;
    }

    public void setAño(int año) {
        this.anio = año;
    }

    public int getCuatrim() {
        return cuatrim;
    }

    public void setCuatrim(int cuatrim) {
        this.cuatrim = cuatrim;
    }

    public String getDisemana() {
        return disemana;
    }

    public void setDisemana(String disemana) {
        this.disemana = disemana;
    }

    public int isEsfinde() {
        return esfinde;
    }

    public void setEsfinde(int esfinde) {
        this.esfinde = esfinde;
    }

    public int getEsfinde() {
        return esfinde;
    }

    public Collection<prac3.bbdd.tablaHECHOS> getTablaHECHOS() {
        return tablaHECHOS;
    }

    public void setTablaHECHOS(Collection<prac3.bbdd.tablaHECHOS> tablaHECHOS) {
        this.tablaHECHOS = tablaHECHOS;
    }

    @Override
    public String toString() {
        return "dimTIEMPO{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", dia=" + dia +
                ", mes=" + mes +
                ", año=" + anio +
                ", cuatrim=" + cuatrim +
                ", disemana=" + disemana +
                ", esfinde=" + esfinde +
                '}';
    }
}
