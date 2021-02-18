package pl.rafal.carapi.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rafal.carapi.car.mail.SimpleEmailService;
import pl.rafal.carapi.car.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

    private final List<Vehicle> vehicles;

    private final SimpleEmailService emailService;

    @Autowired
    public VehicleApi(SimpleEmailService emailService) {
        this.vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(1L, "BMW","850","black"));
        vehicles.add(new Vehicle(2L, "Ford","Fiesta","white"));
        vehicles.add(new Vehicle(3L, "Fiat","Panda","blue"));
        vehicles.add(new Vehicle(4L, "Polonez","Caro","blue"));
        vehicles.add(new Vehicle(5L, "Trabant","Polo","blue"));
        this.emailService = emailService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public Vehicle getVehicleById(@PathVariable int id){
        return vehicles.get(id);
    }

    @RequestMapping(value = "/vehicles/vehicles/{color}", method = RequestMethod.GET)
    public List<Vehicle> getVehicleByColor(@PathVariable String color){
            List<Vehicle> colorListVehicle = new ArrayList<>();
            for(Vehicle vehicle:vehicles){
                if(vehicle.getColor().equals(color)){
                    colorListVehicle.add(vehicle);
                }
            }
            if(!colorListVehicle.isEmpty())
                return colorListVehicle;
            else    return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicles.add(vehicle);
        emailService.send();
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
