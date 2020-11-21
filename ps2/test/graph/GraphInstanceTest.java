/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //  add a vertex
    //  add an edge
    //  remove a vertex
    //  get all vertices
    //  get one set of sources
    //  get one set of targets
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    
    @Test
    public void testAddVertex() {
        
        Graph<String> mygraph = emptyInstance();
        
        assertTrue("didn't expect this graph to contain this vertex, should have added it",
                mygraph.add("Chicago"));
        
        mygraph.add("Chicago");

        assertFalse("expected this graph to contain Chicago", mygraph.add("Chicago"));
        
    }
    
    
    @Test
    public void testAddEdge() {
        
        Graph<String> mygraph = emptyInstance();
        assertEquals("didn't expect this edge to exist",
               0, mygraph.set("Chicago", "Milwaukee", 92));
        
    }
    
    
    @Test
    public void testRemoveVertex() {
        
        Graph<String> mygraph = emptyInstance();
        mygraph.add("Chicago");
        mygraph.add("Milwaukee");
        mygraph.set("Chicago", "Milwaukee", 92);
        
        assertTrue("expected this graph to contain Chicago", mygraph.remove("Chicago"));
        
        assertTrue("didn't expect this graph to contain Chicago",
                mygraph.add("Chicago"));

    }
    
    @Test
    public void testGetVertices() {
        
        
        Graph<String> mygraph = emptyInstance();
        mygraph.add("Chicago");
        mygraph.add("Milwaukee");
        mygraph.set("Chicago", "Milwaukee", 92);
        assertTrue("expected the graph to contain Chicago and Milwaukee", mygraph.vertices().contains("Chicago") && mygraph.vertices().contains("Milwaukee")) ;

    }
    
    @Test
    public void testGetSources() {
  
 
        Graph<String> mygraph = emptyInstance();
        mygraph.add("Chicago");
        mygraph.add("Milwaukee");
        mygraph.set("Chicago", "Milwaukee", 92);
 
       
        assertTrue("expected the source of Milwaukee to be Chicago", mygraph.sources("Milwaukee").containsKey("Chicago")) ;
        assertTrue("expected the distance from Chicago to Milwaukee to be 92 miles", mygraph.sources("Milwaukee").get("Chicago")==92);

    }
    
    @Test
    public void testGetTargets() {

        Graph<String> mygraph = emptyInstance();
        mygraph.add("Chicago");
        mygraph.add("Milwaukee");
        mygraph.set("Chicago", "Milwaukee", 92);
 
       
        assertTrue("expected the target of Chicago to be Milwaukee", mygraph.targets("Chicago").containsKey("Milwaukee")) ;
        assertTrue("expected the distance from Milwaukee to Chicago to be 92 miles", mygraph.targets("Chicago").get("Milwaukee")==92);
      
    }
    
    
       
}
