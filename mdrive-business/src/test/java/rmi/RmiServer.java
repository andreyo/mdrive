package rmi;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 05.05.12
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {

    int thisPort;
    String thisAddress;
    Registry registry;    // rmi registry for lookup the remote objects.

    // This method is called from the remote client by the RMI.
    // This is the implementation of the �gReceiveMessageInterface�h.
    public void receiveMessage(String x) throws RemoteException {
        System.out.println(x);
    }

    public RmiServer() throws RemoteException {
        try {
            // get the address of this host.
            thisAddress = (InetAddress.getLocalHost()).toString();
        } catch (Exception e) {
            throw new RemoteException("can't get inet address.");
        }
        thisPort = 3232;  // this port(registry�fs port)
        System.out.println("this address=" + thisAddress + ",port=" + thisPort);
        try {
            // create the registry and bind the name and object.
            registry = LocateRegistry.createRegistry(thisPort);
            registry.rebind("rmiServer", this);
        } catch (RemoteException e) {
            throw e;
        }
    }

    public static void main(String args[]) {
        try {
            RmiServer s = new RmiServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}