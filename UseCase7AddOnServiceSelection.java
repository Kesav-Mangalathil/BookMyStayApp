import java.util.*;

class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName() + " to Reservation: " + reservationId);
    }

    public void showServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for Reservation: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " : " + s.getCost());
        }
    }

    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservation1 = "S1";
        String reservation2 = "D1";

        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1000);
        AddOnService extraBed = new AddOnService("Extra Bed", 800);

        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, airportPickup);
        manager.addService(reservation2, extraBed);

        manager.showServices(reservation1);
        manager.showServices(reservation2);

        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": " + manager.calculateTotalCost(reservation1));
        System.out.println("Total Add-On Cost for " + reservation2 + ": " + manager.calculateTotalCost(reservation2));
    }
}