import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GraphView {
    private Graph graph = new Graph();
    private Canvas canvas = new Canvas(600, 400);
    private TextArea logArea = new TextArea();
    private ComboBox<String> sourceSelector = new ComboBox<>();

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0a666b;"); // Dark background

        VBox controls = new VBox(5);
        controls.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        TextField nodeId = new TextField();
        nodeId.setPromptText("Node ID");


        Button addNodeBtn = new Button("Add Node");
        addNodeBtn.setOnAction(e -> {
            Node node = new Node(nodeId.getText(), Math.random() * 500 + 50, Math.random() * 300 + 50);
            graph.addNode(node);
            sourceSelector.getItems().add(node.getId());
            drawGraph();
        });

        TextField fromField = new TextField();
        fromField.setPromptText("From");

        TextField toField = new TextField();
        toField.setPromptText("To");

        TextField costField = new TextField();
        costField.setPromptText("Cost");

        Button addEdgeBtn = new Button("Add Edge");
        addEdgeBtn.setOnAction(e -> {
            Node from = findNode(fromField.getText());
            Node to = findNode(toField.getText());
            int cost = Integer.parseInt(costField.getText());
            if (from != null && to != null) {
                graph.addEdge(new Edge(from, to, cost));
                drawGraph();
            }

        });

        Button simulateBtn = new Button("Simulate RPF");
        simulateBtn.setOnAction(e -> {
            Node source = findNode(sourceSelector.getValue());
            if (source != null) {
                RPFRouter router = new RPFRouter(graph, source);
                logArea.clear();
                for (String line : router.simulate()) {
                    logArea.appendText(line + "\n");
                }
            }
        });
        TextField removeNodeField = new TextField();
        removeNodeField.setPromptText("Node ID to Remove");

        Button removeNodeBtn = new Button("Remove Node");
        removeNodeBtn.setOnAction(e -> {
            Node node = findNode(removeNodeField.getText());
            if (node != null) {
                graph.removeNode(node);
                sourceSelector.getItems().remove(node.getId());
                drawGraph();
            }
        });

        TextField removeEdgeFrom = new TextField();
        removeEdgeFrom.setPromptText("From (Remove)");

        TextField removeEdgeTo = new TextField();
        removeEdgeTo.setPromptText("To (Remove)");

        Button removeEdgeBtn = new Button("Remove Edge");
        removeEdgeBtn.setOnAction(e -> {
            Node from = findNode(removeEdgeFrom.getText());
            Node to = findNode(removeEdgeTo.getText());
            if (from != null && to != null) {
                graph.removeEdge(from, to);
                drawGraph();
            }
        });
        // CSS for the buttton styles
        String buttonStyle = "-fx-background-color: #9e0979; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 6 12;";

        addNodeBtn.setStyle(buttonStyle);
        addEdgeBtn.setStyle(buttonStyle);
        simulateBtn.setStyle(buttonStyle);


//        CSS for remove buttons
        String removeButtonStyle = "-fx-background-color: #d32f2f; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 6 12;";
        removeEdgeBtn.setStyle(removeButtonStyle);
        removeNodeBtn.setStyle(removeButtonStyle);


        sourceSelector.setOnAction(e -> {
            String selectedId = sourceSelector.getValue();
            for (Node node : graph.getNodes()) {
                node.setColor(Color.LIGHTBLUE);
            }
            Node source = findNode(selectedId);
            if (source != null) {
                source.setColor(Color.LIGHTGREEN);
            }
            drawGraph();
        });

        controls.getChildren().addAll(
                new Label("Add Node:"), nodeId, addNodeBtn,
                new Label("Add Edge:"), fromField, toField, costField, addEdgeBtn,
                new Label("Source Node:"), sourceSelector, simulateBtn,
                new Label("Remove Node:"), removeNodeField, removeNodeBtn,
                new Label("Remove Edge:"), removeEdgeFrom, removeEdgeTo, removeEdgeBtn
        );


        root.setLeft(controls);
        root.setCenter(canvas);
        root.setBottom(logArea);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Reverse Path Forwarding Simulator");
        stage.show();
    }

    private void drawGraph() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw edges
        gc.setStroke(Color.rgb(245, 66, 99));
        gc.setLineWidth(2);
        for (Edge edge : graph.getEdges()) {
            double x1 = edge.getFrom().getX();
            double y1 = edge.getFrom().getY();
            double x2 = edge.getTo().getX();
            double y2 = edge.getTo().getY();
            gc.strokeLine(x1, y1, x2, y2);

            // Optional: show cost in middle
            double midX = (x1 + x2) / 2;
            double midY = (y1 + y2) / 2;
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(edge.getCost()), midX, midY);
        }

        // Draw nodes
        for (Node node : graph.getNodes()) {
            gc.setFill(node.getColor());
            gc.fillOval(node.getX() - 15, node.getY() - 15, 30, 30);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(node.getX() - 15, node.getY() - 15, 30, 30);
            gc.setFill(Color.BLACK);
            gc.fillText(node.getId(), node.getX() - 5, node.getY() + 5);
        }
    }

    private Node findNode(String id) {
        for (Node node : graph.getNodes()) {
            if (node.getId().equals(id)) return node;
        }
        return null;
    }
}
