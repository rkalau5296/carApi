package pl.rafal.carapi.car.controller;

import org.springframework.web.bind.annotation.*;
import pl.rafal.carapi.car.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

    private final List<Vehicle> vehicles;

    public VehicleApi() {
        this.vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(1L, "BMW","850","black"));
        vehicles.add(new Vehicle(2L, "Ford","Fiesta","white"));
        vehicles.add(new Vehicle(3L, "Fiat","Panda","blue"));
        vehicles.add(new Vehicle(4L, "Fiat","Panda","blue"));
        vehicles.add(new Vehicle(5L, "Fiat","Panda","blue"));

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public Vehicle getVehicleById(@PathVariable Integer id){
        return vehicles.get(id-1);
    }

    @RequestMapping(value = "/vehicles/vehicles/{color}", method = RequestMethod.GET)
    public List<Vehicle> getVehicleByColor(@PathVariable String color){

        return vehicles.stream().filter(vehicle -> vehicle.getColor().equals(color)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void modVehicle(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> first = vehicles.stream().filter(vehicle -> vehicle.getVehicleId() == newVehicle.getVehicleId()).findFirst();
        if(first.isPresent()) {
            vehicles.remove(first.get());
            vehicles.add(newVehicle);
        }
    }

    @RequestMapping(value="/{id}/{color}", method = RequestMethod.PUT)
    public void modOneParamVehicle(@PathVariable Long id, @PathVariable String color) {
        Optional<Vehicle> first = vehicles.stream().filter(vehicle -> vehicle.getVehicleId() == id).findFirst();
        if(first.isPresent()) {
            vehicles.removeIf(vehicle->vehicle.getVehicleId()==id);
            vehicles.add(new Vehicle(first.get().getVehicleId(), first.get().getBrand(), first.get().getModel(), color));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void removeVehicle(@PathVariable Long id) {
        Optional<Vehicle> first = vehicles.stream().filter(vehicle -> vehicle.getVehicleId() == id).findFirst();
        first.ifPresent(vehicles::remove);
    }
}
