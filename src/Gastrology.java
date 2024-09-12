public class Gastrology extends Doctor {
    public Gastrology(int doctorID, String name) {
        super(doctorID, name);
    }

    @Override
    public String getSpecialization() {
        return "Gastrology";
    }

    @Override
    public void displayAvailability() {
        System.out.println("Gastrology doctor availability displayed.");
    }
}
