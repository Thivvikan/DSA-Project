import java.io.*;
import java.text.*;
import java.util.*;

// Driver class of the system.
class Main{
    Scanner sc=new Scanner(System.in);

// Date validating method.
    public boolean Validate_Date(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);  // Ensure strict parsing
        
        try {
            dateFormat.parse(date);  // Attempt to parse the date
            return true;
        } catch (ParseException e) {
            return false;  // Date is invalid if parsing fails
        }
    }

// Time validating method.
    public boolean Validate_Time(String time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);  // Ensure strict parsing
        
        try {
            timeFormat.parse(time);  // Attempt to parse the time
            return true;
        } catch (ParseException e) {
            return false;  // Time is invalid if parsing fails
        }
    }

// ******************Customer section*****************************************************************//
// Customer register method
    public void Customer_Register() {
        // Getting user inputs.
        System.out.print("Enter your name: ");
        String Customer_Name = sc.next();
        System.out.print("Enter your phone number: ");
        String Customer_Phone_Number = sc.next();
        System.out.print("Enter your NIC: ");
        String Customer_NIC = sc.next();
        System.out.print("Enter your age: ");
        int Customer_Age = sc.nextInt();
        System.out.print("Enter your Email ID: ");
        String Customer_Email_ID = sc.next();
        System.out.print("Enter your City: ");
        String Customer_City = sc.next();

        // Check if any field is empty or null
        if (!Customer_Name.isEmpty() && !Customer_Phone_Number.isEmpty() && !Customer_NIC.isEmpty() &&
            Customer_Age > 0 && !Customer_Email_ID.isEmpty() && !Customer_City.isEmpty()) {

            // Check if customer is already registered by reading the file
            boolean isRegistered = false;
                try (BufferedReader reader = new BufferedReader(new FileReader("Customer_Details.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("NIC: " + Customer_NIC)) {
                            isRegistered = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while reading customer details.");
                }

            if (isRegistered) {
                System.out.println("Customer with the NIC number of "+Customer_NIC+", is already registered.");
            } else {
                // Register new customer
                Customer New_Customer = new Customer(Customer_Name, Customer_Phone_Number, Customer_NIC, Customer_Age, Customer_Email_ID, Customer_City);

                // Add customer to the text file using file handling
                try (FileWriter writer = new FileWriter("Customer_Details.txt", true)) {
                    writer.write("Name: " + New_Customer.Get_Customer_Name() + ", Phone Number: " +
                            New_Customer.Get_Customer_Phone_Number() + ", NIC: " +
                            New_Customer.Get_Customer_NIC() + ", Age: " + New_Customer.Get_Customer_Age() +
                            ", Email ID: " + New_Customer.Get_Customer_Email_ID() + ", City: " +
                            New_Customer.Get_Customer_City() + "\n");
                    System.out.println("Customer details saved successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred while saving customer details.");
                }
            }
        } else {
            System.out.println("Please enter all the details correctly.");
        }
        // Calling customer menu method.
        Customer_Menu();
    }

// Customer search method
    public void Customer_Profile_View(String Customer_NIC){
        File originalFile = new File("Customer_Details.txt");
        File tempFile = new File("Temp_Customer_Details.txt");
        boolean customerFound = false;
        // File handling method to retrieve customer details.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains("NIC: " + Customer_NIC)) {
                    customerFound = true;
                    System.out.println("Customer found: " + currentLine);
                }
                writer.write(currentLine + System.lineSeparator());
            }

            reader.close();
            writer.close();

            if (!customerFound) {
                System.out.println("Customer with NIC " + Customer_NIC + " not found.");
                tempFile.delete();
            } 
            else {
                if (originalFile.delete()) {
                    tempFile.renameTo(originalFile);
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while updating customer details.");
        }
        Customer_Menu();    
    }

// Customer update method
    public void Customer_Update(String Customer_NIC) {
        File originalFile = new File("Customer_Details.txt");
        File tempFile = new File("Temp_Customer_Details.txt");
        boolean customerFound = false;

        // File handling to find and write the customer details.
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line contains the NIC of the customer to be updated
                if (currentLine.contains("NIC: " + Customer_NIC)) {
                    customerFound = true;
                    System.out.println("Customer found: " + currentLine);

                    // Extract current details
                    String[] details = currentLine.split(", ");
                    String currentName = details[0].split(": ")[1];
                    String currentPhoneNumber = details[1].split(": ")[1];
                    String currentNIC = details[2].split(": ")[1];
                    int currentAge = Integer.parseInt(details[3].split(": ")[1]);
                    String currentEmail = details[4].split(": ")[1];
                    String currentCity = details[5].split(": ")[1];

                    sc.nextLine();

                    // Prompt user for updated information
                    System.out.print("Enter new name (leave blank to keep current): ");
                    String Updated_Name = sc.nextLine();
                    if (Updated_Name.isEmpty()) {
                        Updated_Name = currentName;
                    }

                    System.out.print("Enter new phone number (leave blank to keep current): ");
                    String Updated_Phone_Number = sc.nextLine();
                    if (Updated_Phone_Number.isEmpty()) {
                        Updated_Phone_Number = currentPhoneNumber;
                    }

                    System.out.print("Enter corrected NIC number (leave blank to keep current): ");
                    String Updated_NIC = sc.nextLine();
                    if (Updated_NIC.isEmpty()) {
                        Updated_NIC = currentNIC;
                    }

                    System.out.print("Enter new age (leave blank to keep current): ");
                    String ageInput = sc.nextLine();
                    int Updated_Age = ageInput.isEmpty() ? currentAge : Integer.parseInt(ageInput);

                    System.out.print("Enter new Email ID (leave blank to keep current): ");
                    String Updated_Email_ID = sc.nextLine();
                    if (Updated_Email_ID.isEmpty()) {
                        Updated_Email_ID = currentEmail;
                    }

                    System.out.print("Enter new City (leave blank to keep current): ");
                    String Updated_City = sc.nextLine();
                    if (Updated_City.isEmpty()) {
                        Updated_City = currentCity;
                    }

                    // Create the updated customer information line
                    currentLine = "Name: " + Updated_Name + ", Phone Number: " + Updated_Phone_Number +
                            ", NIC: " + Updated_NIC + ", Age: " + Updated_Age +
                            ", Email ID: " + Updated_Email_ID + ", City: " + Updated_City;
                    System.out.println("Customer details updated successfully.");
                }
                // Write the current or updated line to the temp file
                writer.write(currentLine + System.lineSeparator());
            }

            reader.close();
            writer.close();

            if (!customerFound) {
                System.out.println("Customer with NIC " + Customer_NIC + " not found.");
                tempFile.delete();
            } else {
                // Replace original file with the updated temp file
                if (originalFile.delete()) {
                    tempFile.renameTo(originalFile);
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while updating customer details.");
        }

        // Return to customer menu
        Customer_Menu();
    }

// Customer delete method
    public void Customer_Delete(String  Customer_NIC){

        File originalFile = new File("Customer_Details.txt");
        File tempFile = new File("Temp_Customer_Details.txt");
        // file handling to find and write the customer details.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean customerFound = false;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains("NIC: " + Customer_NIC)) {
                    customerFound = true;
                    continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            reader.close();
            writer.close();
            // Deleting customer
            if (customerFound) {
                if (originalFile.delete()) {
                    tempFile.renameTo(originalFile);
                    System.out.println("Customer deleted successfully.");
                }
            } else {
                System.out.println("Customer with NIC " + Customer_NIC + " not found.");
                tempFile.delete();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while deleting customer details.");
        }
        Customer_Menu();
    }

// Method to view bookings of a customer.
    public void Customer_View_Booking() {
        // Get user input of NIC to view bookings.
        System.out.print("Enter your NIC to view your bookings: ");
        String Customer_NIC = sc.next();
        File inputFile = new File("Bus_Schedules.txt");
        boolean foundBooking = false;
        // Fle handling method to retrieve booking details.
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String currentSchedule = null;

            while ((line = reader.readLine()) != null) {
                // Capture schedule details when a new schedule starts
                if (line.contains("Bus Number:")) {
                    currentSchedule = line; // Store the bus number or schedule identifier
                }

                // Check for seat status lines within the current schedule
                if (line.startsWith("Seat Status:")) {
                    String[] seats = line.split(", "); // Split seats by comma for easier processing
                    StringBuilder bookedSeats = new StringBuilder("Booked Seats: ");

                    boolean seatsFound = false;

                    // Check each seat to see if it's booked by the specified NIC
                    for (String seat : seats) {
                        if (seat.contains("Booked by " + Customer_NIC)) {
                            bookedSeats.append(seat.trim()).append(" "); // Collect each booked seat
                            foundBooking = true;
                            seatsFound = true;
                        }
                    }

                    // Display schedule details and booked seats if any are found for this NIC
                    if (seatsFound) {
                        System.out.println("Schedule: " + currentSchedule);
                        System.out.println(bookedSeats.toString().trim());
                    }
                }
            }

            if (!foundBooking) {
                System.out.println("No bookings found for the provided NIC.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while viewing bookings.");
        }
        Customer_Menu();
    }

// node class for stack.
class Customer_Node {
    String Data;
    Customer_Node next;
    // Constructor
    public Customer_Node(String Data) {
        this.Data = Data;
        this.next = null;
    }
}

// Customer stack to create stack.
class Customer_Stack {
    private Customer_Node Top;
    // Constructor
    public Customer_Stack() {
        this.Top = null;
    }
    // Push method for adding nodes.
    public void push(String Data) {
        Customer_Node New_Node = new Customer_Node(Data);
        New_Node.next = Top;
        Top = New_Node;
    }
    // Pop method to remove nodes.
    public String pop() {
        if (Top == null) {
            return null;
        }
        String data = Top.Data;
        Top = Top.next;
        return data;
    }
    // Method to check the stack is empty or not.
    public boolean isEmpty() {
        return Top == null;
    }
}

// View all customers method for admin
    public void View_All_Customers() {
        // file handling method to read all customers.
        try {
            FileReader fileReader = new FileReader("Customer_Details.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Customer_Stack stack = new Customer_Stack();
            String line;

            // Push each line onto the stack
            while ((line = bufferedReader.readLine()) != null) {
                stack.push(line);
            }

            // Close readers
            bufferedReader.close();
            fileReader.close();

            System.out.println("Registered Customers (Newest to old):");
            // Pop each element from the stack to display in reverse order
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }

        } catch (FileNotFoundException e) {
            System.out.println("No customer details found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading customer details.");
        }
        Admin_Customer_Menu();
    }

// ******************Bus section*****************************************************************//
// Bus register method
    public void Bus_Register() {
        // Getting user input for bus register.
        System.out.print("Enter Bus number correctly as unchangeable(Eg: ABC1234): ");
        sc.nextLine(); 
        String Bus_Number = sc.nextLine(); 
        // Validating the bus number.
        if (Bus_Number == null || Bus_Number.isEmpty() || !Bus_Number.matches("^[A-Z]{3}[0-9]{4}$")) {
            System.out.println("Please enter a valid bus number.");
            Bus_Menu();
            return;
        }
        // find that the bus is already registered.
        if(Is_Duplicate_BusNumber(Bus_Number)){
            System.out.println("The bus is already registered.");
        }
        else{
            System.out.print("Enter Number of seats: ");
            int Number_of_seats = sc.nextInt();
            // check whether empty fields there.
            if (!Bus_Number.isEmpty() && Number_of_seats > 0) { 
                Bus New_Bus = new Bus(Bus_Number, Number_of_seats);
                // file handling method to save bus details.
                try (FileWriter writer = new FileWriter("Bus_Details.txt", true)) {
                    writer.write("Bus Number: "+New_Bus.Get_Bus_Number() + 
                    ", Number of seats: " + New_Bus.Get_Number_of_seats() +
                    "\n");  // Add a new line after each entry
                    System.out.println("Bus details saved successfully.");
                    writer.close();

                } catch (IOException e) {
                    System.out.println("An error occurred while saving bus details: " + e.getMessage());
                }
            } else {
                System.out.println("Please enter valid bus details.");
            }
        }
    Bus_Menu();
    }

// method to check the bus is already egistered
    private boolean Is_Duplicate_BusNumber(String Bus_Number) {
        // file handling for search bus is there.
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Bus Number: " + Bus_Number + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bus details file for duplicates: " + e.getMessage());
        }
        return false;
    }

