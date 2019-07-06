package org.codespeak.sourcedemotool.demo;

/**
 *
 * @author Vector
 */
public enum CommandTypes {
 
    DEM_SIGNON(new CommandVersion(1, 7)),
    DEM_PACKET(new CommandVersion(2, 7)),
    DEM_SYNCTICK(new CommandVersion(3, 7)),
    DEM_CONSOLECMD(new CommandVersion(4, 7)),
    DEM_USERCMD(new CommandVersion(5, 7)),
    DEM_DATATABLES(new CommandVersion(6, 7)),
    DEM_STOP(new CommandVersion(7, 7)),
    DEM_CUSTOMDATA(new CommandVersion(8, 36)),
    DEM_STRINGTABLES(new CommandVersion(8, 14),
                     new CommandVersion(9, 36));
    
    private CommandVersion[] commandVersions;
    
    private CommandTypes(CommandVersion... commandVersions) {
        this.commandVersions = commandVersions;
    }
    
    /**
     * Gets all command type IDs and their protocol versions for this command type
     * @return all command type IDs and their protocol versions for this command type
     */
    public CommandVersion[] getCommandVersions() {
        return commandVersions;
    }
    
    /**
     * Gets a command type by the specified ID and protocol version
     * @param commandId command ID to search for
     * @param protocolVersion protocol version representing the command ID
     * @return command type for the specified command ID and protocol version
     */
    public static CommandTypes getCommand(int commandId, int protocolVersion) {
        CommandTypes foundType = null;
        int highestProtocolVersionFound = -1;
        
        for (CommandTypes type : values()) {
            CommandVersion[] commandVersions = type.getCommandVersions();
            
            for (CommandVersion cv : commandVersions) {
                int commandProtocol = cv.getProtocolVersion();

                // Check for the same command ID with the highest protocol version that is
                // within the range of the protocol version we are checking for
                if (cv.getCommandId() == commandId && commandProtocol <= protocolVersion) {
                    if (commandProtocol > highestProtocolVersionFound) {
                        highestProtocolVersionFound = commandProtocol;
                        foundType = type;
                    }
                }
            }
        }
        
        return foundType;
    }
    
    public static CommandTypes getLastCommand(int protocolVersion) {
        CommandTypes foundType = null;
        int highestCommandIdFound = -1;
        
        for (CommandTypes type : values()) {
            CommandVersion[] commandVersions = type.getCommandVersions();
            
            for (CommandVersion cv : commandVersions) {
                if (cv.getProtocolVersion() <= protocolVersion) {
                    int commandId = cv.getCommandId();
                    
                    if (cv.getCommandId() > highestCommandIdFound) {
                        highestCommandIdFound = commandId;
                        foundType = type;
                    }
                }
            }
        }
        
        return foundType;
    }
    
}
