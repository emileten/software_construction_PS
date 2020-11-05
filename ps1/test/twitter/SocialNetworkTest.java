/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    // Testing strategy:
    //
    // Partition the function's input into the following pieces and cover each part.

    //If A
    //* doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
    //* as a key in the map; this is true even if A is followed by other people in the network
    
    // GuessFollowsGraph

    
        // no tweets. Should return empty map. testGuessFollowsGraphEmpty. 
             

        // some tweets 
            // no mentions in the list of tweets. should return empty map. testGuessFollowsGraphNoMentions. 
    
        
            // mentions in the list of tweets
                // one username mentioned. testGuessFollowsGraphOneUsernameMentioned.
                // a user is mentioned but doesn't mention anyone. same test as above. 
                // several usernames mentioned. testGuessFollowsGraphSeveralUsernameMentioned. 
    
    
    // influencers

        // empty map. testInfluencersEmptyMap. 
    
        // non-empty map.  
            // positions are determined. testInfluencersNonEmptyMapIdentified. 
            // positions aren't determined. testInfluencersNonEmptyMapNonIdentified. 


    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much? Oh hey @kitschwarz", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d1);
    private static final Tweet tweet3 = new Tweet(3, "kitschwarz", "hey ! here is @alyssa.", d1);
    private static final Tweet tweet4 = new Tweet(4, "etenezakis", "Long time no see @alyssa", d1);
    private static final Tweet tweet5 = new Tweet(5, "donald", "I am such a looser", d1);
    private static final Tweet tweet6 = new Tweet(6, "joe", "I beat you @donald ! ", d1);
    private static final Tweet tweet7 = new Tweet(7, "joe", "such a nice election week.", d1);

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test 
    public void testGuessFollowsGraphNoMentions(){
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5, tweet7));
        
        assertTrue("expected empty graph", followsGraph.isEmpty());   
        
    }
    
   
    @Test 
    public void testGuessFollowsGraphOneUsernameMentioned(){
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5, tweet6));
        

        assertTrue("expected to contain key for joe", followsGraph.containsKey("joe"));   
        assertTrue("expected joe to have donald in its set", followsGraph.get("joe").contains("donald"));   
        assertFalse("expected not to contain key for donald", followsGraph.containsKey("donald"));   
        
       
    }
    
    @Test 
    public void testGuessFollowsGraphSeveralUsernameMentioned(){
    
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4,tweet5, tweet6, tweet7));
        

        assertTrue("expected to contain key for kitschwarz, alyssa, etenezakis and joe", followsGraph.containsKey("kitschwarz") && followsGraph.containsKey("alyssa") && followsGraph.containsKey("etenezakis") && followsGraph.containsKey("joe"));   
        assertTrue("expected that etenezakis set contains alyssa", followsGraph.get("etenezakis").contains("alyssa"));   
        assertTrue("expected that alyssa set contains kitschwarz", followsGraph.get("alyssa").contains("kitschwarz"));   
        
    }
    
    
    @Test
    public void testInfluencersEmptyMap() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersNonEmptyMapIdentified(){
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet3, tweet1));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertFalse("expected non empty list", influencers.isEmpty());
        assertTrue("expected alyssa to be first, kitschwarz to be second", influencers.get(0).equals("alyssa") && influencers.get(1).equals("kitschwarz"));
        
    }
    
    @Test
    public void testInfluencersNonEmptyMapNonIdentified(){
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4,tweet5, tweet6, tweet7));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertFalse("expected non empty list", influencers.isEmpty());      
        assertTrue("expected alyssa to be first, kitschwarz or donald to be second", influencers.get(0).equals("alyssa") && (influencers.get(1).equals("kitschwarz") || influencers.get(1).equals("donald")));
        
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
