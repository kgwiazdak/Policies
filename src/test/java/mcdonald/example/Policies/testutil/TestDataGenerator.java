package mcdonald.example.Policies.testutil;

import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.domain.Policy;

public final class TestDataGenerator {

    public static final String TEST_PERSON_URL_WITHOUT_ID = "/api/person";
    public static final String TEST_PERSON_URL_WITH_ID = "/api/person/{id}";
    public static final String TEST_POLICY_URL_WITHOUT_ID = "/api/policies";
    public static final String TEST_POLICY_URL_WITH_ID = "/api/policies/{id}";


    public static final String TEST_JSON_PERSON_WITHOUT_ID = "{\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
    public static final String TEST_JSON_PERSON_ONE = "{\"id\": 1," + " \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
    public static final String TEST_JSON_PERSON_LIST = "[{\"id\": 1," + " \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}]";

    public static final String TEST_JSON_PERSON_TWO = "{\"id\": 2," + " \"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";

    public static final Person TEST_PERSON_DEFAULT_ID = new Person(-1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
    public static final Person TEST_PERSON_ONE = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
    public static final Person TEST_PERSON_TWO = new Person(2, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);


    public static final String TEST_JSON_POLICY_WITHOUT_ID = "{" + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
    public static final String TEST_JSON_POLICY_ONE = "{" + "\"id\": 1," + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
    public static final String TEST_JSON_POLICY_LIST = "[{" + "\"id\": 1," + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}]";
    public static final String TEST_JSON_POLICY_TWO = "{" + "\"id\": 2," + "  \"name\": \"myPolicy\"," + "  \"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";

    public static final Policy TEST_POLICY_DEFAULT_ID = new Policy(-1, "myPolicy", 18, "Jan", "Kowalski");
    public static final Policy TEST_POLICY_ONE = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
    public static final Policy TEST_POLICY_TWO = new Policy(2, "myPolicy", 18, "Jan", "Kowalski");


    private TestDataGenerator() {
    }

}
