package com.km.socserv.service;

import com.km.socserv.entity.Executor;
import com.km.socserv.repository.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutorService {
    @Autowired
    private ExecutorRepository executorRepository;

    public Executor findById(int id){
        Executor executor = null;
        Optional<Executor> optional = executorRepository.findById(id);
        if (optional.isPresent())
            executor = optional.get();
        return executor;
    }

    public void save(Executor executor){
        executorRepository.save(executor);
    }

    public List<Executor> findAll(){
        return executorRepository.findAll();
    }
}
