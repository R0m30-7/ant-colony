package Formiche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Random rand = new Random();

    public static int panelWidth = 1300; // ?Larghezza schermo
    public static int panelHeight = 650; // ?Altezza schermo

    public static int xBase = 200, yBase = 200; // ? Coordinate dell'uscita del formicaio

    double antSpeed = 40; // ? Velocità della formica misurata in pixel al secondo

    static int antRadius = 7; // ? Il raggio del cerchio che rappresenta la formica, in pixel

    private int maxAnts = 20; // ? Numero massimo di formiche presenti sullo schermo
    private int maxDots = 150; // ? Numero massimo di pallini per ogni lista

    // ? Queste due righe servono per avere le coordinate del mouse
    private int mouseX = 0;
    private int mouseY = 0;

    private int randXAdder = 0; // ? Con queste due variabili aggiungo un valore casuale compreso
    private int randYAdder = 0; // ? tra -5 e 5 alle coordinate del punto da seguire

    int cicli = 0;

    Color brown = new Color(150, 75, 0);

    List<Formica> Formiche = new ArrayList<>(); // ? Questo è l'array che mi contiene tutte le formiche
    List<Punto> toCasa = new ArrayList<>(); // ? Contiene tutti i punti che indicano il formicaio
    List<Punto> toCibo = new ArrayList<>(); // ? Contiene tutti i punti che indicano il cibo
    public static Set<Punto> food = new HashSet<>(); // ? Contiene il cibo, ma è un set
    public static List<Punto> listFood = new ArrayList<>();   //? Contiene il cibo, ma è una lista

    Formica ant = new Formica(new Punto(0, 0, false)); // ? Una formica in generale

    public GamePanel() {
        setPanelSize();

        this.setBackground(Color.DARK_GRAY);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(panelWidth, panelHeight);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) { // Scrivo in questo void le cose che voglio disegnare
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        g.fillOval(xBase, yBase, 4, 4); // ? In queste due righe disegno l'uscita del formicaio

        for (int i = 0; i < Formiche.size(); i++) { // ? Itero ogni formica sullo schermo
            ant = Formiche.get(i);

            MoveAnt(ant); // ? Muovo la formica, o a caso, o se ha il cibo verso il prossimo pallino che
                          // ? indica l'ingresso del formicaio

            DrawDots(g, toCasa, toCibo);
            DrawAnt(g, ant); // ? Disegno la formica
        }

        // ? Disegno un cerchio bianco intorno al mouse
        g.setColor(Color.WHITE);
        mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc();
        mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc();
        g.drawOval(mouseX - MouseInput.getMouseCircleRadius()/2, mouseY - MouseInput.getMouseCircleRadius()/2, MouseInput.getMouseCircleRadius(), MouseInput.getMouseCircleRadius());

        //? Disegno il cibo
        g.setColor(Color.GREEN);
        //? Per ottenere un certo elemento a un certo indice devo convertire il set in una lista
        listFood = new ArrayList<>(food);
        for (int i = 0; i < listFood.size(); i++) {
            g.fillOval((int) listFood.get(i).getX(), (int) listFood.get(i).getY(), antRadius, antRadius);
        }

        if (cicli == Game.getFPSGoal()) { // ? Entro in questo if una volta al secondo

            randXAdder = rand.nextInt(10) - 5;
            randYAdder = rand.nextInt(10) - 5;

            for (int i = 0; i < Formiche.size(); i++) { // ? Itero per ogni formica
                ant = Formiche.get(i);

                if (ant.getX() == xBase && ant.getY() == yBase && ant.getHasFood()) {
                    Formiche.remove(ant); // ? Rimuovo la formica se si trova sul formicaio con del cibo
                    // GenerateFood(); // ? Genero del cibo in posizione randomica
                }

                for (int j = 0; j < food.size(); j++) { // ? Se la formica si trova sul cibo, lo raccoglie
                    if (ant.getX() == listFood.get(j).getX() && ant.getY() == listFood.get(j).getY()) {
                        ant.SetHasFood(true);
                        // ! Qui decido se avere cibo infinito o meno
                        food.remove(ant.posizione);
                    }
                }

                if (ant.getHasFood()) { // ? Genero i punti verso il cibo o verso la casa

                    // TODO Devo fare in modo che tutti i punti siano unici (non più di un punto
                    // TODO sulle stesse coordinate)
                    toCibo.add(new Punto(ant.getX(), ant.getY(), false)); // ? Aggiungo un nuovo
                    // ? punto che punta al cibo
                    if (toCibo.size() > maxDots) { // ? Se ci sono troppi punti, ne rimuovo alcuni
                        toCibo.remove(0);
                    }
                } else {
                    toCasa.add(new Punto(ant.getX(), ant.getY(), true));
                    if (toCasa.size() > maxDots) {
                        toCasa.remove(0);
                    }
                }
            }
            if (Formiche.size() < maxAnts) { // ? Genero una nuova formica
                spawnFormica(xBase, yBase);
            }
            cicli = 0;
        }

        cicli++;
    }

    private void spawnFormica(int x, int y) { // ? Spawno una nuova formica
        ant = new Formica(new Punto(xBase, yBase, false));
        ant.GenerateNewGoal();
        Formiche.add(ant);
    }

    private void DrawAnt(Graphics g, Formica ant) {
        if (ant.getHasFood()) { // ? Il colore della formica senza cibo è nero, con il cibo è marrone
            g.setColor(brown);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(ant.getX() - antRadius, ant.getY() - antRadius, antRadius * 2, antRadius * 2);
        /*
         * g.setColor(Color.WHITE);
         * g.drawOval(ant.getX() - Formica.antSearchRadius, ant.getY() -
         * Formica.antSearchRadius,
         * Formica.antSearchRadius * 2, Formica.antSearchRadius * 2);
         */
    }

    private void MoveAnt(Formica ant) {

        if (ant.getXGoal() == ant.getX() && ant.getYGoal() == ant.getY()) { // ? Se ho raggiunto l'obiettivo, ne genero
                                                                            // ? uno nuovo
            ant.GenerateNewGoal();
        }
        if (ant.getHasFood()) { // ? Se la formica sta trasportando del cibo
            MoveToHome();

        } else {
            MoveToFood();
        }

        if (ant.getXGoal() > ant.getX()) { // ? Questi due if muovono la formica
            ant.AddToX(antSpeed);
        } else if (ant.getXGoal() < ant.getX()) {
            ant.AddToX(-antSpeed);
        }

        if (ant.getYGoal() > ant.getY()) {
            ant.AddToY(antSpeed);
        } else if (ant.getYGoal() < ant.getY()) {
            ant.AddToY(-antSpeed);
        }
    }

    private void MoveToHome() {
        if (DistanzaFra(ant.posizione, new Punto(xBase, yBase, false)) < Formica.antSearchRadius) { // ? Se la distanza
                                                                                                    // fra la formica e
                                                                                                    // l'ingresso del
                                                                                                    // formicaio è
                                                                                                    // all'interno del
                                                                                                    // suo
                                                                                                    // raggio di ricerca
            ant.setxGoal(xBase);
            ant.setyGoal(yBase);
        } else {
            FindBestToHomeDot(ant); // ? Trovo il punto per casa più vicino alla casa
        }
    }

    private void MoveToFood() {

        for (int i = 0; i < food.size(); i++) { // ? Controllo se nel raggio di ricerca della formica si trova del cibo
            if (DistanzaFra(ant.posizione, listFood.get(i)) < Formica.antSearchRadius) {
                ant.setxGoal(listFood.get(i).getX());
                ant.setyGoal(listFood.get(i).getY());
                break;
            } else if (i == food.size() - 1) {
                FindClosestFood(ant); // ? Trovo il punto per cibo più vicino alla formica
            }
        }
    }

    private void FindClosestFood(Formica ant) {
        int x = panelWidth, y = panelHeight;
        int index = 0;
        double min = panelWidth * panelHeight;

        for (int i = 0; i < toCibo.size(); i++) {
            if (DistanzaFra(ant.posizione, toCibo.get(i)) < Formica.antSearchRadius) {
                if (toCibo.get(i).getIntensity() < min) {
                    min = toCibo.get(i).getIntensity();
                    x = (int) toCibo.get(i).getX();
                    y = (int) toCibo.get(i).getY();
                    index = i;
                }
            }
        }

        if (x != panelWidth && y != panelHeight) {
            if (DistanzaFra(ant.posizione, new Punto(x, y, false)) <= 5) {
                toCibo.remove(index); // Stesso discorso per quanto riguarda FindBestToHomeDot() riguardo la rimozione
                                      // dei punti invece di farli evitare
            }
            ant.setxGoal(x + randXAdder); // ? Con questi due rand aggiungo un po' di
            ant.setyGoal(y + randYAdder); // ? randomicità nel movimento delle formiche
        }
    }

    private void FindBestToHomeDot(Formica ant) {
        int x = panelWidth, y = panelHeight;
        double min = panelWidth * panelHeight;
        int index = 0;
        Punto base = new Punto(xBase, yBase, true);

        for (int i = 0; i < toCasa.size(); i++) {
            if (DistanzaFra(ant.posizione, toCasa.get(i)) < Formica.antSearchRadius) {
                if (toCasa.get(i).getIntensity() < min) {
                    if (DistanzaFra(base, toCasa.get(i)) < DistanzaFra(base, ant.posizione)) {
                        min = toCasa.get(i).getIntensity();
                        x = (int) toCasa.get(i).getX();
                        y = (int) toCasa.get(i).getY();
                        index = i;
                    }
                }
            }
        }

        if (x != panelWidth && y != panelHeight) {
            if (DistanzaFra(ant.posizione, new Punto(x, y, false)) <= 5) { // TODO Meglio l'if per non considerare punti
                                                                           // già utilizzati
                toCasa.remove(index); // Io preferirei non eliminare il punto che punta a casa ma semplicemente
            } // farlo ignorare a questa formica, ma non so come fare senza usare un'altra
              // lista
            ant.setxGoal(x + randXAdder); // ? Con questi due rand aggiungo un po' di
            ant.setyGoal(y + randYAdder); // ? randomicità nel movimento delle formiche
        }
    }

    private void DrawDots(Graphics g, List<Punto> toCasa, List<Punto> toCibo) {
        g.setColor(Color.RED);
        for (int i = 0; i < toCasa.size(); i++) {
            g.fillOval((int) toCasa.get(i).getX(), (int) toCasa.get(i).getY(), antRadius, antRadius);
        }

        g.setColor(Color.BLUE);
        if (food.size() == 0 && toCibo.size() != 0) { // ? Se non c'è cibo e ci sono punti blu, azzero i punti blu
            toCibo.clear();
        }
        for (int i = 0; i < toCibo.size(); i++) {
            g.fillOval((int) toCibo.get(i).getX(), (int) toCibo.get(i).getY(), antRadius, antRadius);
        }
    }

    private double DistanzaFra(Punto a, Punto b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}