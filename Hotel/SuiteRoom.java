package Hotel;

public class SuiteRoom extends Room {
    private boolean hasJacuzzi;


    public SuiteRoom(int roomNumber, int floor, double pricePerNight,boolean hasJacuzzi) {
        super(roomNumber, floor, pricePerNight);
        this.hasJacuzzi = hasJacuzzi;
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}
