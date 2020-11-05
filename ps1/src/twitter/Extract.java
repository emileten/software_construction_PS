/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.*;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
                
        Instant start=tweets.get(0).getTimestamp();
        Instant end=tweets.get(0).getTimestamp();
        Instant thisinstant;
        
        if (tweets.size()>1){

            for (int i=1; i<tweets.size();i++){
                
                thisinstant=tweets.get(i).getTimestamp();
                
                if (thisinstant.isAfter(end)){
                    end=thisinstant;
                } 
                
                if (thisinstant.isBefore(start)){
                    start=thisinstant;
                }
                
            }
            
        }
        

        Timespan timespan= new Timespan(start, end);
           
        return timespan;

    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        

        String legal = getLegalAuthorChar();      
        Set<String> mentions = new HashSet<String>();

        for(int i=0; i<tweets.size(); i++){ // iterate over tweets
            
            String this_text=tweets.get(i).getText();            
            ArrayList<Integer> list_of_at = new ArrayList<>();
            
            for (int j=0; j<this_text.length(); j++){ //catch the @ and put them in a list
                
                if (this_text.charAt(j)=='@'){
                    
                    list_of_at.add(j);
                    
                }
                
            }
            
            
            for (int j=0; j<list_of_at.size();j++){ // iterate over the list of @
                
                int start_of_mention=list_of_at.get(j);

                if (start_of_mention>0){ // if the @ isn't the first character of the text, check the previous character. 
                    
                    boolean previous_is_legal=legal.contains(String.valueOf(this_text.charAt(start_of_mention-1)));
                    
                    if (previous_is_legal){
                        
                        continue ;
                        
                    }
                    
                }
                
                String this_mention = new String(); 
                int this_char_index=start_of_mention+1;
                
                if (this_char_index<this_text.length()){ // otherwise, it means "@" is the last character
                                 
                    boolean this_char_is_legal=legal.contains(String.valueOf(this_text.charAt(this_char_index)));
    
                    while (this_char_is_legal){ // if the current character is legal, store it and go to next.
                        
                        this_mention = this_mention + this_text.charAt(this_char_index); // test passed : operate
                        this_char_index=this_char_index+1; //update the test
                        
                        if (this_char_index>=this_text.length()){
                            
                            break;
                            
                        }

                        this_char_is_legal=legal.contains(String.valueOf(this_text.charAt(this_char_index))); //update character

                    }
                    
                }
                
                if (!this_mention.isEmpty()){ // if you have an actual mention, store it
                    
                    if (!mentions.contains(this_mention)){
                        mentions.add(this_mention);
                    }
                    
                }
                               
            }
                  
        }
        
        
        return mentions;
        
    }
    
    
    /**
     * Get legal username characters
     * @return a String containing characters that are legal in a username. 
     */
    public static String getLegalAuthorChar(){
        
        char[] lower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] otherchars= {'-','_'};
        
        String other = new String(otherchars);
        String loweralphabet= new String(lower);
        String upperalphabet = loweralphabet.toUpperCase();
        
        
        String legal = loweralphabet + upperalphabet + other;
        
        return legal;
                
    }

}
