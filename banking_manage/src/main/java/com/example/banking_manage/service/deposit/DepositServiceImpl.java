package com.example.banking_manage.service.deposit;

import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.repository.IDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DepositServiceImpl implements IDepositService {
    @Autowired
    private IDepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit findById(Long id) {
        return null;
    }

    @Override
    public void create(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void update(Long id, Deposit deposit) {

    }

    @Override
    public void removeById(Long id) {

    }
}
