package Hotel;

public class StandardRoom extends Room {
    private boolean hasTv;

    public StandardRoom(int roomNumber, int floor, double pricePerNight, boolean hasTv) {
        super(roomNumber, floor, pricePerNight);
        this.hasTv = hasTv;
    }

    @Override
    public String getRoomType() {
        return "Standard Room";
    }
}
