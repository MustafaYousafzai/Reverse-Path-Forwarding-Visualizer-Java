import java.util.*;

public class RPFRouter {
    private Graph graph;
    private Node source;

    public RPFRouter(Graph graph, Node source) {
        this.graph = graph;
        this.source = source;
    }

    public List<String> simulate() {
        List<String> logs = new ArrayList<>();
        Map<Node, Integer> shortestDistances = graph.dijkstra(source);
        Map<Node, Node> predecessors = graph.getPredecessors(source);

        logs.add("Shortest paths from source " + source.getId() + ":");
        for (Node target : graph.getNodes()) {
            if (target.equals(source)) continue;

            List<String> path = new ArrayList<>();
            for (Node at = target; at != null; at = predecessors.get(at)) {
                path.add(at.getId());
            }
            Collections.reverse(path);
            logs.add("To " + target.getId() + ": " + String.join(" → ", path));
        }
        logs.add(""); // separator

        for (Node current : graph.getNodes()) {
            if (current.equals(source)) {
                logs.add("Source " + source.getId() + " sends multicast.");
                continue;
            }

            for (Node neighbor : graph.getNeighbors(current)) {
                Edge incoming = graph.getEdge(current, neighbor);
                if (incoming == null) continue;

                int pathViaNeighbor = shortestDistances.get(neighbor) + incoming.getCost();
                if (pathViaNeighbor == shortestDistances.get(current)) {
                    logs.add("Node " + current.getId() + " accepts packet from " + neighbor.getId());
                } else {
                    logs.add("Node " + current.getId() + " drops packet from " + neighbor.getId());
                }
            }
        }

        return logs;
    }
}
