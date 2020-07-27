package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import prac3.bbdd.dimTIEMPO;
import prac3.repository.RepositorydimTiempo;

import java.util.Date;

@Service
public class TiempoService {

    @Autowired
    RepositorydimTiempo repositorydimTiempo;

    public void saveTiempo (dimTIEMPO t){
        repositorydimTiempo.save(t);
    }
    public dimTIEMPO getTiempo(int d, int m, int a ){return repositorydimTiempo.findByDiaAndMesAndAnio(d, m, a);}
}
