public class Medicine extends Doctor {
    public Medicine(int doctorID, String name) {
        super(doctorID, name, "General Medicine");
    }

    @Override
    public void displayAvailability() {
        System.out.println("Medicine availability details...");
    }
}