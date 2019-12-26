package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.DepositAccountType;
import util.exception.InvalidCredentialException;

@Stateless
@Local(AccountSessionBeanLocal.class)
@Remote(AccountSessionBeanRemote.class)

public class AccountSessionBean implements AccountSessionBeanRemote, AccountSessionBeanLocal {
    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
            
    @Override
    public Long openDepositAccount(DepositAccountType accountType, 
            BigDecimal ledgerBalance, Customer customer) {
        
        Random random = new Random();
        String accountNumber = "";
        for(int i=0; i<7; i++) {
            accountNumber += random.nextInt(10);
        }
        
            DepositAccount depositAccount = new DepositAccount(accountNumber, accountType,
                                        ledgerBalance, customer);
            em.persist(depositAccount);
            em.flush();
            customer.addDepositAccounts(depositAccount);
            em.merge(customer);
            em.flush();
            return depositAccount.getDepositAccountId();
    }
    
    @Override
    public BigDecimal enquireAvailableBalance(String accountNumber) {
        Query query = em.createQuery("SELECT a FROM DepositAccount a WHERE a.accountNumber = :inAccountNum");
        query.setParameter("inAccountNum", accountNumber);
        DepositAccount acc = (DepositAccount)query.getSingleResult();
        
        return acc.getAvailableBalance();
    }
    
    @Override
    public List<DepositAccount> retrieveDepositAccounts(String atmCardNumber) {
        Query query = em.createQuery("SELECT a FROM AtmCard a WHERE a.cardNumber = :inCardNumber");
        query.setParameter("inCardNumber", atmCardNumber);
        AtmCard atmCard = (AtmCard)query.getSingleResult();
        atmCard.getDepositAccounts().size();
        return atmCard.getDepositAccounts();
    }
    
    @Override
    public Long issueAtmCard(String pin, Customer customer) throws InvalidCredentialException {
        
        Random random = new Random();
        String cardNumber = "";
        for(int i=0; i<16; i++) {
            cardNumber += random.nextInt(10);
        }
        
        List<DepositAccount> depositAccounts = customer.getDepositAccounts();
        if(depositAccounts.size() == 0) {
            throw new InvalidCredentialException("Customer has no deposit account");
        } else {
            // if customer already have an atm card previously
            if(customer.getAtmCard() != null) {
                Long customerId = customer.getCustomerId();
                Customer c = em.find(Customer.class, customerId);
                AtmCard a = em.find(AtmCard.class, c.getAtmCard().getAtmCardId());
                for(int i=0; i<c.getDepositAccounts().size(); i++) {
                    DepositAccount d = em.find(DepositAccount.class,c.getDepositAccounts().get(i).getDepositAccountId());
                    d.setAtmCard(null);
                }
                a.getDepositAccounts().clear();
                a.setCustomer(null);
                c.setAtmCard(null);
                em.remove(a);
            }
            
            String nameOnCard = customer.getFirstName() + customer.getLastName();

            AtmCard atmCard = new AtmCard(cardNumber, nameOnCard, pin, customer, depositAccounts);
            em.persist(atmCard);
            em.flush();
            customer.setAtmCard(atmCard);
            em.merge(customer);
            for(DepositAccount d: customer.getDepositAccounts()) {
                    d.setAtmCard(atmCard);
                    em.merge(d);
                    em.flush();
                    System.out.println("HERE");
            }
            return atmCard.getAtmCardId();
        }
        
    }
    
    @Override
    public void changePin(String cardNumber, String pin, String newPin) throws InvalidCredentialException {
        Query query = em.createQuery("SELECT a FROM AtmCard a WHERE a.cardNumber = :inCardNumber");
        query.setParameter("inCardNumber", cardNumber);
        AtmCard atmCard = (AtmCard)query.getSingleResult();
        
        if(atmCard.getPin().equals(pin)) {
            atmCard.setPin(newPin);
        } else {
            throw new InvalidCredentialException("Invalid credentials");
        }
    }
    
}
