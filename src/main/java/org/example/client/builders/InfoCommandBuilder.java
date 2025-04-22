package org.example.client.builders;

import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.InfoCommand;
import org.example.contract.exceptions.InvalidArgumentException;

public class InfoCommandBuilder implements CommandBuilder {
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        return new InfoCommand(TerminalManager.getUser());
    }
}
