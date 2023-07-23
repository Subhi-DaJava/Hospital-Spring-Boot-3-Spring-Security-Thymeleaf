package com.uyghur.java.hospital.hospital.controller;

import com.uyghur.java.hospital.hospital.entity.Patient;
import com.uyghur.java.hospital.hospital.repository.PatientRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        //Page<Patient> list = repository.findAll(PageRequest.of(page, size));
        Page<Patient> list = repository.findByNameContains(keyword, PageRequest.of(page, size));

        model.addAttribute("list", list.getContent());
        model.addAttribute("pages", new int[list.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "/list/patients";
    }

    @GetMapping("/user/patients")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Patient>> retrieveAllPatients() {
        return ResponseEntity.ok(repository.findAll());
    }

    // localhost://8081/index?id=33
    @GetMapping("/admin/delete")
    public String deleteById(
        @RequestParam(name = "id") long id,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        repository.deleteById(id);
        //Patient patientById = repository.findById(id).orElseThrow(() -> new RuntimeException("No patient with id:{%d}".formatted(id)));
        Optional<Patient> patientById = repository.findById(id);

        if(patientById.isEmpty()) {
            log.info("No patient with id:{%d} in database, form PatientController".formatted(id));
        }
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public String homePage() {
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "/form_patient/formPatient";
    }

    @PostMapping("/admin/addPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPatient(@Valid Patient patient, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.error("Some errors occur, please check the fields");
            return "/form_patient/formPatient";
        }
        Patient patientSaved = repository.save(patient);
        log.info("New Patient with id:{} has been successfully saved in Database", patientSaved.getId());
        return "redirect:/user/index?keyword=" + patient.getName();
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPatient(Model model, @RequestParam(name = "id") Long id) {

       Optional<Patient> patientById = repository.findById(id);
        if(patientById.isPresent()) {
            model.addAttribute("patient", patientById.get());
            log.info("Patient with id:{} has been successfully retrieved from database.", id);
            return "/edit_patient/editPatient";

        } else {
            log.error("No patient with this id:{} in database!", id);
            throw new IllegalArgumentException("No patient with this id:{%d} in database!".formatted(id));
        }

    }

    @GetMapping("/home")
    public String home() {
        return "/home/home";
    }


    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> savePatient(@Valid Patient patient, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.error("Some errors occur, please check the fields");
        }
        Patient patientSaved = repository.save(patient);
        log.info("New Patient with id:{} has been successfully saved in Database", patientSaved.getId());
        return new ResponseEntity<>(patientSaved, HttpStatus.CREATED);
    }

    @GetMapping("/user/patient-list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Patient>> allPatients() {
        return ResponseEntity.ok(repository.findAll());
    }
}
