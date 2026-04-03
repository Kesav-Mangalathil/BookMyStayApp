import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Not enough rooms available for " + roomType);
        } else {
            inventory.put(roomType, updated);
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        System.out.println("\nBooking 2 Single rooms...");
        inventory.updateAvailability("Single", -2);

        System.out.println("Cancelling 1 Double room...");
        inventory.updateAvailability("Double", +1);

        inventory.displayInventory();

        System.out.println("\nAvailable Suites: " + inventory.getAvailability("Suite"));
    }
}