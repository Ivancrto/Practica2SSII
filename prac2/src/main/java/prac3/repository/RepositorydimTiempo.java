package prac3.repository;
import prac3.bbdd.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;

@RepositoryRestResource
public interface RepositorydimTiempo  extends CrudRepository<dimTIEMPO, Integer> {
    dimTIEMPO findByFecha(String fecha);
    dimTIEMPO findByDiaAndMesAndAnio(int dia, int mes, int Anio);
}
