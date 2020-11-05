/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;
import java.util.regex.*;
import static org.junit.Assert.*;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        
        
        for (int i=0 ; i<username.length() ; i++){ //verify legality of each character in username
       
            assertTrue("expected the username to contain only legal username characters", Extract.getLegalAuthorChar().contains(String.valueOf(username.charAt(i))));            
      
        }
        
        
        List<Tweet> tweets_of_username = new ArrayList<Tweet>();

        for (int i=0 ; i<tweets.size() ; i++){
                               
            if (tweets.get(i).getAuthor()==username){
                
                tweets_of_username.add(tweets.get(i));
                
            }
            
        }
        
        return tweets_of_username;
        
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {

       
        List<Tweet> tweets_in_timespan = new ArrayList<Tweet>();

        for(int i=0 ; i<tweets.size() ; i++){
                
            Instant this_timestamp = tweets.get(i).getTimestamp();          
            boolean in_timespan_open = this_timestamp.isAfter(timespan.getStart()) && this_timestamp.isBefore(timespan.getEnd()) ;
            boolean in_timespan_closed = in_timespan_open || this_timestamp.equals(timespan.getStart()) ||  this_timestamp.equals(timespan.getEnd());
            
            if (in_timespan_closed){
                
                tweets_in_timespan.add(tweets.get(i));
                
            }
            
        }
        
        return tweets_in_timespan;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        
       
        
        assertFalse("expected non empty word list", words.isEmpty());
        
        
        for (int i=0 ; i<words.size() ; i++){ //verify legality of words list
       
            assertFalse("expected word without space", words.get(i).contains(String.valueOf(' ')));
      
        }
        
        List<Tweet> tweets_containing = new ArrayList<Tweet>();

        for(int i=0 ; i<tweets.size() ; i++){
                
            String this_text = new String(tweets.get(i).getText()).toLowerCase(); // 'toLowerCase' applies to 'word' as well, to ensure test is case-insensitive. 'New' because I am modifying.          
            boolean contains=false;
            int j=0;
            
            while(!contains && j<words.size()){
                
                String this_word = words.get(j).toLowerCase();
                contains=LegallyContaining(this_text, this_word);
                j = j + 1;
                
            }
            
            if (contains){
 
                tweets_containing.add(tweets.get(i));
 
            }
            
        }
        
        return tweets_containing;
        
    }

    

    /**
     * Tests legal presence of a String in another String.
     * 
     * @param sentence
     *            a string
     * @param word
     *            a string           
     * @return boolean, indicating whether a 'whole word' is present in sentence. 
     */ 
    public static boolean LegallyContaining(String sentence, String word) {
        
        String pattern = "\\b" + word + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m= p.matcher(sentence);
        boolean result = m.find();
        return result;
        
    }

   
}
        
 
    
    
