package com.example.cartest.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.Year;


/**
 * Main car model
 */
@Entity // This tells Hibernate to make a table out of this class
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Brand of car
     */
    @NotBlank(message = "Brand is required")
    private String brand;

    /**
     * Car name
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * Year of manufacturing
     */
    @Range(min = 1900, max = Year.MAX_VALUE)
    @Digits(fraction = 0, integer = 4, message = "Invalid year")
    private int year;

    /**
     * Month of manufacturing
     */
    @Range(min = 0, max = 12)
    private int month = 0;

    /**
     * Engine Volume, fox ex. 1.6, 1.8...
     */
    @Range(min = 0, max = 40)
    private double engineVolume = 0;

    /**
     * Determines if there is a turbine in the engine
     */
    private boolean hasTurbo = false;

    /**
     * Engine power
     */
    @Range(min = 0)
    private double power;

    /**
     * Gear box type
     */
    @Enumerated(EnumType.STRING)
    private GearBox gearBox;

    /**
     * Wheel drive kind
     */
    @Enumerated(EnumType.STRING)
    private WheelDrive wheelDrive;

    /**
     * Color
     */
    private String color;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public boolean isHasTurbo() {
        return hasTurbo;
    }

    public void setHasTurbo(boolean hasTurbo) {
        this.hasTurbo = hasTurbo;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }

    public WheelDrive getWheelDrive() {
        return wheelDrive;
    }

    public void setWheelDrive(WheelDrive wheelDrive) {
        this.wheelDrive = wheelDrive;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Body type for car
     */
    public enum Body {
        SEDAN,
        SUV,
        CROSSOVER,
        WAGON,
        HATCHBACK,
        CABRIOLET,
    }

    /**
     * Type of gear box for car
     */

    public enum GearBox {
        AUTOMATIC("AUTOMATIC"),
        VARIATOR("VARIATOR"),
        MANUAL("MANUAL");

        public final String label;

        GearBox(String label) {
            this.label = label;
        }
    }

    /**
     * Wheel Drive Kind for car
     */
    public enum WheelDrive {
        REAR,
        FRONT,
        ALL,

    }


}
