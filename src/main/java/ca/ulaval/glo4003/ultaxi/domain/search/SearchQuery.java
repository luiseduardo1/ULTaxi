package ca.ulaval.glo4003.ultaxi.domain.search;

import ca.ulaval.glo4003.ultaxi.transfer.user.UserPersistenceDto;

public interface SearchQuery<T> {

    SearchResults<UserPersistenceDto> execute();
}
