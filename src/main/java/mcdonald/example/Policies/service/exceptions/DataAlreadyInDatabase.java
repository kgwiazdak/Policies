package mcdonald.example.Policies.service.exceptions;

public class DataAlreadyInDatabase extends RuntimeException {
    public DataAlreadyInDatabase(String entity, int id) {
        super(String.format("%s with id %s already in database", entity,id));
    }
}
