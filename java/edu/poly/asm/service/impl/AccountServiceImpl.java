package edu.poly.asm.service.impl;

import edu.poly.asm.domain.Account;
import edu.poly.asm.repository.AccountRepository;
import edu.poly.asm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {
        accountRepository.deleteAllByIdInBatch(strings);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return accountRepository.findAll(example, sort);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Account login(String username, String password) {
        Optional<Account> optExist = findById(username);
        if (optExist.isPresent() && bCryptPasswordEncoder.matches(password, optExist.get().getPassword())) {
            Account account = optExist.get();
            account.setPassword("");
            return account;
        }
        return null;
    }

    @Override
    public void deleteAllInBatch() {
        accountRepository.deleteAllInBatch();
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {
        accountRepository.deleteAllInBatch(entities);
    }

    @Override
    public void flush() {
        accountRepository.flush();
    }

    @Override
    public Account getReferenceById(String id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return accountRepository.saveAllAndFlush(entities);
    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return accountRepository.saveAndFlush(entity);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAllById(Iterable<String> ids) {
        return accountRepository.findAllById(ids);
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        accountRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {
        accountRepository.deleteAll(entities);
    }

    @Override
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public <S extends Account> S save(S entity) {
        if (StringUtils.isEmpty(entity.getPassword())) {
            Optional<Account> existingAccount = accountRepository.findById(entity.getUsername());
            existingAccount.ifPresent(acc -> entity.setPassword(acc.getPassword()));
        } else {
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        }
        return accountRepository.save(entity);
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return accountRepository.findAll(sort);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
