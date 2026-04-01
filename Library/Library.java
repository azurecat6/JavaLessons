package Library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Library implements Searchable<LibraryItem> {
    HashMap<Integer, LibraryItem> items = new HashMap<>();

    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }
    public void removeItem(int id) throws ItemNotFoundException {
        items.remove(id);
    }

    public LibraryItem getById(int id) throws ItemNotFoundException {
        return items.get(id);
    }

    public List<LibraryItem> getAllSorted () {
        //сделала через стримы тк это заменяет переопределение compareTo
        return items.values().stream()
                .sorted(Comparator.comparingInt(LibraryItem::getYear).reversed()
                .thenComparing(LibraryItem::getTitle))
                .toList();
    }

    @Override
    public List<LibraryItem> search(String query) {
        if ((query == null) || query.trim().isBlank()) {
            return List.of();
        }
        String searchString = query.toLowerCase().trim();

        return items.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(searchString))
                .toList();
    }
}
