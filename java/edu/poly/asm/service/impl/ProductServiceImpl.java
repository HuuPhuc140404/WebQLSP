package edu.poly.asm.service.impl;

import edu.poly.asm.domain.Product;
import edu.poly.asm.repository.ProductRepository;
import edu.poly.asm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Override
    public void deleteAll(Iterable<? extends Product> entities) {
        productRepository.deleteAll(entities);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        productRepository.deleteAllById(longs);
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return productRepository.existsById(aLong);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Iterable<Product> findAllById(Iterable<Long> longs) {
        return productRepository.findAllById(longs);
    }

    @Override
    public Optional<Product> findById(Long aLong) {
        return productRepository.findById(aLong);
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public <S extends Product> Iterable<S> saveAll(Iterable<S> entities) {
        return productRepository.saveAll(entities);
    }
}
