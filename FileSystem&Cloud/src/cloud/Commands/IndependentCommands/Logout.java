/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.Commands.IndependentCommands;

import cloud.Commands.Command;
import cloud.CommandsConfiguration.CommandConfiguration;
import cloud.Exceptions.GuestLogoutException;
import cloud.Users.UserManagement;

public class Logout implements Command {
    private final UserManagement mUserManagement;
    
    public Logout(UserManagement um) {
        mUserManagement = um;
    }

    @Override
    public void execute() {
        CommandConfiguration cmd = new CommandConfiguration(mUserManagement, null);
        
        try {
            cmd.logout();
        } catch (GuestLogoutException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }
}
