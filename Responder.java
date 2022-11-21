import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

/**
 * The responder class represents a response generator object.
 * It is used to generate an automatic response, based on specified input.
 * Input is presented to the responder as a set of words, and based on those
 * words the responder will generate a String that represents the response.
 *
 * Internally, the reponder uses a HashMap to associate words with response
 * strings and a list of default responses. If any of the input words is
 * found in the HashMap, the corresponding response is returned. If none of
 * the input words is recognized, one of the default responses is randomly
 * chosen.
 * 
 * @author Salvatore Anzalone
 * @version 11/21/2022
 */
public class Responder
{
    // Used to map key words to responses.
    private HashMap<String, String> responseMap;
    // Default responses to use if we don't recognise a word.
    private ArrayList<String> defaultResponses;
    // The name of the file containing the default responses.
    private static final String FILE_OF_DEFAULT_RESPONSES = "default.txt";
    private Random randomGenerator;

    /**
     * Construct a Responder
     */
    public Responder()
    {
        responseMap = new HashMap<>();
        defaultResponses = new ArrayList<>();
        fillResponseMap();
        fillDefaultResponses();
        randomGenerator = new Random();
    }

    /**
     * Generate a response from a given set of input words.
     * 
     * @param words  A set of words entered by the user
     * @return       A string that should be displayed as the response
     */
    public String generateResponse(HashSet<String> words)
    {
        Iterator<String> it = words.iterator();
        while(it.hasNext()) {
            String word = it.next();
            String response = responseMap.get(word);
            if(response != null) {
                return response;
            }
        }
        // If we get here, none of the words from the input line was
        //recognized.
        // In this case we pick one of our default responses (what we say
        //when we cannot think of anything else to say...)
        return pickDefaultResponse();
    }

    /**
     * Enter all the known keywords and their associated responses
     * into our response map.
     */
    private void fillResponseMap()
    {
        responseMap.put("crash\n", 
                        "Well, it never crashes on our system." +
                        "  It must have something to do with your system." +
                        "Tell me more about your configuration.\n");
        responseMap.put("crashes\n", 
                        "Well, it never crashes on our system." +
                        "It must have something to do with your system." +
                        "  Tell me more about your configuration.\n");
        responseMap.put("slow\n", 
                        "I think this has to do with your hardware." +
                        "Upgrading your processor should solve all " +
                        "performance problems. Have you got a " +
                        "problem with our software?\n");
        responseMap.put("performance\n", 
                        "Performance was quite adequate in all our tests." +
                        "  Are you running any other processes in " +
                        "the background?\n");
        responseMap.put("bug\n", 
                        "Well, you know, all software has some bugs." +
                        "  But our software engineers are working very " +
                        "hard to fix them. Can you describe the problem " +
                        "a bit further?\n");
        responseMap.put("buggy\n", 
                        "Well, you know, all software has some bugs." +
                        "  But our software engineers are working very " +
                        "hard to fix them. Can you describe the problem " +
                        "a bit further?\n");
        responseMap.put("windows\n", 
                        "This is a known bug to do with the Windows " +
                        "operating system.  Please report it to Microsoft." +
                        "  There is nothing we can do about this.\n");
        responseMap.put("macintosh\n", 
                        "This is a known bug to do with the Mac " +
                        "operating system.  Please report it to Apple." +
                        "  There is nothing we can do about this.\n");
        responseMap.put("expensive\n", 
                        "The cost of our product is quite competitive." +
                        "Have you looked around and really compared our " +
                        "features?\n");
        responseMap.put("installation\n", 
                        "The installation is really quite straight " +
                        "forward.  We have tons of wizards that do all " +
                        "the work for you. Have you read the " +
                        "installation instructions?\n");
        responseMap.put("memory\n", 
                        "If you read the system requirements carefully, " +
                        "you will see that the specified memory " +
                        "requirements are 1.5 giga byte.   You really " +
                        "should upgrade your memory.  Anything else you " +
                        "want to know?\n");
        responseMap.put("linux\n", 
                        "We take Linux support very seriously." +
                        "  But there are some problems.  Most have " +
                        "to do with incompatible glibc versions." +
                        "  Can you be a bit more precise?\n");
        responseMap.put("bluej\n", 
                        "Ahhh, BlueJ, yes. We tried to buy out those" +
                        " guys long ago, but they simply won't sell..." +
                        "  Stubborn people they are. Nothing we can " +
                        "do about it, I'm afraid.\n");
    }

    /**
     * Build up a list of default responses from which we can pick
     * if we don't know what else to say.
     */
    private void fillDefaultResponses()
    {
        Charset charset = Charset.forName("US-ASCII");
        Path path = Paths.get(FILE_OF_DEFAULT_RESPONSES);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String response = reader.readLine();
            while(response != null) {
                defaultResponses.add(response);
                response = reader.readLine();
            }
        }
        catch(FileNotFoundException e) {
            System.err.println("Unable to open " + FILE_OF_DEFAULT_RESPONSES);
        }
        catch(IOException e) {
            System.err.println("A problem was encountered reading " +
                               FILE_OF_DEFAULT_RESPONSES);
        }
        // Make sure we have at least one response.
        if(defaultResponses.size() == 0) {
            defaultResponses.add("Could you elaborate on that?");
        }
    }

    /**
     * Randomly select and return one of the default responses.
     * @return     A random default response
     */
    private String pickDefaultResponse()
    {
        // Pick a random number for the index in the default response list.
        // The number will be between 0 (inclusive) and the size of the list (exclusive).
        int index = randomGenerator.nextInt(defaultResponses.size());
        return defaultResponses.get(index);
    }
}
