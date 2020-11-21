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
import java.util.ListIterator;
import static org.junit.Assert.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<Vertex<L>>();
    
    // Abstraction function:
    //   represents the vertices of a weighted directed graph
    
    // Representation invariant:
    //  vertices are unique
    //  teh weights associated with the sources or targets of a vertex are strictly positive
    //  edges are duplicated (appears in source and target)
    
    // Safety from rep exposure:
    //   vertices are an immutable reference. 
    //   vertices are mutable object to the extent that they have mutator methods only. 
   
    
    // TODO constructor

    /**
     * Construct incrementally the Graph, by adding up vertices. 
     * 
     * @param vertices
     *            a List of vertices objects
     */
    public ConcreteVerticesGraph(List<Vertex<L>> vertices){
        
        Iterator<Vertex<L>> it = vertices.iterator();

        while (it.hasNext()){
            this.vertices.add(it.next());
        }
                    
    }
        
    /**
     * Check the representation invariants
     * 
     * @param vertices  
     *            a list of vertices
     */
    public void checkRep(List<Vertex<L>> vertices){
               
       assertFalse("there are duplicate vertices", this.vertices().size() < vertices.size()); // the vertcies() method returns a set :) 
                 
    }
    
   
    @Override public boolean add(L vertex) {
        
        if (this.containsVertex(vertex)){
            return false;
        } else {
            this.vertices.add(new Vertex<L>(vertex, new HashMap<L, Integer>(), new HashMap<L, Integer>()));
            return true;
        }
        
    }
    
    
    @Override public int set(L source, L target, int weight) {
        
     
      if (this.containsEdge(source,  target)){
          
          if(weight==0){
              
          } else {
              
          }
     
          return(this.getVertex(source).getWeight(source, target));
          
      } else {
          
          if(!this.containsVertex(source)){
              this.add(source);
          }
          
          if(!this.containsVertex(target)){
              this.add(target);
          }
          
          this.getVertex(source).addTarget(target, weight);
          this.getVertex(target).addSource(source, weight);
          
          return 0;
      }
      
   
    }
    
    @Override public boolean remove(L vertex) {
        
        if(this.containsVertex(vertex)){
            this.vertices.remove(this.which(vertex));
            return true;
        } else {
            return false;
        }
       
        
    }
    
    @Override public Set<L> vertices() {
        
        
       Set<L> labels = new HashSet<L>();
       
       Iterator<Vertex<L>> it = vertices.iterator();
       
       while (it.hasNext()){
           labels.add(it.next().getName());
       }
       
       return labels;
       
    }
    
    @Override public Map<L, Integer> sources(L target) {
        
        return this.getVertex(target).getSources();

    }
    
    @Override public Map<L, Integer> targets(L source) {

        return this.getVertex(source).getTargets();

    }
    
    /**
     * Returns a string representation of the Graph.   
     */
    @Override public String toString(){
        
        StringBuilder StringGraph = new StringBuilder();
        
        Iterator<Vertex<L>> it = this.vertices.iterator();

        while(it.hasNext()){
            
            Vertex<L> thatVertex = it.next();
            
            StringGraph.append(thatVertex.toString() + "\n");
            
        }
      
        return new String(StringGraph);
        
    }
    
    /**
     * Check if this Graph contains a given Edge (source, target)
     * 
     * @param source  
     *            String : the source of the edge
     *            
     * @param target 
     *            String : the target of the edge 
     *            
     * @return true if this Graph contains this edge.                      
     */
    public boolean containsEdge(L source, L target){
        
        boolean contains_source=false;
        boolean contains_target=false;
        boolean contains_edge=false;
        
        Iterator<Vertex<L>> it = vertices.iterator();

        while (it.hasNext()){
            
            Vertex<L> thisVertex = it.next().cloneVertex();
            
            if(thisVertex.getName().equals(source)){
                contains_source=true;
            }
            
            if(thisVertex.getName().equals(target)){
                contains_target=true;
            }
            
        }
        
        if(contains_source && contains_target){
            
                Vertex<L> sourceVertex = this.getVertex(source);
                
                if (sourceVertex.getTargets().containsKey(target)) {                   
                    contains_edge = true ; 
                }
                
        }
        
        return contains_edge ;
        
    }
    
   
    /**
     * Check if this Graph contains a given Edge (source, target)
     * 
     * @param vertex  
     *            String : the name of the vertex to search for        
     * @return true if this Graph contains this vertex.                      
     */
    public boolean containsVertex(L vertex){            
        return this.which(vertex)!=-1;     
    }
    
    /**
     * Get a given vertex from this Graph. 
     * 
     * @param name  
     *            String : the name of the vertex                       
     * @return the vertex if it is in the Graph, otherwise an empty vertex.                 
     */
    public Vertex<L> getVertex(L name){
        
                
        Iterator<Vertex<L>> it = vertices.iterator();

        while (it.hasNext()){
            
            Vertex<L> thisVertex = it.next();
            
            if(thisVertex.getName().equals(name)){
                return thisVertex;
            }
        }
        
        
        return new Vertex<L>(name, new HashMap<L, Integer>(), new HashMap<L, Integer>());
        
    }
    
    /**
     * Get the index of the vertex matching the passed name. 
     * 
     * @param name  
     *            String : the name of the vertex                       
     * @return integer : the index of the vertex in the List, -1 if it's not there.                 
     */
    public int which(L vertex){
        
                
        ListIterator<Vertex<L>> it = vertices.listIterator();

        while (it.hasNext()){
            
            int thisindex = it.nextIndex();
            Vertex<L> thisVertex = it.next();
            
            if(thisVertex.getName().equals(vertex)){
                return thisindex;
            }
        }
        
        return -1;
                
    }
    
}

