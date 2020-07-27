package prac3.bbdd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*Tabla de la bbdd que servira para almacenar los tres cluster realizados, la columna TIPO, nos servira para diferencia si
es de fallcidos, UCI, o el resto. Hemos almacenado los valores, para hacer las consultas en el dashboard.
 */
@Entity
public class ClusterPacientes {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;
    private String tipo;
    private float epoc;
    private float imc;
    private float cancer;
    private float cardiopatia;
    private float colesterol;
    private float edad;
    private float forma_fisica;
    private float hepatitis;
    private float hipertension;
    private float reuma;
    private String sexo;
    private float duracion;
    private float tabaquismo;
    private float tratamiento;
    private float uci;
    private float fallecido;


    public ClusterPacientes(String tipo, String sexo, float epoc, float imc, float cancer, float cardiopatia, float colesterol,float edad,
                            float forma_fisica, float hepatitis, float hipertension, float reuma, float duracion, float tabaquismo,
                            float tratamiento, float uci, float fallecido) {
        this.tipo = tipo;
        this.epoc = epoc;
        this.imc = imc;
        this.cancer = cancer;
        this.cardiopatia = cardiopatia;
        this.colesterol = colesterol;
        this.edad = edad;
        this.forma_fisica = forma_fisica;
        this.hepatitis = hepatitis;
        this.hipertension = hipertension;
        this.reuma = reuma;
        this.sexo = sexo;
        this.duracion = duracion;
        this.tabaquismo = tabaquismo;
        this.tratamiento = tratamiento;
        this.uci = uci;
        this.fallecido = fallecido;
    }
}
