package models;

/**
 * Interface Identifiable cho cac doi tuong co ID va Ten.
 * Giup BaseManager xu ly chung cac thao tac tim kiem, xoa.
 */
public interface Identifiable {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);
}
