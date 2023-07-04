package com.uyghur.java.hospital.hospital;

import com.uyghur.java.hospital.hospital.entity.Patient;
import com.uyghur.java.hospital.hospital.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
	private final PatientRepository repository;

	public HospitalApplication(PatientRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Patient patient = new Patient();
		patient.setName("Subhi");
		patient.setSick(false);
		patient.setDateOfBirth(new Date());
		patient.setScore(30);

		Patient patient1 = new Patient(null, "Subhi1", new Date(), true, 50);

		Patient patient2 = Patient.builder()
				.name("Subhi2")
				.dateOfBirth(new Date())
				.score(12)
				.sick(false)
				.build();

		repository.saveAll(List.of(patient, patient1, patient2));
	}
}
