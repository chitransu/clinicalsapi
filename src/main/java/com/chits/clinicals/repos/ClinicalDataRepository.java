package com.chits.clinicals.repos;

import com.chits.clinicals.model.ClinicalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData,Integer> {
}
