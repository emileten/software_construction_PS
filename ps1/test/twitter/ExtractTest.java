/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    //
    // Testing strategy:
    //
    // Partition the function's input into the following pieces and cover each part.

    // getMentionedUsers
    
    // No author. Covered by testGetMentionedUsersNoMention. 
    // One author, one mention. Covered by testGetMentionedUsersOneMention. 
    // Several authors, one mention each. Covered by testGetMentionedUsersSeveralMentions. 
    // Several authors, including one mentioned several times with different cases. Covered by testGetMentionedUsersSeveralMentions.  
    // One author, multiple mentions. Covered by testGetMentionedUsersOneAuthorSeveral.
    // One author, one mention, badly specified (author character preceding). Covered by testGetMentionedUsersOneAuthorSeveral.
    // Two authors, one mention each, badly specified : 'stacked'. Covered by testGetMentionedUsersStacked.
    // One Author, one mention, at the end.... testGetMentionedUsersEnd

    // getTimespan
    
    // Tweet has two or more dates. covered by testGetTimespanTwoTweets.
    // Tweet has two or more dates, not ordered initially. covered by testGetTimespanTwoTweetsMessy.
    // Tweet has only one tweet hence one date. Covered by testGetTimespanOneTweet
    // Tweet has two tweets and two dates, with a different start and an end. Covered by testGetTimespanTwoTweets. 
    // Tweet has two tweets and two dates that are identical. Covered by testGetMentionedUsersBadWrite.


    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T14:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "emileten", "@bbitdiddle I don't even know who rivest is ! I am not as nerdy.", d3);   
    private static final Tweet tweet4 = new Tweet(4, "emileten", "cannot talk with you @bbitdiddle @alyssa, I am too ignorant. Gonna talk with my new friend @KitSchwarz. Hello @kitschwarz !", d3);
    private static final Tweet tweet5 = new Tweet(5, "emileten", "We have a new friend called@kitschwarz guys ! Oops I wrote too fast.", d4);
    private static final Tweet tweet6 = new Tweet(6, "emileten", "@alyssa@bbitdiddle", d4);
    private static final Tweet tweet7 = new Tweet(7, "emileten", "long time no see @alyssa", d4);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected to contain bbitdiddle", mentionedUsers.contains("bbitdiddle"));
        
    }
    
    @Test
    public void testGetMentionedUsersSeveralMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertTrue("expected to contain bbitdiddle and alyssa", mentionedUsers.contains("bbitdiddle") && mentionedUsers.contains("alyssa") && mentionedUsers.contains("kitschwarz"));
        
    }
    
    @Test
    public void testGetMentionedUsersOneAuthorSeveral() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet3));
        
        assertEquals(1, mentionedUsers.size());
        
    }
    
    @Test
    public void testGetMentionedUsersBadWrite() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
        
    }
    
    @Test
    public void testGetMentionedUsersStacked() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected to contain alyssa but not bbitdiddle", mentionedUsers.contains("alyssa") && ! mentionedUsers.contains("bbitdiddle"));
        
    }
    
    @Test
    public void testGetMentionedUsersEnd() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        
        assertTrue("expected to contain alyssa", mentionedUsers.contains("alyssa"));
        
    }
    
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("2016-02-17T10:00:00Z", d1, timespan.getStart());
        assertEquals("2016-02-17T10:00:00Z", d1, timespan.getEnd());
        
    }
    
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("2016-02-17T10:00:00Z", d1, timespan.getStart());
        assertEquals("2016-02-17T11:00:00Z", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweetsMessy() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
        
        assertEquals("2016-02-17T10:00:00Z", d1, timespan.getStart());
        assertEquals("2016-02-17T11:00:00Z", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweetsIdentical() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet4));
        
        assertEquals("2016-02-17T10:00:00Z", d3, timespan.getStart());
        assertEquals("2016-02-17T10:00:00Z", d3, timespan.getEnd());
    }
    
    
    @Test
    public void testGetLegalAuthorChar() {
        
        String legal = Extract.getLegalAuthorChar();
        
        assertTrue("expected true", legal.charAt(0)=='a');
        assertTrue("expected true", legal.charAt(1)=='b');
        assertTrue("expected true", legal.charAt(legal.length()-1)=='_');
        
    }
   

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
