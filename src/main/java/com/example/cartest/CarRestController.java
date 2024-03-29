package com.example.cartest;

import com.example.cartest.domain.Car;
import com.example.cartest.repo.CarRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Car rest controller to retrieve and manipulate data via rest for EasyUI components
 */
@RestController
@RequestMapping(path = "/restcar")
public class CarRestController {
    public static final String ERROR_FIELD_NAME = "errorMsg";
    public static final String SUCCESS_FIELD_NAME = "success";

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CarRepository repo;

    /**
     * Add new car
     *
     * @param car new car instance
     * @return map which contains success message or error message
     */
    @PostMapping(path = "/add")
    public @ResponseBody
    Map<String, Object> add(@ModelAttribute Car car) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            repo.save(car);
            responseMap.put(SUCCESS_FIELD_NAME, Boolean.TRUE);
        } catch (Exception ex) {
            responseMap.putIfAbsent(ERROR_FIELD_NAME, "Something went wrong: " + ex.getLocalizedMessage());
        }
        return responseMap;
    }

    /**
     * Update car by id
     *
     * @param id     id of existing car model
     * @param car    car instance
     * @param errors error during model validation
     * @return map which contains success message or error message
     */
    @PostMapping(path = "/update")
    public @ResponseBody
    Map<String, Object> update(@RequestParam int id,
                               @Valid @ModelAttribute Car car,
                               Errors errors) {
        Map<String, Object> responseMap = new HashMap<>();
        if (errors.hasErrors()) {
            responseMap.putIfAbsent(ERROR_FIELD_NAME,
                    errors
                            .getAllErrors()
                            .stream().map(x -> {
                        if (x instanceof FieldError) {
                            return ((FieldError) x).getField() + ": " + x.getDefaultMessage();
                        }
                        return x.getDefaultMessage();
                    })
                            .collect(Collectors.joining(";b")));
        } else {
            try {
                Optional<Car> existingCar = repo.findById(id);
                if (!existingCar.isPresent()) {
                    throw new RuntimeException("Car not found. ID: " + id);
                }

                repo.save(car);
                responseMap.put(SUCCESS_FIELD_NAME, Boolean.TRUE);

            } catch (Exception ex) {
                responseMap.putIfAbsent(ERROR_FIELD_NAME, "Something went wrong: " + ex.getLocalizedMessage());
            }
        }
        return responseMap;
    }

    /**
     * Retrieve all or filtered cars in database with sort
     *
     * @param sort        Name of the sort field
     * @param order       Sort kind. Accept two values: 'ASC' or 'DESC'
     * @param filterRules Filter rules, which defined custom filter. This parameter is a string
     *                    representation of JSON array. Each JSON is filter rule,
     *                    which contains 'field','op' and 'value' properties.
     *                    op: the filter operation, possible values are:
     *                    contains,equal,notequal,beginwith,endwith,less,lessorequal,greater,greaterorequal
     *                    Now supported op's: 'contains'
     * @return Appropriate list of cars
     */
    @RequestMapping(
            value = "/all",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    //@PostMapping(path = "/all")
    public @ResponseBody
    Iterable<Car> getAll(@RequestParam(required = false) String sort,
                         @RequestParam(required = false) String order,
                         @RequestParam(required = false) String filterRules
    ) {

        if (StringUtils.isNotBlank(filterRules)) {
            Type type = new TypeToken<ArrayList<Map<String, String>>>() {
            }.getType();
            ArrayList<Map<String, String>> data = new Gson().fromJson(filterRules, type);
            if (data != null && !data.isEmpty()) {
                return repo.findCars(data);
            }
        }

        if (StringUtils.isBlank(sort)) {
            return repo.findAll();
        }

        Sort.Direction direction = Sort.Direction.ASC;
        if (StringUtils.isNotBlank(order)) {
            direction = Sort.Direction.fromString(order);
        }
        return repo.findAll(Sort.by(direction, sort));
    }

    /**
     * Delete car by id
     *
     * @param id Id of existing car model
     * @return map which contains success message or error message
     */
    @PostMapping(path = "/delete")
    public @ResponseBody
    Map<String, Object> delete(@RequestParam int id) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Optional<Car> car = repo.findById(id);

            if (car.isPresent()) {
                repo.delete(car.get());
                responseMap.put(SUCCESS_FIELD_NAME, Boolean.TRUE);
            } else {
                responseMap.put(ERROR_FIELD_NAME, "Can't find given item with id: " + id);
            }
        } catch (Exception ex) {
            responseMap.putIfAbsent(ERROR_FIELD_NAME, "Something went wrong: " + ex.getLocalizedMessage());
        }
        return responseMap;
    }

    @GetMapping(path = "/gearboxes")
    public @ResponseBody
    List<Map<String, String>> getGearBoxes() {
        List<Map<String, String>> result = new ArrayList<>();
        for (Car.GearBox gearBox : Car.GearBox.values()) {
            Map<String, String> gearBoxMap = new HashMap<>();
            gearBoxMap.put("name", gearBox.name());
            result.add(gearBoxMap);

        }
        return result;
    }
}
