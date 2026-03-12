package managers;

import java.util.List;
import java.util.Comparator;

public interface IManager<T> {
    void add(T item);

    void update(String id, T item);

    void delete(String id);

    T getById(String id);

    List<T> getAll();

    void sort(Comparator<T> comparator);

    List<T> search(String keyword, String field);

    void loadFromFile();

    void saveToFile();
}
