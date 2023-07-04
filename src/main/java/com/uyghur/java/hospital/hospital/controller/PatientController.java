package com.uyghur.java.hospital.hospital.controller;

import com.uyghur.java.hospital.hospital.entity.Patient;
import com.uyghur.java.hospital.hospital.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Patient> list = repository.findAll();
        model.addAttribute("list", list);
        return "/list/patients";
    }
}
