/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import static org.junit.Assert.*;
import java.lang.StringBuilder;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   represents a graph with vertices and edges 
    // Representation invariant:
    //   there are no duplicate edges in the edges List.
    // Safety from rep exposure:
    //   vertices labels are made of an immutable data type : <L> 
    //   the List contains Edge data type elements, which is an immutable data type, 
    //   vertices and edges are immutable references
    
    /**
     * Construct incrementally the Graph, by adding up vertices and edges on the empty one. 
     * 
     * @param vertices
     *            a Set of <L>
     * @param edges
     *            a List of distinct Edges           
     */
    public ConcreteEdgesGraph(Set<L> vertices, List<Edge<L>> edges){
        
        Iterator<L> vertexiterator = vertices.iterator();
        
        while(vertexiterator.hasNext()){
            this.vertices.add(vertexiterator.next());
        }
        
       
       Iterator<Edge<L>> edgeiterator = edges.iterator();
       
       while(edgeiterator.hasNext()){
           this.edges.add(edgeiterator.next());
       }
                    
    }
    
    public Graph<L> empty(){
        return new ConcreteEdgesGraph<L>(new HashSet<L>(), new ArrayList<Edge<L>>());
    }
    
    /**
     * Check the representation invariant : edges are distinct. 
     * Implemented without the help of an equals() for Edge type. 
     * 
     * @param edges 
     *            a list of Edges
     */
    public void checkRep(List<Edge<L>> edges){
        
        final List<Edge<L>> myEdges = new ArrayList<>(edges); // copying the Edges because I am going to modify them
        
        int i = 0;
        
        while (i <= myEdges.size()){ //strategy : pick the first edge, remove it, and compare it to all remaining -- iterate over remaining if it passes, otherwise throw error. 
            
           Edge<L> thisEdge = myEdges.get(i).CloneEdge();          
           myEdges.remove(i);           
           List<String> myEdgesIds = GetEdgeIds(myEdges);
           assertFalse("the Edges of the list aren't distinct", myEdgesIds.contains(thisEdge.toString()));          

           i++;
           
        }
               
    }
    
     
    @Override public boolean add(L vertex) {
        if(this.vertices.contains(vertex)){
            return false;
        } else {
            this.vertices.add(vertex);
            return true;
        }
    }
    
    @Override public int set(L source, L target, int weight) {
        
        
        Edge<L> thatEdge = new Edge<L>(source, target, weight);       
        String idOfThat =  thatEdge.toString();       
        List<String> idsOfThis = GetEdgeIds(this.edges);
        int weight_to_return = 0;
        
        if (idsOfThis.contains(idOfThat)){          
            int indexOfThat = idsOfThis.indexOf(idOfThat); 
            weight_to_return = this.edges.get(indexOfThat).getWeight();
            this.edges.remove(indexOfThat);                 
        } 
        
        if(weight!=0){
            this.edges.add(thatEdge);
        }         

        return(weight_to_return);
        
    }
    
    @Override public boolean remove(L vertex) {
                
        final List<Edge<L>> new_edges = new ArrayList<>();
        Iterator<Edge<L>> it = this.edges.iterator();
        boolean contains = false;
        
        while(it.hasNext()){
            
            if (it.next().getSource()!=vertex && it.next().getTarget()!=vertex){
                new_edges.add(it.next());
            }

        }
        
        if (this.vertices.contains(vertex)){
            contains = true;
            this.vertices.remove(vertex);
        }
        
        return(contains);

    }
    
    @Override public Set<L> vertices() {
        
        return(new HashSet<>(this.vertices));

    }
    
    @Override public Map<L, Integer> sources(L target) {
        
        Map<L, Integer> sources = new HashMap<L, Integer>();

        Iterator<Edge<L>> it = this.edges.iterator();

        while(it.hasNext()){
            
            Edge<L> thatEdge = it.next();
            
            if (thatEdge.getTarget()==target){
                sources.put(thatEdge.getSource(), thatEdge.getWeight());
            }

        }

        return(sources);
        
    }
    
    @Override public Map<L, Integer> targets(L source) {
        
        Map<L, Integer> targets = new HashMap<L, Integer>();

        Iterator<Edge<L>> it = this.edges.iterator();

        while(it.hasNext()){
            
            Edge<L> thatEdge = it.next();

            if (thatEdge.getSource()==source){
                targets.put(thatEdge.getTarget(), thatEdge.getWeight());
            }

        }

        return(targets);
        
    }
    
    
    /**
     * Returns a string representation of the Graph.  
     * This representation is a string with multiple rows, each row being the string representation of a given Edge.
     * The string representation of the Graph will thus have as many rows at it has Edges. 
     * 
     * @return a String, which is a human readable representation of the Graph. 
     */
    @Override public String toString(){
        
        StringBuilder StringGraph = new StringBuilder();
        
        Iterator<Edge<L>> it = this.edges.iterator();

        while(it.hasNext()){
            
            Edge<L> thatEdge = it.next();
            
            StringGraph.append(thatEdge.toString() + "\n");
            
        }
      
        return new String(StringGraph);
        
    }
    
    public List<String> GetEdgeIds(List<Edge<L>> Edges){
        
        List<String> EdgeIds = new ArrayList<String>();
        
        Iterator<Edge<L>> it = Edges.iterator();
        
        while(it.hasNext()){
            EdgeIds.add(it.next().toString());            
        }
        
        return EdgeIds;
    
    }
    
}
    
    

/**
 * An immutable directed and weighted graph edge, which is composed of three elements :
 * labeled source and target and a numerical value - a 'weight' -  defining their relation.
 * the Edge is allowed to be a loop, i.e, identical source and target. 
 * The source and the target are labeled with a String, and the weight is an integer.
 * 
 */
class Edge<L> {
    
    private final L source;
    private final L target;
    private final int weight;

    // Abstraction function:
    //  represents the source, target and weight of a weighted-graph directed edge.
    
    // Representation invariant:
    //  weight is non-negative 
    
    // Safety from rep exposure:
    //   source, target and weight point to immutable objects
    //   source, target and weight are immutable references
    
    
    /**
     * Make a weighted directed graph edge with source and target and a weight. 
     * 
     * @param source
     *            a String that labels the source vertex. 
     * @param target
     *            a String that labels the target vertex.
     * @param weight
     *            a strictly positive jnteger, the weight of the edge.      * 
     */
    public Edge(final L source, final L target, final int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    
    
    /**
     * Copy constructor 
     * 
     * @param edge
     *            an Edge     
     */
    public Edge<L> CloneEdge() {
        
        Edge<L> copyEdge = new Edge<L>(this.source, this.target, this.weight);

        return copyEdge;

    }
    
    /**
     * Check the representation invariants. 
     */
    public void checkRep() {  
        assertTrue("The weight is not a strictly positive integer", weight>0);        
    }
    
    /**
     * @return the label of the source vertex
     */
    public L getSource(){
        return source;
    }
    
    /**
     * @return the label of the target vertex
     */
    public L getTarget(){
        return target ;
    }
    
    /**
     * @return the weight assigned to this edge
     */
    public int getWeight(){
        return weight;
    }
    
    
    /**
     * @return the description of the abstract value
     */
    @Override public String toString() {

        return this.source + " => " + this.target + " : " + String.valueOf(this.weight);
        
    }

    
}
