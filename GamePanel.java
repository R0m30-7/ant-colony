package Formiche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Random rand = new Random();

    protected static int panelWidth = 1920; // ?Larghezza schermo
    protected static int panelHeight = 1080; // ?Altezza schermo

    protected static int xBase = 960, yBase = 540; // ? Coordinate dell'uscita del formicaio

    final double antSpeed = 40; // ? Velocità della formica misurata in pixel al secondo
    final double antDimenMult = 0.75;  //? Il moltiplicatore dell'immagine della formica, originariamente è 64x64, che trasformando diventa antDimenMult * 64

    protected static final int maxFood = 8000;
    private int foodCollected = 0;
    private final int mult = 7;   //? Serve nella parte dove spawno il cibo randomicamente
    private final int maxTimeToSpawnFood = 7000; //? Il tempo che intercorre tra lo spawn del cibo
    private int timeToSpawnFood = maxTimeToSpawnFood;   //? Serve nella parte dove spawno il cibo randomicamente, è la variabile che diminuisce

    static final int dotDiameter = 7; // ? Il raggio del cerchio che rappresenta la formica, in pixel

    private final int maxAnts = 35; // ? Numero massimo di formiche presenti sullo schermo
    private final int maxDots = 400; // ? Numero massimo di pallini per ogni lista

    BufferedImage antWithFood = null;
    BufferedImage antWithOutFood = null;
    private static boolean loaded = false;  //? Tengo conto se ho già caricato le immagini

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
    protected static List<Punto> food = new ArrayList<>(); // ? Contiene il cibo

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
        Graphics2D g2d = (Graphics2D) g;

        if(!loaded){
            LoadImages(g);
        }

        //? Genero del cibo randomicamente
        if(rand.nextInt(timeToSpawnFood) == 0 && food.size() < maxFood){   // Con 18001 spawna il cibo ogni 5 minuti in media
            timeToSpawnFood = maxTimeToSpawnFood;
            MouseInput.xSpawn = rand.nextInt(panelWidth + 1);
            MouseInput.ySpawn = rand.nextInt(panelHeight + 1);
            for(int i = 0; i < mult; i++){
                MouseInput.SpawnFood(mult - i, false);
            }
        } else{
            timeToSpawnFood--;
        }

        DrawDots(g, toCasa, toCibo);    //? Disegno i punti verso casa e verso il cibo
        
        //? Disegno il cibo
        g.setColor(Color.GREEN);
        for (int i = 0; i < food.size(); i++) {
            g.fillOval((int) food.get(i).getX(), (int) food.get(i).getY(), dotDiameter, dotDiameter);
        }

        for (int i = 0; i < Formiche.size(); i++) { // ? Itero ogni formica sullo schermo
            ant = Formiche.get(i);
            
            MoveAnt(ant); // ? Muovo la formica, o a caso, o se ha il cibo verso il prossimo pallino che
            // ? indica l'ingresso del formicaio
            DrawAnt(ant, g2d); // ? Disegno la formica
        }
        
        g.setColor(Color.YELLOW);
        g.fillOval(xBase, yBase, 4, 4); // ? In queste due righe disegno l'uscita del formicaio
        
        // ? Disegno un cerchio bianco intorno al mouse
        g.setColor(Color.WHITE);
        mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc();
        mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc();
        g.drawOval(mouseX - MouseInput.getMouseCircleRadius()/2, mouseY - MouseInput.getMouseCircleRadius()/2, MouseInput.getMouseCircleRadius(), MouseInput.getMouseCircleRadius());
        
        //? Disegno le scritte
        WriteTextOnScreen(g);

        if (cicli == Game.getFPSGoal()) { // ? Entro in questo if una volta al secondo
            panelWidth = (int) GameWindow.getjFrameSize().getWidth() - 16;
            panelHeight = (int) GameWindow.getjFrameSize().getHeight() - 39;

            randXAdder = rand.nextInt(10) - 5;
            randYAdder = rand.nextInt(10) - 5;

            for (int i = 0; i < Formiche.size(); i++) { // ? Itero per ogni formica
                ant = Formiche.get(i);
                //? Controllo se la formica è al formicaio e se ha del cibo
                if (ant.getX() >= xBase - 3 && ant.getX() <= xBase + 3 && ant.getY() >= yBase - 3 && ant.getY() <= yBase + 3 && ant.getHasFood()) {
                    Formiche.remove(ant); // ? Rimuovo la formica se si trova sul formicaio con del cibo
                    foodCollected++;
                }

                for (int j = 0; j < food.size(); j++) { // ? Se la formica si trova sul cibo, lo raccoglie
                    if (ant.getX() >= food.get(j).getX() - 3 && ant.getX() <= food.get(j).getX() + 3 && ant.getY() >= food.get(j).getY() - 3 && ant.getY() <= food.get(j).getY() + 3 && !ant.getHasFood()) {
                        ant.SetHasFood(true);
                        // ! Qui decido se avere cibo infinito o meno
                        food.remove(j);
                    }
                }

                if (ant.getHasFood()) { // ? Genero i punti verso il cibo o verso la casa

                    // TODO Devo fare in modo che tutti i punti siano unici (non più di un punto sulle stesse coordinate)
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

    private void DrawAnt(Formica ant, Graphics2D g2d) {
        double theta = 0;
        AffineTransform transform = new AffineTransform();

        //System.out.println("Old x: " + ant.getLastPos().getX() + ", y: " + ant.getLastPos().getY() + "\nx: " + ant.getX() + ", y: " + ant.getY());
        theta = Math.atan2(ant.getYGoal() - ant.getY(), ant.getXGoal() - ant.getX()) + Math.toRadians(90);  //? Punta direttamente al goal
        // TODO theta = Math.atan2(ant.getLastPos().getY() - ant.getY(), ant.getLastPos().getX() - ant.getX()) + Math.toRadians(90); //? Utilizza l'ultima posizione per calcolare la direzione
        //! Servono per debug
        g2d.setColor(Color.WHITE);
        //g2d.drawString(String.valueOf(Math.toDegrees(theta)), ant.getX(), ant.getY() - 5);
        g2d.fillOval(ant.getXGoal() - dotDiameter / 2, ant.getYGoal() - dotDiameter / 2, dotDiameter, dotDiameter);

        transform.translate(ant.getX() - antWithFood.getWidth() * antDimenMult / 2, ant.getY() - antWithFood.getHeight() * antDimenMult / 2);
        transform.scale(antDimenMult, antDimenMult);
        transform.rotate(theta, antWithFood.getWidth() / 2, antWithFood.getHeight() / 2);

        if (ant.getHasFood()) {
            g2d.drawImage(antWithFood, transform, null);
        } else {
            g2d.drawImage(antWithOutFood, transform, null);
        }
        /* g2d.setColor(Color.WHITE);
        g2d.fillOval(ant.getX(), ant.getY(), 2, 2); */

        /*
        g.setColor(Color.WHITE);
        g.drawOval(ant.getX() - Formica.antSearchRadius, ant.getY() - Formica.antSearchRadius, Formica.antSearchRadius * 2, Formica.antSearchRadius * 2);
        */
    }

    private void MoveAnt(Formica ant) {
        double theta = 0;
        double velX = 0;
        double velY = 0;

        if (ant.getXGoal() == ant.getX() && ant.getYGoal() == ant.getY()) { // ? Se ho raggiunto l'obiettivo, ne genero uno nuovo
            ant.GenerateNewGoal();
        }
        if (ant.getHasFood()) { // ? Se la formica sta trasportando del cibo
            MoveToHome();

        } else {
            MoveToFood();
        }

        theta = Math.atan2(ant.getYGoal() - ant.getY(), ant.getXGoal() - ant.getX());
        velX = antSpeed * Math.cos(theta);
        velY = antSpeed * Math.sin(theta);
        
        ant.AddToX(velX);
        ant.AddToY(velY);
    }

    private void MoveToHome() {
        if (DistanzaFra(ant.posizione, new Punto(xBase, yBase, false)) < Formica.antSearchRadius) { // ? Se la distanza fra la formica e l'ingresso del formicaio è all'interno del suo raggio di ricerca
            ant.setxGoal(xBase);
            ant.setyGoal(yBase);
        } else {
            FindBestToHomeDot(ant); // ? Trovo il punto per casa più vicino alla casa
        }
    }

    private void MoveToFood() {
        double distanza = panelHeight * panelWidth;
        double min = panelHeight * panelWidth;
        double x = 0, y = 0;

        for (int i = 0; i < food.size(); i++) { // ? Controllo se nel raggio di ricerca della formica si trova del cibo
            distanza = DistanzaFra(ant.posizione, food.get(i));
            if (distanza < Formica.antSearchRadius) {
                if(distanza < min){
                    min = distanza;
                    x = food.get(i).getX();
                    y = food.get(i).getY();
                }

            }
        }
        if(min != panelHeight * panelWidth){
            ant.setxGoal(x);
            ant.setyGoal(y);
        } else {
            FindClosestFood(ant); // ? Trovo il punto per cibo più vicino alla formica
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
            g.fillOval((int) toCasa.get(i).getX() - dotDiameter / 2, (int) toCasa.get(i).getY() - dotDiameter / 2, dotDiameter, dotDiameter);
        }

        g.setColor(Color.BLUE);
        if (food.size() == 0 && toCibo.size() != 0) { // ? Se non c'è cibo e ci sono punti blu, azzero i punti blu
            toCibo.clear();
        }
        for (int i = 0; i < toCibo.size(); i++) {
            g.fillOval((int) toCibo.get(i).getX() - dotDiameter / 2, (int) toCibo.get(i).getY() - dotDiameter / 2, dotDiameter, dotDiameter);
        }
    }

    private void WriteTextOnScreen(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Ants: " + Formiche.size() + "/" + maxAnts, 3, 15);
        g.drawString("Food on screen: " + food.size() + "/" + maxFood, 3, 30);
        g.drawString("Food collected: " + foodCollected, 3, 45);
        g.drawString("Dots to home: " + toCasa.size() + "/" + maxDots, 3, 60);
        g.drawString("Dots to food: " + toCibo.size() + "/" + maxDots, 3, 75);
        g.drawString("Dimension: " + panelWidth + " x " + panelHeight, 3, 90);
        g.drawString("Time to food spawn: " + timeToSpawnFood, 3, 105);
        g.drawString("FPS: " + Game.FPSToDisplay, panelWidth - 45, 15);
    }

    private void LoadImages(Graphics g){
        //! Quando compilo in un file jar, la directory delle immagini dovrà contenere solo il nome e l'estensione, come nell'esempio: pathname:"antWithFood.png"
        //System.out.println(System.getProperty("user.dir"));
        File file = new File("Java\\Formiche\\antWithFood.png");
        
        try {
            antWithFood = ImageIO.read(file);
        } catch (IOException e) {
            //System.out.println("L'immagine della formica con il cibo non esiste");
        }
        file = new File("Java\\Formiche\\antWithoutFood.png");
        try {
            antWithOutFood = ImageIO.read(file);
        } catch (IOException e) {
            //System.out.println("L'immagine della formica senza cibo non esiste");
        }
        loaded = true;
    }

    public static double DistanzaFra(Punto a, Punto b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}