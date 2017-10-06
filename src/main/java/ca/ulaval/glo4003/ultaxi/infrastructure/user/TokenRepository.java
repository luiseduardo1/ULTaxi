package ca.ulaval.glo4003.ultaxi.infrastructure.user;

public interface TokenRepository {

    void save(String id, String token);

    void delete(String id);

    String getToken(String id);

}
