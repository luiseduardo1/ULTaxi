package ca.ulaval.glo4003.ws.domain.command;

import java.util.List;

public interface CommandRepository {

    List<Command> findAll();

    Command findById(String id);

    void save(Command command);
}
