package ca.ulaval.glo4003.ultaxi.utils.hashing;

public interface HashingStrategy {

    String hash(String value);

    String hashWithRandomSalt(String value);

    boolean areEquals(String plainValue, String hashedValue);
}
