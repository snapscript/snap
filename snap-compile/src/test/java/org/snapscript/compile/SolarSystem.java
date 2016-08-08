package org.snapscript.compile;

/**
 * This program displays a simulated solar system in 2 dimensions.
 * Buttons along the top of the GUI allow the user to show or hide
 * various physical properties of the planets. These properties are
 * the acceleration and velocity vectors, the percent kinetic and potential
 * energy, and the trajectory the planet has taken. In addition, two
 * buttons allow the user to zoom in or out for better viewing. Finally,
 * there is a sliding scale on the right of the GUI that allows the user
 * to alter the mass of the sun.
 *
 * To run the program, you first must compile it (javac SolarSystem.java),
 * then you execute it using java SolarSystem <number of planets>.
 * @author Brett Kraabel
 * @author us.geocities.com/brettkraabel
 * @version 1.0
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * This class is used to make the spatial coordinates more transparent.
 */
class Coord {
    /**
     * X represents the horizontal dimension which is measured from LHS
     * of GUI. Y represents the vertical dimension which is measured
     * from top of GUI (i.e. Y increases as you go down).
     */
    public static final byte X = 1, Y = 0;
}
/**
 * Main entry into program. Creates GUI and repaints it in infinite loop.
 */
public class SolarSystem {
    /**
     * The time increment for numerical integration of differential equations
     * governing motion in the solar system.
     */
    private static double dt = 0.001;
    /**
     * Default constructor.
     */
    public SolarSystem() { }
    /**
     * Main entry point into program. User may input number of planets
     * on command line.
     */
    public static void main(String[] arguments) throws Exception {
        SkyFrame skyFrame;
        int numOfPlanets = 9;

        if (arguments.length == 0)
            skyFrame = new SkyFrame(numOfPlanets, dt);
        else {
            if (arguments[0].equals("h")) {
                usage();
                System.exit(1);
                skyFrame = new SkyFrame(0, 0);
        }
        try {
            numOfPlanets = Integer.parseInt(arguments[0]);
            numOfPlanets = Math.abs(numOfPlanets);
            skyFrame = new SkyFrame(numOfPlanets, 0);
        }
        catch(Exception e) {
            System.err.println(e.toString());
            usage();
            System.exit(0);
            skyFrame = new SkyFrame(0, 0);
        }
    }

        while (true) {
            skyFrame.getPanel().repaint();
            Thread.sleep(20);
        }
    }
    /**
     * The method getColor is used by the sattelites of the solar system
     * to get their color.
     * @param i Integer that determines the color that is returned.
     * @return Color
     */
    public static Color getColor(int i) {
        Color color;
        int j = i%8;

        switch(j) {
            case 0 : color = Color.red;
            break;
            case 1: color = Color.blue;
            break;
            case 2 : color = Color.green;
            break;
            case 3 : color = Color.pink;
            break;
            case 4: color = Color.orange;
            break;
            case 5: color = Color.cyan;
            break;
            case 6 : color = Color.magenta;
            break;
            case 7 : color = Color.lightGray;
            break;
            default : color = Color.gray;
        }
        return color;
    }
    /**
     * The method usage prints a message to the console instructing the
     * use on how to use the program.
     */
    private static void usage() {
        String explaination =
        "This program displays a simulated solar system in 2 dimensions.\n"+
        "Buttons along the top of the GUI allow the user to show or hide\n" +
        "various physical properties of the planets. These properties are\n" +
        "the acceleration and velocity vectors, the percent kinetic and potential\n" +
        "energy, and the trajectory the planet has taken. In addition, two\n" +
        "buttons allow the user to zoom in or out for better viewing. Finally,\n" +
        "there is a sliding scale on the right of the GUI that allows the user\n" +
        "to alter the mass of the sun.";

        System.out.println("Usage :\n" +
            "java SolarSystem [options] <number of planets>\n" +
            "\n" +
            "options :\n" +
            "h\t\tprint this message\n\n" +
            explaination);
    }
}

/**
 * This class provides the frame that contains the GUI interface and
 * the display.
 */
