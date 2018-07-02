package graph;


import java.util.*;


/** Class representing a social network as a graph.
 *
 * @author davidrpugh
 */
public class CapGraph implements Graph {

    /** Underlying collection used to store the vertices and edges. */
    private HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();

    /** Add a user to the social network as a vertex in the graph.
     *
     * Adding a user to the social network is a constant time O(1) operation.
     * @param num the unique integer identifying the user.
     */
    @Override
    public void addVertex(int num) {
        graph.putIfAbsent(num, new HashSet<>());
    }

    /** Add a directed edge between two users in the social network.
     *
     * Adding a directed edge to the social network is a constant time O(1) operation.
     * @param from the unique integer identifying the user from whom the directed edge should be added.
     * @param to the unique integer identifying the user to whom the directed edge should be added.
     */
    @Override
    public void addEdge(int from, int to) {
        HashSet<Integer> neighbors = graph.getOrDefault(from, new HashSet<>());
        neighbors.add(to);
        graph.put(from, neighbors);
    }

    public Set<Edge> getEdges() {
        HashSet<Edge> edges = new HashSet<>();
        for (int v : getVertices()) {
            for (int neighbor : getNeighbors(v)) {
                edges.add(new Edge(v, neighbor));
            }
        }
        return edges;
    }

    public Set<Integer> getVertices() {
        return graph.keySet();
    }

    public Set<Integer> getNeighbors(int num) {
        return graph.getOrDefault(num, new HashSet<>());
    }

    @Override
    public CapGraph getEgonet(int center) {
        CapGraph egoNet = new CapGraph();
        HashSet<Integer> neighbors = graph.getOrDefault(center, new HashSet<>());
        if (!neighbors.isEmpty()) {
          for (int neighbor : neighbors) {
              egoNet.addVertex(neighbor);
              egoNet.addEdge(center, neighbor);

              HashSet<Integer> neighborNeighbors = graph.get(neighbor);
              for (int neighborNeighbor : neighborNeighbors) {
                  if (neighbors.contains(neighborNeighbor)) {
                      egoNet.addEdge(neighborNeighbor, neighbor);
                  }
              }
          }
        }
        return egoNet;
    }

    @Override
    public List<Graph> getSCCs() {
       TarjanSCCAlgorithm algo = new TarjanSCCAlgorithm(this);
       List<Graph> sccs = new LinkedList<>();
       for (Set<Integer> vertices : algo.getStronglyConnectedComponents()) {
           CapGraph scc = new CapGraph();
           for (int v : vertices) {
               scc.addVertex(v);
           }
           sccs.add(scc);
       }
       return sccs;
    }

    private void visit(HashSet<Integer> visited, LinkedList<Integer> path, int u) {
        if (!visited.contains(u)) {
            visited.add(u);
            for (int v : getNeighbors(u)) {
                visit(visited, path, v);
            }
            path.addFirst(u);
        }
    }

    private void assign(HashSet<Integer> assigned, HashMap<Integer, HashSet<Integer>> components, CapGraph transposedGraph, int u, int r) {
        if (!assigned.contains(u)) {
            assigned.add(u);
            HashSet<Integer> existingVertices = components.getOrDefault(r, new HashSet<>());
            existingVertices.add(u);
            components.put(r, existingVertices);

            for (int v : transposedGraph.getNeighbors(u)) {
                assign(assigned, components, transposedGraph, v, r);
            }
        }
    }

    /* Returns the transpose of the `CapGraph` graph. */
    public CapGraph getTranspose() {
        CapGraph transposed = new CapGraph();
        Set<Integer> vertices = getVertices();

        for (int vertex : vertices) {
            transposed.addVertex(vertex);
            Set<Integer> neighbors = getNeighbors(vertex);
            if (!neighbors.isEmpty()){
                for (int neighbor : neighbors) {
                    transposed.addEdge(neighbor, vertex);
                }
            }
        }

        return transposed;
    }

    @Override
    public HashMap<Integer, HashSet<Integer>> exportGraph() {
        return graph;
    }

    class Edge {

        private int from;
        private int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

    }

}
