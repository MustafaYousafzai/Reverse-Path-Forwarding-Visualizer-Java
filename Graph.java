import java.util.*;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Map<Node, Integer> dijkstra(Node source) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Boolean> visited = new HashMap<>();
        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
            visited.put(node, false);
        }
        distances.put(source, 0);

        for (int i = 0; i < nodes.size(); i++) {
            Node u = getMinDistanceNode(distances, visited);
            if (u == null) break;
            visited.put(u, true);

            for (Edge edge : edges) {
                if (edge.getFrom().equals(u)) {
                    Node v = edge.getTo();
                    if (!visited.get(v) && distances.get(u) + edge.getCost() < distances.get(v)) {
                        distances.put(v, distances.get(u) + edge.getCost());
                    }
                }
            }
        }

        return distances;
    }

    private Node getMinDistanceNode(Map<Node, Integer> distances, Map<Node, Boolean> visited) {
        int min = Integer.MAX_VALUE;
        Node minNode = null;
        for (Node node : distances.keySet()) {
            if (!visited.get(node) && distances.get(node) <= min) {
                min = distances.get(node);
                minNode = node;
            }
        }
        return minNode;
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(node)) {
                neighbors.add(edge.getTo());
            }
        }
        return neighbors;
    }

    public Edge getEdge(Node from, Node to) {
        for (Edge edge : edges) {
            if (edge.getFrom().equals(from) && edge.getTo().equals(to)) {
                return edge;
            }
        }
        return null;
    }
    public List<Edge> getEdgesFrom(Node node) {
        List<Edge> result = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(node)) {
                result.add(edge);
            }
        }
        return result;
    }

    public Map<Node, Node> getPredecessors(Node source) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Node node : getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Edge edge : getEdgesFrom(current)) {
                Node neighbor = edge.getTo();
                int newDist = distances.get(current) + edge.getCost();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, current);
                    queue.remove(neighbor); // update priority
                    queue.add(neighbor);
                }
            }
        }

        return predecessors;
    }

//    Method to remove node
    public void removeNode(Node node) {
        edges.removeIf(e -> e.getFrom().equals(node) || e.getTo().equals(node));
        nodes.remove(node);
    }

//    Method to remove Edge
    public void removeEdge(Node from, Node to) {
        edges.removeIf(e -> (e.getFrom().equals(from) && e.getTo().equals(to)) ||
                (e.getFrom().equals(to) && e.getTo().equals(from)));
    }


}
