package mcdonald.example.Policies.service;
import mcdonald.example.Policies.domain.Policy;

import java.util.Collection;

public interface PolicyService {
    Policy add(Policy policy);
    void update(Policy policy);
    void delete(int id);
    Policy get(int id);

    Collection<Policy> findAll();
}
