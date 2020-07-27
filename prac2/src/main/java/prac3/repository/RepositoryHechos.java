package prac3.repository;

import org.springframework.data.jpa.repository.Query;
import prac3.bbdd.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RepositoryHechos extends CrudRepository<tablaHECHOS, Integer> {

    @Query(value = "SELECT P.epoc, P.imc, P.cancer, P.cardiopatia, P.colesterol, P.edad, P.forma_fisica, P.hepatitis, P.hipertension, P.reuma, P.sexo, H.duracion,P.tabaquismo, H.tratamiento FROM test.dimpaciente P, test.tablahechos H WHERE P.id = H.cliente_id and H.uci = 0 and  H.fallecido = 0;", nativeQuery = true)
    List<String> U0F0();

    @Query(value = "SELECT P.epoc, P.imc, P.cancer, P.cardiopatia, P.colesterol, P.edad, P.forma_fisica, P.hepatitis, P.hipertension, P.reuma, P.sexo, H.duracion,P.tabaquismo, H.tratamiento, H.uci  FROM test.dimpaciente P, test.tablahechos H WHERE P.id = H.cliente_id and  H.fallecido = 1;", nativeQuery = true)
    List<String> UXF1();

    @Query(value = "SELECT P.epoc, P.imc, P.cancer, P.cardiopatia, P.colesterol, P.edad, P.forma_fisica, P.hepatitis, P.hipertension, P.reuma, P.sexo, H.duracion,P.tabaquismo ,H.fallecido, H.tratamiento FROM test.dimpaciente P, test.tablahechos H WHERE P.id = H.cliente_id and H.uci = 1; ", nativeQuery = true)
    List<String> U1FX();
}
