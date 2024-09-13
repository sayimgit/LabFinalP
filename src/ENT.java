public class ENT extends Doctor {
    public ENT(int doctorID, String name) {
        super(doctorID, name, "ENT Specialist");
    }

    @Override
    public void displayAvailability() {
        System.out.println("ENT availability details...");
    }
}