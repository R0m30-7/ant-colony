package Formiche;

import java.util.Random;

public class Formica {
    public Punto posizione = new Punto(0, 0, false);
    private int xGoal = 0, yGoal = 0; // ? L'obiettivo random che la formica deve raggiungere

    public static int antSearchRadius = 60 + GamePanel.antRadius; // ? In pixel, è il raggio entro cui la formica "vede"

    private boolean hasFood = false; // ? Determina se la formica sta trasportando del cibo

    private Punto posCiboRaccolto = new Punto(0, 0, false);

    Random rand = new Random();

    public Formica(Punto pos) {
        posizione.setX(pos.getX());
        posizione.setY(pos.getX());
    }

    public void GenerateNewGoal() {

        while (true) {
            xGoal = (int) posizione.getX() + rand.nextInt(GamePanel.antRadius * antSearchRadius * 2) - GamePanel.antRadius * antSearchRadius;

            if (xGoal + GamePanel.antRadius * 2 < GamePanel.panelWidth && xGoal > 0) {
                break;
            }
        }

        while (true) {
            yGoal = (int) posizione.getY() + rand.nextInt(GamePanel.antRadius * antSearchRadius * 2) - GamePanel.antRadius * antSearchRadius;

            if (yGoal + GamePanel.antRadius * 2 < GamePanel.panelHeight && yGoal > 0) {
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

    public Punto getPosCiboRaccolto() {
        return posCiboRaccolto;
    }

    public void setPosCiboRaccolto(Punto posCiboRaccolto) {
        this.posCiboRaccolto = posCiboRaccolto;
    }
}
