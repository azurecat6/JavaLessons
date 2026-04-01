package Hotel;

public class RoomNotFoundException extends RuntimeException {
    int roomNumber;

    public RoomNotFoundException(int roomNumber) {
        super("Room number " + roomNumber + " not found");
    }

    public RoomNotFoundException() {
    }
}
