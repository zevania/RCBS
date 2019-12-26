package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.DepositAccountType;
import util.exception.InvalidCredentialException;


public interface AccountSessionBeanLocal {

    public Long openDepositAccount(DepositAccountType accountType, BigDecimal ledgerBalance, Customer customer);

    public BigDecimal enquireAvailableBalance(String accountNumber);

    public Long issueAtmCard(String pin, Customer customer) throws InvalidCredentialException;

    public void changePin(String cardNumber, String pin, String newPin) throws InvalidCredentialException;

    public List<DepositAccount> retrieveDepositAccounts(String atmCardNumber);
    
}
