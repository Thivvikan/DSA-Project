    // Nested Seat class to represent each seat
    class Seat {
        int Seat_Number;
        boolean Is_Booked;
        String Customer_NIC;
        Seat next;

        // Seat constructor.
        Seat(int Seat_Number) {
            this.Seat_Number = Seat_Number;
            this.Is_Booked = false;
            this.Customer_NIC = null;
            this.next = null;
        }
    }