package Formiche;

import java.util.Random;

public class Formica {
    public Punto posizione = new Punto(0, 0, false);
    private int xGoal = 0, yGoal = 0; // ? L'obiettivo random che la formica deve raggiungere

    public static int antSearchRadius = 60 + GamePanel.dotDiameter; // ? In pixel, è il raggio entro cui la formica "vede"

    private boolean hasFood = false; // ? Determina se la formica sta trasportando del cibo

    Random rand = new Random();

    public Formica(Punto pos) {
        posizione.setX(pos.getX());
        posizione.setY(pos.getY());
    }

    public void GenerateNewGoal() {

        while (true) {
            xGoal = (int) posizione.getX() + (rand.nextInt(GamePanel.dotDiameter * antSearchRadius * 2) - GamePanel.dotDiameter * antSearchRadius) / 2;

            if (xGoal + GamePanel.dotDiameter * 2 < GamePanel.panelWidth && xGoal > 0) {
                break;
            }
        }

        while (true) {
            yGoal = (int) posizione.getY() + (rand.nextInt(GamePanel.dotDiameter * antSearchRadius * 2) - GamePanel.dotDiameter * antSearchRadius) / 2;

            if (yGoal + GamePanel.dotDiameter * 2 < GamePanel.panelHeight && yGoal > 0) {
                break;
            }
        }
    }

    public void SetHasFood(boolean bool) {
        hasFood = bool;
    }

    public void AddToX(double speed) {
        posizione.setX(posizione.getX() + speed / Game.getFPSGoal());
    }

    public void AddToY(double speed) {
        posizione.setY(posizione.getY() + speed / Game.getFPSGoal());
    }

    public int getX() {
        return (int) posizione.getX();
    }

    public int getY() {
        return (int) posizione.getY();
    }

    public boolean getHasFood() {
        return hasFood;
    }

    public int getXGoal() {
        return xGoal;
    }

    public int getYGoal() {
        return yGoal;
    }

    public void setxGoal(double xGoal) {
        this.xGoal = (int) xGoal;
    }

    public void setyGoal(double yGoal) {
        this.yGoal = (int) yGoal;
    }
}
