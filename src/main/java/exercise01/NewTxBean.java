package exercise01;

import javax.ejb.*;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

@Stateless
public class NewTxBean {

    @TransactionAttribute(REQUIRES_NEW)
    public void newTransaction() throws Exception {
        try {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (RuntimeException e) {
            throw new MyException(e);
        }
    }

}
