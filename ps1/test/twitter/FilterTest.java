/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    // Testing strategy:
    //
    // Partition the function's input into the following pieces and cover each part.

    // writtenBy
        // username belongs to the set of usernames 
            // is in one of the tweet(s). testWrittenByInOne
            // is in several tweets. testWrittenByInSeveral
            // same as above, ordering differently the input list of tweets. testWrittenByInSeveralAltOrder
            // is in each tweet. testWrittenByInAll
    
        // username doesn't belong to the set of usernames 
            // should return empty tweet list. testWrittenByAbsent
    
        // username is illegal 
            // throws an error. testWrittenByBadUsername 
    
        // list of tweet is empty 
            // throws an error. testWrittenByNoTweet
    
    // inTimespan
        // no tweet falls in the timespan
           // returns empty list.  testInTimespanMultipleTweetsNoResult
 
        // one or more tweets fall in the timespan 
            // one tweet falls within the interval. testInTimespanMultipleTweetsOneResult
            // one tweet falls in one of the boundaries. testInTimespanMultipleTweetsOneResultBoundary
            // several tweets fall within the interval . testInTimespanMultipleTweetsMultipleResults
            // same as above, with a different ordering. testInTimespanMultipleTweetsMultipleResultsAltOrder
    
        // tweet list is empty  
            // throws an error. testInTimespanNoTweet
    
    // containing
    
        // containining passed words 
            // one word 
                // contained in one tweet testContainingOneTweet
                // contained in more than one tweet. testContaining
            // one word with illegal character, should return an error. testContainingIllegalChar  
            // several words. testContainingSeveralWords
            
      
        // not containing. 
            // completely absent, should return empty list. testContainingNoResult
            // not absent but not separated by spaces, should return an empty list. testContainingNoTrueResult
            
        // zero words
    
        // zero tweets
       
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-18T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "I like to write so I am writing again", d3);
    private static final Tweet tweet4 = new Tweet(4, "etenezakis", "I am writing in a weird wayaboutChicago", d3);
    private static final Tweet tweet5 = new Tweet(5, "etenezakis", "I am writing in a weird way about Chicago", d3);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByInOne() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet1", writtenBy.contains(tweet1));
    }
    
    
    @Test
    public void testWrittenByInSeveral() {
       
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");

        assertEquals("expected list of length 2", 2, writtenBy.size());
        assertTrue("expected list to contain tweet1 and tweet3", writtenBy.contains(tweet1) & writtenBy.contains(tweet3));
        assertTrue("expected order : tweet 1, tweet3", writtenBy.get(0)==tweet1 & writtenBy.get(1)==tweet3);

    }
    
    @Test
    public void testWrittenByInSeveralAltOrder() {

        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet3, tweet2, tweet1), "alyssa");

        assertEquals("expected list of length 2", 2, writtenBy.size());
        assertTrue("expected list to contain tweet1 and tweet3", writtenBy.contains(tweet1) & writtenBy.contains(tweet3));
        assertTrue("expected order : tweet 3, tweet1", writtenBy.get(0)==tweet3 & writtenBy.get(1)==tweet1);

    }
    
    @Test
    public void testWrittenByInAll() {
      
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet3), "alyssa");

        assertEquals("expected list of length 2", 2, writtenBy.size());
        assertTrue("expected list to contain tweet1 and tweet3", writtenBy.contains(tweet1) & writtenBy.contains(tweet3));

    }
    
    @Test
    public void testWrittenByAbsent() {
      
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "etenezakis");

        assertTrue("expected empty list",writtenBy.isEmpty());

    }
    
    
    @Test
    public void testWrittenByBadUsername() {
      
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "@efj*"); //this should return an assertion error

    }
    
    
  
    
    @Test
    public void testInTimespanMultipleTweetsNoResult() {
        Instant testStart = Instant.parse("2016-02-17T17:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T19:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty list", inTimespan.isEmpty());

    }
    
    @Test
    public void testInTimespanMultipleTweetsOneResult() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet3), new Timespan(testStart, testEnd));
        
        assertTrue("expected length-one list", inTimespan.size()==1);
        assertTrue("expected list to contain tweet 1", inTimespan.get(0)==tweet1);

    }

    @Test
    public void testInTimespanMultipleTweetsOneResultBoundary() {
                
        Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet3), new Timespan(testStart, testEnd));
        
        assertTrue("expected length-one list", inTimespan.size()==1);
        assertTrue("expected list to contain tweet 1", inTimespan.get(0)==tweet1);

    }   
    
   
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResultsAltOrder() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2, tweet1), new Timespan(testStart, testEnd));
        
        assertTrue("expected order : tweet2 and then tweet1", inTimespan.get(0)==tweet2 && inTimespan.get(1)==tweet1);
        
    }
    
    
     @Test
     public void testContainingOneTweet() {
         List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet3), Arrays.asList("talk"));
         
         assertFalse("expected non-empty list", containing.isEmpty());
         assertTrue("expected list to contain tweets", containing.contains(tweet2) && !containing.contains(tweet3));

     }

       
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    
    @Test
    public void testContainingIllegal() {
        
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("ta lk")); //this should return an assertion error
        
    }

    
    @Test
    public void testContainingSeveralWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "write"));
        
        assertTrue("expected list to contain all tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 2, containing.indexOf(tweet3));
    }
    
    @Test
    public void testContainingNoResult(){
 
       List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("Chicago"));
        
       assertTrue("expected empty list", containing.isEmpty());
 
    }
    
    
    @Test
    public void testContainingNoTrueResult(){
 
       List<Tweet> containing = Filter.containing(Arrays.asList(tweet4), Arrays.asList("Chicago"));
        
       assertTrue("expected empty list", containing.isEmpty());
 
    }
    
    @Test
    public void testContainingResultAtEnd(){
 
       List<Tweet> containing = Filter.containing(Arrays.asList(tweet5), Arrays.asList("Chicago"));
        
       assertTrue("expected to contain tweet 5", containing.contains(tweet5));
 
    }
    
    @Test 
    public void testLegallyContaining(){
        
        assertTrue(Filter.LegallyContaining("Long time no see @alyssa", "@alyssa"));
        
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
