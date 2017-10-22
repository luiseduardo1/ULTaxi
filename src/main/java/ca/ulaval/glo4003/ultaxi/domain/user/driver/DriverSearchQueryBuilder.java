package ca.ulaval.glo4003.ultaxi.domain.user.driver;

import java.util.List;

public interface DriverSearchQueryBuilder {

    List<Driver> findAll();

    DriverSearchQueryBuilder withFirstName(String firstName);

    DriverSearchQueryBuilder withLastName(String lastName);

    DriverSearchQueryBuilder withSocialInsuranceNumber(String socialInsuranceNumber);
}
