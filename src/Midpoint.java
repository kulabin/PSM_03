public class Midpoint implements Integrator {
    @Override
    public void update(Pendulum p, double dt, double g, double L) {
        double theta = p.getTheta();
        double omega = p.getOmega();
        double alpha = -(g / L) * Math.sin(theta);
        double thetaMid = theta + omega * dt / 2;
        double omegaMid = omega + alpha * dt / 2;
        double alphaMid = -(g / L) * Math.sin(thetaMid);
        theta += omegaMid * dt;
        omega += alphaMid * dt;
        p.setTheta(theta);
        p.setOmega(omega);
    }
}
