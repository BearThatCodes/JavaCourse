package cscie55.hw7;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Isaac on 5/23/2015.
 */
public class LinkAnalyzerImpl implements LinkAnalyzer{
    static String URL = "//localhost/linkanalyzer";
    private ArrayList<LinkAnalyzerNode> nodes = new ArrayList<LinkAnalyzerNode>();

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
}
