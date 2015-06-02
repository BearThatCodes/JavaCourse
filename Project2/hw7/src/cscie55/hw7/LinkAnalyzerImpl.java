package cscie55.hw7;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Isaac on 5/23/2015.
 */
public class LinkAnalyzerImpl extends java.rmi.server.UnicastRemoteObject implements LinkAnalyzer {
    protected static String URL = "//localhost/linkanalyzer";
    private static int nodeID = 0;
    private ArrayList<LinkAnalyzerNode> nodes = new ArrayList<LinkAnalyzerNode>();

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p/>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws java.rmi.RemoteException if failed to export object
     * @since JDK1.1
     */
    protected LinkAnalyzerImpl() throws RemoteException {
        super();
    }

    /**
     * Return the Links whose timestamp is between startTime and endTime.
     *
     * @param startTime Minimum timestamp to be returned.
     * @param endTime   Maximum timestamp to be returned.
     * @return Links whose timestamp is between startTime and endTime.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByTime(long startTime, long endTime) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(LinkAnalyzerNode node:nodes){
            returnLinks.addAll(node.linksByTime(startTime,endTime));
        }

        return returnLinks;
    }

    /**
     * Return the Links with a given URL.
     *
     * @param url URL to search for.
     * @return Links with the given URL.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByURL(String url) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(LinkAnalyzerNode node:nodes){
            returnLinks.addAll(node.linksByURL(url));
        }

        return returnLinks;
    }

    /**
     * Return the Links containing all of the given tags.
     *
     * @param tags Set of tags of interest.
     * @return Links containing all of the given tags.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByTag(String... tags) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(LinkAnalyzerNode node:nodes){
            returnLinks.addAll(node.linksByTag(tags));
        }

        return returnLinks;
    }

    /**
     * Register the given LinkAnalyzerNode as a node for use by this LinkAnalyzer.
     *
     * @param node A node to be used by this LinkAnalyzer.
     * @throws java.rmi.RemoteException
     */
    @Override
    public void registerNode(LinkAnalyzerNode node) throws RemoteException {
        nodes.add(node);
    }

    public static void main(String[] args) {
        /*try {
            LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

        try {
            LinkAnalyzerImpl analyzer = new LinkAnalyzerImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(URL, analyzer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
