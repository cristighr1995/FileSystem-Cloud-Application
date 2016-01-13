package cloud.Commands.CloudCommands;

import cloud.CloudServiceSystem.CloudService;
import cloud.Commands.Command;

public class Listcloud implements Command {
    private final CloudService mCloudService;
    
    public Listcloud(CloudService cs) {
        mCloudService = cs;
    }

    @Override
    public void execute() {
        mCloudService.lscl();
    }
    
}
