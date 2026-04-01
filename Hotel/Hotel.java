package Hotel;

import java.util.*;

public class Hotel implements Filterable<Room>{
    HashMap<Integer, Room> rooms = new HashMap<>();
    HashSet<Room> bookedRooms = new HashSet<>();

    public void addRoom(Room room){
        if(room == null) return;
        rooms.put(room.roomNumber, room);
    }
    public Room getRoom(int roomNumber) {
        if(!rooms.containsKey(roomNumber)){
            throw new RoomNotFoundException(roomNumber);
        }
        return rooms.get(roomNumber);
    }

    public void bookRoom(int roomNumber){
        Room room = rooms.get(roomNumber);
        if(!rooms.containsKey(roomNumber)) {
            throw new RoomNotFoundException(roomNumber);
        }
        if(bookedRooms.contains(room)){
            throw new RoomAlreadyBookedException(roomNumber);
        } else {
            bookedRooms.add(room);
        }
    }

    public void cancelBooking(int roomNumber){
        Room room = rooms.get(roomNumber);
        if(room == null) throw new RoomNotFoundException(roomNumber);
        if(bookedRooms.contains(room)){
            bookedRooms.remove(rooms.get(roomNumber));
        }
    }

    public List<Room> getAvailableRooms(){
        return rooms.values().stream()
                .filter(room -> !bookedRooms.contains(room) & rooms.containsKey(room.roomNumber)) //проверка на null
                .sorted(Comparator.comparing(room -> room.pricePerNight))
                .toList();
    }

    @Override
    public List<Room> filterByFloor(int floor) {
        if (rooms.values().stream().noneMatch(room -> room.floor == floor)) return new ArrayList<>();
        return rooms.values().stream()
                .filter(room -> room.floor == floor)
                .toList();
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.rooms.put(101, new StandardRoom(101,1,1000.0, true));
        hotel.rooms.put(201, new StandardRoom(201,2,700.0, false));
        hotel.rooms.put(301, new StandardRoom(301,3,1000.0,true));
        hotel.rooms.put(202, new SuiteRoom(202, 2, 5000.0, true));
        hotel.rooms.put(302, new SuiteRoom(302, 3, 2500.0,false));

        hotel.bookRoom(202);
        hotel.bookRoom(302);

        List<Room> availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms:" + availableRooms);

        List<Room> filtered = hotel.filterByFloor(2);
        System.out.println("Filtered Rooms:" + filtered);

        try{
            hotel.getRoom(304);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            hotel.bookRoom(210);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            hotel.bookRoom(202);
        } catch (RoomAlreadyBookedException e) {
            System.out.println(e.getMessage());
        }

    }
}
