import javafx.scene.paint.Color;

public class Node {
    private String id;
    private double x, y;
    private Color color;  // New field

    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = Color.LIGHTBLUE;  // Default color
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // New getter and setter for color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
