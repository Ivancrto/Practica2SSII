package prac3.bbdd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*Dicha tabla de la bbdd, nos sirve para almacenar el filtrado colaborativo y luego realizar las consultas para el DASHBOARD
Hemos almacenado en cada hospital los tres compuestos mas recomendados para los usuarios, en el campo personasRecomendadas
es el numero total de personas a las que se le recomienda
* */
@Entity
public class HospitalCompuesto {
    //id nHospital idCompuesto npersonas
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;
    //Podriamos relacionarlo con el dimHospital, pero lo evitamos para quitar complejidad
    private int idHosptila;
    private int idCompuesto;
    private int personasRecomendadas;

    public HospitalCompuesto(int idHosptila, int idCompuesto, int personasRecomendadas) {
        this.idHosptila = idHosptila;
        this.idCompuesto = idCompuesto;
        this.personasRecomendadas = personasRecomendadas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHosptila() {
        return idHosptila;
    }

    public void setIdHosptila(int idHosptila) {
        this.idHosptila = idHosptila;
    }

    public int getIdCompuesto() {
        return idCompuesto;
    }

    public void setIdCompuesto(int idCompuesto) {
        this.idCompuesto = idCompuesto;
    }

    public int getPersonasRecomendadas() {
        return personasRecomendadas;
    }

    public void setPersonasRecomendadas(int personasRecomendadas) {
        this.personasRecomendadas = personasRecomendadas;
    }
}
