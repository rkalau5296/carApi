package pl.rafal.carapi.car.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private long vehicleId;
    private String brand;
    private String model;
    private String color;
}
