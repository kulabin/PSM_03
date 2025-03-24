import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Sim extends JPanel implements ActionListener {
    private final double g =  9.81;
    private final double L = 1;
    private final double dt = 0.01;
    private final double mass = 1;
    private final int maxDataPoints = 1000;

    private final double intialTheta = Math.toRadians(45);
    private final double intialOmega = 0;

    private Pendulum pendulumEuler;
    private Pendulum pendulumMidpoint;
    private Pendulum pendulumRK4;

    private Integrator euler;
    private Integrator midpoint;
    private Integrator rk4;

    private ArrayList<Double> energyEuler  = new ArrayList<>();
    private ArrayList<Double> energyMidpoint = new ArrayList<>();
    private ArrayList<Double> energyRK4 = new ArrayList<>();
    private ArrayList<Double> time = new ArrayList<>();
    private double simTime = 0;

    private ArrayList<Point> trajectoryEuler = new ArrayList<>();
    private ArrayList<Point> trajectoryMidpoint = new ArrayList<>();
    private ArrayList<Point> trajectoryRK4 = new ArrayList<>();

    private int pivotX, pivotY;

    private Timer timer;

    public Sim(){
        pendulumEuler = new Pendulum(intialTheta,intialOmega);
        pendulumMidpoint = new Pendulum(intialTheta,intialOmega);
        pendulumRK4 = new Pendulum(intialTheta,intialOmega);

        euler = new Euler();
        midpoint = new Midpoint();
        rk4 = new RK4();
        timer = new Timer(10,this);
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        euler.update(pendulumEuler,dt,g,L);
        midpoint.update(pendulumMidpoint,dt,g,L);
        rk4.update(pendulumRK4,dt,g,L);
        simTime += dt;
        time.add(simTime);
        if (time.size() > maxDataPoints){
            time.remove(0);
            energyEuler.remove(0);
            energyMidpoint.remove(0);
            energyRK4.remove(0);
        }

        energyEuler.add(computeEnergy(pendulumEuler));
        energyMidpoint.add(computeEnergy(pendulumMidpoint));
        energyRK4.add(computeEnergy(pendulumRK4));
        int width = getWidth();
        int animationHeight = getHeight()/2;
        pivotX=width/2;
        pivotY= animationHeight /8;
        int length = (int)(animationHeight *0.8);

        int xE = pivotX + (int)(length * Math.sin(pendulumEuler.getTheta()));
        int yE = pivotY + (int)(length * Math.cos(pendulumEuler.getTheta()));
        int xM = pivotX + (int)(length * Math.sin(pendulumMidpoint.getTheta()));
        int yM = pivotY + (int)(length * Math.cos(pendulumMidpoint.getTheta()));
        int xR = pivotX + (int)(length * Math.sin(pendulumRK4.getTheta()));
        int yR = pivotY + (int)(length * Math.cos(pendulumRK4.getTheta()));

        trajectoryEuler.add(new Point(xE, yE));
        trajectoryMidpoint.add(new Point(xM, yM));
        trajectoryRK4.add(new Point(xR, yR));
        if (trajectoryEuler.size() > maxDataPoints) {
            trajectoryEuler.remove(0);
            trajectoryMidpoint.remove(0);
            trajectoryRK4.remove(0);
        }

        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight()/2;
        int animationHeight = height/2;
        pivotX=width/2;
        pivotY=animationHeight/8;
        int length = (int)(animationHeight *0.8);

        g.setColor(Color.BLACK);
        g.fillOval(pivotX-5,pivotY-5,10,10);

        int xE = pivotX + (int)(length * Math.sin(pendulumEuler.getTheta()));
        int yE = pivotY + (int)(length * Math.cos(pendulumEuler.getTheta()));
        g.setColor(Color.RED);
        g.drawLine(pivotX,pivotY,xE,yE);
        g.fillOval(xE-5,yE-5,10,10);

        int xM = pivotX + (int)(length * Math.sin(pendulumMidpoint.getTheta()));
        int yM = pivotY + (int)(length * Math.cos(pendulumMidpoint.getTheta()));
        g.setColor(Color.BLUE);
        g.drawLine(pivotX,pivotY,xM,yM);
        g.fillOval(xM-5,yM-5,10,10);

        int xR = pivotX + (int)(length * Math.sin(pendulumRK4.getTheta()));
        int yR = pivotY + (int)(length * Math.cos(pendulumRK4.getTheta()));
        g.setColor(Color.GREEN);
        g.drawLine(pivotX,pivotY,xR,yR);
        g.fillOval(xR-5,yR-5,10,10);

        for (Point p : trajectoryEuler){
            g.setColor(new Color(255,0,0,100));
        }

        for (Point p : trajectoryMidpoint){
            g.setColor(new Color(0,0,255,100));
        }

        for (Point p : trajectoryRK4) {
            g.setColor(new Color(0, 255, 0, 100));
        }

        int graphY = animationHeight;
        int graphHeight = height - animationHeight;
        g.setColor(Color.BLACK);
        g.drawLine(40,graphY+graphHeight-20,width-20,graphY+graphHeight-20);
        g.drawLine(40,graphY+20,40,graphY+graphHeight-20);

        double maxEnergy = 0;
        for (double e : energyEuler){
            if (e > maxEnergy){
                maxEnergy = e;
            }
        }
        for (double e : energyMidpoint){
            if (e > maxEnergy){
                maxEnergy = e;
            }
        }
        for (double e : energyRK4){
            if (e > maxEnergy){
                maxEnergy = e;
            }
        }
        maxEnergy *= 1.1;

        int nPoints = time.size();
        int[] xPointsE = new int[nPoints];
        int[] yPointsE = new int[nPoints];
        int[] xPointsM = new int[nPoints];
        int[] yPointsM = new int[nPoints];
        int[] xPointsR = new int[nPoints];
        int[] yPointsR = new int[nPoints];

        for (int i = 0; i < nPoints; i++){
            int x = 40 + i*((width-60)/maxDataPoints);
            xPointsE[i] = x;
            xPointsM[i] = x;
            xPointsR[i] = x;
            int yEVal = (int)(graphY + graphHeight - 20 - (energyEuler.get(i) / maxEnergy) * (graphHeight - 40));
            int yMVal = (int)(graphY + graphHeight - 20 - (energyMidpoint.get(i) / maxEnergy) * (graphHeight - 40));
            int yRVal = (int)(graphY + graphHeight - 20 - (energyRK4.get(i) / maxEnergy) * (graphHeight - 40));
            yPointsE[i] = yEVal;
            yPointsM[i] = yMVal;
            yPointsR[i] = yRVal;
        }

        g.setColor(Color.RED);
        g.drawPolyline(xPointsE, yPointsE, nPoints);
        g.setColor(Color.GREEN);
        g.drawPolyline(xPointsM, yPointsM, nPoints);
        g.setColor(Color.BLUE);
        g.drawPolyline(xPointsR, yPointsR, nPoints);

        // Legenda wykresu
        g.setColor(Color.BLACK);
        g.drawString("Czerwona: Euler, Zielona: Midpoint, Niebieska: RK4 (energia całkowita)", 50, graphY + 30);
    }

    private double computeEnergy(Pendulum p) {
        double theta = p.getTheta();
        double omega = p.getOmega();
        double potential = mass * g * L * (1 - Math.cos(theta));
        double kinetic = 0.5 * mass * Math.pow(L * omega, 2);
        return potential + kinetic;
    }
}
