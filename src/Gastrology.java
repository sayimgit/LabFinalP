public class Gastrology extends Doctor {
    public Gastrology(int doctorID, String name) {
        super(doctorID, name, "Gastrologist");
    }

    @Override
    public void displayAvailability() {
        System.out.println("Gastrology availability details...");
    }
}
