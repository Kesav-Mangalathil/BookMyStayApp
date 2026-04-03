import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType);
    }
}

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void showAllBookings() {
        System.out.println("\nBooking History:");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            r.display();
        }
    }

    public void generateSummary() {
        List<Reservation> list = history.getAllReservations();

        Map<String, Integer> countByType = new HashMap<>();

        for (Reservation r : list) {
            String type = r.getRoomType();
            countByType.put(type, countByType.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nBooking Summary  (Room Type Count):");
        for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("S1", "Kesav", "Single"));
        history.addReservation(new Reservation("S2", "Arun", "Single"));
        history.addReservation(new Reservation("D1", "Meena", "Double"));
        history.addReservation(new Reservation("SU1", "Ravi", "Suite"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.showAllBookings();
        reportService.generateSummary();
    }
}