package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import java.util.HashMap;
import java.util.Map;

public class TokenRepositoryInMemory implements TokenRepository {

    private Map<String, String> tokens = new HashMap<>();

    @Override
    public void save(String id, String token) {
        tokens.putIfAbsent(id, token);
    }

    @Override
    public void delete(String id) {
        tokens.remove(id);
    }

    @Override
    public String getToken(String id) {
        return tokens.get(id);
    }

}
