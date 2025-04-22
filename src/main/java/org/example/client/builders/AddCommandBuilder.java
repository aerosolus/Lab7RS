package org.example.client.builders;

import org.example.client.dataManaging.WorkerCreator;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.AddCommand;
import org.example.contract.commands.Command;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.InvalidArgumentException;

public class AddCommandBuilder implements CommandBuilder {

    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        WorkerCreator workerCreator = new WorkerCreator();
        WorkerDTO workerDTO = workerCreator.createWorker();
        User user = TerminalManager.getUser();
        return new AddCommand(workerDTO, user);
    }
}
