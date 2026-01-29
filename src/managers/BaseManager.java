package managers;

import models.Identifiable;
import java.util.ArrayList;

/**
 * BaseManager quan ly danh sach doi tuong ke thua tu Person.
 * 
 * @param <T> Kieu doi tuong (Student hod Teacher)
 */
public abstract class BaseManager<T extends Identifiable> {
    protected ArrayList<T> list = new ArrayList<>();
    protected final String FILE_NAME;

    public BaseManager(String fileName) {
        this.FILE_NAME = fileName;
    }

    // Them moi
    public void add(T item) {
        list.add(item);
        System.out.println("-> Them moi thanh cong!");
    }

    // Xoa theo ID
    public void delete(String id) {
        T item = findById(id);
        if (item != null) {
            list.remove(item);
            System.out.println("-> Da xoa: " + id);
        } else {
            System.out.println("-> Khong tim thay de xoa: " + id);
        }
    }

    // Tim theo ID
    public T findById(String id) {
        for (T item : list) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // Tim kiem theo ten gan dung
    public void searchByName(String keyword) {
        System.out.println("--- KET QUA TIM KIEM (" + keyword + ") ---");
        boolean found = false;
        for (T item : list) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("-> Khong tim thay ket qua nao.");
        }
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(list);
    }

    public int getCount() {
        return list.size();
    }

    // Phuong thuc abstract can override
    public abstract void loadFromFile();

    public abstract void saveToFile();
}
