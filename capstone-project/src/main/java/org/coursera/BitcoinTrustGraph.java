package org.coursera;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class BitcoinTrustGraph {

    private HashSet<Edge> edges;
    private HashMap<Integer, Integer> inDegrees;
    private HashMap<Integer, Integer> weightedInDegrees;

    BitcoinTrustGraph() {
       this.edges = new HashSet<>();
       this.inDegrees = new HashMap<>();
       this.weightedInDegrees = new HashMap<>();
    }

    public void addEdge(Edge edge) {
        inDegrees.merge(edge.getTargetId(), 1, Integer::sum);
        weightedInDegrees.merge(edge.getTargetId(), edge.getRating(), Integer::sum);
        edges.add(edge);
    }

    public double getTrustworthiness(int nodeId) {
        double totalRatings = weightedInDegrees.getOrDefault(nodeId, 0);
        int numberRaters = inDegrees.getOrDefault(nodeId, 0);
        return (numberRaters == 0) ? 0 : totalRatings / numberRaters;
    }

    public Set<Integer> getNodes() {
        return inDegrees.keySet();
    }

    private LinkedList<Integer> shortestPath(Integer initialNodeId) {
        return null;
    }

}
