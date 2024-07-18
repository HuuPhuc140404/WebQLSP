package edu.poly.asm.service;

import edu.poly.asm.domain.Product;

import java.util.Optional;

public interface ProductService {
    long count();

    void delete(Product entity);

    void deleteAll();

    void deleteAll(Iterable<? extends Product> entities);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteById(Long aLong);

    boolean existsById(Long aLong);

    Iterable<Product> findAll();

    Iterable<Product> findAllById(Iterable<Long> longs);

    Optional<Product> findById(Long aLong);

    <S extends Product> S save(S entity);

    <S extends Product> Iterable<S> saveAll(Iterable<S> entities);
}
