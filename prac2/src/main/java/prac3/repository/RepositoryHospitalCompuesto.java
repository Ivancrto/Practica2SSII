package prac3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import prac3.bbdd.HospitalCompuesto;

@RepositoryRestResource
public interface RepositoryHospitalCompuesto extends CrudRepository<HospitalCompuesto, Integer> {

}
