package ca.ulaval.glo4003.ultaxi.domain.user;

public interface HashingStrategy {

    String hash(String value);

    boolean areEquals(String plainValue, String hashedValue);
}
