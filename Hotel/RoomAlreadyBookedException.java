package Hotel;

public class RoomAlreadyBookedException extends RuntimeException {
    int roomNumber;

    public RoomAlreadyBookedException() {
    }

    public RoomAlreadyBookedException(int roomNumber) {
        super("Room " + roomNumber + " already booked");
    }
}
