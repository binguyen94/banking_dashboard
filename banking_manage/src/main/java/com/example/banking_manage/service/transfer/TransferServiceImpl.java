package com.example.banking_manage.service.transfer;

import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TransferServiceImpl implements ITransferService{
    @Autowired
    private ITransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer findById(Long id) {
        return null;
    }

    @Override
    public void create(Transfer transfer) {

    }

    @Override
    public void update(Long id, Transfer transfer) {

    }

    @Override
    public void removeById(Long id) {

    }
}
