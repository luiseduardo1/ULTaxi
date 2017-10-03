package ca.ulaval.glo4003.ws.infrastructure.user;

import java.util.HashMap;
import java.util.Map;

public class TokenRepositoryInMemory implements TokenRepository {

    private Map<String, String> tokens = new HashMap<>();

    @Override
    public void save(String token, String id) {
        tokens.putIfAbsent(id, token);
    }

    @Override
    public void delete(String id) {
        tokens.remove(id);
    }

}
