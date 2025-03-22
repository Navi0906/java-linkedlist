class TicketNode {
    int ticketID;
    String customerName;
    String movieName;
    String seatNumber;
    String bookingTime;
    TicketNode next;

    public TicketNode(int ticketID, String customerName, String movieName, String seatNumber, String bookingTime) {
        this.ticketID = ticketID;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
    }
}

class TicketReservationSystem {
    private TicketNode head = null;
    private TicketNode tail = null;

    // Add a ticket at the end
    public void addTicket(int id, String customer, String movie, String seat, String time) {
        TicketNode newNode = new TicketNode(id, customer, movie, seat, time);
        if (head == null) {
            head = tail = newNode;
            tail.next = head;
        } else {
            tail.next = newNode;
            newNode.next = head;
            tail = newNode;
        }
        System.out.println("Ticket booked successfully.");
    }

    // Remove a ticket by Ticket ID
    public void removeTicket(int ticketID) {
        if (head == null) {
            System.out.println("No tickets available.");
            return;
        }

        TicketNode temp = head;
        TicketNode prev = tail;

        do {
            if (temp.ticketID == ticketID) {
                if (temp == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = temp.next;
                    if (temp == tail) {
                        tail = prev;
                    }
                }
                System.out.println("Ticket with ID " + ticketID + " removed.");
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Ticket with ID " + ticketID + " not found.");
    }

    // Display all tickets
    public void displayTickets() {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }
        TicketNode temp = head;
        do {
            displayTicket(temp);
            temp = temp.next;
        } while (temp != head);
    }

    // Search by Customer Name or Movie Name
    public void searchTicket(String keyword) {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }
        boolean found = false;
        TicketNode temp = head;
        do {
            if (temp.customerName.equalsIgnoreCase(keyword) || temp.movieName.equalsIgnoreCase(keyword)) {
                displayTicket(temp);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);

        if (!found) {
            System.out.println("No ticket found for: " + keyword);
        }
    }

    // Count total tickets
    public void totalTickets() {
        if (head == null) {
            System.out.println("Total booked tickets: 0");
            return;
        }
        int count = 0;
        TicketNode temp = head;
        do {
            count++;
            temp = temp.next;
        } while (temp != head);
        System.out.println("Total booked tickets: " + count);
    }

    // Helper to display a single ticket
    private void displayTicket(TicketNode node) {
        System.out.println("Ticket ID: " + node.ticketID + ", Customer: " + node.customerName + ", Movie: " + node.movieName +
                ", Seat: " + node.seatNumber + ", Time: " + node.bookingTime);
    }
}

public class TicketReserve {
    public static void main(String[] args) {
        TicketReservationSystem system = new TicketReservationSystem();

        system.addTicket(101, "Dhruv", "Inception", "A1", "12:00 PM");
        system.addTicket(102, "Madhav", "Inception", "A2", "12:05 PM");
        system.addTicket(103, "Pranav", "Avatar", "B1", "1:00 PM");

        System.out.println("\n--- All Tickets ---");
        system.displayTickets();

        System.out.println("\n--- Search by Customer Name 'Alice' ---");
        system.searchTicket("Dhruv");

        System.out.println("\n--- Search by Movie Name 'Avatar' ---");
        system.searchTicket("Avatar");

        System.out.println("\n--- Remove Ticket with ID 102 ---");
        system.removeTicket(102);

        System.out.println("\n--- All Tickets After Removal ---");
        system.displayTickets();

        System.out.println("\n--- Total Tickets Booked ---");
        system.totalTickets();
    }
}
