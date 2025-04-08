BUS RESERVATION - CLI Application 
The Bus Reservation System is a feature-rich application developed using Java, leveraging custom data 
structures and comprehensive file handling to ensure efficient bus reservations and schedule management. The 
system provides a seamless and user-friendly platform for customers, bus owners, and administrators, focusing 
on dynamic seat allocation, customer notifications, and real-time data accuracy. By automating seat 
management and integrating waiting lists, the system minimizes manual errors, enhances user satisfaction, and 
streamlines the booking process. It also incorporates flexible user roles to deliver tailored experiences, ensuring 
smooth operations and proper access control throughout the platform. 
Key Features Implemented: 
• Customer Registration: Designed a customer registration system that validates data formats (email, 
age) and securely stores customer information for future use. 
• Bus Registration: Enabled bus owners to register buses with complete details (bus number, total seats, 
route, timing, and fare). Seat statuses were tracked with seat numbers and availability. 
• Search & Book Seats: Implemented search functionality for customers to filter buses based on start 
point, destination, date, and time. Reservations update seat availability in real-time and associate the 
customer NIC with booked seats. 
• Reservation Management: Created functionalities to cancel reservations with automated notifications 
to customers and reallocate seats from the waiting queue. 
• Additional Seat Requests: Provided the ability for customers to request extra seats. Integrated a 
waiting list system to queue customers for fully booked buses. 
• Schedule Management: Allowed admins to add and update bus schedules. Customers could search for 
schedules using date, time, and destination before booking. 
• User Roles & Access Control: Developed distinct sections for customers, bus owners, and admins 
with unique access rights to manage registrations, bookings, schedules, and cancellations. 
Technical Implementation: 
• Custom Linked Lists: Built all data handling features without relying on Java’s built-in data 
structures, ensuring hands-on understanding of linked lists and queues. 
• File Handling: Used text files to store customer details, bus schedules, seat statuses, and waiting lists, 
ensuring persistence across sessions. 
User Roles & Functional Overview: 
• Customer: Register, view/update personal details, book/cancel seats, request additional seats, and view 
reservations. 
• Bus Owner: Register/update/delete buses, view schedules, and manage bookings. 
• Admin: View/manage all customer accounts, register/update/delete schedules, manage buses, and 
oversee bookings for the entire system. 
Login credentials: 
• Username: User 
• Password: user123
