package com.chits.clinicals.controllers;

import com.chits.clinicals.model.ClinicalData;
import com.chits.clinicals.model.Patient;
import com.chits.clinicals.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

    private PatientRepository patientRepository;
    Map<String,String> filters = new HashMap<>();

    @Autowired
    PatientController(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    @RequestMapping(value = "/patients" , method = RequestMethod.GET)
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public Patient getPatient(@PathVariable("id") int id){
        return patientRepository.findById(id).get();
    }

    @RequestMapping(value = "/patients" , method = RequestMethod.POST)
    public Patient savePatient(@RequestBody Patient patient){
        return patientRepository.save(patient);
    }

    @RequestMapping(value = "/patients/analyze/{id}" , method = RequestMethod.GET)
    public Patient analyze(@PathVariable int id){
        Patient patient = patientRepository.findById(id).get();
        List<ClinicalData> clinicalDataList = patient.getClinicalData();
        ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalDataList);
        for(ClinicalData clinicalData : duplicateClinicalData){

            if(filters.containsKey(clinicalData.getComponentName())){
                clinicalDataList.remove(clinicalData);
                continue;
            }else{
                filters.put(clinicalData.getComponentName(),null);
            }
            String[] heightAndWeight = clinicalData.getComponentValue().split("/");
            if(heightAndWeight != null && heightAndWeight.length > 1){
                float heightInMeters = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
                float bmi = Float.parseFloat(heightAndWeight[1])/(heightInMeters*heightInMeters);
                ClinicalData bmiData = new ClinicalData();
                bmiData.setComponentName("bmi");
                bmiData.setComponentName(Float.toString(bmi));
                clinicalDataList.add(bmiData);
            }
            patient.setClinicalData(clinicalDataList);
        }
        return patient;
    }
}
