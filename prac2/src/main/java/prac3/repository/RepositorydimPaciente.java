package prac3.repository;

import prac3.bbdd.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorydimPaciente extends CrudRepository<dimPACIENTE, Integer> {

}
