package com.uyghur.java.hospital.hospital.controller;

import com.uyghur.java.hospital.hospital.entity.Patient;
import com.uyghur.java.hospital.hospital.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class PatientController {

    private static Logger log = LoggerFactory.getLogger(PatientController.class);

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
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

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> retrieveAllPatients() {
        return ResponseEntity.ok(repository.findAll());
    }

    // localhost://8081/index?id=33
    @GetMapping("/delete")
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
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/index";
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "/form_patient/formPatient";
    }

    @PostMapping("/addPatient")
    public String addPatient(Patient patient) {
        repository.save(patient);

        return "redirect:/";
    }


}
