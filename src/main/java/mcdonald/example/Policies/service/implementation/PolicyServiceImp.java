package mcdonald.example.Policies.service.implementation;

import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.repository.PolicyRepositoryPort;
import mcdonald.example.Policies.service.PolicyService;
import mcdonald.example.Policies.service.exceptions.DataAlreadyInDatabase;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class PolicyServiceImp implements PolicyService {
    private final PolicyRepositoryPort policyRepositoryPort;
    private final IdGenerator idGenerator;

    public PolicyServiceImp(PolicyRepositoryPort policyRepositoryPort, IdGenerator idGenerator) {
        this.policyRepositoryPort = policyRepositoryPort;
        this.idGenerator = idGenerator;
    }

    public Policy add(Policy policy) {
        policy.setId(idGenerator.generatePolicyID());
        if (policyRepositoryPort.get(policy.getId()).isPresent()) {
            throw new DataAlreadyInDatabase(
                    "policy", policy.getId()
            );
        }
        return policyRepositoryPort.save(policy);
    }

    public void update(Policy policy) {
        if (policyRepositoryPort.get(policy.getId()).isEmpty()) {
            throw new DataNotInDatabase(
                    "policy", policy.getId()
            );
        }
        policyRepositoryPort.save(policy);
    }

    public void delete(int id) {
        if (policyRepositoryPort.get(id).isEmpty()) {
            throw new DataNotInDatabase(
                    "policy", id
            );
        }
        policyRepositoryPort.delete(id);
    }

    public Policy get(int id) {
        return policyRepositoryPort.get(id).orElseThrow(()
                -> new DataNotInDatabase(
                "policy", id
        ));
    }

    @Override
    public Collection<Policy> findAll() {
        return policyRepositoryPort.findAll();
    }
}
