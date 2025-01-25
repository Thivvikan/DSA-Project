final class Seat_Linked_List {
    private Seat Head;

    // Adding the seats method for the linked list
    public void Add_Seat(int Seat_Number) {
        // Object creation of seat class.
        Seat New_Seat = new Seat(Seat_Number);
        if (Head == null) {
            Head = New_Seat;
        } else {
            Seat current = Head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = New_Seat;
        }
    }
    // Constructor of Seat linked list class.
    Seat_Linked_List(int Number_Of_Seats) {
        Head = null;
        for (int i = 1; i <= Number_Of_Seats; i++) {
            Add_Seat(i);
        }
    }

    public void Book_Seat(int seatNumber, String NIC) {
        Seat current = Head;
        while (current != null) {
            if (current.Seat_Number == seatNumber && !current.Is_Booked) {
                current.Is_Booked = true;
                current.Customer_NIC = NIC;  // Associate the NIC with the seat
                System.out.println("Seat " + seatNumber + " booked successfully for NIC: " + NIC);
                return;
            }
            current = current.next;
        }
        System.out.println("Seat " + seatNumber + " is not available or already booked.");
    }


    public void Display_Available_Seats() {
        Seat current = Head;
        System.out.println("Available Seats:");
        while (current != null) {
            if (!current.Is_Booked) {
                System.out.println("Seat Number: " + current.Seat_Number);
            }
            current = current.next;
        }
    }

    public boolean Is_Full() {
        Seat current = Head;
        while (current != null) {
            if (!current.Is_Booked) {  // If there is any seat not booked
                return false;  // The list is not full
            }
            current = current.next;
        }
        return true;  // All seats are booked
    }

    public String Get_Seats_Status() {
        StringBuilder seatStatus = new StringBuilder();
        Seat current = Head;

        while (current != null) {
            if (current.Is_Booked) {
                seatStatus.append("Seat " + current.Seat_Number + ": Booked by " + current.Customer_NIC);
            } else {
                seatStatus.append("Seat " + current.Seat_Number + ": Available");
            }
            current = current.next;
            if (current != null) {
                seatStatus.append(", ");
            }
        }

        return seatStatus.toString();
    }

    public boolean Is_Seat_Available(int Seat_Number) {
        Seat current = Head;  // Start from the head of the linked list

        while (current != null) {
            if (current.Seat_Number == Seat_Number) {
                return !current.Is_Booked;  // Return true if seat is not booked
            }
            current = current.next;  // Move to the next seat in the list
        }

        return false;  // Return false if seat number is not found
    }


    public void Set_Seats_Status(String seatStatusLine) {
        Seat current = Head;  // Start from the head of the linked list
        int index = 0;

        // Iterate through each character in the status line
        while (current != null && index < seatStatusLine.length()) {
            char status = seatStatusLine.charAt(index);

            // Set the seat's booking status based on the character ('1' means booked, '0' means available)
            current.Is_Booked = (status == '1');
            current = current.next;
            index++;
        }
    }
}