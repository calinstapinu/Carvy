package presentation;

import controller.*;
import model.Car;
import model.Client;
import model.Transaction;
import model.enums.TransactionType;
import service.CarService;
import service.ClientService;

import repository.*;
import service.*;

import repository.parsers.CarParser;
import repository.parsers.ClientParser;
import repository.parsers.EmployeeParser;
import repository.parsers.LeasingParser;
import repository.parsers.TransactionParser;

import java.io.File;
import java.util.List;

/**
 * Entry point for the Carvy dealership application.
 * Provides a console-based menu for managing cars, clients, employees, leasing contracts, and transactions.
 */
public class ConsoleApp {
    public static void main(String[] args) {

        // Initialize files
        File carFile = new File("data/cars.txt");
        File clientFile = new File("data/clients.txt");
        File employeeFile = new File("data/employees.txt");
        File leasingFile = new File("data/leasings.txt");
        File transactionFile = new File("data/transactions.txt");

// Initialize repositories with specific types and parsers
        CarRepository carRepo = new CarRepository(carFile, new CarParser());
        ClientRepository clientRepo = new ClientRepository(clientFile, new ClientParser());
        EmployeeRepository empRepo = new EmployeeRepository(employeeFile, new EmployeeParser());
        LeasingRepository leasingRepo = new LeasingRepository(leasingFile, new LeasingParser(new CarParser(), new ClientParser()));
        TransactionRepository transactionRepo = new TransactionRepository(transactionFile, new TransactionParser(new CarParser(), new ClientParser()));


        // Initialize leasing manager
        LeasingManager leasingManager = new LeasingManagerImpl();
        LeasingManagerImpl leasingManagerImpl = new LeasingManagerImpl();

        // Initialize services
        CarService carService = new CarService(carRepo);
        ClientService clientService = new ClientService(clientRepo);
        EmployeeService employeeService = new EmployeeService(empRepo);
        LeasingService leasingService = new LeasingService(leasingRepo, leasingManager, leasingManagerImpl);
        TransactionService transactionService = new TransactionService(transactionRepo);


        // Initialize controllers
        CarController carController = new CarController(carService);
        ClientController clientController = new ClientController(clientService);
        EmployeeController employeeController = new EmployeeController(employeeService);
        LeasingController leasingController = new LeasingController(leasingService);
        TransactionController transactionController = new TransactionController(transactionService);

        boolean appRunning = true;
        while (appRunning) {
            int userType = MenuHandler.showMenu("Welcome to Carvy - Your Dealership", new String[]{
                    "Administrator",
                    "Client"
            });

            switch (userType) {
                case 1 ->
                        adminMenu(carController, clientController, employeeController, leasingController, transactionController, carService, clientService);
                case 2 -> clientMenu(carController, leasingController, clientService, leasingService);
                case 3 -> {
                    System.out.println("Exiting the application...");
                    appRunning = false; // Ends the application
                }
            }
        }
    }

