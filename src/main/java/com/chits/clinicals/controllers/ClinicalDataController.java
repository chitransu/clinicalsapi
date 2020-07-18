package com.chits.clinicals.controllers;

import com.chits.clinicals.model.ClinicalData;
import com.chits.clinicals.model.ClinicalDataRequest;
import com.chits.clinicals.model.Patient;
import com.chits.clinicals.repos.ClinicalDataRepository;
import com.chits.clinicals.repos.PatientRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

    private ClinicalDataRepository clinicalDataRepository;
    private PatientRepository patientRepository;

    ClinicalDataController(ClinicalDataRepository clinicalDataRepository,PatientRepository patientRepository){
        this.clinicalDataRepository = clinicalDataRepository;
        this.patientRepository = patientRepository;
    }

    @RequestMapping(value = "/clinicals" , method = RequestMethod.POST)
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest clinicalDataRequest){
        Patient patient = patientRepository.findById(clinicalDataRequest.getPatientId()).get();
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(clinicalDataRequest.getComponentName());
        clinicalData.setComponentValue(clinicalDataRequest.getComponentValue());
        clinicalData.setPatient(patient);
        return clinicalDataRepository.save(clinicalData);
    }
}
