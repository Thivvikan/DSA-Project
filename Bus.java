// Creating bus class
class Bus {
    private String Bus_Number;
    private int Number_of_seats;
    // constructor of bus class
    public Bus(String Bus_Number, int Number_of_seats) {
        this.Bus_Number = Bus_Number;
        this.Number_of_seats = Number_of_seats;
    }
    // Get method for bus number
    public String Get_Bus_Number() {
        return Bus_Number;
    }
    // Get method for Number of seats
    public int Get_Number_of_seats() {
        return Number_of_seats;
    }
}
