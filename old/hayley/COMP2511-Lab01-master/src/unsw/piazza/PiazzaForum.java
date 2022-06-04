package unsw.piazza;

import java.util.List;
import java.util.ArrayList;

/**
 * A Piazza Forum 
 * 
 * @author Hayley Gayfer
 */
public class PiazzaForum {

    private String className;
    private ArrayList<Thread> threads;
    
    /**
     * Initialises the new PiazzaForum with the given group name
     */
    public PiazzaForum(String className) {
        this.className = className;
        threads = new ArrayList<Thread>();
    }

    /**
     * @return The name of the forum
     */
    public String getName() {
        return className;
    }

    /**
     * Sets the name of the group of the Forum
     * @param name
     */
    public void setName(String name) {
        className = name;
    }

    /**
     * Returns a list of Threads in the Forum, in the order that they were published
     */
    public List<Thread> getThreads() {
        return threads;
    }

    /**
     * Creates a new thread with the given title and adds it to the Forum.
     * The content is provided to allow you to create the first Post.
     * Threads are stored in the order that they are published.
     * Returns the new Thread object
     * @param title
     * @param content
     */
    public Thread publish(String title, String content) {
        Thread newThread = new Thread(title, content);
        threads.add(newThread);
        return newThread;
    }

    /**
     * Searches all forum Threads for any that contain the given tag.
     * Returns a list of all matching Thread objects in the order that they were published.
     * @param tag
     * @return
     */
    public List<Thread> searchByTag(String tag) {
        ArrayList<Thread> matchingThreads = new ArrayList<Thread>();
        for (int i = 0; i < threads.size(); i++) {
            if (threads.get(i).getTags().contains(tag)) matchingThreads.add(threads.get(i));
        }
        return matchingThreads;
    }

}