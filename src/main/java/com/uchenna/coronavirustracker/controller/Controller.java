package com.uchenna.coronavirustracker.controller;


import com.uchenna.coronavirustracker.model.LocationStat;
import com.uchenna.coronavirustracker.service.DataService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private final DataService dataService;

    public Controller(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/home")
    public String home(Model model) throws IOException, InterruptedException {
        List<LocationStat> allStat = dataService.fetchVirusData();
        int totalReportedCases = allStat.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStat.stream().mapToInt(stat -> Integer.parseInt(stat.getDiffFromPrevDay())).sum();

        model.addAttribute("localDataStat", allStat);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
