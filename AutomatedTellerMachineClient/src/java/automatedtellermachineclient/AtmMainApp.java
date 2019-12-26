package automatedtellermachineclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.DepositAccount;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.InvalidCredentialException;

public class AtmMainApp {
    private AccountSessionBeanRemote accountSessionBeanRemote;
    private CustomerSessionBeanRemote customerSessionBeanRemote;

    public AtmMainApp() {
    }

    public AtmMainApp(AccountSessionBeanRemote accountSessionBeanRemote, CustomerSessionBeanRemote customerSessionBeanRemote) {
        this.accountSessionBeanRemote = accountSessionBeanRemote;
        this.customerSessionBeanRemote = customerSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Retail Banking System - ATM ***\n");
            
            System.out.println("1: Change PIN");
            System.out.println("2: Enquire Available Balance");
            System.out.println("3: Exit");
            response = 0;
            
            while(response < 1 || response > 3) {
                // re-enter option because it is out of range
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 1) {
                    doChangePIN();
                } else if(response == 2) {
                    doEnquireAvailableBalance();
                } else if(response == 3) {
                    break;
                }  else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 3) {
                break;
            }
        }
    }
    
    public void doChangePIN() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Retail Core Banking System - ATM :: Change PIN ***\n");
        System.out.print("Enter ATM Card number> ");
        String atmCardNumber = scanner.nextLine().trim();
        System.out.print("Enter pin number> ");
        String pin = scanner.nextLine().trim();
        System.out.print("Enter new pin number> ");
        String newPin = scanner.nextLine().trim();
        
        try {
            accountSessionBeanRemote.changePin(atmCardNumber, pin, newPin);
            System.out.println("ATM Card PIN changed successfully! \n");
        } catch (InvalidCredentialException ex) {
            System.out.println("An error has occurred while changing PIN: " + ex.getMessage() + "\n");
        }
    }
    
    public void doEnquireAvailableBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Retail Core Banking System - ATM :: Enquire Available Balance ***\n");
        System.out.print("Enter ATM Card number> ");
        String atmCardNumber = scanner.nextLine().trim();
        List<DepositAccount> accounts = accountSessionBeanRemote.retrieveDepositAccounts(atmCardNumber);
        for(int i=0; i<accounts.size(); i++) {
            System.out.println(i+1+": "+accounts.get(i).getAccountNumber());
        }
        System.out.println("Choose an account");
        int response = 0;
        while(response < 1 || response > accounts.size()) {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response>=1 && response <= accounts.size()) {
                    // don't need to call session bean??
                    System.out.println("Available Balance: "+accounts.get(response-1).getAvailableBalance());
                }  else {
                    System.out.println("Invalid option, please try again!\n");
                }
        }
        
    }
    
}
