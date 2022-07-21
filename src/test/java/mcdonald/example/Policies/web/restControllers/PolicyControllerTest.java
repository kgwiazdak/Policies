package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.config.PolicyAndPersonExceptionHandler;
import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.service.PolicyService;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyControllerTest {

    @Autowired
    PolicyAndPersonExceptionHandler policyAndPersonExceptionHandler;

    MockMvc mockMvc;
    @Autowired
    private PolicyController policyControllerSut;
    @MockBean
    private PolicyService policyService;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(policyControllerSut).setControllerAdvice(policyAndPersonExceptionHandler).build();
    }

    @Test
    void shouldGetPolicyIfPolicyIsInDatabase() throws Exception {
        // given
        final int id = 1;
        String outputJson = "{" + "\"id\": 1," + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        Policy policy = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        when(policyService.get(1)).thenReturn(policy);
        // when & then
        mockMvc.perform(get("/api/policies/{id}", id).accept(MediaType.APPLICATION_JSON)).andExpect(content().json(outputJson)).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePolicy() throws Exception {
        // given
        final int id = 1;
        String inputJson = "{" + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        Policy policy = new Policy(id, "myPolicy", 18, "Jan", "Kowalski");
        // when & then
        mockMvc.perform(put("/api/policies/{id}", id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenUpdateThrowsDataNotInDatabaseException() throws Exception {
        // given
        final int id = 1;
        String inputJson = "{\"name\": \"myPolicy\"," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        Policy policy = new Policy(id, "myPolicy", 18, "Jan", "Kowalski");
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(policyService).update(policy);
        // when & then
        mockMvc.perform(put("/api/policies/{id}", id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNotFound());
        verify(policyService).update(policy);
    }


    @Test
    void shouldPostPolicy() throws Exception {
        // given
        String inputJson = "{\"name\": \"myPolicy\"," + " \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        Policy policyExpectedInput = new Policy(-1, "myPolicy", 18, "Jan", "Kowalski");
        Policy policyExpectedOutput = new Policy(2, "myPolicy", 18, "Jan", "Kowalski");
        String jsonExpectedOutput = "{\"id\": 2," + " \"name\": myPolicy," + " \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";

        when(policyService.add(policyExpectedInput)).thenReturn(policyExpectedOutput);
        // when & then
        mockMvc.perform(post("/api/policies").contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isCreated())
                .andExpect(content().json(jsonExpectedOutput));
    }


    @Test
    void shouldDeletePolicy() throws Exception {
        // given
        int id = 0;
        Policy policy = new Policy(0, "myPolicy", 18, "Jan", "Kowalski");
        when(policyService.get(id)).thenReturn(policy);
        // when & then
        mockMvc.perform(delete("/api/policies/{id}", id)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404IfPolicyNotInDatabase() throws Exception {
        // given
        int id = 0;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(policyService).delete(id);
        // when & then
        mockMvc.perform(delete("/api/policies/{id}", id)).andExpect(status().isNotFound());
        verify(policyService).delete(id);
    }


    @Test
    void findAll() throws Exception {
        // given
        String outputJson = "[{\"id\": 1," + " \"name\": myPolicy," + " \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}]";
        Policy policy = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        when(policyService.findAll()).thenReturn(List.of(policy));
        // when & then
        mockMvc.perform(get("/api/policies")).andExpect(status().isOk()).andExpect(content().json(outputJson));
    }
}