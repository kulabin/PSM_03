public class Pendulum {
    double theta;
    double omega;

    public Pendulum(double theta, double omega) {
        this.theta = theta;
        this.omega = omega;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getOmega() {
        return omega;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }
}
