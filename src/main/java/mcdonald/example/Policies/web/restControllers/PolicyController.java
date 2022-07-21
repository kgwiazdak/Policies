package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.service.PolicyService;
import mcdonald.example.Policies.service.implementation.IdGenerator;
import mcdonald.example.Policies.web.request.PolicyRequest;
import mcdonald.example.Policies.web.response.PolicyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    private final IdGenerator idGenerator;

    public PolicyController(PolicyService policyServiceImp, IdGenerator idGenerator) {
        this.policyService = policyServiceImp;
        this.idGenerator = idGenerator;
    }

    private Policy fromRequestToPolicy(@RequestBody PolicyRequest request, @PathVariable int id) {
        return new Policy(
                id,
                request.getName(),
                request.getPrice(),
                request.getFirstName(),
                request.getLastName()
        );
    }

    private PolicyResponse fromPolicyToResponse(Policy policy) {
        return new PolicyResponse(
                policy.getId(),
                policy.getName(),
                policy.getPrice(),
                policy.getFirstName(),
                policy.getLastName()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PolicyResponse> get(@PathVariable int id) {
        return new ResponseEntity<>(fromPolicyToResponse(policyService.get(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid PolicyRequest request, @PathVariable int id) {
        policyService.update(fromRequestToPolicy(request, id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PolicyResponse> post(@RequestBody @Valid PolicyRequest request) {
        return new ResponseEntity<>(fromPolicyToResponse(policyService.add(fromRequestToPolicy(request, -1))), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        policyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Collection<PolicyResponse>> findAll() {
        return ResponseEntity.ok(policyService.findAll().stream().map(this::fromPolicyToResponse).collect(Collectors.toList()));
    }
}