class SkyFrame implements ChangeListener, ActionListener {
    /**
     * The panel that displays the solar system.
     */
    private SkyPanel skyPanel;
    /**
     * The range within which the user may modify the sun's mass.
     */
    private static double maxSunMassMultiplier = 1.5;
    private static double minSunMassMultiplier = 0.5;
    /**
     * The initial mass of the sun (arbitrary units).
     */
    private static int massOfSun = 1000;
    /**
     * The initial size of the frame in pixels.
     */
    private static int xPixels = 1000, yPixels = 1000;
    /**
     * The scale factor used to zoom in and out.
     */
    private static int lengthScaleFactor = 1;
    /**
     * The maximum scale factor allowed for zooming out.
     */
    private static int maxScaleFactor = 5;
    /**
     * The sun for the solar system.
     * @see Sun
     */
    private Sun sun;
    /**
     * The slider that allows the user to modify the sun's mass.
     * @see JSlider
     */
    private JSlider slider;
    /**
     * Button that allows the user to zoom in (i.e. view the solar system
     *  in more detail).
     */
    private JButton zoomIn;
    /**
     * Button that allows the user to zoom out (i.e. view the solar system
     * in less detail.
     */
    private JButton zoomOut;
    /**
     * Button that allows the user to display the acceleration vectors of
     * each planet in the solar system.
     */
    private JButton accButton;
    /**
     * Button that allows the user to display the velocity vectors of each
     * planet in the solar system.
     */
    private JButton velButton;
    /**
     * Button that allows the user to display the energy of each planet
     * in the solar system.
     */
    private JButton energyButton;
    /**
     * Button that allows the user to display the previous positions of each
     * planet in the solar system.
     */
    private JButton positionButton;
    /**
     * Button that allows the user to freeze the display of the solar system
     * (i.e. stop the motion of the planets).
     */
    private JButton freezeButton;
    /**
     * String to label button.
     */
    private final String zoomInString = "Zoom In", zoomOutString = "Zoom Out";
    /**
     * String to label button.
     */
    private final String showAcc = "Show Acceleration",
        hideAcc = "Hide Acceleration", showVel = "Show Velocity",
        hideVel = "Hide Velocity";
    /**
     * String to label button.
     */
    private final String showEnergy = "Show Energy",
        hideEnergy = "Hide Energy", showPos = "Trace Position",
        hidePos = "Hide Trace", freeze = "Freeze", unFreeze = "Unfreeze";
    /**
     * Panel in which to put the buttons.
     * @see JPanel
     */
    private JPanel buttonPanel;
    private JFrame frame;
    /**
     * Constructs a frame containing the specified number of planets.
     * @param mNumberOfPlanets The number of planets in the solar system.
     * @param dt The time differential for integrating the equations of motion.
     */
    public SkyFrame(int mNumberOfPlanets, double dt) {
        frame = new JFrame("Solar System");

        sun = new Sun(75, 1000, xPixels/2, yPixels/2);

        frame.setSize(xPixels, yPixels);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        skyPanel = new SkyPanel(dt,
        xPixels,
        yPixels,
        mNumberOfPlanets,
        sun);
        skyPanel.getPanel().setBackground(Color.black);

        BorderLayout MasterPanelLayout = new BorderLayout();
        Container pane = frame.getContentPane();
        pane.setLayout(MasterPanelLayout);
        pane.add(skyPanel.getPanel(), BorderLayout.CENTER);

        slider = new JSlider(SwingConstants.VERTICAL,
        (int)(sun.getMass()*minSunMassMultiplier),
        (int)(sun.getMass()*maxSunMassMultiplier),
        (int)sun.getMass());
        slider.setMajorTickSpacing((int)(sun.getMass()*
            (maxSunMassMultiplier -
            minSunMassMultiplier)/20));
        slider.setMinorTickSpacing((int)(sun.getMass()*
            (maxSunMassMultiplier -
            minSunMassMultiplier)/40));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        pane.add(slider, BorderLayout.EAST);

        buttonPanel = new JPanel();
        zoomIn = new JButton(zoomInString);
        zoomIn.addActionListener(this);
        zoomIn.setEnabled(false);
        buttonPanel.add(zoomIn);

        zoomOut = new JButton(zoomOutString);
        zoomOut.addActionListener(this);
        zoomOut.setEnabled(true);
        buttonPanel.add(zoomOut);

        accButton = new JButton(showAcc);
        accButton.addActionListener(this);
        buttonPanel.add(accButton);

        velButton = new JButton(showVel);
        velButton.addActionListener(this);
        buttonPanel.add(velButton);

        energyButton = new JButton(showEnergy);
        energyButton.addActionListener(this);
        buttonPanel.add(energyButton);

        positionButton = new JButton(showPos);
        positionButton.addActionListener(this);
        buttonPanel.add(positionButton);

        freezeButton = new JButton(freeze);
        freezeButton.addActionListener(this);
        buttonPanel.add(freezeButton);

        pane.add(buttonPanel, BorderLayout.NORTH);

        frame.setContentPane(pane);
        frame.setVisible(true);
    }
    /**
     * The method actionPerformed implements the action called for by the
     * user.
     * @param event An ActionEvent.
     * @see ActionEvent
     */
    @Override
   public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals(zoomOutString)) {
            zoomIn.setEnabled(true);
            ++lengthScaleFactor;
            energyButton.setEnabled(false);
            skyPanel.displayEnergy(false);
            skyPanel.populateStarGroup(lengthScaleFactor);
            if (lengthScaleFactor > maxScaleFactor)
                zoomOut.setEnabled(false);
        }
        else if (command.equals(zoomInString)) {
            --lengthScaleFactor;
            if (lengthScaleFactor == 1) {
                zoomIn.setEnabled(false);
                energyButton.setEnabled(true);
            if (energyButton.getText().equals(hideEnergy))
                skyPanel.displayEnergy(true);
            }
        zoomOut.setEnabled(true);
        }
        else if (command.equals(showAcc)) {
            skyPanel.displayAcc(true);
            accButton.setText(hideAcc);
        }
        else if (command.equals(showVel)) {
            skyPanel.displayVel(true);
            velButton.setText(hideVel);
        }
        else if (command.equals(showEnergy)) {
            skyPanel.displayEnergy(true);
            energyButton.setText(hideEnergy);
        }
        else if (command.equals(showPos)) {
            skyPanel.displayPos(true);
            positionButton.setText(hidePos);
        }
        else if (command.equals(freeze)) {
            skyPanel.setFreeze(true);
            freezeButton.setText(unFreeze);
        }
        else if (command.equals(hideAcc)) {
            skyPanel.displayAcc(false);
            accButton.setText(showAcc);
        }
        else if (command.equals(hideVel)) {
            skyPanel.displayVel(false);
            velButton.setText(showVel);
        }
        else if (command.equals(hideEnergy)) {
            skyPanel.displayEnergy(false);
            energyButton.setText(showEnergy);
        }
        else if (command.equals(hidePos)) {
            skyPanel.displayPos(false);
            skyPanel.resetPos();
            positionButton.setText(showPos);
        }
        else if (command.equals(unFreeze)) {
            skyPanel.setFreeze(false);
            freezeButton.setText(freeze);
        }
    }
    /**
     * Gets the current value of lengthScaleFactor used to calculate
     * position of planets within the display.
     */
    public static int getScaleFactor() {
        return lengthScaleFactor;
    }
    /**
     * Gets the SkyPanel that displays the solar system.
     * @see SkyPanel
     */
    public JPanel getPanel() {
        return skyPanel.getPanel();
    }
    /**
     * Changes the sun's mass according to the input from the slider.
     * @param event ChangeEvent
     * @see ChangeEvent
     */
    @Override
   public void stateChanged(ChangeEvent event) {
        JSlider source = (JSlider)event.getSource();
        if (source.getValueIsAdjusting() != true)
            sun.setMass(slider.getValue());
    }
}

/**
 * Contains the sun, planets and moons of the simulated solar system.
 * @see JPanel
 */
