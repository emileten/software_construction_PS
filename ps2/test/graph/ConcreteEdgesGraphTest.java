/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        
        return new ConcreteEdgesGraph<String>(new HashSet<String>(), new ArrayList<Edge<String>>());
        
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   no edges 
    //   >=2 edges
    
    @Test
    public void testGraphToString() { 
        
        Graph<String> mygraph = emptyInstance();
        
        assertTrue("expected String representation to be empty", mygraph.toString().isEmpty());
        
        mygraph.add("Chicago");
        mygraph.add("Milwaukee");
        mygraph.set("Chicago", "Milwaukee", 92);
 
        mygraph.add("NYC");
        mygraph.add("Boston");
        mygraph.set("NYC", "Boston", 217);
        
        assertFalse("unexpected string representation", mygraph.toString().isEmpty());
        
    } 
    
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    
    // Constructor :
        //  strictly positive weight
        //  non-strictly positive weight
    
    // Observers :
        // getSource
        // getTarget
        // getWeight 
        // toString
        
    
    @Test
    public void testEdgeCorrect() {       
        new Edge<String>("Chicago", "Milwaukee", 92);          
    }
    
    @Test
    public void testEdgeNotCorrect(){       
        new Edge<String>("Chicago", "Milwaukee", -3);              
    }
    
    @Test
    public void testObservers(){
        Edge<String> myEdge = new Edge<String>("Chicago", "Milwaukee", 92);
        assertTrue("wrong source", myEdge.getSource().equals("Chicago"));
        assertTrue("wrong target", myEdge.getTarget().equals("Milwaukee"));
        assertTrue("wrong weight", myEdge.getWeight()==92);      
    }
    
    @Test
    public void testToString(){
        Edge<String> myEdge = new Edge<String>("Chicago", "Milwaukee", 92);
        assertEquals("expected Chicago => Milwaukee : 92", "Chicago => Milwaukee : 92", myEdge.toString());        
    }
    
    
}
