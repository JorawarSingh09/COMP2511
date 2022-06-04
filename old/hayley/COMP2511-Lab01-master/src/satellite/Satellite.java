package satellite;


public class Satellite {

    // attributes
    private String name;
    private int height;
    private double velocity;
    private double position;
    
    /**
     * Constructor for Satellite
     * @param name
     * @param height
     * @param velocity
     */
    public Satellite(String name, int height, double position, double velocity) {
        this.name = name;
        this.height = height;
        this.position = position;
        this.velocity = velocity;
    }   

    /**
     * Getter for name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for position (degrees)
     */
    public double getPositionDegrees() {
        return position;
    }

    /**
     * Getter for position (radians)
     */
    public double getPositionRadians() {
        return Math.toRadians(position);
    }

    /**
     * Returns the linear velocity (metres per second) of the satellite
     */
    public double getLinearVelocity() {
        return velocity;
    }

    /**
     * Returns the angular velocity (degrees per second) of the satellite
     */
    public double getAngularVelocity() {
        return velocity / height;
    }

    /**
     * Setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for height
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setter for velocity
     * @param velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Setter for position
     * @param position
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * Calculates the distance travelled by the satellite in the given time
     * @param time (seconds)
     * @return distance in metres
     */
    public double distance(double time) {
        return time * velocity;
    }

    public static void main(String[] args) {
        Satellite A = new Satellite("satellite A", 10000, 122.0, 55.0);
        Satellite B = new Satellite("satellite B", 5438, 0.0, 234.0);
        Satellite C = new Satellite("satellite C", 9029, 284.0, 0.0);

        System.out.println("I am " + A.getName() + " at position " + A.getPositionDegrees() + " degrees, " + A.getHeight() + " km above the centre of the earth and moving at a velocity of " + A.getLinearVelocity() + " metres per second.");

        A.setHeight(9999);
        B.setPosition(45);
        C.setVelocity(36.5);

        System.out.println(A.getPositionRadians());
        System.out.println(B.getAngularVelocity());
        System.out.println(C.distance(120));
    }

}