class SkyPanel implements JPanelInterface {
    /**
     * The initial size of the panel in pixels
     */
    private int xMaxPixels, xMinPixels, yMaxPixels, yMinPixels;
    /**
     * Minimum and maximum planet diameter in pixels.
     */
    private double minPlanetDia = 20, maxPlanetDia = 60;
    /**
     * The maximun number of orbits allowed in solar system.
     */
    private final int numberOfPlanets;
    /**
     * Number of stars in the background.
     */
    private final int numStars = 30;
    /**
     * The maximum number of orbits (i.e. planets) allowed in the solar
     * system.
     */
    private final int maxNumberOfOrbits;
    /**
     * A boolean array indicating if orbit indexed is occupied by a planet.
     */
    private boolean[] orbitOccupied;
    /**
     * An array of planet objects. @see Planet
     */
    private Planet[] planet;
    /**
     * Boolean variables indicating whether or not to display energy, velocity,
     * acceleration or position. Variable freeze is set to true to stop the
     * display from updating.
     */
    private boolean displayEnergy = false, displayVel = false, displayAcc = false,
    displayPos = false, freeze = false;
    /**
     * A vector containing Stars objects.
     * @see Stars
     */
    private Vector starGroup;
    /**
     * Sun object.
     * @see Sun
     */
    private Sun sun;
    private JPanel panel;
    /**
     * SkyPanel constructor.
     * The constructor calculates the maximum number of planets that will
     * be visible given mMaxXPosition, and then determines the maximum
     * number of orbits that are needed. It then creates an array of
     * planets and places each planet at random within the orbits. Finally,
     * it creates a star group which is displayed for the default view
     * of the solar system (more star groups are created as needed when/if
     * the user zooms out for a wider view of the solar system).
     * @param dt Time increment for numerical integration of differential
     * equations of motion.
     * @param mMaxXPosition Maximum value of X coordinate allowed (in pixels).
     * @param mMaxYPosition Maximum value of Y coordinate allowed (in pixels).
     * @param mNumberOfPlanets Number of planets in solar system.
     * @param mSun Reference to Sun object of solar system.
     * @see Sun
     */
    public SkyPanel(double dt,
                    int mMaxXPosition,
                    int mMaxYPosition,
                    int mNumberOfPlanets,
                    Sun mSun){
       panel = new JPanelAdapter(this);
        xMinPixels = 0;
        xMaxPixels = mMaxXPosition;
        yMinPixels = 0;
        yMaxPixels = mMaxYPosition;
        numberOfPlanets = mNumberOfPlanets;
        sun = mSun;

        int numVisibleOrbits =(int)((xMaxPixels/
        sun.getAbsPos(Coord.X) -
        sun.getDiameter()/2)/maxPlanetDia);

        maxNumberOfOrbits = numVisibleOrbits > numberOfPlanets ?
        numVisibleOrbits :
        numberOfPlanets;

        orbitOccupied = new boolean[maxNumberOfOrbits];
        for (int i = 0; i < maxNumberOfOrbits; ++i)
            orbitOccupied[i] = false;

        planet = new Planet[numberOfPlanets];

        for (int i = 0; i < numberOfPlanets; ++i) {
            double diameter = minPlanetDia + Math.random()*
            (maxPlanetDia - minPlanetDia);
            planet[i] = new Planet(dt, // Time increment for calculating motion.
                                   diameter, // Diameter of planet
                                   getUnoccupiedOrbit(), // Radius of orbit for planet
                                   SolarSystem.getColor(i), // Color of planet
                                   diameter/maxPlanetDia, // Mass of planet
                                   this, // Host panel
                                   sun);
        }

        starGroup = new Vector(1);
        populateStarGroup(1);
    }

    /**
     * Creates a new star group if needed for the given lengthScaleFactor.
     * @param mLengthScaleFactor The scale factor which determines sizing and
     * distances for positioning objects in display. This allows user to
     * zoom in or out.
     */
    public void populateStarGroup(int mLengthScaleFactor) {
        //
        // Check to see if we have already made a star group for
        // this zoom factor.
        //
        if (starGroup.size() >= mLengthScaleFactor)
            return;
        //
        // If not, make a new star group.
        //

        Stars stars = new Stars(numStars/SkyFrame.getScaleFactor(), // number of stars
        SkyFrame.getScaleFactor()*(xMaxPixels -
        sun.getAbsPos(Coord.X)), // maximum distance from sun
                      (SkyFrame.getScaleFactor() - 1)*(xMaxPixels -
                                                       sun.getAbsPos(Coord.X)), // minimum distance from sun

                      sun);
        starGroup.addElement(stars);
    }

    /**
     * Searches randomly among the allowed orbits until it finds an
     * unoccupied orbit.
     * @return double The intial radius of the unoccupied orbit.
     */
    private double getUnoccupiedOrbit() {
        int trialOrbitNum = (int)(Math.random()*maxNumberOfOrbits);

        while (true) {
            if (!orbitOccupied[trialOrbitNum]) {
                orbitOccupied[trialOrbitNum] = true;
                break;
            }
            else
                trialOrbitNum = (int)(Math.random()*maxNumberOfOrbits);
        }

        return 1.5*sun.getDiameter() + trialOrbitNum*maxPlanetDia;
    }


    /**
     * Prints the kinetic energy of the planet indicated by the parameter
     * i as a percent of the total energy (kinetic + potential) of the planet.
     * @param comp2D A 2D graphics object.
     * @see Graphics2D
     * @param i Index of planet for which to display the kinetic energy.
     */
    private void drawEnergy(Graphics2D comp2D, int i) {
        comp2D.setColor(Color.white);

        //
        // Print kinetic energy as percent of total energy.
        //

        String percentKE = Integer.toString((int)(100*planet[i].getKE()/
                                            (planet[i].getKE() +
                                             planet[i].getPE())));
        comp2D.drawString("KE% " + percentKE,
                         (int)planet[i].getAbsPos(Coord.X) + 50,
                         (int)planet[i].getAbsPos(Coord.Y) + 70);
        //
        // Print potential energy as percent of total energy.
        //
        String percentPE = Integer.toString((int)(100*planet[i].getPE()/
                                            (planet[i].getKE() +
                                             planet[i].getPE())));
        comp2D.drawString("PE% " + percentPE,
                         (int)planet[i].getAbsPos(Coord.X) + 50,
                         (int)planet[i].getAbsPos(Coord.Y) + 82);
    }

