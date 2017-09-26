package ca.ulaval.glo4003.ws.infrastructure.contact.Command;

import ca.ulaval.glo4003.ws.domain.command.Command;
import ca.ulaval.glo4003.ws.domain.command.CommandRepository;
import ca.ulaval.glo4003.ws.domain.contact.Contact;
import ca.ulaval.glo4003.ws.infrastructure.command.CommandRepositoryInMemory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class CommandRepositoryInMemoryTest {

    @Mock
    private Command command;
    private CommandRepository commandRepository;

    @Before
    public void setUp() {
        commandRepository = new CommandRepositoryInMemory();

    }

    @Test
    public void givenCommand_whenSave_ThenReturnCommandInMemory() {
        commandRepository.save(command);

        Command foundCommand  = commandRepository.findById(command.getId());

        assertEquals(command, foundCommand);
    }

}
