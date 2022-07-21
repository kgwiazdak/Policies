package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.config.PolicyAndPersonExceptionHandler;
import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.service.PersonService;
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
        String outputJson = "{\"id\": 1," + "  \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        Person person = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personService.get(1)).thenReturn(person);
        // when & then
        mockMvc.perform(get("/api/person/{id}", id).accept(MediaType.APPLICATION_JSON)).andExpect(content().json(outputJson)).andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        // given
        final int id = 1;
        String inputJson = "{\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        Person person = new Person(id, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        // when & then
        mockMvc.perform(put("/api/person/{id}", id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenUpdateThrowsDataNotInDatabaseException() throws Exception {
        // given
        final int id = 1;
        String inputJson = "{\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        Person person = new Person(id, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
//        when(personService.update(person)).thenThrow(new DataNotInDatabase("Sth wrong", id));
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(personService).update(person);
        // when & then
        mockMvc.perform(put("/api/person/{id}", id).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isNotFound());
        verify(personService).update(person);
    }


    @Test
    void shouldPostPerson() throws Exception {
        // given
        String inputJson = "{\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        Person personExpectedInput = new Person(-1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        Person personExpectedOutput = new Person(2, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        String jsonExpectedOutput = "{\"id\": 2," + " \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";

        when(personService.add(personExpectedInput)).thenReturn(personExpectedOutput);
        // when & then
        mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(inputJson)).andExpect(status().isCreated())
                .andExpect(content().json(jsonExpectedOutput));
    }


    @Test
    void shouldDeletePerson() throws Exception {
        // given
        int id = 0;
        Person person = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personService.get(id)).thenReturn(person);
        // when & then
        mockMvc.perform(delete("/api/person/{id}", id)).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404IfPersonNotInDatabase() throws Exception {
        // given
        int id = 0;
        doThrow(new DataNotInDatabase("Sth wrong", id)).when(personService).delete(id);
        // when & then
        mockMvc.perform(delete("/api/person/{id}", id)).andExpect(status().isNotFound());
        verify(personService).delete(id);
    }


    @Test
    void findAll() throws Exception {
        // given
        String outputJson = "[{\"id\": 1," + " \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}]";
        Person person = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personService.findAll()).thenReturn(List.of(person));
        // when & then
        mockMvc.perform(get("/api/person")).andExpect(status().isOk()).andExpect(content().json(outputJson));
    }
}