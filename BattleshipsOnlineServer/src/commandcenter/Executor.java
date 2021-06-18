package commandcenter;

import commandcenter.command.Command;

public class Executor {

    public String execute(Command cmd) {
        return cmd.execute();
    }
}
