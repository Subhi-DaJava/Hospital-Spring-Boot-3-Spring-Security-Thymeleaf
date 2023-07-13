package com.uyghur.java.hospital.hospital.repository;


import com.uyghur.java.hospital.hospital.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByNameContains(String name,
                                     Pageable pageable);

    @Query("SELECT patient FROM Patient patient WHERE patient.name LIKE :name")
    Page<Patient> search(@Param("name") String name, Pageable pageable);
}
