package prac3.bbdd;

import javax.persistence.*;

@Entity
public class tablaHECHOS {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private dimPACIENTE cliente_id;
    @ManyToOne
    @JoinColumn(name="hospital_id")
    private dimHOSPITAL hospital_id;
    @ManyToOne
    @JoinColumn(name="fechaIngreso_id")
    private dimTIEMPO fechaIngreso_id;

    private int Duracion;
    private int UCI;
    private int Fallecido;
    private int Tratamiento;

    public tablaHECHOS() {
    }

    public tablaHECHOS(dimPACIENTE cliente_id, dimHOSPITAL hospital_id, dimTIEMPO fechaIngreso_id,
                       int duracion, int UCI, int fallecido, int tratamiento) {
        this.cliente_id = cliente_id;
        this.hospital_id = hospital_id;
        this.fechaIngreso_id = fechaIngreso_id;
        Duracion = duracion;
        this.UCI = UCI;
        Fallecido = fallecido;
        Tratamiento = tratamiento;
    }

    public tablaHECHOS(int duracion, int UCI, int fallecido, int tratamiento) {
        Duracion = duracion;
        this.UCI = UCI;
        Fallecido = fallecido;
        Tratamiento = tratamiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public dimPACIENTE getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(dimPACIENTE cliente_id) {
        this.cliente_id = cliente_id;
    }

    public dimHOSPITAL getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(dimHOSPITAL hospital_id) {
        this.hospital_id = hospital_id;
    }

    public dimTIEMPO getFechaIngreso_id() {
        return fechaIngreso_id;
    }

    public void setFechaIngreso_id(dimTIEMPO fechaIngreso_id) {
        this.fechaIngreso_id = fechaIngreso_id;
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int duracion) {
        Duracion = duracion;
    }

    public int isUCI() {
        return UCI;
    }

    public void setUCI(int UCI) {
        this.UCI = UCI;
    }

    public int isFallecido() {
        return Fallecido;
    }

    public void setFallecido(int fallecido) {
        Fallecido = fallecido;
    }

    public int getTratamiento() {
        return Tratamiento;
    }

    public void setTratamiento(int tratamiento) {
        Tratamiento = tratamiento;
    }

}
