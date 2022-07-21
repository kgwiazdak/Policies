package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.config.PolicyAndPersonExceptionHandler;
import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.service.PolicyService;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import mcdonald.example.Policies.testutil.TestDataGenerator;
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
        String outputJson = TestDataGenerator.TEST_JSON_POLICY_ONE;
        Policy policy = TestDataGenerator.TEST_POLICY_ONE;
        when(policyService.get(1)).thenReturn(policy);
        // when & then
        mockMvc.perform(get(TestDataGenerator.TEST_POLICY_URL_WITH_ID, id).accept(MediaType.APPLICATION_JSON)).andExpect(content().json(outputJson)).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePolicy() throws Exception {
        // given
        final int id = 1;
        String inputJson = TestDataGenerator.TEST_JSON_POLICY_WITHOUT_ID;
        Policy policy = TestDataGenerator.TEST_POLICY_ONE;
        // when & then
        mockMvc.perform(put(TestDataGenerator.TEST_POLICY_URL_WITH_ID, id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenUpdateThrowsDataNotInDatabaseException() throws Exception {
        // given
        final int id = 1;
        String inputJson = TestDataGenerator.TEST_JSON_POLICY_WITHOUT_ID;
        Policy policy = TestDataGenerator.TEST_POLICY_ONE;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(policyService).update(policy);
        // when & then
        mockMvc.perform(put(TestDataGenerator.TEST_POLICY_URL_WITH_ID, id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNotFound());
        verify(policyService).update(policy);
    }


    @Test
    void shouldPostPolicy() throws Exception {
        // given
        String inputJson = TestDataGenerator.TEST_JSON_POLICY_WITHOUT_ID;
        Policy policyExpectedInput = TestDataGenerator.TEST_POLICY_DEFAULT_ID;
        Policy policyExpectedOutput = TestDataGenerator.TEST_POLICY_TWO;
        String jsonExpectedOutput = TestDataGenerator.TEST_JSON_POLICY_TWO;
        when(policyService.add(policyExpectedInput)).thenReturn(policyExpectedOutput);
        // when & then
        mockMvc.perform(post(TestDataGenerator.TEST_POLICY_URL_WITHOUT_ID).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isCreated())
                .andExpect(content().json(jsonExpectedOutput));
    }


    @Test
    void shouldDeletePolicy() throws Exception {
        // given
        int id = 1;
        Policy policy = TestDataGenerator.TEST_POLICY_ONE;
        when(policyService.get(id)).thenReturn(policy);
        // when & then
        mockMvc.perform(delete(TestDataGenerator.TEST_POLICY_URL_WITH_ID, id)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404IfPolicyNotInDatabase() throws Exception {
        // given
        int id = 0;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(policyService).delete(id);
        // when & then
        mockMvc.perform(delete(TestDataGenerator.TEST_POLICY_URL_WITH_ID, id)).andExpect(status().isNotFound());
        verify(policyService).delete(id);
    }


    @Test
    void findAll() throws Exception {
        // given
        String outputJson = TestDataGenerator.TEST_JSON_POLICY_LIST;
        Policy policy = TestDataGenerator.TEST_POLICY_ONE;
        when(policyService.findAll()).thenReturn(List.of(policy));
        // when & then
        mockMvc.perform(get(TestDataGenerator.TEST_POLICY_URL_WITHOUT_ID)).andExpect(status().isOk()).andExpect(content().json(outputJson));
    }
}