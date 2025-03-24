public class RK4 implements Integrator {
    @Override
    public void update(Pendulum p, double dt, double g, double L) {
        double theta = p.getTheta();
        double omega = p.getOmega();

        double k1theta = omega;
        double k1omega = -(g / L) * Math.sin(theta);

        double k2theta = omega + k1omega * dt / 2;
        double k2omega = -(g / L) * Math.sin(theta + k1theta * dt / 2);

        double k3theta = omega + k2omega * dt / 2;
        double k3omega = -(g / L) * Math.sin(theta + k2theta * dt / 2);

        double k4theta = omega + k3omega * dt;
        double k4omega = -(g / L) * Math.sin(theta + k3theta * dt);

        theta = theta+dt/6*(k1theta+2*k2theta+2*k3theta+k4theta);
        omega = omega+dt/6*(k1omega+2*k2omega+2*k3omega+k4omega);
        p.setOmega(omega);
        p.setTheta(theta);
    }
}
