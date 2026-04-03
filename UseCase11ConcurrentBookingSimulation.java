import java.util.*;

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

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {

    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingRequestQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                r = queue.getNextRequest();
            }

            if (r == null) break;

            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " confirmed booking for " + r.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + r.getGuestName() + " (No Availability)");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Kesav", "Single"));
        queue.addRequest(new Reservation("Arun", "Single"));
        queue.addRequest(new Reservation("Meena", "Single"));
        queue.addRequest(new Reservation("Ravi", "Single"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
    }
}