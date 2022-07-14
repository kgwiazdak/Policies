package mcdonald.example.Policies.repository;
import mcdonald.example.Policies.domain.Policy;
import java.util.Optional;

public interface PolicyRepositoryPort {
    Policy save(Policy policy);
    void delete(int id);
    Optional<Policy> get(int id);
}
