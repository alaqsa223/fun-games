
/****************************************
* Student Name:Al Aqsa
* Date Due: 05/03/2023
* Date Submitted: 05/01/2023
* Program Name: RentalHouse
* Program Description:This programming project is to develop a simple house rental system such as Airbnb that rents 
houses to its guests. The house owners are able to register their houses for rental and guests may 
rent houses registered. The system needs to keep track of house and guest information, and 
computes the total rental costs.
****************************************/

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.*;
import java.text.*;

public class HouseRental {
    public List<House> houses;
    public List<Guest> guests;

    public HouseRental() {
        this.houses = new ArrayList<>();
        this.guests = new ArrayList<>();
    }

    // method for registering house
    public void registerHouse(String registrationID, String type, double rentalCost, String ownerName,
            LocalDate houseAvailableStart, LocalDate houseAvailableEnd,
            LocalDate houseUnavailableStart, LocalDate houseUnavailableEnd) {
        House house = new House(registrationID, type, rentalCost, ownerName, houseAvailableStart, houseAvailableEnd,
                houseUnavailableStart, houseUnavailableEnd);
        houses.add(house);
        System.out.println("House registered successfully!");
    }

    /// method for registering guest
    public void registerGuest(String guestID, String guestName, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        Guest guest = new Guest(guestID, guestName, rentalStartDate, rentalEndDate);
        guests.add(guest);
        System.out.println("Guest registered successfully!");
    }

    // method for unregistering house
    public void unregisterHouse(String registrationID) {
        boolean houseRemoved = false;
        for (House house : houses) {
            if (house.getRegistrationID().equals(registrationID)) {
                houses.remove(house);
                houseRemoved = true;
                break;
            }
        }
        if (houseRemoved) {
            System.out.println("House unregistered successfully!");
        } else {
            System.out.println("House not found.");
        }
    }

    // method for unregistering guest
    public void unregisterGuest(String guestID) {
        boolean guestRemoved = false;
        for (Guest guest : guests) {
            if (guest.getGuestID().equals(guestID)) {
                guests.remove(guest);
                guestRemoved = true;
                break;
            }
        }
        if (guestRemoved) {
            System.out.println("Guest unregistered successfully!");
        } else {
            System.out.println("Guest not found.");
        }
    }

    // method for showing house list
    public void showHouseList() {
        if (houses.isEmpty()) {
            System.out.println("No houses registered yet.");
        } else {
            System.out.println("List of registered houses:");
            for (House house : houses) {
                System.out.println("Registration ID: " + house.getRegistrationID());
                System.out.println("Type: " + house.getType());
                System.out.println("Rental cost per night: " + house.getRentalCost());
                System.out.println("Owner's name: " + house.getOwnerName());
                System.out.println("Start of available dates: " + house.getHouseAvailableStart());
                System.out.println("End of available dates: " + house.getHouseAvailableEnd());
                System.out.println("Start of unavailable dates: " + house.getHouseUnAvailableStart());
                System.out.println("End of unavailable dates: " + house.getHouseUnAvailableEnd());
                System.out.println();
            }
            System.out.println("---------------------------------------");
        }
    }

    // method for showing guest list
    public void showGuestList() {
        if (guests.isEmpty()) {
            System.out.println("No guests registered yet.");
        } else {
            System.out.println("List of registered guests:");
            for (Guest guest : guests) {
                System.out.println("Guest ID: " + guest.getGuestID());
                System.out.println("Name: " + guest.getGuestName());
                System.out.println("Rental Starting Date: " + guest.getRentalStartDate());
                System.out.println("Rental Ending Date: " + guest.getRentalEndDate());
                System.out.println("---------------------------------------");
            }
        }
    }

    // method for renting a house
    public void houseRent(House house, Guest guest) {
        LocalDate availableStartDate = house.getHouseAvailableStart();
        LocalDate availableEndDate = house.getHouseAvailableEnd();
        LocalDate unavailableStartDate = house.getHouseUnAvailableStart();
        LocalDate unavailableEndDate = house.getHouseUnAvailableEnd();

        LocalDate rentalStartDate = guest.getRentalStartDate();
        LocalDate rentalEndDate = guest.getRentalEndDate();

        if (rentalStartDate.compareTo(availableStartDate) >= 0 && availableEndDate.compareTo(rentalEndDate) >= 0) {
            house.setHouseUnAvailableStart(rentalStartDate);
            house.setHouseUnAvailableEnd(rentalEndDate);
            System.out.println("House is available for the selected rental period");
        } else if (rentalStartDate.compareTo(unavailableEndDate) <= 0
                && unavailableStartDate.compareTo(rentalEndDate) <= 0) {
            System.out.println("House is not available for the selected rental period");
        } else {
            System.out.println("House is not available for the selected rental period");
        }
    }

