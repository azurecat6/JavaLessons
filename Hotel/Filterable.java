package Hotel;

import java.util.List;

public interface Filterable<T> {

    List<T> filterByFloor(int floor) ;
}
