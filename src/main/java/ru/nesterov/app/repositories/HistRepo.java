package ru.nesterov.app.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nesterov.app.domain.History;

/**
 * @author Sergey Nesterov
 */
public interface HistRepo extends CrudRepository<History, String> {

    Iterable<History> findBySecidAndTradedate(String secid, String tradedate);

    Iterable<History> findBySecid(String secid);

    Iterable<History> findByTradedate(String date);
}