    private static void adminMenu(
            CarController carController,
            ClientController clientController,
            EmployeeController employeeController,
            LeasingController leasingController,
            TransactionController transactionController,
            CarService carService,
            ClientService clientService
    ) {
        boolean running = true;
        while (running) {
            int choice = MenuHandler.showMenu("Administrator Menu", new String[]{
                    "Manage Cars",
                    "Manage Clients",
                    "Manage Employees",
                    "Manage Leasing Contracts",
                    "Manage Transactions"
            });

            switch (choice) {
                case 1 -> carMenu(carController);
                case 2 -> clientMenu(clientController);
                case 3 -> employeeMenu(employeeController, carService);
                case 4 -> leasingMenu(leasingController, carService, clientService);
                case 5 -> transactionMenu(transactionController, carService, clientService);
                case 0 -> {
                    System.out.println("Exiting...");
                    running = false;

                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void clientMenu(CarController carController, LeasingController leasingController, ClientService clientService, LeasingService leasingService) {
        boolean running = true;
        while (running) {
            int choice = MenuHandler.showMenu("Client Menu", new String[]{
                    "View Available Cars",
                    "Search Car by ID",
                    "Search Car by Name",
                    "Calculate Leasing Estimate"
            });

            switch (choice) {
                case 1 -> carController.listAvailableCars();
                case 2 -> {
                    long carId = MenuHandler.readLong("Enter Car ID: ");
                    try {
                        Car car = carController.findCarById(carId); // Add a `findCarById` method in `CarController` if needed
                        System.out.println(car);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    String carName = MenuHandler.readText("Enter Car Name: ");
                    try {
                        // Assuming a method exists to search cars by name
                        List<Car> cars = carController.findCarsByName(carName);
                        cars.forEach(System.out::println);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 4 -> {
                    long carId = MenuHandler.readLong("Enter Car ID: ");
                    int durationMonths = MenuHandler.readInt("Enter duration in months: ");
                    float interestRate = MenuHandler.readFloat("Enter annual interest rate: ");
                    float downPayment = MenuHandler.readFloat("Enter down payment: ");
                    float adminFee = 200.0f; // Example static admin fee
                    float taxRate = 8.0f; // Example static tax rate

                    try {
                        float monthlyRate = leasingService.calculateMonthlyRate(30000, durationMonths, interestRate, downPayment);
                        float totalAmount = leasingService.calculateTotalAmount(monthlyRate, durationMonths, adminFee, taxRate);

                        System.out.println("Monthly Rate: " + monthlyRate);
                        System.out.println("Total Amount: " + totalAmount);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Returning to the main menu...");
                    running = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }



    private static void carMenu(CarController carController) {
        boolean inCarMenu = true;
        while (inCarMenu) {
            int choice = MenuHandler.showMenu("Manage Cars", new String[]{
                    "Add Car",
                    "List All Cars",
                    "Cars by Sorting",
                    "Cars by Filtering",
                    "Mark Car as SOLD",
                    "Mark Car as LEASED"
            });

            switch (choice) {
                case 1 -> {
                    long carId = MenuHandler.readLong("Car ID: ");
                    String brand = MenuHandler.readText("Brand: ");
                    String model = MenuHandler.readText("Model: ");
                    int year = MenuHandler.readInt("Year of Manufacture: ");
                    float price = MenuHandler.readFloat("Price: ");
                    int mileage = MenuHandler.readInt("Mileage: ");
                    carController.addCar(carId, brand, model, year, price, mileage);
                }
                case 2 -> carController.listAllCars();
                case 3 -> carSortingMenu(carController); // Navigate to sorting submenu
                case 4 -> carFilteringMenu(carController);
                case 5 -> {
                    long carId = MenuHandler.readInt("Car ID: ");
                    carController.markCarAsSold(carId);
                }
                case 6 -> {
                    long carId = MenuHandler.readInt("Car ID: ");
                    carController.markCarAsLeased(carId);
                }
                case 0 -> {
                    System.out.println("Returning to the main menu...");
                    inCarMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void carSortingMenu(CarController carController) {
        boolean inSortingMenu = true;
        while (inSortingMenu) {
            int choice = MenuHandler.showMenu("Cars by Sorting", new String[]{
                    "Sort Cars by Year",
                    "Sort Cars by Price"
            });

            switch (choice) {

                case 1 -> carController.listCarsSortedByYear();
                case 2 -> carController.listCarsSortedByPrice();
                case 0 -> {
                    System.out.println("Returning to the car menu...");
                    inSortingMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void carFilteringMenu(CarController carController) {
        boolean inFilteringMenu = true;
        while (inFilteringMenu) {
            int choice = MenuHandler.showMenu("Cars by Filtering", new String[]{
                    "List Available Cars",
                    "List Sold Cars",
                    "List Leased Cars",
                    "Filter by Year",
                    "Filter by Budget"
            });

            switch (choice) {
                case 1 -> carController.listAvailableCars();
                case 2 -> carController.listSoldCars();
                case 3 -> carController.listLeasedCars();
                case 4 -> {
                    int year = MenuHandler.readInt("Cars newer than: ");
                    carController.listCarsNewerThan(year);
                }
                case 5 -> {
                    int maxBudget = MenuHandler.readInt("Your Maximum Budget: ");
                    carController.listCarsWithinBudget(maxBudget);
                }
                case 0 -> {
                    System.out.println("Returning to the car menu...");
                    inFilteringMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }



    private static void clientMenu(ClientController clientController) {
        boolean inClientMenu = true;

        while (inClientMenu) {
            int choice = MenuHandler.showMenu("Manage Clients", new String[]{
                    "Add Client",
                    "List All Clients",
                    "Find Client by ID",
                    "Find Client by Name",
                    "Delete Client"
            });

            switch (choice) {
                case 1 -> {
                    Long clientId = MenuHandler.readLong("Client ID: ");
                    String firstName = MenuHandler.readText("First Name: ");
                    String lastName = MenuHandler.readText("Last Name: ");
                    String cnp = MenuHandler.readText("CNP: ");
                    clientController.addClient(firstName, lastName, cnp, clientId);
                }
                case 2 -> clientController.listAllClients();
                case 3 -> {
                    long clientId = MenuHandler.readInt("Client ID: ");
                    clientController.findClientById(clientId);
                }
                case 4 -> {
                    String name = MenuHandler.readText("Full Name: ");
                    clientController.findClientByName(name);
                }
                case 5 -> {
                    long clientId = MenuHandler.readInt("Client ID: ");
                    clientController.deleteClient(clientId);
                }
                case 0 -> {
                    System.out.println("Returning to the main menu...");
                    inClientMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void employeeMenu(EmployeeController employeeController, CarService carService) {
        boolean inEmployeeMenu = true;

        while (inEmployeeMenu) {
            int choice = MenuHandler.showMenu("Manage Employees", new String[]{
                    "Add Employee",
                    "List All Employees",
                    "Find Employee by ID",
                    "Assign Car to Employee",
                    "Delete Employee"
            });

            switch (choice) {
                case 1 -> {
                    Long employeeId = MenuHandler.readLong("Employee ID: ");
                    String firstName = MenuHandler.readText("First Name: ");
                    String lastName = MenuHandler.readText("Last Name: ");
                    String cnp = MenuHandler.readText("CNP: ");
                    String role = MenuHandler.readText("Role (e.g., Salesperson, Manager): ");
                    employeeController.addEmployee(firstName, lastName, cnp, employeeId, role);
                }
                case 2 -> employeeController.listAllEmployees();
                case 3 -> {
                    long employeeId = MenuHandler.readInt("Employee ID: ");
                    employeeController.findEmployeeById(employeeId);
                }
                case 4 -> {
                    long employeeId = MenuHandler.readInt("Employee ID: ");
                    long carId = MenuHandler.readLong("Car ID: ");

                    try {
                        Car car = carService.findCarById(carId);

                        employeeController.assignCarToEmployee(employeeId, car);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 5 -> {
                    long employeeId = MenuHandler.readInt("Employee ID: ");
                    employeeController.deleteEmployee(employeeId);
                }
                case 0 -> {
                    System.out.println("Returning to the main menu...");
                    inEmployeeMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void leasingMenu(LeasingController leasingController, CarService carService, ClientService clientService) {
        boolean inLeasingMenu = true;

        while (inLeasingMenu) {
            int choice = MenuHandler.showMenu("Manage Leasing Contracts", new String[]{
                    "Create Leasing Contract",
                    "Calculate Total Amount for Leasing Contract"
            });

            switch (choice) {
                case 1 -> {
                    long leasingId = MenuHandler.readInt("Leasing ID: ");
                    long carId = MenuHandler.readLong("Car ID: ");
                    long clientId = MenuHandler.readLong("Client ID: ");

                    try {
                        Car car = carService.findCarById(carId);
                        Client client = clientService.findClientById(clientId);

                        int durationMonths = MenuHandler.readInt("Contract Duration (months): ");
                        float interestRate = MenuHandler.readFloat("Interest Rate: ");
                        float downPayment = MenuHandler.readFloat("Down Payment: ");
                        float adminFee = MenuHandler.readFloat("Administrative Fee: ");
                        float taxRate = MenuHandler.readFloat("Tax Rate (%): ");

                        leasingController.createLeasing(leasingId, car, client, durationMonths, interestRate, downPayment, adminFee, taxRate);

                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
//                case 2 -> {
//                    long clientId = MenuHandler.readLong("Client ID: ");
//
//                    try {
//                        Client client = clientService.findClientById(clientId);
//                        leasingController.listLeasingsByClient(client);
//                    } catch (IllegalArgumentException e) {
//                        System.err.println("Error: " + e.getMessage());
//                    }
//                }
//                case 2 -> {
//                    long leasingId = MenuHandler.readLong("Leasing Contract ID: ");
//                    leasingController.findLeasingById(leasingId);
//                }
                case 2 -> {
                    long leasingId = MenuHandler.readLong("Leasing Contract ID: ");
                    float adminFee = MenuHandler.readFloat("Administrative Fee: ");
                    float taxRate = MenuHandler.readFloat("Tax Rate (%): ");

                    leasingController.calculateTotalAmount(leasingId, adminFee, taxRate);
                }
                case 0 -> {
                    System.out.println("Returning to the main menu...");
                    inLeasingMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void transactionMenu(TransactionController transactionController, CarService carService, ClientService clientService) {
        boolean inTransactionMenu = true;

        while (inTransactionMenu) {
            int choice = MenuHandler.showMenu("Manage Transactions", new String[]{
                    "Add Transaction",
                    "List All Transactions",
                    "List Transactions by Type"
            });

            switch (choice) {
                case 1 -> { // Adding a transaction
                    try {
                        long transactionId = MenuHandler.readLong("Transaction ID: ");
                        long carId = MenuHandler.readLong("Car ID: ");
                        long clientId = MenuHandler.readLong("Client ID: "); // Requesting Client ID instead of Name

                        // Read and validate TransactionType
                        String transactionTypeInput = MenuHandler.readText("Transaction Type (SOLD/LEASED): ").trim().toUpperCase();

                        if (!transactionTypeInput.equals("SOLD") && !transactionTypeInput.equals("LEASED")) {
                            throw new IllegalArgumentException("Invalid Transaction Type! Please choose SOLD / LEASED.");
                        }

                        TransactionType transactionType = TransactionType.valueOf(transactionTypeInput);

                        // Fetch car and client using IDs
                        Car car = carService.findCarById(carId);
                        Client client = clientService.findClientById(clientId); // Use Client ID to find the client

                        // Create and add the transaction
                        Transaction transaction = new Transaction(transactionId, car, client, transactionType, new java.util.Date());
                        transactionController.addTransaction(transaction);
                        System.out.println("Transaction added successfully.");
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> transactionController.listAllTransactions();
                case 3 -> {
                    String transactionTypeInput = MenuHandler.readText("Transaction Type (SOLD/LEASED): ").toUpperCase();
                    try {
                        TransactionType transactionType = TransactionType.valueOf(transactionTypeInput);
                        transactionController.listTransactionsByType(transactionType);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid Transaction Type! Please choose SOLD or LEASED.");
                    }
                }
                case 0 -> {
                    System.out.println("Returning to main menu...");
                    inTransactionMenu = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }


}