    /**
     * Displays the velocity vector of the planet indexed by the parameter i.
     * @param comp2D A 2D graphics object.
     * @see Graphics2D
     * @param i Index of planet for which to display the kinetic energy.
     */
    private void drawVelocityVector(Graphics2D comp2D, int i) {
        float xPos = (float)planet[i].getAbsPos(Coord.X);
        float yPos = (float)planet[i].getAbsPos(Coord.Y);
        float xVel = xPos + 25*(float)planet[i].getVel(Coord.X)/
                               SkyFrame.getScaleFactor();
        float yVel = yPos + 25*(float)planet[i].getVel(Coord.Y)/
                               SkyFrame.getScaleFactor();

        comp2D.setColor(Color.white);
        comp2D.draw(new Line2D.Float(xPos, yPos, xVel, yVel));
        drawArrow(comp2D, xPos, yPos, xVel, yVel);
    }

    /**
     * Displays the acceleration vector for the planet indexed by the
     * parameter i.
     * @param comp2D A 2D graphics object.
     * @see Graphics2D
     * @param i Index of planet for which to display the kinetic energy.
     */
    private void drawAccelerationVector(Graphics2D comp2D, int i) {
        double xPos = planet[i].getAbsPos(Coord.X);
        double yPos = planet[i].getAbsPos(Coord.Y);
        double xAcc = xPos + 2000*planet[i].getAcc(Coord.X)/
        SkyFrame.getScaleFactor();
        double yAcc = yPos + 2000*planet[i].getAcc(Coord.Y)/
        SkyFrame.getScaleFactor();

        comp2D.setColor(Color.white);
        comp2D.draw(new Line2D.Float((float)xPos,
                   (float)yPos,
                   (float)xAcc,
                   (float)yAcc));
        drawArrow(comp2D,
                  (float)xPos,
                  (float)yPos,
                  (float)xAcc,
                  (float)yAcc);
    }

    /**
     * Draws an arrow (vector) from (x0, y0) to (x1, y1).
     * @param comp2D A 2D graphics object.
     * @see Graphics2D
     * @param x0 X coordinate of base of arrow.
     * @param y0 Y coordinate of base of arrow.
     * @param x1 X coordinate of tip of arrow.
     * @param y1 Y coordinate of tip of arrow.
     */
    private void drawArrow(Graphics2D comp2D,
        float x0,
        float y0,
        float x1,
        float y1) {
        float dx = x1 - x0, dy = y1 - y0;
        float r = (float)Math.sqrt(dx*dx + dy*dy);
        float len = 15/SkyFrame.getScaleFactor();
        int[] x, y;
        int numPts = 4;

        x = new int[numPts];
        y = new int[numPts];
        x[0] = (int)x1;
        y[0] = (int)y1;
        x[1] = (int)(x1 - len*dy/(2*r));
        y[1] = (int)(y1 + len*dx/(2*r));
        x[2] = (int)(x1 + len*dx/r);
        y[2] = (int)(y1 + len*dy/r);
        x[3] = (int)(x1 + len*dy/(2*r));
        y[3] = (int)(y1 - len*dx/(2*r));

        GeneralPath path = new GeneralPath(Path2D.WIND_EVEN_ODD, numPts);
        path.moveTo(x[0], y[0]);
        for (int i = 1; i < numPts; ++i)
            path.lineTo(x[i], y[i]);
        path.closePath();
        comp2D.fill(path);
    }

    /**
     * Set method to set boolean variable displayAcc.
     * @param value Boolean value,
     * true = display acceleration vector,
     * false = do not display acceleration vector.
     */
    public void displayAcc(boolean value) {
        displayAcc = value;
    }

    /**
     * Set method to set boolean variable displayVel.
     * @param value Boolean value,
     * true = display velocity vector,
     * false = do not display velocity vector.
     */
    public void displayVel(boolean value) {
        displayVel = value;
    }

    /**
     * Set method to set boolean variable displayEnergy.
     * @param value Boolean value,
     * true = display kinetic energy,
     * false = do not display kinetic energy.
     */
    public void displayEnergy(boolean value){
        displayEnergy = value;
    }

    /**
     * Set method to set boolean variable displayPos.
     * @param value Boolean value,
     * true = display trace of spatial position,
     * false = do not display trace of spatial position.
     */
    public void displayPos(boolean value) {
        displayPos = value;
    }

    /**
     * Get method to get the value of the boolean variable displayPos.
     * @return displayPos Boolean value.
     * true = diplay trace of previous spatial positions of planets.
     * false = do not display trace of previous spatial positions of planets.
     */
    public boolean showPos() {
        return displayPos;
    }

    /**
     * Erases traces of previous spatial positions of planets.
     */
    public void resetPos() {
        for (int i = 0; i < numberOfPlanets; ++i)
            planet[i].erasePrevPositions();
    }
    /**
     * A set method to set the boolean variable freeze.
     * true = do not translate planets.
     * false = translate planets according to normal physical laws of motion.
     */
    public void setFreeze(boolean value) {
        freeze = value;
    }

    /**
     * Get method to retrieve the maximum diameter allowed for planets.
     * return double Maximum planet diameter (in pixels).
     */
    public double getMaxPlanetDiameter() {
        return maxPlanetDia;
    }
    
    public JPanel getPanel(){
       return panel;
    }

   @Override
   public void update(JPanel panel, Graphics comp) {
      paint(panel, comp);
   }

