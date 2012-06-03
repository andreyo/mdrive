package rmi;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 05.05.12
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {

    public static void main(String args[]) {
        runClient(new String[]{"localhost", "3232", "hi gents!"});
    }

    private static void runClient(String[] args) {
        ReceiveMessageInterface rmiServer;
        Registry registry;
        String serverAddress = args[0];
        String serverPort = args[1];
        String text = args[2];
        System.out.println("sending " + text + " to " + serverAddress + ":" + serverPort);
        try {
            // get the �gregistry�h
            registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            // look up the remote object
            rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
            // call the remote method
            rmiServer.receiveMessage(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}