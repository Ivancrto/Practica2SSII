package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac3.bbdd.tablaHECHOS;
import prac3.repository.RepositoryHechos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class HechoService {
    @Autowired
    RepositoryHechos repositoryHechos;

    public void saveHecho(tablaHECHOS th){ repositoryHechos.save(th); }

    public String info1() {
        List<String> x = repositoryHechos.U0F0();
        String valores = "epoc,imc,cancer,cardiopatia,colesterol,edad,formafisica,hepatitis,hipertension,reuma,sexo,duracion,tabaquismo,tratamiento\n";
        for(String i: x){
            valores += i + "\n";
        }
        return valores;
    }

    public String info2() {
        List<String> x = repositoryHechos.UXF1();
        String valores = "epoc,imc,cancer,cardiopatia,colesterol,edad,formafisica,hepatitis,hipertension,reuma,sexo,duracion,tabaquismo,tratamiento,uci\n";
        for(String i: x){
            valores += i + "\n";
        }
        return valores;
    }

    public String info3() {
        List<String> x = repositoryHechos.U1FX();
        String valores = "epoc,imc,cancer,cardiopatia,colesterol,edad,formafisica,hepatitis,hipertension,reuma,sexo,duracion,tabaquismo,tratamiento,fallecido\n";
        for(String i: x){
            valores += i + "\n";
        }
        return valores;
    }


}
