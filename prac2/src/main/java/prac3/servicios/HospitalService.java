package prac3.servicios;

import prac3.bbdd.dimHOSPITAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac3.repository.RepositorydimHospital;

import java.io.OptionalDataException;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    RepositorydimHospital repositorydimHospital;

    public void saveHospital(dimHOSPITAL h){ repositorydimHospital.save(h); }

    public dimHOSPITAL getHospitals(int id){
        Optional<dimHOSPITAL> h = repositorydimHospital.findById(id);
        return h.get();
    }
}
