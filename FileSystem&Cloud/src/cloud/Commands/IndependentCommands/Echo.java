package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import java.util.StringTokenizer;

public class Echo implements Command {
    private String output;
    private final String command;
    
    public Echo(String com) {
        command = com;
        this.parseCommand();
    }
    
    private void parseCommand() {
        StringTokenizer mStringTokenizer = new StringTokenizer(command);
        mStringTokenizer.nextElement();
        StringBuilder sb = new StringBuilder(command.length() - 5);

        while (mStringTokenizer.hasMoreTokens()) {
            String token = mStringTokenizer.nextToken();
            if(token.compareTo("-POO") != 0)
                sb.append(token).append(" ");
        }

        output = sb.toString();
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(null, null);
        cmd.echo(output);
    }
}
