package cscie55.hw7;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Processes files containing JSON representations of URLs and accompanying metadata.
 */
public class LinkAnalyzerNodeImpl extends UnicastRemoteObject implements LinkAnalyzerNode {
    private ArrayList<Link> links = new ArrayList<Link>();

    public LinkAnalyzerNodeImpl(File directoryToProcess) throws RemoteException{

        File[] files = directoryToProcess.listFiles();

        if(files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String line;

                        while ((line = reader.readLine()) != null) {
                            try {
                                links.add(Link.parse(line));
                            } catch (Exception e) {
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
        else{
            System.out.println("No links in file.");
        }
    }

    public static void main(String[] args) {
        File directoryToProcess = new File(args[0]);

        try {
            LinkAnalyzerNodeImpl analyzerNode = new LinkAnalyzerNodeImpl(directoryToProcess);

            LinkAnalyzer analyzerService = (LinkAnalyzer) Naming.lookup(LinkAnalyzer.URL);

            analyzerService.registerNode(analyzerNode);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
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

        System.out.println("Links by time from " + startTime + " to " + endTime);

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
}
