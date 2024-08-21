package Formiche;

public class Punto {
    private double x, y;
    private boolean toHome = true;
    private double intensity = GamePanel.panelWidth * GamePanel.panelHeight;

    public Punto(double x, double y, boolean toHome) {
        this.x = x;
        this.y = y;
        this.toHome = toHome;

        if (toHome) {
            intensity = Math.sqrt(Math.pow(x - GamePanel.xBase, 2) + Math.pow(GamePanel.yBase, 2));

        } else {

            for (int i = 0; i < GamePanel.food.size(); i++) {
                if (Math.sqrt(Math.pow(x - GamePanel.food.get(i).getX(), 2)
                        + Math.pow(y - GamePanel.food.get(i).getY(), 2)) < intensity) {
                    intensity = Math.sqrt(Math.pow(x - GamePanel.food.get(i).getX(), 2)
                            + Math.pow(y - GamePanel.food.get(i).getY(), 2));
                }
            }
        }
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getIntensity() {
        return intensity;
    }
}
