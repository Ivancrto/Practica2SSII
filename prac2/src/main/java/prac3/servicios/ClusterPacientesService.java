package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac3.bbdd.ClusterPacientes;
import prac3.repository.RepositoryCluserPaciente;

@Service
public class ClusterPacientesService {
    @Autowired
    RepositoryCluserPaciente repositoryCluserPaciente;

    public void saveCP(ClusterPacientes cp){
        repositoryCluserPaciente.save(cp);

    }
}
