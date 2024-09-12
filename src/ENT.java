public class ENT extends Doctor {
    public ENT(int doctorID, String name) {
        super(doctorID, name);
    }

    @Override
    public String getSpecialization() {
        return "ENT";
    }

    @Override
    public void displayAvailability() {
        System.out.println("ENT doctor availability displayed.");
    }
}
