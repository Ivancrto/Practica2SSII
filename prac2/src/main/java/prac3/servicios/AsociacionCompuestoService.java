package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac3.bbdd.AsociacionCompuesto;
import prac3.repository.RepositoryAsociacionCompuesto;

@Service
public class AsociacionCompuestoService {
    @Autowired
    RepositoryAsociacionCompuesto repositoryAsociacionCompuesto;

    public void saveAC(AsociacionCompuesto ac){
        repositoryAsociacionCompuesto.save(ac);
    }
}
