package retailcorebankingsystemclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.Customer;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.enumeration.DepositAccountType;
import util.exception.InvalidCredentialException;

public class TellerMainApp {
    
    private AccountSessionBeanRemote accountSessionBeanRemote;
    private CustomerSessionBeanRemote customerSessionBeanRemote;

    public TellerMainApp() {
    }

    public TellerMainApp(AccountSessionBeanRemote accountSessionBeanRemote, CustomerSessionBeanRemote customerSessionBeanRemote) {
        this();
        this.accountSessionBeanRemote = accountSessionBeanRemote;
        this.customerSessionBeanRemote = customerSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Retail Banking System - TELLER ***\n");
            
            System.out.println("1: Create Customer");
            System.out.println("2: Open Deposit Account");
            System.out.println("3: Issue ATM Card");
            System.out.println("4: Exit");
            response = 0;
            
            while(response < 1 || response > 4) {
                // re-enter option because it is out of range
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 1) {
                    doCreateNewCustomer();
                } else if(response == 2) {
                    doOpenDepositAccount();
                } else if(response == 3) {
                    doIssueAtmCard();
                } else if(response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 4) {
                break;
            }
        }
    }
    
    public void doCreateNewCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Retail Core Banking System - TELLER :: Create New Customer ***\n");
        System.out.print("Enter first name> ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name> ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter identification number> ");
        String identificationNumber = scanner.nextLine().trim();
        System.out.print("Enter contact number> ");
        String contactNumber = scanner.nextLine().trim();
        System.out.print("Enter address line 1> ");
        String addressLine1 = scanner.nextLine().trim();
        System.out.print("Enter address line 2> ");
        String addressLine2 = scanner.nextLine().trim();
        System.out.print("Enter postal code> ");
        String postalCode = scanner.nextLine().trim();
        Long newCustomerId = customerSessionBeanRemote.createCustomer(firstName, lastName, identificationNumber, contactNumber, addressLine1, addressLine2, postalCode);
        System.out.println("New Customer created successfuly! "+ newCustomerId + "\n");
    }
    
    public void doOpenDepositAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Retail Core Banking System - TELLER :: Open Deposit Account ***\n");
        System.out.print("Enter customer identification number> ");
        String identificationNumber = scanner.nextLine().trim();
        System.out.println("Choose account type: ");
        System.out.println("1: SAVINGS");
        System.out.println("2: CURRENT");
        Integer response = 0;   
        DepositAccountType accountType = DepositAccountType.SAVINGS;
        while(response < 1 || response > 4) {
            System.out.print("> ");
            response = scanner.nextInt();
            if(response == 1) {
                accountType = DepositAccountType.SAVINGS;
            } else if (response == 2) {
                accountType = DepositAccountType.CURRENT;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        System.out.print("Enter initial deposit> ");
        BigDecimal ledgerBalance = scanner.nextBigDecimal();
        try {
            Customer customer = customerSessionBeanRemote.retrieveCustomerByIdentificationNumber(identificationNumber);
            Long accId = accountSessionBeanRemote.openDepositAccount(accountType, ledgerBalance, customer);
            System.out.println("New Deposit Account created successfuly! "+ accId + "\n");
        } catch (InvalidCredentialException ex) {
            System.out.println("An error has occurred while opening the account: " + ex.getMessage() + "\n");
        }
    }
    
    public void doIssueAtmCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Retail Core Banking System - TELLER :: Issue ATM Card ***\n");
        
        try {
            System.out.print("Enter customer identification number> ");
            String identificationNumber = scanner.nextLine().trim();
            Customer customer = customerSessionBeanRemote.retrieveCustomerByIdentificationNumber(identificationNumber);
            System.out.print("Enter pin> ");
            String pin = scanner.nextLine().trim();
            Long atmId = accountSessionBeanRemote.issueAtmCard(pin, customer);
            System.out.println("New ATM Card issued successfuly! "+ atmId + "\n");
        } catch (InvalidCredentialException ex) {
            System.out.println("An error has occurred while opening the account: " + ex.getMessage() + "\n");
        }
    }
}
