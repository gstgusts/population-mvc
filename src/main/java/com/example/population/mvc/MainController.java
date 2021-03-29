package com.example.population.mvc;

import com.example.population.data.City;
import com.example.population.data.County;
import com.example.population.data.DatabaseManager;
import com.example.population.data.Region;
import com.example.population.dto.CityDto;
import com.example.population.models.AddCityModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class MainController {
    @GetMapping("")
    public String getIndexPage(Model model) {
        var dm = new DatabaseManager();
        var cities = dm.getCities();
        model.addAttribute("cities", cities);
        return "index";
    }

    @GetMapping("/city/{id}")
    public String getCityDetails(@PathVariable int id, Model model) {

        var dm = new DatabaseManager();
        var city = dm.getCityById(id);

        dm.getPopulationDataForCity(city);

        model.addAttribute("city", city);

        return "details";
    }

    @GetMapping("/city/add")
    public String getAddCity(Model model) {

        var dataModel = new AddCityModel();

        var dm = new DatabaseManager();
        var counties = dm.getCounties();

        dataModel.setCounties(counties);

        // model.addAttribute("counties", counties);

        var regions = dm.getRegions();

        dataModel.setRegions(regions);

        dataModel.name = "asfdasdfasdfasdf";

        // model.addAttribute("regions", regions);

        model.addAttribute("viewModel", dataModel);

        return "add_city";
    }

    @PostMapping("/city/add")
    public ModelAndView addCity(@ModelAttribute("addCityData") CityDto dto) {

        var dm = new DatabaseManager();

        Region region = null;
        County county = null;

        if(dto.getCRegionId() != null) {
            var res = dm.getRegions().stream()
                    .filter(r -> r.getId() == dto.getCRegionId())
                    .findFirst();

            if(res.isPresent()) {
                region = res.get();
            }
        }

        if(dto.getCCountyId() != null) {
            var res = dm.getCounties().stream()
                    .filter(r -> r.getId() == dto.getCCountyId())
                    .findFirst();

            if(res.isPresent()) {
                county = res.get();
            }
        }

        var city = new City(0, dto.getCName(), region, county, dto.getCFounded(), new ArrayList<>());

        dm.addUpdateCity(city);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/region/{id}/cities")
    public String getCitiesInRegion(@PathVariable int id, Model model) {

        var dm = new DatabaseManager();
        var cities = dm.getCitiesByRegionId(id);

        model.addAttribute("cities", cities);

        return "cities_in_region";
    }

    @GetMapping("/chart/{id}")
    public String getGoogleChart(@PathVariable int id, Model model) {

        var dm = new DatabaseManager();
        var city = dm.getCityById(id);

        dm.getPopulationDataForCity(city);

        model.addAttribute("city", city);

        var sb = new StringBuilder();

        sb.append("var graphData = [\n");
        sb.append("['Year', 'Population'],\n");

        for (var pop :
                city.getPopulation()) {
            sb.append("['"+ pop.getYear() +"',  "+pop.getPopulation()+"],\n");
        }

        sb.append("];");

        sb.append("var cityName='"+city.getName()+"';");

        model.addAttribute("graph", sb.toString());

        return "google_chart";
    }
}
