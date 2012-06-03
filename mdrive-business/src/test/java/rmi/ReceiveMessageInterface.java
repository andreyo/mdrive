package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 05.05.12
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public interface ReceiveMessageInterface extends Remote {

    void receiveMessage(String x) throws RemoteException;
}