   @Override
   public void paint(JPanel panel, Graphics comp) {
      Graphics2D comp2D = (Graphics2D) comp;
      comp2D.fill(new Rectangle2D.Double(0, 0, 1000, 1000));
      comp2D.setPaint(Color.black);
      sun.draw(comp2D);
      //
      // Only draw the star groups that are being displayed (i.e.
      // are within the current scale factor).
      //
      for (int i = 0; i < SkyFrame.getScaleFactor(); ++i)
          ((Stars)starGroup.get(i)).draw(comp2D);

      for (int i = 0; i < numberOfPlanets; ++i) {
          if (!freeze)
              planet[i].translate();
          planet[i].draw(comp2D);
          if (displayVel)
              drawVelocityVector(comp2D, i);
          if (displayAcc)
              drawAccelerationVector(comp2D, i);
          if (displayEnergy)
              drawEnergy(comp2D, i);
      }
      try {
         Thread.sleep(100);
      }catch(Exception e){}
   }
}
/**
 * Class Stars defines a group of stars that are displayed at random
 * locations. The stars are yellow circles that change diameter to simulate
 * twinlking.

 */
class Stars {
    /**
     * Coordinates of the stars relative to the Sun's position.
     */
    private double[] xPos, yPos;
    /**
     * Initial diameter of the stars.
     */
    private double diameter = 5;
    /**
     * Number of stars.
     */
    private int numOfStars;
    /**
     * The Sun at the center of the solar system.
     */
    private Sun sun;
    /**
     * Constructor for creating a group of stars.
     * @param mNumOfStars The number of stars to create.
     * @param maxRadius The maximum distance from the sun that a star may
     * be positioned.
     * @param minRadius The minimum distance from the sun that a star may
     * be positioned.
     * @param mSun The Sun object which defines the center of the solar
     * system.
     */
    public Stars(int mNumOfStars,
        double maxRadius,
        double minRadius,
        Sun mSun) {
        numOfStars = Math.abs(mNumOfStars);
        xPos = new double[numOfStars];
        yPos = new double[numOfStars];
        maxRadius = Math.abs(maxRadius);
        minRadius = Math.abs(minRadius);
        sun = mSun;

        if (minRadius == 0)
            minRadius += sun.getDiameter();
        //
        // Chose positions for each star at random between max and min radius.
        //
        for (int i = 0; i < numOfStars; ++i) {
            xPos[i] = (float)Math.random()*maxRadius;
            float yMin = xPos[i] > minRadius ?
            0 :
           (float)Math.sqrt(minRadius*minRadius - xPos[i]*xPos[i]);
            float yMax = (float)Math.sqrt(maxRadius*maxRadius -
            xPos[i]*xPos[i]);
            yPos[i] = (float)Math.random()*(yMax - yMin) + yMin;
            if (Math.random() > 0.5)
                xPos[i]*= -1;
            if (Math.random() > 0.5)
                yPos[i]*= -1;
        }
    }
    /**
     * The method draw displays the stars.
     * @param comp2D The Graphics2D object in which to display the stars.
     */
    public void draw(Graphics2D comp2D) {
        comp2D.setColor(Color.yellow);

        //
        // Make stars twinkle by chosing random diameter for each star.
        //

        for (int i = 0; i < numOfStars; ++i) {
            double actualDia = diameter*Math.random();

        Ellipse2D.Double starShape =
        new Ellipse2D.Double(xPos[i]/
                             SkyFrame.getScaleFactor() +
                             sun.getAbsPos(Coord.X),
                             yPos[i]/SkyFrame.getScaleFactor() +
                             sun.getAbsPos(Coord.Y),
                             actualDia,
                             actualDia);
        comp2D.fill(starShape);
        }
    }
}
/**
 * The interface host defines the interface needed for an object to serve
 * as a host about which a satellite may orbit.
 */
interface Host {
    /**
     * The method getAbsPos calculates the absolute position of the
     * Host. This is the position used to display the Host
     * and is taken with respect to the upper left corner of the display.
     * @param mAxis The cartesian axis with respect to which the position
     * is to be calculated.
     * @return double The absolute position of the Satellite with respect
     * to the given axis.
     */
    public double getAbsPos(byte mAxis);
    /**
     * The method getMass returns the mass of the Host in arbitrary
     * units.
     * @return double
     */
    public double getMass();
}
/**
 * The abstract class Satellite defines satellites that orbit about a Sun.
 * @see Sun
 */
