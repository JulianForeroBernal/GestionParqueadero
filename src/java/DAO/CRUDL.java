package java.DAO;
import java.util.List;

public interface CRUDL<T> {
    void create(T entity);
    T read(int id);
    void update(T entity);
    void delete(int id);
    List<T> list();
}
