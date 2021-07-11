package ru.nesterov.app.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nesterov.app.domain.Security;

/**
 * @author Sergey Nesterov
 */
public interface SecRepo extends CrudRepository<Security, String> {

    Security findBySecid(String secid);
}
