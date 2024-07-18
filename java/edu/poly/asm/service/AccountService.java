package edu.poly.asm.service;

import edu.poly.asm.domain.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void deleteAllByIdInBatch(Iterable<String> strings);

    <S extends Account> List<S> findAll(Example<S> example, Sort sort);

    <S extends Account> List<S> findAll(Example<S> example);

    Account login(String username, String password);

    void deleteAllInBatch();

    void deleteAllInBatch(Iterable<Account> entities);

    void flush();

    Account getReferenceById(String string);

    <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

    <S extends Account> S saveAndFlush(S entity);

    List<Account> findAll();

    List<Account> findAllById(Iterable<String> strings);

    <S extends Account> List<S> saveAll(Iterable<S> entities);

    long count();

    void delete(Account entity);

    void deleteAll();

    void deleteAllById(Iterable<? extends String> strings);

    void deleteAll(Iterable<? extends Account> entities);

    void deleteById(String string);

    boolean existsById(String string);

    Optional<Account> findById(String string);

    <S extends Account> S save(S entity);

    List<Account> findAll(Sort sort);

    Page<Account> findAll(Pageable pageable);
}