    // method for returning a house
    public void returnHouse(House house, Guest guest) {
        LocalDate rentalStartDate = guest.getRentalStartDate();
        LocalDate rentalEndDate = guest.getRentalEndDate();
        double rentalCostPerNight = house.getRentalCost();

        long totalRentalNights = ChronoUnit.DAYS.between(rentalStartDate, rentalEndDate);
        // calculate cost
        double totalRentalCost = rentalCostPerNight * (int) totalRentalNights;
        // update house availability
        house.setHouseAvailableStart(rentalStartDate);
        house.setHouseAvailableEnd(rentalEndDate);

        System.out.println("Total rental cost: " + totalRentalCost);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HouseRental rentalSystem = new HouseRental();
        // menu option for choices
        int user_choice = -1;
        while (user_choice != 0) {
            System.out.println("1: Register new house\n" +
                    "2: Unregister existing house\n" +
                    "3: Print all existing house information\n" +
                    "4: Register new guest\n" +
                    "5: Unregister guest\n" +
                    "6: Print all existing guest information\n" +
                    "7: Rent house\n" +
                    "8: Return house\n" +
                    "0: exit\n");
            System.out.println("Enter choice: ");
            try {
                user_choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 8.");
                continue;
            }
            if (user_choice == 1) {
                // register a new house
                System.out.println("Enter House Registration ID:");
                String registrationID = scanner.nextLine();
                System.out.println("Enter House Type (Single House, Apartment, Condo):");
                String type = scanner.nextLine();
                System.out.println("Enter Rental Cost per Night:");
                double rentalCost = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter Owner's Name:");
                String ownerName = scanner.nextLine();
                System.out.println("Enter House available Starting Date (YYYY-MM-DD):");
                LocalDate houseAvailablestart = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter House Available Ending Date (YYYY-MM-DD):");
                LocalDate houseAvailableEnd = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter House unavailable Starting Date (YYYY-MM-DD):");
                LocalDate houseunAvailableStart = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter House unavailable Ending Date (YYYY-MM-DD):");
                LocalDate houseunAvailableEnd = LocalDate.parse(scanner.nextLine());

                rentalSystem.registerHouse(registrationID, type, rentalCost, ownerName, houseAvailablestart,
                        houseAvailableEnd,
                        houseunAvailableStart, houseunAvailableEnd);

            }

            else if (user_choice == 2) {
                // unregister an existing house
                System.out.println("Enter House Registration ID to unregister house");
                rentalSystem.unregisterHouse(scanner.nextLine());
            }

            else if (user_choice == 3) {
                // show house list

                rentalSystem.showHouseList();
            }

            else if (user_choice == 4) {
                // register a new guest
                System.out.println("Enter Guest ID:");
                String guestID = scanner.nextLine();
                System.out.println("Enter Guest Name:");
                String guestName = scanner.nextLine();
                System.out.println("Enter Rental Starting Date (YYYY-MM-DD):");
                LocalDate rentalStartDate = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter Rental Ending Date (YYYY-MM-DD):");
                LocalDate rentalEndDate = LocalDate.parse(scanner.nextLine());

                rentalSystem.registerGuest(guestID, guestName, rentalStartDate, rentalEndDate);

            }

            else if (user_choice == 5) {
                // unregister an existing guest
                System.out.println("Enter guest Registration ID to unregister guest");
                rentalSystem.unregisterGuest(scanner.nextLine());
            }

            else if (user_choice == 6) {
                // show the guest list
                rentalSystem.showGuestList();
            }

            else if (user_choice == 7) {
                // rent a house
                System.out.println("Enter House Registration ID:");
                String houseID = scanner.nextLine();
                House selectedHouse = null;
                for (House house : rentalSystem.houses) {
                    if (house.getRegistrationID().equals(houseID)) {
                        selectedHouse = house;
                        break;
                    }
                }
                if (selectedHouse == null) {
                    System.out.println("House not found.");
                } else {
                    System.out.println("Enter Guest ID:");
                    String guestID = scanner.nextLine();
                    Guest selectedGuest = null;
                    for (Guest guest : rentalSystem.guests) {
                        if (guest.getGuestID().equals(guestID)) {
                            selectedGuest = guest;
                            break;
                        }
                    }
                    if (selectedGuest == null) {
                        System.out.println("Guest not found.");
                    } else {
                        rentalSystem.houseRent(selectedHouse, selectedGuest);
                    }
                }
            } else if (user_choice == 8) {
                // return a house
                System.out.println("Enter House Registration ID:");
                String houseID = scanner.nextLine();
                House selectedHouse = null;
                for (House house : rentalSystem.houses) {
                    if (house.getRegistrationID().equals(houseID)) {
                        selectedHouse = house;
                        break;
                    }
                }
                if (selectedHouse == null) {
                    System.out.println("House not found.");
                } else {
                    System.out.println("Enter Guest ID:");
                    String guestID = scanner.nextLine();
                    Guest selectedGuest = null;
                    for (Guest guest : rentalSystem.guests) {
                        if (guest.getGuestID().equals(guestID)) {
                            selectedGuest = guest;
                            break;
                        }
                    }
                    if (selectedGuest == null) {
                        System.out.println("Guest not found.");
                    } else {
                        rentalSystem.returnHouse(selectedHouse, selectedGuest);
                    }
                }
            }

            else if (user_choice == 0) {
                // exit the program
                System.out.println("Exiting program...");
                break;
            }

            else {
                // invalid input
                System.out.println("Invalid input. Please enter a number between 0 and 8.");
            }

            System.out.println();

        }

    }

