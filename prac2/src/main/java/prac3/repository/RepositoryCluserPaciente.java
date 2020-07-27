package prac3.repository;

import org.springframework.data.repository.CrudRepository;
import prac3.bbdd.ClusterPacientes;


public interface RepositoryCluserPaciente extends CrudRepository<ClusterPacientes, Integer> {

}
