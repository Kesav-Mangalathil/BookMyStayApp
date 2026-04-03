import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {
        int current = inventory.getOrDefault(type, -1);

        if (current == -1) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.put(type, current - 1);
    }
}

class BookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        int availability = inventory.getAvailability(reservation.getRoomType());

        if (availability == -1) {
            throw new InvalidBookingException("Room type does not exist: " + reservation.getRoomType());
        }

        if (availability <= 0) {
            throw new InvalidBookingException("Room not available: " + reservation.getRoomType());
        }
    }
}

class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {
        try {
            BookingValidator.validate(reservation, inventory);

            inventory.reduceAvailability(reservation.getRoomType());

            System.out.println("Booking Successful for " + reservation.getGuestName()
                    + " (" + reservation.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Double", 0);

        BookingService service = new BookingService(inventory);

        service.confirmBooking(new Reservation("Kesav", "Single"));
        service.confirmBooking(new Reservation("", "Single"));
        service.confirmBooking(new Reservation("Arun", "Double"));
        service.confirmBooking(new Reservation("Meena", "Suite"));
    }
}