    // house class for managing houses
    class House {
        private String registrationID;
        private String type;
        private double rentalCost;
        private String ownerName;
        private LocalDate houseAvailablestart;
        private LocalDate houseAvailableEnd;
        private LocalDate houseunAvailableStart;
        private LocalDate houseunAvailableEnd;

        public House(String registrationID, String type, double rentalCost, String ownerName,
                LocalDate houseAvailablestart,
                LocalDate houseAvailableEnd, LocalDate houseunAvailableStart,
                LocalDate houseunAvailableEnd) {
            this.registrationID = registrationID;
            this.type = type;
            this.rentalCost = rentalCost;
            this.ownerName = ownerName;
            this.houseAvailablestart = houseAvailablestart;
            this.houseAvailableEnd = houseAvailableEnd;
            this.houseunAvailableStart = houseunAvailableStart;
            this.houseunAvailableEnd = houseunAvailableEnd;
        }

        public String getRegistrationID() {
            return registrationID;
        }

        public String getType() {
            return type;
        }

        public double getRentalCost() {
            return rentalCost;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public LocalDate getHouseAvailableStart() {
            return houseAvailablestart;
        }

        public void setHouseAvailableStart(LocalDate houseAvailablestart) {
            this.houseAvailablestart = houseAvailablestart;
        }

        public LocalDate getHouseAvailableEnd() {
            return houseAvailableEnd;

        }

        public void setHouseAvailableEnd(LocalDate houseAvailableEnd) {
            this.houseAvailableEnd = houseAvailableEnd;
        }

        public LocalDate getHouseUnAvailableStart() {
            return houseunAvailableStart;
        }

        public void setHouseUnAvailableStart(LocalDate houseunAvailableStart) {
            this.houseunAvailableStart = houseunAvailableStart;
        }

        public LocalDate getHouseUnAvailableEnd() {
            return houseunAvailableEnd;
        }

        public void setHouseUnAvailableEnd(LocalDate houseunAvailableEnd) {
            this.houseunAvailableEnd = houseunAvailableEnd;
        }

    }

    // guest class for managing guests
    class Guest {
        private String guestID;
        private String guestName;
        private LocalDate rentalStartDate;
        private LocalDate rentalEndDate;

        public Guest(String guestID, String guestName, LocalDate rentalStartDate, LocalDate rentalEndDate) {
            this.guestID = guestID;
            this.guestName = guestName;
            this.rentalStartDate = rentalStartDate;
            this.rentalEndDate = rentalEndDate;
        }

        public String getGuestID() {
            return guestID;
        }

        public void setGuestID(String guestID) {
            this.guestID = guestID;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public LocalDate getRentalStartDate() {
            return rentalStartDate;
        }

        public void setRentalStartDate(LocalDate rentalStartDate) {
            this.rentalStartDate = rentalStartDate;
        }

        public LocalDate getRentalEndDate() {
            return rentalEndDate;
        }

        public void setRentalEndDate(LocalDate rentalEndDate) {
            this.rentalEndDate = rentalEndDate;
        }

    }
}
