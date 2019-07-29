package com.example.cartest;

import com.example.cartest.domain.Car;
import com.example.cartest.repo.CarRepository;
import com.example.cartest.utils.PropertyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Car controller for view and get requests (via browser URL)
 */
@Controller
@RequestMapping(path = "/car")
public class CarController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CarRepository repo;

    /**
     * Main view
     *
     * @param model
     * @return
     */
    @GetMapping(path = "/")
    public String carsList(Model model) {
        model.addAttribute("carsList", getAll());
        return "easycar";
    }


    @GetMapping(path = "/add")
    public @ResponseBody
    String add(@ModelAttribute Car car) {
        try {
            repo.save(car);
            return "Saved";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }


    @GetMapping(path = "/update")
    public @ResponseBody
    Car update(@RequestParam int id,
               @RequestParam String field,
               @RequestParam Object value) {
        Optional<Car> car = repo.findById(id);
        if (!car.isPresent()) {
            throw new RuntimeException("Car not found. ID: " + id);
        }
        PropertyHelper.callSetter(car.get(), field, value);
        repo.save(car.get());
        return car.get();

    }

    @GetMapping(path = "/delete")
    public @ResponseBody
    Car delete(@RequestParam int id) {
        Optional<Car> car = repo.findById(id);
        if (car.isPresent()) {
            repo.delete(car.get());
            return car.get();
        } else {
            throw new RuntimeException("Car not found. ID: " + id);
        }
    }


    /**
     * Get all cars
     *
     * @return
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Car> getAll() {
        // This returns a JSON or XML with the users
        return repo.findAll();
    }


    /**
     * For testing use only
     *
     * @param brand
     * @param name
     * @param year
     * @param month
     * @param gearBox
     * @param params
     * @return
     */

    @GetMapping(path = "/add2")
    public @ResponseBody
    String add2(@RequestParam String brand,
                @RequestParam String name,
                @RequestParam int year,
                @RequestParam int month,
                @RequestParam Car.GearBox gearBox,
                @RequestParam(required = false) Map<String, String> params
    ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Car n = new Car();
        n.setName(name);
        n.setBrand(brand);
        n.setYear(year);
        n.setMonth(month);
        n.setGearBox(gearBox);
        if (params != null) {
            for (String paramName : params.keySet()) {
                PropertyHelper.callSetter(n, paramName, params.get(params));
            }
        }
        repo.save(n);
        return "Saved";
    }


}
