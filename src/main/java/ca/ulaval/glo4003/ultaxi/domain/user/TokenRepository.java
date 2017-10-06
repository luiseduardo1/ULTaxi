package ca.ulaval.glo4003.ultaxi.domain.user;

public interface TokenRepository {

    void save(String id, String token);

    void delete(String id);

    String getToken(String id);

}
