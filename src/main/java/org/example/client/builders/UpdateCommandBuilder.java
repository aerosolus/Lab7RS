package org.example.client.builders;

import org.example.client.dataManaging.WorkerCreator;
import org.example.client.utility.CommandWithID;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.UpdateCommand;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.InvalidArgumentException;

public class UpdateCommandBuilder implements CommandBuilder, CommandWithID {
    @Override
    public Command build(String[] str) {
        if (str.length != 2) throw new InvalidArgumentException();
        if (checkArgForId(str[1])) {
            if (Long.parseLong(str[1]) <= 0) throw new NumberFormatException();
            WorkerCreator workerCreator = new WorkerCreator();
            WorkerDTO workerDTO = workerCreator.createWorker();
            return new UpdateCommand(Long.parseLong(str[1]), TerminalManager.getUser(), workerDTO);
        } else {
            throw new NumberFormatException();
        }
    }
}
