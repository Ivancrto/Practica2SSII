package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac3.bbdd.HospitalCompuesto;
import prac3.repository.RepositoryHospitalCompuesto;


@Service
public class HospitalCompuestoService {
    @Autowired
    RepositoryHospitalCompuesto repositoryHospitalCompuesto;
    public void saveHC (HospitalCompuesto h){
        repositoryHospitalCompuesto.save(h);
    }
}
