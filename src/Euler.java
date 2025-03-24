public class Euler implements Integrator{
    @Override
    public void update(Pendulum p, double dt, double g, double L) {
        double theta = p.getTheta();
        double omega = p.getOmega();
        double alpha = -(g / L) * Math.sin(theta);
        theta += omega * dt;
        omega += alpha * dt;
        p.setTheta(theta);
        p.setOmega(omega);
    }
}
