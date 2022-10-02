import java.util.*; // needed for Map object

/** Conversation class holds variables for storing canned responses, the last given response, 
 * and a Map object that maps keywords to their mirrors; it also contains a constructor that initializes the 
 * map, and methods for responding either with a canned response or a mirror response
 */
class Conversation {

  // To store automatic responses that don't depend on what was said 
  public static String[] cannedResponses = {"Mmm-hmm.", "Yeah?", "Hmm.", "Oh, wow.", "Interesting.", "I see.", "Ah."};
  public String lastResponse = ""; // will store the last response given
  public static Map<String, String> mirrorWords = new Hashtable<>(); // maps keywords to their mirrors

  /** Constructor creates new Conversation object with initialized map of words and their mirrors */
  public Conversation() {
      mirrorWords.put("I'm", "you're");
      mirrorWords.put("I", "you");
      mirrorWords.put("me", "you");
      mirrorWords.put("Me", "you");
      mirrorWords.put("am", "are");
      mirrorWords.put("Am", "are");
      mirrorWords.put("my", "your");
      mirrorWords.put("My", "your");
      mirrorWords.put("you", "I");
      mirrorWords.put("You", "I");
      mirrorWords.put("your", "my");
      mirrorWords.put("Your", "my");
  }

  /**
   * Picks a random "canned" response to something the user said, prints it, and adds it to the transcript
   * @param round: the current conversation round
   * @return chosenResponse: the chosen canned response
   */
  public String respondCanned(int round) {
    double chosen = Math.random()*7;
    int randomInt = (int) chosen;
    String chosenResponse = cannedResponses[randomInt];
    System.out.println(chosenResponse);
    return chosenResponse;
  }

  /**
   * Mirrors the keywords in a response
   * @param line: string array of response
   * @param first_Mirror: index of the first mirror word (already found in main)
   * @return response: response with mirror words
   */
  public String respondMirror(String[] line, int first_Mirror) {
    // "I" to "you", "me" to "you", "am" to "are", "you" to "I", "my" to "your", "your" to "my"
    for (int i = first_Mirror; i < line.length; i++ ) {
      if (mirrorWords.containsKey(line[i])) {
        line[i] = mirrorWords.get(line[i]);
        if (i == 0) {
          line[0] = line[0].substring(0,1).toUpperCase() + line[0].substring(1, line[0].length());
          // if the mirror word is first, capitalize the first letter
        }
      }
    }

      String response = String.join(" ", line) + "?";
      System.out.println(response);
      return response;
    }
    
  /** Main method runs the chatbot; creates Conversation instance called 
   * bot that calls respondCanned and respondMirror accordingly
   */
  public static void main(String[] arguments) {
    Conversation bot = new Conversation();
    Scanner input = new Scanner(System.in);
    System.out.println("How many rounds? ");

    int rounds = input.nextInt();
    input.nextLine(); // consume end of line character
    String[] transcript = new String[2 + (rounds*2)]; // initialize transcript
    int currRound = 1;
    

    // start of conversation + transcript
    String start = "Hi there! What's on your mind?";
    int num_Lines = 1;
    System.out.println(start);
    transcript[0] = start + "\n";
    
    /** for each round, collect the input and either mirror it (if it has a key word) or print a canned response */
    while (currRound <= rounds) {
      // user responds
      String response = input.nextLine();

      // update variables
      bot.lastResponse = response;
      transcript[num_Lines] = response + "\n";
      num_Lines++;
      
      // bot responds accordingly
      String copy = String.copyValueOf(bot.lastResponse.toCharArray()); // resp is now the last line

      // remove punctuation so that words can be recognized
      copy = copy.replace(".", "");
      copy = copy.replace("?", "");
      copy = copy.replace("!", "");
      
    
      String[] line = copy.split(" "); // turns line to list that can be iterated and changed
      boolean isMirror = false;

      for (int i = 0; i < line.length; i++) {
          if (mirrorWords.containsKey(line[i])) {
            isMirror = true;
            transcript[num_Lines] = bot.respondMirror(line, i) + "\n";
            break; // for loop is continued in respondMirror
          }
      }
      if (!isMirror) {
        transcript[num_Lines] = bot.respondCanned(currRound) + "\n";
        // if there's no mirror words, respond with a canned phrase
      }

      num_Lines++;
      currRound++;
    }
    String end = "See ya!"; 
    System.out.println(end);
    transcript[num_Lines] = end;
    System.out.println("\nTRANSCRIPT: \n" + String.join("", transcript) + "\n"); // print transcript
    }
  }
  
