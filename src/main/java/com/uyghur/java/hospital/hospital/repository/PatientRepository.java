package com.uyghur.java.hospital.hospital.repository;


import com.uyghur.java.hospital.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
