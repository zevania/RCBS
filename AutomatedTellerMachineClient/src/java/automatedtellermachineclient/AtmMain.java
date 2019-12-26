package automatedtellermachineclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;

public class AtmMain {
    
    @EJB
    private static AccountSessionBeanRemote accountSessionBeanRemote;
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;

    public static void main(String[] args) {
        
        AtmMainApp atmMainApp = new AtmMainApp(accountSessionBeanRemote, customerSessionBeanRemote);
        atmMainApp.runApp();
    }
    
}
