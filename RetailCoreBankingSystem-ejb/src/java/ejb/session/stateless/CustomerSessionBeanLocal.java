package ejb.session.stateless;

import entity.Customer;
import util.exception.InvalidCredentialException;


public interface CustomerSessionBeanLocal {

    public Long createCustomer(String firstName, String lastName, String identificationNumber, String contactNumber, String addressLine1, String addressLine2, String postalCode);

    public Customer retrieveCustomerByIdentificationNumber(String identificationNumber) throws InvalidCredentialException;
    
}
