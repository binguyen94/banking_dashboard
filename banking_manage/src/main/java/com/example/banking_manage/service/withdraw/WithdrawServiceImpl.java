package com.example.banking_manage.service.withdraw;

import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.repository.IWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class WithdrawServiceImpl implements IWithdrawService{
    @Autowired
    private IWithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Withdraw findById(Long id) {
        return null;
    }

    @Override
    public void create(Withdraw withdraw) {

    }

    @Override
    public void update(Long id, Withdraw withdraw) {

    }

    @Override
    public void removeById(Long id) {

    }
}
