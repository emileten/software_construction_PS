/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>(new ArrayList<Vertex<String>>());
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // testing ConcreteVerticesGraph.toString()
    
    @Test
    public void testGraphToString() { 
        
        Graph<String> mygraph = emptyInstance();
        
        assertTrue("expected String representation to be empty", mygraph.toString().isEmpty());
        
        Map< String, Integer > sources = new HashMap< String, Integer >();
        Map< String, Integer > targets = new HashMap< String, Integer >();
        sources.put("Milwaukee", 92);
        targets.put("Saint Louis", 296);
        
        Vertex<String> myVertex = new Vertex<String>("Chicago", sources, targets); 
        
        List<Vertex<String>> myList = new ArrayList<Vertex<String>>();
        
        myList.add(myVertex);
        
        Graph<String> othergraph = new ConcreteVerticesGraph<String>(myList);
        
        assertFalse("expected non-empty string representation", othergraph.toString().isEmpty());
        
    } 
    
    
    
    
    
    
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   Constructor
    //   Observers
    //   Modifiers
    
   
    
    @Test
    public void testVertex() {       
        
        Map< String, Integer > sources = new HashMap< String, Integer >();
        Map< String, Integer > targets = new HashMap< String, Integer >();
        sources.put("Milwaukee", 92);
        targets.put("Saint Louis", 296);
        
        Vertex<String> myvertex = new Vertex<String>("Chicago", sources, targets); 
        assertTrue("expected the label of the new vertex to be Chicago", myvertex.getName().equals("Chicago"));

        
        Vertex<String> clonedvertex = myvertex.cloneVertex(); //just testing cloning
        assertTrue("expected the label of the cloned vertex to be the same as generic", clonedvertex.getName().equals("Chicago"));
        
        Vertex<String> myemptyvertex = new Vertex<String>("Chicago", new HashMap<String, Integer>(), new HashMap<String, Integer>());
        
        assertTrue("expected the sources of this vertex to be an empty map and the name to be Chicago", myemptyvertex.getSources().isEmpty() && myemptyvertex.getName().equals("Chicago"));
        
    }
    
   
    @Test
    public void testObservers(){

        Map< String, Integer > sources = new HashMap< String, Integer >();
        Map< String, Integer > targets = new HashMap< String, Integer >();
        sources.put("Milwaukee", 92);
        targets.put("Saint Louis", 296);
        
        Vertex<String> myVertex = new Vertex<String>("Chicago", sources, targets); 

        assertTrue("expected Chicago", myVertex.getName().equals("Chicago"));
        assertTrue("expected Milwaukee",myVertex.getSources().containsKey("Milwaukee"));
        assertTrue("expected Saint Louis", myVertex.getTargets().containsKey("Saint Louis"));
        assertTrue("expected 92", myVertex.getWeight("Milwaukee", "Chicago")==92);

    }
    
    
    @Test
    public void testMutators(){
        
        Map< String, Integer > sources = new HashMap< String, Integer >();
        Map< String, Integer > targets = new HashMap< String, Integer >();
        sources.put("Milwaukee", 92);
        targets.put("Saint Louis", 296);
  
        Vertex<String> myVertex = new Vertex<String>("Chicago", sources, targets); 

        myVertex.addSource("New York", 789);
        myVertex.addTarget("Bogota", 2700);
        
        assertTrue("expected to contain New York", myVertex.getSources().containsKey("New York"));
        assertTrue("expected distance to Bogota to be 2700 miles", myVertex.getWeight("Chicago", "Bogota")==2700);

    }
    
    @Test
    public void testToString(){
       
        Map< String, Integer > sources = new HashMap< String, Integer >();
        Map< String, Integer > targets = new HashMap< String, Integer >();
        sources.put("Milwaukee", 92);
        targets.put("Saint Louis", 296);
  
        Vertex<String> myVertex = new Vertex<String>("Chicago", sources, targets); 
        
        myVertex.toString();
        
    }
        
}
