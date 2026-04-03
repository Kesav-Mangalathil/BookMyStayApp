import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

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

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

class SearchService {

    private RoomInventory inventory;
    private HashMap<String, Room> roomData;

    public SearchService(RoomInventory inventory, HashMap<String, Room> roomData) {
        this.inventory = inventory;
        this.roomData = roomData;
    }

    public void searchAvailableRooms() {
        System.out.println("\nAvailable Rooms:");

        for (String type : roomData.keySet()) {
            int available = inventory.getAvailability(type);

            if (available > 0) {
                Room room = roomData.get(type);
                System.out.println("Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + available);
                System.out.println("------------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 3);

        HashMap<String, Room> roomData = new HashMap<>();
        roomData.put("Single", new Room("Single", 2000, "WiFi, TV"));
        roomData.put("Double", new Room("Double", 3500, "WiFi, TV, AC"));
        roomData.put("Suite", new Room("Suite", 6000, "WiFi, TV, AC, Mini Bar"));

        SearchService searchService = new SearchService(inventory, roomData);

        searchService.searchAvailableRooms();
    }
}