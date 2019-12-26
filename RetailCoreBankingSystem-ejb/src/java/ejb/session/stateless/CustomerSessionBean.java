package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidCredentialException;

@Stateless
@Local(CustomerSessionBeanLocal.class)
@Remote(CustomerSessionBeanRemote.class)


public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createCustomer(String firstName, String lastName, String identificationNumber, String contactNumber, 
            String addressLine1, String addressLine2, String postalCode)
    {
        Customer customer = new Customer(firstName, lastName, identificationNumber, contactNumber, addressLine1, 
        addressLine2, postalCode);
        em.persist(customer);
        em.flush();
        
        return customer.getCustomerId();
    }
    
    public Customer retrieveCustomerByIdentificationNumber(String identificationNumber) throws InvalidCredentialException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.identificationNumber = :inIdentificationNum");
        query.setParameter("inIdentificationNum", identificationNumber);
        Customer customer = (Customer)query.getSingleResult();
        if(customer != null) {
            customer.getDepositAccounts().size();
            return customer;
        } else {
            throw new InvalidCredentialException("Customer does not exist");
        }
    }
    
}
