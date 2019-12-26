package retailcorebankingsystemclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;


public class TellerMain {
    
    @EJB
    private static AccountSessionBeanRemote accountSessionBeanRemote;
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;

    public static void main(String[] args) {
        
        TellerMainApp tellerMainApp = new TellerMainApp(accountSessionBeanRemote, customerSessionBeanRemote);
        tellerMainApp.runApp();
    }
    
}
