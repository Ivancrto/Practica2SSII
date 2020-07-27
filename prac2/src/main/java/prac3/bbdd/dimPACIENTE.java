package prac3.bbdd;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class dimPACIENTE {
    @Id
    private int id;
    private int edad;
    private String sexo;
    private int IMC;
    private int formaFisica;
    private int tabaquismo;
    private int colesterol;
    private int hipertension;
    private int cardiopatia;
    private int reuma;
    private int EPOC;
    private int hepatitis;
    private int cancer;
    @OneToOne(mappedBy = "cliente_id", cascade = CascadeType.ALL)
    private tablaHECHOS tablaHECHOS;

    public dimPACIENTE() {
    }

    public dimPACIENTE(int edad, String sexo, int IMC, int formaFisica, int tabaquismo,
                       int colesterol, int hipertension, int cardiopatia, int reuma,
                       int EPOC, int hepatitis, int cancer) {
        this.edad = edad;
        this.sexo = sexo;
        this.IMC = IMC;
        this.formaFisica = formaFisica;
        this.tabaquismo = tabaquismo;
        this.colesterol = colesterol;
        this.hipertension = hipertension;
        this.cardiopatia = cardiopatia;
        this.reuma = reuma;
        this.EPOC = EPOC;
        this.hepatitis = hepatitis;
        this.cancer = cancer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIMC() {
        return IMC;
    }

    public void setIMC(int IMC) {
        this.IMC = IMC;
    }

    public int getFormaFisica() {
        return formaFisica;
    }

    public void setFormaFisica(int formaFisica) {
        this.formaFisica = formaFisica;
    }

    public int isTabaquismo() {
        return tabaquismo;
    }

    public void setTabaquismo(int tabaquismo) {
        this.tabaquismo = tabaquismo;
    }

    public int isColesterol() {
        return colesterol;
    }

    public void setColesterol(int colesterol) {
        this.colesterol = colesterol;
    }

    public int isHipertension() {
        return hipertension;
    }

    public void setHipertension(int hipertension) {
        this.hipertension = hipertension;
    }

    public int isCardiopatia() {
        return cardiopatia;
    }

    public void setCardiopatia(int cardiopatia) {
        this.cardiopatia = cardiopatia;
    }

    public int isReuma() {
        return reuma;
    }

    public void setReuma(int reuma) {
        this.reuma = reuma;
    }

    public int isEPOC() {
        return EPOC;
    }

    public void setEPOC(int EPOC) {
        this.EPOC = EPOC;
    }

    public int isHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(int hepatitis) {
        this.hepatitis = hepatitis;
    }

    public int isCancer() {
        return cancer;
    }

    public void setCancer(int cancer) {
        this.cancer = cancer;
    }

    public prac3.bbdd.tablaHECHOS getTablaHECHOS() {
        return tablaHECHOS;
    }

    public void setTablaHECHOS(prac3.bbdd.tablaHECHOS tablaHECHOS) {
        this.tablaHECHOS = tablaHECHOS;
    }
}