// Bus update method
    public void Bus_Update(String Bus_Number) {
        File Original_File = new File("Bus_Details.txt");
        File Temp_File = new File("Temp_Bus_Details.txt");
        boolean Bus_Found = false;
        // Check whether the Bus_Details file is created or not.
        if (!Original_File.exists()) {
            System.out.println("No buses registered yet.");
        }
        // file handling method for finding and updating bus details.
        try (BufferedReader reader = new BufferedReader(new FileReader(Original_File));
            BufferedWriter writer = new BufferedWriter(new FileWriter(Temp_File))) {

            String Current_Line;
            while ((Current_Line = reader.readLine()) != null) {
                String[] Bus_Details = Current_Line.split(",");
                String Current_Bus_Number = Bus_Details[0].trim().split(":")[1].trim(); // Extract bus number after 'Bus Number:'

                if (Current_Bus_Number.equalsIgnoreCase(Bus_Number)) {
                    Bus_Found = true;
                    System.out.println("Bus found: " + Current_Line);

                    // Ask for new number of seats
                    System.out.print("Enter new Number of seats: ");
                    int Updated_Number_of_Seats = sc.nextInt();

                    // Write updated bus details
                    writer.write("Bus Number: " + Bus_Number + ", Number of seats: " + Updated_Number_of_Seats + "\n");
                    System.out.println("Bus details updated successfully.");
                } else {
                    // Copy the unchanged line to the temporary file
                    writer.write(Current_Line + System.lineSeparator());
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while updating bus details: " + e.getMessage());
        }

        if (Bus_Found) {
            // Replace the original file with the updated file
            if (Original_File.delete() && Temp_File.renameTo(Original_File)) {
                // System.out.println("Bus details have been updated.");
            } else {
                System.out.println("An error occurred while finalizing the update.");
            }
        } else {
            System.out.println("Bus with number " + Bus_Number + " not found.");
            Temp_File.delete();  // Clean up temporary file
        }
        Bus_Menu();
    }

// Bus delete method
    public void Bus_Delete(String Bus_Number) {
        File Original_File = new File("Bus_Details.txt");
        File Temp_File = new File("Temp_Bus_Details.txt");
        boolean Bus_Found = false;

        // Check if the file exists
        if (!Original_File.exists()) {
            System.out.println("No buses registered yet.");
            return;
        }
        // file handling method to find the bus and delete.
        try (BufferedReader reader = new BufferedReader(new FileReader(Original_File));
            BufferedWriter writer = new BufferedWriter(new FileWriter(Temp_File))) {

            String Current_Line;
            while ((Current_Line = reader.readLine()) != null) {
                String[] Bus_Details = Current_Line.split(",");
                String Current_Bus_Number = Bus_Details[0].trim().split(":")[1].trim(); // Extract bus number after 'Bus Number:'

                if (!Current_Bus_Number.equalsIgnoreCase(Bus_Number)) {
                    // Write all buses except the one to be deleted
                    writer.write(Current_Line + System.lineSeparator());
                } else {
                    Bus_Found = true;  // Bus to be deleted was found
                    System.out.print("Bus with number " + Bus_Number + " deleted successfully. ");
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while processing bus details: " + e.getMessage());
            return;
        }

        if (Bus_Found) {
            // Replace original file with the updated one
            if (Original_File.delete() && Temp_File.renameTo(Original_File)) {
                System.out.println("File successfully updated.");
            } else {
                System.out.println("An error occurred while finalizing the deletion.");
            }
        } else {
            System.out.println("Bus with number " + Bus_Number + " not found.");
            Temp_File.delete();
        }
        Bus_Menu();
    }

// View all buses method for admin
    public void View_All_Buses(){
        // file handling method to retrieve all buses from file.
        try {
            FileReader fileReader = new FileReader("Bus_Details.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            System.out.println("Registered Buses:");
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line); 
            }

            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No bus details found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading customer details.");           
        }
        Admin_Menu();
    }

// ******************Bus schedule section*****************************************************************//
// Insert method for Schedule
    public void Enter_Schedule() {
        // Getting user input for getting the bus number.
        System.out.print("Enter Bus number: ");
        sc.nextLine();
        String Bus_Number = sc.nextLine();

        // Check if the bus is registered and retrieve the number of seats
        int Number_of_seats = Get_Seats_From_Bus(Bus_Number);

        if (Number_of_seats > 0) {
            // Bus is registered, proceed with the schedule entry
            System.out.print("Enter Starting Point: ");
            String Starting_Point = sc.nextLine();
            
            System.out.print("Enter Destination: ");
            String Destination = sc.nextLine();
            
            String Date;
            do {
                System.out.print("Enter Date (DD/MM/YYYY): ");
                Date = sc.nextLine();
                if (!Validate_Date(Date)) {
                    System.out.println("Invalid date format. Please enter a valid date (DD/MM/YYYY).");
                }
            } while (!Validate_Date(Date)); // Repeat until valid input
            
            String Time;
            do {
                System.out.print("Enter Time (HH:MM): ");
                Time = sc.nextLine();
                if (!Validate_Time(Time)) {
                    System.out.println("Invalid time format. Please enter a valid time (HH:MM).");
                }
            } while (!Validate_Time(Time)); // Repeat until valid input
            
            System.out.print("Enter Fare: ");
            double Fare = sc.nextDouble();
            sc.nextLine();  // Consume newline after double input

            // Create a seat linked list based on the number of seats
            Seat_Linked_List seatList = new Seat_Linked_List(Number_of_seats);

            // Save the schedule to a file
            try (FileWriter writer = new FileWriter("Bus_Schedules.txt", true)) {
                writer.write("Bus Number: " + Bus_Number + 
                            ", Starting Point: " + Starting_Point + 
                            ", Destination: " + Destination + 
                            ", Date: " + Date + 
                            ", Time: " + Time + 
                            ", Fare: " + Fare + 
                            ", Number of seats: " + Number_of_seats + "\n");
                
                // Write the seat statuses in the new format with seat numbers and status
                writer.write("Seat Status: " + seatList.Get_Seats_Status() + "\n"); 
                
                writer.flush();  // Ensure data is written
                System.out.println("Schedule saved successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving the schedule: " + e.getMessage());
            }
        } 
        else {
            System.out.println("Invalid bus number. The bus is not registered. Please register the bus first.");
        }
        Schedule_Menu();
    }

// Method to get the number of seats for a given bus from Bus_Details.txt
    public int Get_Seats_From_Bus(String Bus_Number) {
        // file handling method to find the bus.
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Bus Number: " + Bus_Number)) {
                    String[] details = line.split(", ");
                    for (String detail : details) {
                        if (detail.startsWith("Number of seats: ")) {
                            return Integer.parseInt(detail.split(": ")[1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading bus details: " + e.getMessage());
        }
        return -1;
    }

// Update schedule method
    public void Update_Schedule() {
        System.out.print("Enter Bus number: ");
        String Bus_Number = sc.next();

        System.out.print("Enter Starting Point: ");
        String Starting_Point = sc.next();

        System.out.print("Enter Destination: ");
        String Destination = sc.next();

        System.out.print("Enter Date (DD/MM/YYYY): ");
        String Date = sc.next();

        System.out.print("Enter Time (HH:MM): ");
        String Time = sc.next();

        // Temporary file to store updated schedules
        File tempFile = new File("temp_Bus_Schedules.txt");
        
        boolean scheduleFound = false;
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"));
            FileWriter writer = new FileWriter(tempFile)) {

            while ((line = reader.readLine()) != null) {
                // Identify the schedule based on input details
                if (line.contains("Bus Number: " + Bus_Number) &&
                    line.contains("Starting Point: " + Starting_Point) &&
                    line.contains("Destination: " + Destination) &&
                    line.contains("Date: " + Date) &&
                    line.contains("Time: " + Time)) {

                    scheduleFound = true; // Mark that we've found the schedule to update
                    System.out.println("Schedule found: " + line);

                    sc.nextLine();

                    // Prompt the user for new details
                    System.out.print("Enter new Starting Point (or press Enter to keep the same): ");
                    String New_Starting_Point = sc.nextLine();
                    if (!New_Starting_Point.isEmpty()) {
                        Starting_Point = New_Starting_Point;
                    }

                    System.out.print("Enter new Destination (or press Enter to keep the same): ");
                    String New_Destination = sc.nextLine();
                    if (!New_Destination.isEmpty()) {
                        Destination = New_Destination;
                    }

                    System.out.print("Enter new Date (DD/MM/YYYY) (or press Enter to keep the same): ");
                    String New_Date = sc.nextLine();
                    if (!New_Date.isEmpty()) {
                        Date = New_Date;
                    }

                    System.out.print("Enter new Time (HH:MM) (or press Enter to keep the same): ");
                    String New_Time = sc.nextLine();
                    if (!New_Time.isEmpty()) {
                        Time = New_Time;
                    }

                    System.out.print("Enter new Fare (or press Enter to keep the same): ");
                    String New_Fare_Input = sc.nextLine();
                    double New_Fare = -1;
                    if (!New_Fare_Input.isEmpty()) {
                        New_Fare = Double.parseDouble(New_Fare_Input);
                    }

                    String updatedSchedule = "Bus Number: " + Bus_Number +
                                            ", Starting Point: " + Starting_Point +
                                            ", Destination: " + Destination +
                                            ", Date: " + Date +
                                            ", Time: " + Time;

                    if (New_Fare != -1) {
                        updatedSchedule += ", Fare: " + New_Fare;
                    } else {
                        // If fare wasn't updated, keep the old fare from the original line
                        updatedSchedule += line.substring(line.indexOf(", Fare: "));
                    }

                    // Write the updated schedule to the temporary file
                    writer.write(updatedSchedule + "\n");

                } else {
                    // If it's not the line to be updated, copy it as-is to the temp file
                    writer.write(line + "\n");
                }
            }

            // Close both the writer and reader
            writer.flush();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // If the schedule was found, rename the temp file to replace the original file
        if (scheduleFound) {
            // Delete the original file and rename the temp file
            File originalFile = new File("Bus_Schedules.txt");
            if (originalFile.delete()) {
                if (tempFile.renameTo(originalFile)) {
                    System.out.println("Schedule updated successfully.");
                } else {
                    System.out.println("Failed to rename temp file.");
                }
            } else {
                System.out.println("Failed to delete original schedule file.");
            }
        } else {
            // If no matching schedule was found, delete the temp file
            System.out.println("Schedule not found. Please check the details.");
            tempFile.delete();
        }
        Schedule_Menu();
    }

// Delete schedule method
    public void Delete_Schedule() {
        // Getting bus number as user input
        System.out.print("Enter Bus number: ");
        String Bus_Number = sc.next();

        System.out.print("Enter Starting Point: ");
        String Starting_Point = sc.next();

        System.out.print("Enter Destination: ");
        String Destination = sc.next();

        System.out.print("Enter Date (DD/MM/YYYY): ");
        String Date = sc.next();

        System.out.print("Enter Time (HH:MM): ");
        String Time = sc.next();

        File tempFile = new File("temp_Bus_Schedules.txt");
        boolean scheduleFound = false;
        String line;

        // File handling to delete the schedule
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"));
            FileWriter writer = new FileWriter(tempFile)) {

            while ((line = reader.readLine()) != null) {
                if (line.contains("Bus Number: " + Bus_Number) &&
                    line.contains("Starting Point: " + Starting_Point) &&
                    line.contains("Destination: " + Destination) &&
                    line.contains("Date: " + Date) &&
                    line.contains("Time: " + Time)) {

                    scheduleFound = true;
                    System.out.println("Schedule found and deleted: " + line);

                    // Check and skip the next line if it is "Seat Status"
                    String nextLine = reader.readLine();
                    if (nextLine != null && nextLine.startsWith("Seat Status:")) {
                        // Skip the Seat Status line
                        continue;
                    } else if (nextLine != null) {
                        // If not "Seat Status," write it back to the temp file
                        writer.write(nextLine + "\n");
                    }
                    continue; // Skip writing the current schedule line
                }

                // Write non-deleted lines to the temp file
                writer.write(line + "\n");
            }

            writer.flush();

        } catch (IOException e) {
            System.out.println("An error occurred while deleting the schedule: " + e.getMessage());
        }

        // Finalizing deletion
        if (scheduleFound) {
            File originalFile = new File("Bus_Schedules.txt");
            if (originalFile.delete()) {
                if (tempFile.renameTo(originalFile)) {
                    System.out.println("Schedule and seat status deleted successfully.");
                } else {
                    System.out.println("Failed to rename temp file.");
                }
            } else {
                System.out.println("Failed to delete original schedule file.");
            }
        } else {
            System.out.println("Schedule not found. Please check the details.");
            tempFile.delete();
        }
        Schedule_Menu();
    }

// ******************Customer Booking section*****************************************************************//
// Check customer is registered or not.
    public boolean Is_Customer_Registered(String NIC) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Customer_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains "NIC: <NIC>"
                if (line.contains("NIC: " + NIC)) {
                    return true; // Customer is registered
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer file: " + e.getMessage());
        }
        return false; // Customer is not registered
    }

// Method to find the schedule based on starting point, destination, date, and time
    private String Find_Schedule(String startingPoint, String destination, String date, String time) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Starting Point: " + startingPoint) && 
                    line.contains("Destination: " + destination) &&
                    line.contains("Date: " + date) && 
                    line.contains("Time: " + time))
                    {
                    return line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
        return null; 
    }

// Method to retrieve available schedules based on starting point, destination, date.
    public void Get_Available_Schedules(String startingPoint, String destination, String date) {
        boolean scheduleFound = false;  // Flag to track if any schedule is found

        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(startingPoint) && line.contains(destination) && line.contains(date)) {
                    System.out.println(line);  // Print the schedule details
                    scheduleFound = true;  // Mark that a schedule was found
                }
            }

            // If no schedule matches the criteria
            if (!scheduleFound) {
                System.out.println("No schedules found for the given starting point, destination, and date.");
                Customer_Menu();
            }

        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
    }

// Method to select the customer preferred schedule.
    public void Preferred_Schedules(String Starting_Point, String Destination, String Date, String Time, String Bus_Number) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            boolean isScheduleLine = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(Starting_Point) && line.contains(Destination) && line.contains(Date) && line.contains(Time) && line.contains(Bus_Number)) {
                    System.out.println(line); 
                    isScheduleLine = true; 
                } else if (isScheduleLine && line.startsWith("Seat Status:")) {
                    System.out.println(line);
                    isScheduleLine = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
    }

