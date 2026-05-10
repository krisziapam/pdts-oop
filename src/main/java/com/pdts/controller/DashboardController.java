// Path: src/main/java/com/pdts/controller/DashboardController.java
package com.pdts.controller;

import com.pdts.service.ApplicantService;
import com.pdts.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DashboardController {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private RequirementService requirementService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String enrollment,
                            @RequestParam(required = false) String region) {

        model.addAttribute("applicants",
            applicantService.search(search, category, enrollment, region));
        model.addAttribute("statusSummary", requirementService.getStatusSummary());
        model.addAttribute("searchQuery", search);
        model.addAttribute("filterCategory", category);
        model.addAttribute("filterEnrollment", enrollment);
        model.addAttribute("filterRegion", region);

        return "dashboard";
    }

    @GetMapping("/applicants/{id}")
    public String applicantDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("applicant",
            applicantService.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found")));
        model.addAttribute("documents",
            requirementService.findByApplicationId(id));
        return "applicant-detail";
    }
}
