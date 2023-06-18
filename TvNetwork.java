import java.util.*;
class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}

class TVNetworkOptimization {
    int stations;
    List<Edge> edges;

    public TVNetworkOptimization(int stations) 
    {
        this.stations = stations;
        edges = new ArrayList<>();
    }

    public void addEdge(int source, int destination, int weight) 
    {
        Edge edge = new Edge();
        edge.source = source;
        edge.destination = destination;
        edge.weight = weight;
        edges.add(edge);
    }

    public List<Edge> optimizeNetwork() {
        List<Edge> minimumSpanningTree = new ArrayList<>();
        Collections.sort(edges);

        int[] parent = new int[stations];
        for (int i = 0; i < stations; i++) {
            parent[i] = i;
        }

        int count = 0;
        int index = 0;
        while (count < stations - 1) {
            Edge edge = edges.get(index);
            int sourceParent = findParent(parent, edge.source);
            int destinationParent = findParent(parent, edge.destination);

            if (sourceParent != destinationParent) {
                minimumSpanningTree.add(edge);
                union(parent, sourceParent, destinationParent);
                count++;
            }

            index++;
        }

        return minimumSpanningTree;
    }

    private int findParent(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = findParent(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    private void union(int[] parent, int source, int destination) {
        int sourceParent = findParent(parent, source);
        int destinationParent = findParent(parent, destination);
        parent[destinationParent] = sourceParent;
    }
}

public class project {
    public static void main(String[] args) {
        TVNetworkOptimization network = new TVNetworkOptimization(6);
        network.addEdge(0, 1, 4);
        network.addEdge(0, 2, 3);
        network.addEdge(1, 2, 1);
        network.addEdge(1, 3, 2);
        network.addEdge(2, 3, 4);
        network.addEdge(3, 4, 2);
        network.addEdge(4, 5, 6);

        List<Edge> minimumSpanningTree = network.optimizeNetwork();

        System.out.println("Optimized network connections:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge.source + " -- " + edge.destination + " : " + edge.weight);
        }
    }
}

