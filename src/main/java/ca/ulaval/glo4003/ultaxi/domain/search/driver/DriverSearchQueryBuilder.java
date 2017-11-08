package ca.ulaval.glo4003.ultaxi.domain.search.driver;

public interface DriverSearchQueryBuilder {

    DriverSearchQuery build();

    DriverSearchQueryBuilder withFirstName(String firstName);

    DriverSearchQueryBuilder withLastName(String lastName);

    DriverSearchQueryBuilder withSocialInsuranceNumber(String socialInsuranceNumber);
}
