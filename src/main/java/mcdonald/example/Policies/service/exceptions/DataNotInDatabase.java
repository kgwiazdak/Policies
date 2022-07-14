package mcdonald.example.Policies.service.exceptions;

public class DataNotInDatabase extends RuntimeException{
    public DataNotInDatabase(String entity, int id) {
        super(String.format("%s with id %s not in database", entity, id));

    }
}
