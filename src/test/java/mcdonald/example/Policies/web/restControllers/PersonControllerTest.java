package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.config.PolicyAndPersonExceptionHandler;
import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.service.PersonService;
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
class PersonControllerTest {

    @Autowired
    PolicyAndPersonExceptionHandler policyAndPersonExceptionHandler;

    MockMvc mockMvc;

    @Autowired
    private PersonController personControllerSut;
    @MockBean
    private PersonService personService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personControllerSut).setControllerAdvice(policyAndPersonExceptionHandler).build();
    }

    @Test
    void shouldGetPersonIfPersonIsInDatabase() throws Exception {
        // given
        final int id = 1;
        String outputJson = TestDataGenerator.TEST_JSON_PERSON_ONE;
        Person person = TestDataGenerator.TEST_PERSON_ONE;
        when(personService.get(1)).thenReturn(person);
        // when & then
        mockMvc.perform(get(TestDataGenerator.TEST_PERSON_URL_WITH_ID, id).accept(MediaType.APPLICATION_JSON)).andExpect(content().json(outputJson)).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        // given
        final int id = 1;
        String inputJson = TestDataGenerator.TEST_JSON_PERSON_WITHOUT_ID;
        Person person = TestDataGenerator.TEST_PERSON_ONE;
        // when & then
        mockMvc.perform(put(TestDataGenerator.TEST_PERSON_URL_WITH_ID, id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenUpdateThrowsDataNotInDatabaseException() throws Exception {
        // given
        final int id = 1;
        String inputJson = TestDataGenerator.TEST_JSON_PERSON_ONE;
        Person person = TestDataGenerator.TEST_PERSON_ONE;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(personService).update(person);
        // when & then
        mockMvc.perform(put(TestDataGenerator.TEST_PERSON_URL_WITH_ID, id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNotFound());
        verify(personService).update(person);
    }


    @Test
    void shouldPostPerson() throws Exception {
        // given
        String inputJson = TestDataGenerator.TEST_JSON_PERSON_WITHOUT_ID;
        Person personExpectedInput = TestDataGenerator.TEST_PERSON_DEFAULT_ID;
        Person personExpectedOutput = TestDataGenerator.TEST_PERSON_TWO;
        String jsonExpectedOutput = TestDataGenerator.TEST_JSON_PERSON_TWO;

        when(personService.add(personExpectedInput)).thenReturn(personExpectedOutput);
        // when & then
        mockMvc.perform(post(TestDataGenerator.TEST_PERSON_URL_WITHOUT_ID).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isCreated())
                .andExpect(content().json(jsonExpectedOutput));
    }


    @Test
    void shouldDeletePerson() throws Exception {
        // given
        int id = 1;
        Person person = TestDataGenerator.TEST_PERSON_ONE;
        when(personService.get(id)).thenReturn(person);
        // when & then
        mockMvc.perform(delete(TestDataGenerator.TEST_PERSON_URL_WITH_ID, id)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404IfPersonNotInDatabase() throws Exception {
        // given
        int id = 0;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(personService).delete(id);
        // when & then
        mockMvc.perform(delete(TestDataGenerator.TEST_PERSON_URL_WITH_ID, id)).andExpect(status().isNotFound());
        verify(personService).delete(id);
    }


    @Test
    void findAll() throws Exception {
        // given
        String outputJson = TestDataGenerator.TEST_JSON_PERSON_LIST;
        Person person = TestDataGenerator.TEST_PERSON_ONE;
        when(personService.findAll()).thenReturn(List.of(person));
        // when & then
        mockMvc.perform(get(TestDataGenerator.TEST_PERSON_URL_WITHOUT_ID)).andExpect(status().isOk()).andExpect(content().json(outputJson));

    }
}