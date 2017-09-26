package ca.ulaval.glo4003.ws.infrastructure.command;

import ca.ulaval.glo4003.ws.domain.command.Command;
import ca.ulaval.glo4003.ws.domain.command.CommandRepository;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRepositoryInMemory implements CommandRepository {

    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void save(Command command) {
        commands.put(command.getId(), command);
    }

    public List<Command> findAll() {
        return Lists.newArrayList(commands.values());
    }

    public Command findById(String id){
        return commands.get(id);
    }
}
