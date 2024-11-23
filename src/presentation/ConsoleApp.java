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

import java.util.List;

/**
 * Entry point for the Carvy dealership application.
 * Provides a console-based menu for managing cars, clients, employees, leasing contracts, and transactions.
 */
public class ConsoleApp {
    public static void main(String[] args) {

        // Initialize repositories
        CarRepository carRepo = new CarRepository();
        ClientRepository clientRepo = new ClientRepository();
        EmployeeRepository employeeRepo = new EmployeeRepository();
        LeasingRepository leasingRepo = new LeasingRepository();
        TransactionRepository transactionRepo = new TransactionRepository();

        // Initialize leasing manager
        LeasingManager leasingManager = new LeasingManagerImpl();
        LeasingManagerImpl leasingManagerImpl = new LeasingManagerImpl();

        // Initialize services
        CarService carService = new CarService(carRepo);
        ClientService clientService = new ClientService(clientRepo);
        EmployeeService employeeService = new EmployeeService(employeeRepo);
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
                case 2 -> clientMenu(carController, leasingController, clientService);
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

    private static void clientMenu(CarController carController, LeasingController leasingController, ClientService clientService) {
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
                    // Leasing estimate calculation
                    long carId = MenuHandler.readLong("Enter Car ID: ");
                    try {
                        Car car = carController.findCarById(carId);
                        int durationMonths = MenuHandler.readInt("Enter duration in months: ");
                        float interestRate = MenuHandler.readFloat("Enter interest rate (%): ");
                        float monthlyRate = leasingController.calculateMonthlyRate(durationMonths, car.getPrice());

                        System.out.println("Leasing Estimate:");
                        System.out.println("Monthly Rate: " + monthlyRate);
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
                    "List Available Cars",
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
                case 3 -> carController.listAvailableCars();
                case 4 -> {
                    long carId = MenuHandler.readInt("Car ID: ");
                    carController.markCarAsSold(carId);
                }
                case 5 -> {
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
                    String firstName = MenuHandler.readText("First Name: ");
                    String lastName = MenuHandler.readText("Last Name: ");
                    String cnp = MenuHandler.readText("CNP: ");
                    clientController.addClient(firstName, lastName, cnp);
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
                    String firstName = MenuHandler.readText("First Name: ");
                    String lastName = MenuHandler.readText("Last Name: ");
                    String cnp = MenuHandler.readText("CNP: ");
                    String role = MenuHandler.readText("Role (e.g., Salesperson, Manager): ");
                    employeeController.addEmployee(firstName, lastName, cnp, role);
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
                    "List Client's Leasing Contracts",
                    "Find Leasing Contract by ID",
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

                        leasingController.createLeasing(leasingId, car, client, durationMonths, interestRate);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    long clientId = MenuHandler.readLong("Client ID: ");

                    try {
                        Client client = clientService.findClientById(clientId);
                        leasingController.listLeasingsByClient(client);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    long leasingId = MenuHandler.readLong("Leasing Contract ID: ");
                    leasingController.findLeasingById(leasingId);
                }
                case 4 -> {
                    long leasingId = MenuHandler.readLong("Leasing Contract ID: ");
                    leasingController.calculateTotalAmount(leasingId);
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
        boolean inCarMenu = true;

        while (inCarMenu) {
            int choice = MenuHandler.showMenu("Gestionare Tranzacții", new String[]{
                    "Creează Tranzacție",
                    "Listează Toate Tranzacțiile",
                    "Listează Tranzacții După Tip"
            });

            switch (choice) {
                case 1 -> {
                    long transactionId = MenuHandler.readLong("Transaction ID: ");
                    long carId = MenuHandler.readLong("ID Mașină: ");
                    String clientName = MenuHandler.readText("Nume Client: ");
                    String transactionTypeInput = MenuHandler.readText("Tip Tranzacție (SOLD/LEASED): ").toUpperCase();

                    try {
                        // Obține mașina din serviciu
                        Car car = carService.findCarById(carId);

                        // Obține clientul din serviciu
                        Client client = clientService.findClientByName(clientName);

                        // Validăm tipul tranzacției
                        TransactionType transactionType = TransactionType.valueOf(transactionTypeInput);

                        // Creăm tranzacția
                        Transaction transaction = new Transaction(transactionId, car, client, transactionType, new java.util.Date());
                        transactionController.addTransaction(transaction);
                        System.out.println("Tranzacția a fost adăugată cu succes.");
                    } catch (IllegalArgumentException e) {
                        System.err.println("Tip de tranzacție invalid! Te rog să introduci SOLD sau LEASED.");
                    } catch (Exception e) {
                        System.err.println("Eroare: " + e.getMessage());
                    }
                }
                case 2 -> transactionController.listAllTransactions();
                case 3 -> {
                    String transactionTypeInput = MenuHandler.readText("Tip Tranzacție (SOLD/LEASED): ").toUpperCase();
                    try {
                        TransactionType transactionType = TransactionType.valueOf(transactionTypeInput);
                        transactionController.listTransactionsByType(transactionType);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Tip de tranzacție invalid! Te rog să introduci SOLD sau LEASED.");
                    }
                }
                case 0 -> {
                    System.out.println("Revenire la meniul principal...");
                    inCarMenu = false;
                }
                default -> System.out.println("Opțiune invalidă!");
            }
        }
    }
}