// Method to check if the customer already has a booking for the selected schedule
    public boolean Has_Existing_Booking(String NIC, String Selected_Schedule) {
        if (Selected_Schedule == null || Selected_Schedule.isEmpty()) {
            System.out.println("Error: Selected schedule is empty or null.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            boolean scheduleFound = false;

            while ((line = reader.readLine()) != null) {
                // Check if the line matches the selected schedule details
                if (line.contains(Selected_Schedule)) {
                    scheduleFound = true;
                }

                // If we're within the selected schedule section, check the Seat Status lines
                if (scheduleFound && line.startsWith("Seat Status:")) {
                    if (line.contains("Booked by " + NIC)) {
                        return true; // Booking exists for this NIC and schedule
                    }
                }

                // Reset `scheduleFound` when reaching the next schedule or end of file
                if (scheduleFound && line.trim().isEmpty()) {
                    scheduleFound = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }
        return false; // No existing booking found
    }

// Waiting queue mapping object.
    private final CustomMap waitingLists = new CustomMap();

// Method to add customer to waiting list.
    public void Add_To_Waiting_List(String NIC, String selectedSchedule) {
        CustomQueue queue = waitingLists.get(selectedSchedule);
        if (queue == null) {
            queue = new CustomQueue();
            waitingLists.put(selectedSchedule, queue);
        }
        
        queue.enqueue(NIC);

        // Write to the Waiting_Queues.txt file for persistence
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Waiting_Queues.txt", true))) {
            writer.write("NIC: " + NIC + ", Schedule: " + selectedSchedule + "\n");
            System.out.println("Added to the waiting list for schedule: " + selectedSchedule);
        } catch (IOException e) {
            System.out.println("Error adding customer to waiting list: " + e.getMessage());
        }
    }

// Method to book a seat in the schedule.
    public void Book_Seat(String Selected_Schedule, String NIC) {
        File scheduleFile = new File("Bus_Schedules.txt");
        File tempFile = new File("Temp_Bus_Schedules.txt");
        // file handling to make bookings
        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean scheduleFound = false;
            boolean seatBooked = false;
            boolean hasAvailableSeats = false;
            while ((line = reader.readLine()) != null) {
                // Identify the start of the selected schedule section
                if (line.contains(Selected_Schedule)) {
                    scheduleFound = true;
                }
                // If we are within the correct schedule, look for available seats
                if (scheduleFound && line.startsWith("Seat Status:")) {
                    String[] seatStatuses = line.split(", "); // Split each seat status
                    // Check if there's at least one available seat
                    for (String seatStatus : seatStatuses) {
                        if (seatStatus.contains("Available")) {
                            hasAvailableSeats = true;
                            break;
                        }
                    }
                    if (hasAvailableSeats) {
                        // Display available seats to the user
                        System.out.println("Available Seats:");
                        for (String seatStatus : seatStatuses) {
                            if (seatStatus.contains("Available")) {
                                System.out.println(seatStatus); // Show only available seats
                            }
                        }
                        // Ask the customer to select a seat number
                        System.out.print("Enter the seat number you wish to book: ");
                        int seatNumber = sc.nextInt();
                        // Update seat status for the selected seat
                        StringBuilder updatedSeats = new StringBuilder();
                        for (String seatStatus : seatStatuses) {
                            if (seatStatus.contains("Seat " + seatNumber + ": Available")) {
                                // Change the selected seat status to "Booked by <NIC>"
                                updatedSeats.append("Seat ").append(seatNumber).append(": Booked by ").append(NIC);
                                seatBooked = true;
                            } else {
                                updatedSeats.append(seatStatus); // Keep other seats as they are
                            }
                            updatedSeats.append(", "); // Add comma between seat statuses
                        }
                        // Remove last comma and write the updated seat line
                        writer.write("Seat Status: " + updatedSeats.toString().replaceAll(", $", "") + "\n");
                        scheduleFound = false; // Exit the schedule block after booking
                    } else {
                        // No available seats, add customer to waiting list
                        Add_To_Waiting_List(NIC, Selected_Schedule);
                        System.out.println("No seats available for this schedule. You have been added to the waiting list.");
                        tempFile.delete();
                        return;
                    }
                } else {
                    // Write lines as-is if not in the selected schedule or seat status
                    writer.write(line + "\n");
                }

                // Reset schedule block at the end of each schedule section
                if (scheduleFound && line.trim().isEmpty()) {
                    scheduleFound = false;
                }
            }
            if (seatBooked) {
                System.out.println("Seat booked successfully!");
            } else if (!hasAvailableSeats) {
                System.out.println("All seats were already booked, and you've been added to the waiting list.");
            }
        } catch (IOException e) {
            System.out.println("Error processing booking: " + e.getMessage());
        }
        // Replace original file with the updated file
        if (scheduleFile.delete()) {
            if (tempFile.renameTo(scheduleFile)) {
                System.out.println("Schedule updated successfully.");
            } else {
                System.out.println("Error: Could not rename temp file to schedule file.");
            }
        } else {
            System.out.println("Error: Could not delete original schedule file.");
        }
    }

// Method to handle the booking process
    public void Customer_Book_Seat() {
        System.out.print("Enter Starting Point: ");
        String Starting_Point = sc.next();

        System.out.print("Enter Destination: ");
        String Destination = sc.next();

        System.out.print("Enter Date (DD/MM/YYYY): ");
        String Date = sc.next();

        Get_Available_Schedules(Starting_Point, Destination, Date);

        System.out.print("Enter preferred schedule time(as shown): ");
        String Time = sc.next();

        System.out.print("Enter preferred schedule bus number(as shown): ");
        String Bus_Number = sc.next();

        Preferred_Schedules(Starting_Point, Destination, Date, Time, Bus_Number);

        System.out.print("Enter NIC: ");
        String NIC = sc.next();

        String Selected_Schedule= Find_Schedule(Starting_Point, Destination, Date, Time);
        // Inside Customer_Book_Seat
        if (Selected_Schedule == null || Selected_Schedule.isEmpty()) {
            System.out.println("Error: Selected schedule is not valid.");
            Customer_Menu();
            return;
        }

        if (!Is_Customer_Registered(NIC)) {
            System.out.println("Customer not registered. Please register first.");
            Customer_Menu();
            return;
        }

        if (Has_Existing_Booking(NIC, Selected_Schedule)) {
            System.out.println("You already have a booking for this schedule. Try the Additional seat request via option 6.");
            Customer_Menu();
            return;
        }

        Book_Seat( Selected_Schedule, NIC);

        Customer_Menu();
    }

// Method to request additional seat.
    public void Additional_Seat_Request() {
        System.out.print("Enter Starting Point: ");
        String Starting_Point = sc.next();

        System.out.print("Enter Destination: ");
        String Destination = sc.next();

        System.out.print("Enter Date (DD/MM/YYYY): ");
        String Date = sc.next();
        Validate_Date(Date);

        System.out.print("Enter Time (HH:MM): ");
        String Time = sc.next();
        Validate_Time(Time);

        // Find the schedule based on input criteria
        String Selected_Schedule = Find_Schedule(Starting_Point, Destination, Date, Time);
        if (Selected_Schedule == null) {
            System.out.println("Schedule not found.");
            Customer_Menu();
            return;
        }

        System.out.print("Enter NIC: ");
        String NIC = sc.next();

        if (!Is_Customer_Registered(NIC)) {
            System.out.println("Customer not registered. Please register first.");
            Customer_Menu();
            return;
        }

        if (Has_Existing_Booking(NIC, Selected_Schedule)) {
            // Customer has a booking, so add to waiting list for an additional seat
            Add_To_Waiting_List(NIC, Selected_Schedule);
            System.out.println("Successfully requested an additional seat.");
        } else {
            System.out.println("You do not have an existing booking for this schedule.");
            System.out.println("Please proceed to the booking section via option 5 to book a seat.");
        }
        Customer_Menu();
    }

// ******************Customer Booking cancel section*****************************************************************//
// Method to cancel bookings
    public void Cancel_Booking() {
        System.out.print("Enter your NIC: ");
        String NIC = sc.next();

        // Retrieve all schedules with the customer's bookings
        List<String> Schedules_With_Booking = find_Schedules_With_Booking(NIC);

        // If no bookings exist for this NIC, notify the customer and return
        if (Schedules_With_Booking.isEmpty()) {
            System.out.println("No bookings found for the provided NIC.");
            return;
        }

        // Display schedules with bookings
        System.out.println("You have bookings in the following schedules:");
        for (int i = 0; i < Schedules_With_Booking.size(); i++) {
            System.out.println((i + 1) + ". " + Schedules_With_Booking.get(i));
        }

        // Ask the customer to select a schedule
        System.out.print("Enter the number of the schedule to cancel a booking from: ");
        int scheduleNumber = sc.nextInt();
        
        // Validate the selection
        if (scheduleNumber < 1 || scheduleNumber > Schedules_With_Booking.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // Get the selected schedule details
        String Selected_Schedule = Schedules_With_Booking.get(scheduleNumber - 1);

        View_Seats_Booked(NIC, Selected_Schedule);

        // Ask for the seat number to cancel
        System.out.print("Enter the seat number to cancel: ");
        int seatNumber = sc.nextInt();

        // Perform the cancellation in the schedule
        if (Cancel_Seat_Booking(NIC, Selected_Schedule, seatNumber)) {
            System.out.println("Seat " + seatNumber + " has been successfully canceled in the selected schedule.");
        } else {
            System.out.println("Failed to cancel the seat. Please check the seat number and try again.");
        }

        Inform_neighbour_Seat_Customer(Selected_Schedule, seatNumber);

        Book_Cancelled_Seat_To_Waiting_Customer(Selected_Schedule, seatNumber);

        Customer_Menu();
    }

// Method to view booked seats to cancel.
    public void View_Seats_Booked(String NIC, String Selected_Schedule) {
        File inputFile = new File("Bus_Schedules.txt");
        boolean foundBooking = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String currentSchedule = null;
            boolean isSelectedSchedule = false;

            while ((line = reader.readLine()) != null) {
                // Capture schedule details when a new schedule starts
                if (line.contains("Bus Number:")) {
                    currentSchedule = line;
                    isSelectedSchedule = currentSchedule.contains(Selected_Schedule); // Check if it matches the selected schedule
                }

                // Check for seat status lines within the selected schedule
                if (isSelectedSchedule && line.startsWith("Seat Status:")) {
                    String[] seats = line.split(", "); // Split seats by comma for easier processing
                    StringBuilder bookedSeats = new StringBuilder("Booked Seats: ");

                    boolean seatsFound = false;

                    // Check each seat to see if it's booked by the specified NIC
                    for (String seat : seats) {
                        if (seat.contains("Booked by " + NIC)) {
                            bookedSeats.append(seat.trim()).append(" "); // Collect each booked seat
                            foundBooking = true;
                            seatsFound = true;
                        }
                    }

                    // Display schedule details and booked seats if any are found for this NIC
                    if (seatsFound) {
                        System.out.println("Schedule: " + currentSchedule);
                        System.out.println(bookedSeats.toString().trim());
                    }
                }
            }

            if (!foundBooking) {
                System.out.println("No bookings found for the provided NIC in the selected schedule.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while viewing bookings.");
        }
    }

// Method to inform neighbour customers.
    public void Inform_neighbour_Seat_Customer(String Selected_Schedule, int seatNumber) {
        File inputFile = new File("Bus_Schedules.txt");
        Map<String, String> customerDetails = loadCustomerDetails(); // Load NIC-Name mappings
        boolean isSelectedSchedule = false;
        // file handling.
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String currentSchedule = null;

            while ((line = reader.readLine()) != null) {
                // Detect the beginning of a schedule
                if (line.contains("Bus Number:")) {
                    currentSchedule = line;
                    isSelectedSchedule = currentSchedule.contains(Selected_Schedule); // Check if it matches the selected schedule
                }

                // Process seats only if we're in the correct schedule
                if (isSelectedSchedule && line.startsWith("Seat Status:")) {
                    String[] seats = line.split(", "); // Split seats by comma for easier processing

                    // Retrieve NICs of neighboring seats
                    String leftNeighborNIC = getSeatNIC(seats, seatNumber - 1);
                    String rightNeighborNIC = getSeatNIC(seats, seatNumber + 1);

                    // Notify left neighbor if found
                    if (leftNeighborNIC != null) {
                        String leftNeighborName = customerDetails.getOrDefault(leftNeighborNIC, "Customer");
                        System.out.println("Hello " + leftNeighborName + ", your seat partner has canceled their booking.");
                    }

                    // Notify right neighbor if found
                    if (rightNeighborNIC != null) {
                        String rightNeighborName = customerDetails.getOrDefault(rightNeighborNIC, "Customer");
                        System.out.println("Hello " + rightNeighborName + ", your seat partner has canceled their booking.");
                    }
                    break; // Exit after processing the selected schedule
                }
            }
            if (!isSelectedSchedule) {
                System.out.println("Schedule not found.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while notifying neighbor seats.");
        }
    }

// Helper method to retrieve the NIC from a seat string
    private String getSeatNIC(String[] seats, int seatNumber) {
        for (String seat : seats) {
            if (seat.contains("Seat " + seatNumber) && seat.contains("Booked by")) {
                return seat.split("Booked by ")[1].trim(); // Extract and return the NIC
            }
        }
        return null; // Return null if no NIC is found for the seat
    }

// Method loads NIC and Name mappings from a file.
    private Map<String, String> loadCustomerDetails() {
        Map<String, String> customerDetails = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Customer_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                try {
                    if (parts.length >= 3) {  // Ensure the line has at least 3 parts
                        String name = parts[0].split(": ")[1].trim(); // Extract Name
                        String nic = parts[2].split(": ")[1].trim();  // Extract NIC
                        customerDetails.put(nic, name); // NIC as key, Name as value
                    } else {
                        System.out.println("Skipping malformed line: " + line);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error parsing line due to unexpected format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading customer details.");
        }
        return customerDetails;
    }

// Method to find schedules with the customer's bookings
    private List<String> find_Schedules_With_Booking(String NIC) {
        List<String> schedules = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            String currentSchedule = null;
            
            while ((line = reader.readLine()) != null) {
                // Detect the start of a new schedule
                if (line.startsWith("Bus Number:")) {
                    currentSchedule = line;  // Save the schedule details
                }
                // If a seat is booked by the given NIC, add the schedule to the list
                if (line.contains("Booked by " + NIC) && currentSchedule != null) {
                    schedules.add(currentSchedule);
                    currentSchedule = null;  // Move to the next schedule once added
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
        return schedules;
    }

// Method to cancel a specific seat booking in a schedule
    private boolean Cancel_Seat_Booking(String NIC, String scheduleDetails, int seatNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"));
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean isBookingCancelled = false;

            while ((line = reader.readLine()) != null) {
                // Identify the schedule to update based on the details
                if (line.equals(scheduleDetails)) {
                    fileContent.append(line).append("\n");  // Add schedule header
                    while ((line = reader.readLine()) != null && !line.startsWith("Bus Number:")) {
                        // Find the specific seat booked by the NIC
                        if (line.contains("Seat " + seatNumber + ": Booked by " + NIC)) {
                            // Update seat status to available
                            line = line.replace("Seat " + seatNumber + ": Booked by " + NIC, "Seat " + seatNumber + ": Available");
                            isBookingCancelled = true;
                        }
                        fileContent.append(line).append("\n");
                    }
                }
                // Check if the line is not null before appending
                if (line != null) {
                    fileContent.append(line).append("\n");  // Continue with other schedules
                }
            }
            reader.close();
            // If booking was canceled, write the updated content back to the file
            if (isBookingCancelled) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Bus_Schedules.txt"));
                writer.write(fileContent.toString());
                writer.close();
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error updating schedule file: " + e.getMessage());
        }
        return false;
    }

// method to book seat for waiting queue customer
    private void Book_Cancelled_Seat_To_Waiting_Customer(String scheduleDetails, int seatNumber) {
        try {
            // Step 1: Read waiting queue file to get NIC of the first waiting customer
            BufferedReader queueReader = new BufferedReader(new FileReader("Waiting_Queues.txt"));
            StringBuilder updatedQueueContent = new StringBuilder();
            String waitingLine;
            String customer_NIC = null;
            boolean customerFound = false;

            while ((waitingLine = queueReader.readLine()) != null) {
                if (waitingLine.contains("Schedule: " + scheduleDetails) && !customerFound) {
                    customer_NIC = waitingLine.split(", Schedule:")[0].split("NIC: ")[1].trim();
                    customerFound = true;
                } else {
                    updatedQueueContent.append(waitingLine).append("\n");
                }
            }
            queueReader.close();

            if (customerFound && customer_NIC != null) {
                // Step 2: Update the Bus_Schedules.txt file
                File scheduleFile = new File("Bus_Schedules.txt");
                File tempFile = new File("Temp_Bus_Schedules.txt");

                try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;
                    boolean scheduleFound = false;
                    boolean seatBooked = false;

                    while ((line = reader.readLine()) != null) {
                        // Identify the start of the selected schedule section
                        if (line.contains(scheduleDetails)) {
                            scheduleFound = true;
                        }

                        if (scheduleFound && line.startsWith("Seat Status:")) {
                            // Update seat status for the selected seat
                            String[] seatStatuses = line.split(", ");
                            StringBuilder updatedSeats = new StringBuilder();

                            for (String seatStatus : seatStatuses) {
                                if (seatStatus.contains("Seat " + seatNumber + ": Available")) {
                                    updatedSeats.append("Seat ").append(seatNumber).append(": Booked by ").append(customer_NIC);
                                    seatBooked = true;
                                } else {
                                    updatedSeats.append(seatStatus); // Keep other seats as they are
                                }
                                updatedSeats.append(", ");
                            }

                            writer.write("Seat Status: " + updatedSeats.toString().replaceAll(", $", "") + "\n");
                            scheduleFound = false; // Exit the schedule block after booking
                        } else {
                            writer.write(line + "\n");
                        }
                    }

                    if (seatBooked) {
                        System.out.println("Canceled Seat " + seatNumber + " booked successfully for " + customer_NIC);
                    } else {
                        System.out.println("No available seats found for booking.");
                    }
                }

                // Replace original file with the updated file
                if (scheduleFile.delete() && tempFile.renameTo(scheduleFile)) {
                    System.out.println("Schedule updated successfully.");
                } else {
                    System.out.println("Error: Could not update schedule file.");
                }

                // Step 3: Update waiting queue file to remove the served customer
                BufferedWriter queueWriter = new BufferedWriter(new FileWriter("Waiting_Queues.txt"));
                queueWriter.write(updatedQueueContent.toString());
                queueWriter.close();
            } else {
                System.out.println("No waiting customer found for this schedule.");
            }
        } catch (IOException e) {
            System.out.println("Error processing booking for waiting customer: " + e.getMessage());
        }
    }

// Method to search schedule by bus number.
    public void Search_Schedule_Bus() {
        // Getting bus number.
        System.err.print("Enter your bus number: ");
        String Bus_Number = sc.next();

        boolean found = false; // Flag to check if any match is found

        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            boolean isScheduleLine = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(Bus_Number)) {
                    System.out.println(line);
                    found = true; // Match found
                    isScheduleLine = true;
                } else if (isScheduleLine && line.startsWith("Seat Status:")) {
                    System.out.println(line);
                    isScheduleLine = false;
                }
            }
            if (!found) {
                System.out.println("No schedule found for the specified bus number.");
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
    }

// Method to search specific schedule.
    public void Search_Schedule() {
        System.err.print("Enter your bus number: ");
        String Bus_Number = sc.next();

        System.err.print("Enter Date: ");
        String Date = sc.next();
        Validate_Date(Date);

        System.err.print("Enter time: ");
        String Time = sc.next();
        Validate_Time(Time);

        boolean found = false; // Flag to check if any match is found

        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            boolean isScheduleLine = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(Date) && line.contains(Time) && line.contains(Bus_Number)) {
                    System.out.println(line);
                    found = true; // Match found
                    isScheduleLine = true;
                } else if (isScheduleLine && line.startsWith("Seat Status:")) {
                    System.out.println(line);
                    isScheduleLine = false;
                }
            }
            if (!found) {
                System.out.println("No schedule found for the specified bus number, date, and time.");
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
        Bus_Menu();
    }

// Method to search schedule by date.
    public void Search_Schedule_Date() {
        // Getting the date to show schedule.
        System.err.print("Enter Date: ");
        String Date = sc.next();
        Validate_Date(Date);

        boolean found = false; // Flag to check if any match is found
        // File handling method to retrieve schedule.
        try (BufferedReader reader = new BufferedReader(new FileReader("Bus_Schedules.txt"))) {
            String line;
            boolean isScheduleLine = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(Date)) {
                    System.out.println(line);
                    found = true; // Match found
                    isScheduleLine = true;
                } else if (isScheduleLine && line.startsWith("Seat Status:")) {
                    System.out.println(line);
                    isScheduleLine = false;
                }
            }
            if (!found) {
                System.out.println("No schedule found for the specified date.");
            }
        } catch (IOException e) {
            System.out.println("Error reading schedule file: " + e.getMessage());
        }
        Schedule_Menu();
    }

