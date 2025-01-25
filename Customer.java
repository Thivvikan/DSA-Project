// Customer class creation
class Customer{
    private String Name;
    private String Phone_Number;
    private String NIC;
    private int Age;
    private String Customer_Email_ID;
    private String Customer_City;

    // Constructor of the customer class
    public Customer(String Name, String Phone_Number, String NIC, int Age, String Customer_Email_ID, String Customer_City) {
        this.Name = Name;
        this.Phone_Number = Phone_Number;
        this.NIC = NIC;
        this.Age= Age;
        this.Customer_Email_ID=Customer_Email_ID;
        this.Customer_City=Customer_City;
    }
    // Get method for Name
    public String Get_Customer_Name() {
        return Name;
    }
    // Get method for Phone number
    public String Get_Customer_Phone_Number() {
        return Phone_Number;
    }
    // Get method for NIC
    public String Get_Customer_NIC() {
        return NIC;
    }
    // Get method for Age
    public int Get_Customer_Age() {
        return Age;
    }
    // Get method for Email id
    public String Get_Customer_Email_ID() {
        return Customer_Email_ID;
    }
    // Get method for customer city
    public String Get_Customer_City() {
        return Customer_City;
    }
}