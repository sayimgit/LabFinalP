public class Medicine extends Doctor {
    public Medicine(int doctorID, String name) {
        super(doctorID, name);
    }

    @Override
    public String getSpecialization() {
        return "Medicine";
    }

    @Override
    public void displayAvailability() {
        System.out.println("Medicine doctor availability displayed.");
    }
}