abstract class Satellite implements Host{
    /**
     * The x and y coordinates of the Satellite's position.
     */
    protected double xPos, yPos;
    /**
     * The x and y components of the Satellite's velocity.
     */
    protected double xVel = 0, yVel = 0;
    /**
     * The x and y components of the Satellite's acceleration.
     */
    protected double xAcc = 0, yAcc = 0, dt;
    /**
     * The diameter of the Satellite.
     */
    protected double diameter;
    /**
     * The mass of the Satellite (arb. units).
     */
    protected double mass;
    /**
     * The color of the Satellite
     */
    protected final Color color;
    /**
     * The Host about which the Satellite orbits.
     * @see Host
     */
    protected Host host;
    /**
     * Constructor for Satellite objects.
     * @param mDt The time increment for calculating the motion.
     * @param mDiameter The diameter of the satellite (arb. units).
     * @param mRadius The initial radius of the Satellite's orbit.
     * @param mMass The mass of the Satellite (arb. units).
     * @param mHost The Host about which the Satellite orbits.
     * @see Host
     */
    public Satellite(double mDt,
                     double mDiameter,
                     double mRadius,
                     Color mColor,
                     double mMass,
                     Host mHost){
        dt = mDt;
        diameter = mDiameter;
        mass = mMass;
        color = mColor;
        host = mHost;
        //
        // The satellite's coordinates (xPos, yPos) relative to the
        // coordinates of the host satellite.
        //
        xPos = Math.random()*mRadius;
        yPos = Math.sqrt(mRadius*mRadius - xPos*xPos);
        //
        // Choose at random from the four Cartesian quadrants for the
        // intial position.
        //
        if (probOneHalf())
            xPos*= -1;
        if (probOneHalf())
            yPos*= -1;
    }
    /**
     * The method probOneHalf returns true with probability 1/2, otherwise
     * returns false.
     * @return boolean
     */
    protected boolean probOneHalf() {
        return Math.random() > 0.5;
    }
    /**
     * The method getAbsPos calculates the absolute position of the
     * Satellite. This is the position used to display the Satellite
     * and is taken with respect to the upper left corner of the display.
     * @param mAxis The cartesian axis with respect to which the position
     * is to be calculated.
     * @return double The absolute position of the Satellite with respect
     * to the given axis.
     */
    @Override
   public double getAbsPos(byte mAxis) {
        if (mAxis == Coord.X)
            return xPos/SkyFrame.getScaleFactor() + host.getAbsPos(mAxis);
        else
            return yPos/SkyFrame.getScaleFactor() + host.getAbsPos(mAxis);
    }
    /**
     * The getRelPosScaled calculate the relative position of the Satellite
     * scaled by the scale factor of the SkyFrame.
     * @see SkyFrame
     * @param mAxis The cartesian axis with respect to which the position
     * is to be calculated.
     * @return double The relative position of the Satellite scaled and
     * with respect to the given axis.
     */
    protected double getRelPosScaled(byte mAxis) {
        if (mAxis == Coord.X)
            return xPos/SkyFrame.getScaleFactor();
        else
            return yPos/SkyFrame.getScaleFactor();
    }
    /**
     * The method getRelPosUnscaled calculates the relative position of
     * the Satellite without scaling.
     * @param mAxis The cartesian axis with respect to which to calculate
     * the position.
     * @return double The relative position of the Satellite with respect
     * to the given axis.
     */
    protected double getRelPosUnscaled(byte mAxis) {
    if (mAxis == Coord.X)
            return xPos;
        else
            return yPos;
    }
    /**
     * The method getTheta returns the angular position of the Satellite.
     * The angle is measured clockwise from the positive horizontal axis,
     * with the Sun at the origin.
     * @return double The angular position in radians.
     */
    public double getTheta() {
        double theta = Math.atan(yPos/xPos);
        if (xPos < 0)
            theta = Math.PI + theta;
        else if (xPos > 0 && yPos < 0)
            theta = 2*Math.PI + theta;
        return theta;
    }
    /**
     * The method getVel calculates a Cartesian component of the Satellite's
     * velocity (arb. units).
     * @param mAxis The cartesian component of the velocity to calculate.
     * @return double A cartesian component of the velocity (arb. units).
     */
    public double getVel(byte mAxis) {
        if (mAxis == Coord.X)
            return xVel;
        else
            return yVel;
    }
    /**
     * The method getAcc calculates a Cartesian component of the Satellite's
     * acceleration (arb. units).
     * @param mAxis The cartesian component of the acceleration to calculate.
     * @return double A cartesian component of the acceleration (arb. units).
     */
    public double getAcc(byte mAxis) {
        if (mAxis == Coord.X)
            return xAcc;
        else
            return yAcc;
    }
    /**
     * The abstract method translate calculates the displacement of the
     * Satellite as a function of time and updates the Satellite's
     * coordinates xPos and yPos.
     */
    public abstract void translate();
    /**
     * The method getDiameter returns the Satellite's diameter.
     * @return double.
     */
    public double getDiameter() {
        return diameter;
    }
    /**
     * The method getColor returns the Satellite's color.
     * @return Color
     * @see Color
     */
    public Color getColor() {
    return color;
    }
    /**
     * The method getMass returns the Satellite's mass (arb. units).
     * @return double
     */
    @Override
   public double getMass() {
    return mass;
    }
    /**
     * The method draw displays the Satellite.
     * @param comp2D The Graphics2D object in which to display the
     * Satellite.
     * @see Graphics2D
     */
    public void draw(Graphics2D comp2D) {
        double zoomedDiameter = diameter/SkyFrame.getScaleFactor();
        Ellipse2D.Double form =
        new Ellipse2D.Double((int)(getAbsPos(Coord.X) - zoomedDiameter/2),
                                  (int)(getAbsPos(Coord.Y) - zoomedDiameter/2),
                                  (int)zoomedDiameter,
                                  (int)zoomedDiameter);
        comp2D.setColor(color);
        comp2D.fill(form);
    }
}
/**
 * Class Planet extends Satellite @see Satellite and implements the planets
 * that revolve about the Sun @see Sun. A Planet is given an initial
 * position and velocity and follows Newton's laws of motion.
 */
