package prac3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.collections.List;
import prac3.bbdd.dimPACIENTE;
import prac3.repository.RepositorydimPaciente;

import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    RepositorydimPaciente repositorydimPaciente;


    public void savePaciente(dimPACIENTE p) {
        repositorydimPaciente.save(p);
    }

    public dimPACIENTE getPaciente(int id){
          Optional<dimPACIENTE> p=  repositorydimPaciente.findById(id);
          return p.get();
    }
    
    public int mediaEdad() {
    	Iterable<dimPACIENTE> lpacientes =  repositorydimPaciente.findAll();
    	int media = 0;
    	int total = 0;
    	for(dimPACIENTE it: lpacientes) {
    		media += it.getEdad();
    		total++;
    	}
    	return (int) media/total;
    }
}
