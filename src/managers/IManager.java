package managers;

import java.util.List;
import java.util.Comparator;
import java.util.function.Predicate;

public interface IManager<T> {
    void add(T item);

    void update(String id, T item);

    void delete(String id);

    T getById(String id);

    List<T> getAll();

    void sort(Comparator<T> comparator);

    List<T> search(Predicate<T> condition);

    void loadFromFile();

    void saveToFile();
}
