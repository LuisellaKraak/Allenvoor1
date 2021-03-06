package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Medication;

import java.util.Optional;

public interface MedicationServiceInterface{

    void save(Medication medication);

    Optional<Medication> findById(Integer integer);

    Optional<Medication> findByMedicationName(String medicationName);
}
