package exercise01;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class MyException extends Exception {

    public MyException(RuntimeException e) {
    }

}
