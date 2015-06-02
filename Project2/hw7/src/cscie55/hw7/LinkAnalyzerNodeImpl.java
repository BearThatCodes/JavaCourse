package cscie55.hw7;

import sun.nio.ch.Net;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Isaac on 5/01/2015.
 */
public class LinkAnalyzerNodeImpl extends java.rmi.server.UnicastRemoteObject implements LinkAnalyzerNode {
    private ArrayList<Link> links = new ArrayList<Link>();

    public LinkAnalyzerNodeImpl(File directoryToProcess) throws RemoteException{
            super();

            for(File file:directoryToProcess.listFiles()){
                if(!file.isDirectory()){
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String line;

                        while ((line = reader.readLine()) != null){
                            try {
                                links.add(Link.parse(line));
                            }
                            catch (Exception e){
                                //Exception ignored
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Could not read file " + file.toString());
                    } catch (IOException e) {
                        System.out.println("Could not read line in file " + file.toString());
                    }
                }
            }
    }

    /**
     * Return the Links whose timestamp is between startTime and endTime, searching just the files managed by this node.
     *
     * @param startTime Minimum timestamp to be returned.
     * @param endTime   Maximum timestamp to be returned.
     * @return Links whose timestamp is between startTime and endTime.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByTime(long startTime, long endTime) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(Link link:links){
            if(link.timestamp() > startTime && link.timestamp() < endTime){
                returnLinks.add(link);
            }
        }

        return returnLinks;
    }

    /**
     * Return the Links with a given URL, searching just the files managed by this node.
     *
     * @param url URL to search for.
     * @return Links with the given URL.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByURL(String url) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(Link link:links){
            if(link.url().equals(url)){
                returnLinks.add(link);
            }
        }

        return returnLinks;
    }

    /**
     * Return the Links containing all of the given tags, searching just the files managed by this node.
     *
     * @param tags Set of tags of interest.
     * @return Links containing all of the given tags.
     * @throws java.rmi.RemoteException
     */
    @Override
    public Set<Link> linksByTag(String... tags) throws RemoteException {
        HashSet<Link> returnLinks = new HashSet<Link>();

        for(Link link:links){
            Boolean tagListInTags = true;

            //If the tag we're checking is NOT in this link's tag list, set tagListInTags to false
            for(String tag:tags){
                if(!link.tags().contains(tag)){
                    tagListInTags = false;
                }
            }

            if(tagListInTags){
                returnLinks.add(link);
            }
        }

        return returnLinks;
    }

    public static void main(String[] args) {
        File directoryToProcess = new File(args[0]);

        try {
            LinkAnalyzerNodeImpl analyzerNode = new LinkAnalyzerNodeImpl(directoryToProcess);

            LinkAnalyzerImpl analyzerService = (LinkAnalyzerImpl) Naming.lookup(LinkAnalyzerImpl.URL);

            analyzerService.registerNode(analyzerNode);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }


    }
}
