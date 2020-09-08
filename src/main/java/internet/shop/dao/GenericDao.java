package internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T object);

    Optional<T> get(K id);

    List<T> getAll();

    T update(T object);

    boolean delete(K id);
}