class Planet extends Satellite {
    /**
     * An array of int's used to indicate the previous positions of the planet.
     * Used to plot the trajectory of the planet. The array is filled and
     * the index of the current position is tracked.
     */
    private int[] prevXPos, prevYPos;
    /**
     * Used to index into the position arrays to indicate the current position
     * of the planet.
     */
    private int currPosition = 0;
    /**
     * Counts the number of transaltions the Planet undergoes.
     */
    private int count = 0;
    /**
     * Determines the number of translations between which the position of
     * the Planet is recorded.
     */
    private int recordPosition = 5;
    /**
     * Indicates when we must loop back to the beginning of the previous
     * position array
     */
    private boolean looped = false;
    /**
     * The maximum number of previous positions that are recorded.
     */
    private final int maxPrevPositions = 128;
    /**
     * Total energy of the planet.
     */
    private final double TE;
    /**
     * The actual number of moons orbiting around the planet.
     */
    private final byte numOfMoons;
    /**
     * The maximum number of moons that may orbit around the planet.
     */
    private final byte maxNumOfMoons = 4;
    /**
     * The array of moons that orbit around the planet.
     */
    private Moon[] moon;
    /**
     * The panel in which the planet is displayed.
     */
    private SkyPanel hostPanel;
    /**
     * Constructor.
     * @param mDt The time increment for calculting the motion of the planet.
     * @param mDiameter The diameter of the planet in pixels.
     * @param mRadius The radius of the planet's orbit in pixels.
     * @param mColor The color of the planet. @see Color
     * @param mMass The planet's mass in arbitrary units.
     * @param mHostPanel The panel in which the planet is displayed.
     * @param mHost The Host object about which the planet orbits. @see Host
     */
    public Planet(double mDt,
                  double mDiameter,
                  double mRadius,
                  Color mColor,
                  double mMass,
                  SkyPanel mHostPanel,
                  Host mHost ){
        super(mDt, mDiameter, mRadius, mColor, mMass, mHost);
        hostPanel = mHostPanel;
        prevXPos = new int[maxPrevPositions];
        prevYPos = new int[maxPrevPositions];

        for (int i = 0; i < maxPrevPositions; ++i)
            prevXPos[i] = prevYPos[i] = 0;

        numOfMoons = (byte)(Math.random()*maxNumOfMoons);

        if (numOfMoons > 0) {
            moon = new Moon[numOfMoons];
            for (int i = 0; i < numOfMoons; ++i)
                moon[i] = new Moon(dt, // Time increment for calculating motion.
                                   Math.random()*6 + 3, // Moon's diameter.
                                   diameter*(1 + 0.2*Math.random()),// Radius of orbit.
                                   this); // Host about which moon orbits.
        }

        double distToHost = Math.sqrt(getRelPosUnscaled(Coord.X)*
                                      getRelPosUnscaled(Coord.X) +
                                      getRelPosUnscaled(Coord.Y)*
                                      getRelPosUnscaled(Coord.Y));
        //
        // For calculating the force due to gravity, we set the universal
        // gravitational constant to one (or incorporate it into the sun's
        // mass).
        //
        double forceDueToGravity = host.getMass()*mass/
                                  (distToHost*distToHost);
        double theta = getTheta();
        //
        // The following formula for the velocity ensures that the
        // planet's initial motion about the sun is circular.
        //
        yVel = Math.sqrt(host.getMass()/distToHost)*
               Math.sin(theta + Math.PI/2);
        xVel = Math.sqrt(host.getMass()/distToHost)*
               Math.cos(theta + Math.PI/2);

        xAcc = forceDueToGravity*Math.cos(theta + Math.PI)/mass;
        yAcc = forceDueToGravity*Math.sin(theta + Math.PI)/mass;
        //
        // Record the initial total energy of planet.
        //
        TE = mass*(xVel*xVel + yVel*yVel)/2 +
        mass*Math.sqrt(xAcc*xAcc + yAcc*yAcc)*distToHost;
    }
    /**
     * The method translate calculates the motion of the planet by numerically
     * integrating Newton's laws of motion.
     */
    @Override
   public void translate() {
        double force, distToHost, theta;

        for (int i = 0; i < 1/dt; ++i) {
            distToHost = Math.sqrt(getRelPosUnscaled(Coord.X) *
                                   getRelPosUnscaled(Coord.X) +
                                   getRelPosUnscaled(Coord.Y) *
                                   getRelPosUnscaled(Coord.Y));

            force = mass*host.getMass()/(distToHost*distToHost);
            theta = getTheta();
            //
            // Calculate new acceleration.
            //
            xAcc = force*Math.cos(theta + Math.PI)/mass;
            yAcc = force*Math.sin(theta + Math.PI)/mass;
            //
            // Calculate new position using the original velocity.
            //
            xPos+= xVel*dt + dt*dt*xAcc/2;
            yPos+= yVel*dt + dt*dt*yAcc/2;
            //
            // Calculate new velocity.
            //
            xVel+= xAcc*dt;
            yVel+= yAcc*dt;

            if (hostPanel.showPos())
                recordPosition();
            //
            // Translate the moons orbiting around the planet.
            //
            for (int j = 0; j < numOfMoons; ++j)
                moon[j].translate();
        }
    }
    /**
     * The method recordPosition records the current position of the planet
     * in the arrays prevXPos and prevYPos.
     */
    private void recordPosition() {
        if (++count > (int)(recordPosition/dt)) {
            count = 0;
        if (currPosition == maxPrevPositions) {
            currPosition = 0;
            looped = true;
        }

        prevXPos[currPosition] = (int)getRelPosUnscaled(Coord.X);
        prevYPos[currPosition++] = (int)getRelPosUnscaled(Coord.Y);
        }
    }
    /**
     * The method getKE returns the kinetic energy of the planet in arbitrary
     * units.
     */
    public double getKE() {
        return mass*(xVel*xVel + yVel*yVel)/2;
    }
    /**
     * The method getPE returns the potential energy of the planet in arbitrary
     * units.
     */
    public double getPE() {
        return mass*Math.sqrt(xAcc*xAcc + yAcc*yAcc)*
        Math.sqrt(xPos*xPos + yPos*yPos);
    }
    /**
     * The method getTE returns the total energy of the planet in arbitrary
     * units.
     */
    public double getTE() {
        return TE;
    }
    /**
     * The method draw displays the Planet in scaled units of pixels.
     * @param comp2D The Graphics2D object in which to display the
     * planet.
     * @see Graphics2D
     */
    @Override
   public void draw(Graphics2D comp2D) {
        double zoomedDiameter = diameter/SkyFrame.getScaleFactor();
        //
        // Draw the night-time semi-circle of the planet.
        //
        comp2D.setColor(Color.darkGray);
        comp2D.fill(semiCircle(getAbsPos(Coord.X),
        getAbsPos(Coord.Y),
        zoomedDiameter,
        getTheta() + 3*Math.PI/2));
        //
        // Draw the day-time semi-circle of the planet.
        //
        comp2D.setColor(color);
        comp2D.fill(semiCircle(getAbsPos(Coord.X),
        getAbsPos(Coord.Y),
        zoomedDiameter,
        getTheta() + Math.PI/2 ));
        //
        // Show previous positions of the planet if requested by hostPanel.
        //
        if (hostPanel.showPos())
            drawPrevPositions(comp2D);
        //
        // Draw the moons orbiting around the planet.
        //
        for (int i = 0; i < numOfMoons; ++i)
            moon[i].draw(comp2D);
    }
    /**
     * The method drawPrevPositions draws the previous positions of the planet
     * by placing dots to indicate it's path.
     * @param comp2D The Graphics2D object used for the display.
     */
    private void drawPrevPositions(Graphics2D comp2D) {
        int end;
        int x, y;
        if (looped)
            end = maxPrevPositions;
        else
            end = currPosition;

        for (int i = 0; i < end; ++i) {
            x = prevXPos[i]/SkyFrame.getScaleFactor() +
               (int)host.getAbsPos(Coord.X);
            y = prevYPos[i]/SkyFrame.getScaleFactor() +
               (int)host.getAbsPos(Coord.Y);
            comp2D.fill( new Ellipse2D.Float(x, y, 1, 1));
        }
    }
    /**
     * The method erasePrevPostions removes the dots indicating the previous
     * position of the planet from the screen.
     */
    public void erasePrevPositions() {
        looped = false;
        currPosition = 0;
        count = 0;
    }
    /**
     * The method semiCircle is used to draw a day-time and night-time
     * semi-circle of the planet with respect to the Host about which
     * the planet orbits.
     * @param x The scaled horizontal positon of the planet with respect
     * to the Host about which the planet orbits.
     * @param y The scaled vertical positon of the planet with respect
     * to the Host about which the planet orbits.
     * @param dia The scaled diameter of the planet.
     * @param theta The angular position of the planet.
     * @return Area A semi-circular area used to draw the day-time or
     * night-time part of the planet.
     */
    private Area semiCircle(double x, double y, double dia, double theta) {
        double xp = x - dia/2;
        double yp = y - dia/2;
        Ellipse2D.Double circle = new Ellipse2D.Double(xp, yp, dia, dia);
        Rectangle2D.Double rect = new Rectangle2D.Double(xp, yp, dia, dia/2);
        Area semiCircleArea = new Area(circle);
        Area rectArea = new Area(rect);
        semiCircleArea.subtract(rectArea);
        semiCircleArea.transform(AffineTransform.
        getRotateInstance(theta, x, y));
        return semiCircleArea;
    }
}
/**
 * The class Moon defines a moon which executes pure circular motion about
 * it's host, so the formulas for calculating position are simplified compared
 * to those used for calculating planetary motion about the sun.
 * @see Planet
 */
