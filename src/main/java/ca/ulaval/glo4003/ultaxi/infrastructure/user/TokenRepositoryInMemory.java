package ca.ulaval.glo4003.ultaxi.infrastructure.user;

import ca.ulaval.glo4003.ultaxi.domain.user.TokenRepository;

import java.util.HashMap;
import java.util.Map;

public class TokenRepositoryInMemory implements TokenRepository {

    private final Map<String, String> tokens = new HashMap<>();

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