/**
 * A mutable directed and weighted graph vertex, which is composed of three elements : 
 * the label of the Vertex,
 * the sources this vertex is associated with through the weights of each of these associations, 
 * the targets this vertex is associated with through the weights of each of these associations. 
 * 
 */
class Vertex<L> {
    
    private final L name;
    private final Map<L, Integer> sources;
    private final Map<L, Integer> targets;

    // Abstraction function:
    //   Represents the 'node' of a graph - a node is associated with edges, through which the node might be linked to a source or a target.
    //   each of this edge has a weight associated to it. 
    
    // Representation invariant:
    //   weights are non-negative 
    
    // Safety from rep exposure:
    //   sources and targets are immutable references
    //   observers return copies of the fields
    
    /**
     * Make a weighted directed graph vertex with its sources, targets assocations and their weights. 
     * 
     * @param sources
     *            a Map of String keys associated with an integer value.  
     * @param target
     *            a Map of String keys associated with an integer value. 
     */
    public Vertex(final L name, final Map<L, Integer> sources, final Map<L, Integer> targets) {
        this.name = name;
        this.sources = sources;
        this.targets = targets;
    }
    
    /**
     * check the representation invariant 
    */
    public void checkRep() {
    
        for (Integer value : this.sources.values()){
            assertTrue("expected weights to be strictly positive", value>0);                 
        }
        
        for (Integer value : this.targets.values()){
            assertTrue("expected weights to be strictly positive", value>0);                  
        }
        
    }

    /**
     * Get the label of this vertex
     * @return String a copy of the label of this vertex
     */
    public L getName(){
        return this.name;        
    }
    
    /**
     * Get the sources of this vertex. 
     * @return HashSet<String> : a copy of the sources of this vertex
     */
    public Map<L, Integer> getSources(){     
        return this.sources;        
    }

    /**
     * Get the targets of this vertex. 
     * @return HashSet<String> : a copy of the targets of this vertex
     */
    public Map<L, Integer> getTargets(){        
        return this.targets;        
    }
    

    /**
     * Get the weight of an edge
     * @param source
     * @param target
     * Get the weight associated with a source
     * @return Integer: the weight associated with the inferred edge, 0 if it doesn't exist
     */
    public int getWeight(L source, L target){
          
        if (source.equals(this.getName())){          
            if(this.targets.containsKey(target)){
                return(this.targets.get(target));                 
            } else {
                return 0;
            }
        } else {
            
            if (this.sources.containsKey(source)){
                return(this.sources.get(source));                
            } else {
                return 0;
            }
        } 

    }
    
    /**
     * Add a source to this vertex, with a weight.
     * @param source
     * @param weight
     */ 
    public void addSource(L source, int weight){
        this.sources.put(source, weight);
    }

    /**
     * Add a target to this vertex, with a weight.
     * @param target
     * @param weight
     */ 
    public void addTarget(L target, int weight){
        this.targets.put(target, weight);
    }
    
    /**
     * Copy constructor 
     * 
     * @param vertex
     *            a vertex     
     */
    public Vertex<L> cloneVertex() {
        
        Vertex<L> copyVertex = new Vertex<L>(this.name, this.sources, this.targets);

        return copyVertex;

    }
    
   
    /**
     * Returns a string representation of a Vertex.  
     * This representation is a string with two rows, one showing sources with weights, one showing targets with weights. 
     * 
     * @return a String, which is a human readable representation of the Vertex. 
     */
    @Override public String toString(){
        
        StringBuilder StringGraph = new StringBuilder();

        StringGraph.append("sources :" + "\n");


        for (L key : this.sources.keySet()){
            StringGraph.append(key + "--" + sources.get(key) + "\n");
        }

        StringGraph.append("\n\n\n");
        
        StringGraph.append("targets :" + "\n");

        for (L key : this.targets.keySet()){
            StringGraph.append(key + "--" + targets.get(key) + "\n");
        }
      
        return new String(StringGraph);
        
    }

    
}
