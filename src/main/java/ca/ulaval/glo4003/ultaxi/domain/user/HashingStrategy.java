package ca.ulaval.glo4003.ultaxi.domain.user;

public interface HashingStrategy {

    String hash(String password);

    boolean areEquals(String plainPassword, String hashedPassword);
}
