package com.uyghur.java.hospital.hospital.controller;

import com.uyghur.java.hospital.hospital.entity.Patient;
import com.uyghur.java.hospital.hospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class PatientController {

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
}