class Moon extends Satellite {
    /**
     * Constructor.
     * @param mDt The time increment used to integrate the equations of motion.
     * @param mDiameter The diameter of the moon in pixels.
     * @param mRadius The radius of the moon's orbit.
     * @param mHost The host about which the moon orbits.
     */
    public Moon(double mDt,
                double mDiameter,
                double mRadius,
                Host mHost)
    {
    super(mDt, mDiameter, mRadius, Color.lightGray, 1, mHost);
    }
    /**
     * The method translate calculates the motion of the moon. Since moons
     * are only allowed to undergo circular motion, the calculation is
     * simplified compared to planetary motion.
     * @see Planet
     */
    @Override
   public void translate() {
        double r, theta;
        //
        // Find radial position of moon.
        //
        r = Math.sqrt(getRelPosUnscaled(Coord.X)*getRelPosUnscaled(Coord.X) +
        getRelPosUnscaled(Coord.Y)*getRelPosUnscaled(Coord.Y));
        //
        // Calculate new angular position which is old old angular position
        // plus speed multiplied by delta t. The number 1000 is an arbitrary
        // number chosen to give the moons a convenient angular speed of orbit
        // (it is not related to any othe part of the program).
        //
        theta = getTheta() + Math.sqrt(1000*host.getMass()/(r*r*r))*dt;
        //
        // Convert angular position to Cartesian coordiantes.
        //
        xPos = r*Math.cos(theta);
        yPos = r*Math.sin(theta);
    }
}
/**
 * The class Sun extends Satellite @see Satellite and defines the origin
 * about which all planets orbit in the solar system. It is yellow and
 * has rays of random length eminatting from it.
 */
class Sun extends Satellite {
    /**
     * Constructor.
     * @param mDiameter The Diameter of the sun in pixels.
     * @param mMass The mass of the sun in arbitrary units.
     * @param mXPos The position of the sun on the horizontal axis.
     * @param mYPos The position of the sun on the vertical axis.
     */
    public Sun(double mDiameter,
               double mMass,
               double mXPos,
               double mYPos) {
        super(1,
              mDiameter,
              0,
              Color.yellow,
              mMass,
              (Host)null);

        xPos = mXPos;
        yPos = mYPos;
    }
    /**
     * The method getAbsPos returns the absolute position of the Sun in pixels.
     * @param mAxis The cartesian axis of interest.
     * @return double The absolute positon of the Sun in pixels.
     */
    @Override
   public double getAbsPos(byte mAxis) {
        if (mAxis == Coord.X)
            return xPos;
        else
            return yPos;
    }
    /**
     * The methos setMass allows the user to set the mass of the sun in order
     * to observe the effects on planetary motion. The mass is measured in
     * arbitray units.
     * @param newMass The new mass of the sun in arbitrary units.
     */
    public void setMass(double newMass) {
        diameter*=newMass/mass;
        mass = newMass;
    }
    /**
     * The method draw displays the Sun in scaled units of pixels.
     * @param comp2D The Graphics2D object in which to display the
     * Sun.
     * @see Graphics2D
     */
    @Override
   public void draw(Graphics2D comp2D) {
        super.draw(comp2D);

        BasicStroke pen = new BasicStroke(2F);
        comp2D.setStroke(pen);

        double xf, yf;

        double zoomedDiameter = diameter/SkyFrame.getScaleFactor();
        //
        // Draw rays of sun.
        //
        for (int i = 0; i < 10; ++i) {
        if (probOneHalf())
            xf = xPos+ Math.random()*zoomedDiameter*1.5;
        else
            xf = xPos - Math.random()*zoomedDiameter*1.5;

        if (probOneHalf())
            yf = yPos + Math.random()*zoomedDiameter*1.5;
        else
            yf = yPos - Math.random()*zoomedDiameter*1.5;

        Line2D.Double ray = new Line2D.Double(xPos,
                                              yPos,
                                              xf,
                                              yf);
        comp2D.draw(ray);
        }
    }
    @Override
   public void translate() {} // Sun does not move.
    }

