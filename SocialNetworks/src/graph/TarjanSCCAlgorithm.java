package graph;

import java.util.*;


public class TarjanSCCAlgorithm {

    private HashMap<Vertex, HashSet<Vertex>> graph;
    private int index;
    private Stack<Vertex> S = new Stack<>();
    private List<Set<Integer>> sccs = new LinkedList<>();


    TarjanSCCAlgorithm(CapGraph graph) {
        this.graph = new HashMap<>();
        HashMap<Integer, Vertex> valuesToVertices = new HashMap<>();
        for (int i : graph.getVertices()) {
            valuesToVertices.put(i, new Vertex(i));
        }

        for (int i : graph.getVertices()) {
            HashSet<Vertex> vertices = new HashSet<>();
            Set<Integer> neighbors = graph.getNeighbors(i);
            for (int neighbor : neighbors) {
                vertices.add(valuesToVertices.get(neighbor));
            }
            Vertex v = valuesToVertices.get(i);
            this.graph.put(v, vertices);
        }
        this.index = 0;
    }

    public List<Set<Integer>> getStronglyConnectedComponents() {

        for (Vertex vertex : graph.keySet()) {
            if (vertex.getIndex() == -1) {
                findStronglyConnectedComponent(vertex);
            }
        }
        return sccs;
    }

    private void findStronglyConnectedComponent(Vertex v) {

        // Set the depth index for v to the smallest unused index
        v.setIndex(index);
        v.setLowLink(index);
        index++;

        S.push(v);
        v.setOnStack(true);

        for (Vertex w : graph.get(v)) {
            if (w.getIndex() == -1) {
                findStronglyConnectedComponent(w);
                v.setLowLink(Math.min(v.getLowLink(), w.getLowLink()));
            } else if (w.getOnStack()) {
                v.setLowLink(Math.min(v.getLowLink(), w.getIndex()));
            }
        }

        // If v is a root node, pop the stack and generate an SCC
        if (v.getLowLink() == v.getIndex()) {
            HashSet<Integer> scc = new HashSet<>();
            Vertex w;
            do {
                w = S.pop();
                w.setOnStack(false);
                scc.add(w.getValue());
            } while (!w.equals(v));
            sccs.add(scc);
        }
    }

    private class Vertex {
        private int index = -1;
        private int lowLink = -1;
        private boolean onStack = false;
        private int value;

        Vertex(int value) {
            this.value = value;
        }

        int getIndex() {
            return index;
        }

        void setIndex(int index) {
            this.index = index;
        }

        int getLowLink() {
            return lowLink;
        }

        void setLowLink(int lowLink) {
            this.lowLink = lowLink;
        }

        void setOnStack(boolean onStack) {
            this.onStack = onStack;
        }

        boolean getOnStack() { return onStack; }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Vertex) {
                return hashCode() == obj.hashCode();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public String toString() {
            return "Vertex(value: " + Integer.toString(getValue()) + ", index: " + Integer.toString(getIndex()) + ", lowLink: " + Integer.toString(getLowLink()) + ")";
        }

    }

}