// ******************Menu section*****************************************************************//
// Main Menu 
    public void Main_Menu(){
        // Displaying the main menu options.
        System.out.println("Menu (Enter the respective number as it is infront of the option you have to visit)");
        System.out.println("1: Customer Section");
        System.out.println("2: Bus Section");
        System.out.println("3: Admin Section");
        System.out.println("4: Exit");
        // Asking for the selection from user.
        System.out.print("Enter your selection: ");
        int Choice=sc.nextInt(); //Gathering the customer input.
        switch(Choice){  //Checking the user input for the next function.
            case 1:
                Customer_Menu();
                break;
            case 2:
                Bus_Menu();
                break;
            case 3:
                System.out.println("!!!!!Login to enter Admin section!!!!!");
                // gather user inputs for Admin login.
                System.out.print("User Name: ");
                String User_Name = sc.next();
                System.out.print("Password: ");
                String Password = sc.next();
                // Checking the login details.
                if (!User_Name.equals("User") || !Password.equals("user123")) {
                    System.out.println("Invalid user name or password.");
                    Main_Menu();
                } else {
                    Admin_Menu();
                }
                break;
            case 4:
                System.out.println("Exiting system.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                break;
        }
    }

// Customer Menu
    public void Customer_Menu(){
        // Displaying the customer menu options.
        System.out.println("1: Customer registration");
        System.out.println("2: View Customer profile");
        System.out.println("3: Customer Update");
        System.out.println("4: Customer Delete");
        System.out.println("5: Book seats");
        System.out.println("6: Additional seat request");
        System.out.println("7: Cancel bookings");
        System.out.println("8: View customer bookings");
        System.out.println("9: Back to Main Menu");
        System.out.println("10: Exit");
        // Asking for the selection from user.
        System.out.print("Enter your selection: ");
        int Choice=sc.nextInt(); 
        switch(Choice){ //Checking the user input for the next function.
            case 1:
                Customer_Register();
                break;
            case 2:
                System.out.print("Eneter your NIC number: ");
                String Customer_NIC=sc.next();
                Customer_Profile_View(Customer_NIC);
                break;
            case 3:
                System.out.print("Eneter your NIC number: ");
                Customer_NIC=sc.next();
                Customer_Update(Customer_NIC);
                break;
            case 4:
                System.out.print("Eneter your NIC number: ");
                Customer_NIC=sc.next();
                Customer_Delete(Customer_NIC);
                break;
            case 5:
                Customer_Book_Seat();
                break;
            case 6:
                Additional_Seat_Request();
            case 7:
                Cancel_Booking();
                break;
            case 8:
                Customer_View_Booking();
                Customer_Menu();
                break;
            case 9:
                Main_Menu();
                break;
            case 10:
                System.out.println("Exiting system.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                break;
        }
    }

// Bus Menu
    public void Bus_Menu(){
        // Displaying the customer menu options.
        System.out.println("1: Bus registration");
        System.out.println("2: Bus Update");
        System.out.println("3: Delete Bus");
        System.out.println("4: View Schedule");
        System.out.println("5: View bookings");
        System.out.println("6: Back to Main Menu");
        System.out.println("7: Exit");
        // Asking for the selection from user.
        System.out.print("Enter your selection: ");
        int Choice=sc.nextInt();
        switch(Choice){ //Checking the user input for the next function.
            case 1:
                Bus_Register();
                break;
            case 2:
                System.out.print("Eneter bus number: ");
                String Bus_Number=sc.next();
                Bus_Update(Bus_Number);
                break;
            case 3:
                System.out.print("Eneter bus number: ");
                String Bus_number=sc.next();
                Bus_Delete(Bus_number);
                break;
            case 4:
                Search_Schedule_Bus();
                Bus_Menu();
                break;
            case 5:
                Search_Schedule();
                break;
            case 6:
                Main_Menu();
                break;
            case 7:
                System.out.println("Exiting system.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                break;
        }
    }

// Schedule Menu
    public void Schedule_Menu(){
        // Displaying the customer menu options.
        System.out.println("1: Enter a Schedule");
        System.out.println("2: Change/update schedule");
        System.out.println("3: Delete Schedule");
        System.out.println("4: View schedules of specific bus");
        System.out.println("5: View Schedules in a specific date");
        System.out.println("6: Back to Admin menu");
        System.out.println("7: Exit");
        // Asking for the selection from user.
        System.out.print("Enter your selection: "); 
        int Choice=sc.nextInt();
        switch(Choice){ //Checking the user input for the next function.
            case 1:
                Enter_Schedule();
                break;
            case 2:
                Update_Schedule();
                break;
            case 3:
                Delete_Schedule();
                break;
            case 4:
                Search_Schedule_Bus();
                Schedule_Menu();
                break;
            case 5:
                Search_Schedule_Date();
                break;
            case 6:
                Admin_Menu();
                break;
            case 7:
                System.out.println("Exiting system.");
                System.exit(0); 
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                break;
            }
    }

// Admin Menu
    public void Admin_Menu(){
        System.out.println("1: View Customers & Manage Customers");
        System.out.println("2: View Buses");
        System.out.println("3: Delete Bus");
        System.out.println("4: Schedule Management section.");
        System.out.println("5: Back to Main Menu");
        System.out.println("6: Exit");
        System.out.print("Enter your selection: ");
        int Choice=sc.nextInt();

        switch(Choice){
            case 1:
                View_All_Customers();
                break;
            case 2:
                View_All_Buses();
                break;
            case 3:
                System.out.println("Enter the number of bus to delete: ");
                String Bus_Number=sc.next();
                Bus_Delete(Bus_Number);
                break;  
            case 4:
                Schedule_Menu();
                break;
            case 5:
                Main_Menu();
                break;
            case 6:
                System.out.println("Exiting system.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                break;
        }
    }

// method for admin customer menu
    public void Admin_Customer_Menu(){
        // displaying options
        System.out.println("1: Customer registration");
        System.out.println("2: Customer Update");
        System.out.println("3: Customer Delete");
        System.out.println("4: Cancel bookings");
        System.out.println("5: View customer bookings");
        System.out.println("6: Back to Admin Menu");
        System.out.println("7 Exit");
        // asking for customer selection
        System.out.print("Enter your selection: ");
        int Choice=sc.nextInt();

        switch(Choice){ //  checking the selection
            case 1:
                Customer_Register();
                break;
            case 2:
                System.out.print("Eneter your NIC number: ");
                String Customer_NIC=sc.next();
                Customer_Update(Customer_NIC);
                break;
            case 3:
                System.out.print("Eneter your NIC number: ");
                Customer_NIC=sc.next();
                Customer_Delete(Customer_NIC);
                break;
            case 4:
                Cancel_Booking();
                break;
            case 5:
                Customer_View_Booking();
                break;
            case 6:
                Admin_Menu();
                break;
            case 7:
                System.out.println("Exiting system.");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                break;
        }
    }

// Main method coding start.
    public static void main(String[] args) {
        Main M1 = new Main();
        M1.Main_Menu();
    }
}