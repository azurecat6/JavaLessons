package Hotel;

import java.util.Objects;

public abstract class Room {
    int roomNumber;
    double pricePerNight;
    int floor;


    public Room(int roomNumber, int floor, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.pricePerNight = pricePerNight;
        System.out.println("Room " + roomNumber + " готова к заселению.");
    }

    public abstract String getRoomType() ;

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return getRoomNumber() == room.getRoomNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRoomNumber());
    }

    @Override
    public String toString() {
        return "Room " + roomNumber +
                ", pricePerNight = " + pricePerNight +
                ", floor = " + floor;
    }
}
