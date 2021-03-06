package org.codespeak.sourcedemotool.demo;

/**
 * A class defining a command ID and the protocol version it supports
 *
 * @author Vector
 */
public class CommandVersion {
   
    private byte commandId;
    private int protocolVersion;
    
    public CommandVersion(byte commandId, int protocolVersion) {
        this.commandId = commandId;
        this.protocolVersion = protocolVersion;
    }
    
    /**
     * Gets the ID of this command
     * @return ID of this command
     */
    public byte getCommandId() {
        return commandId;
    }
    
    /**
     * Gets the protocol version of this command
     * @return protocol version of this command
     */
    public int getProtocolVersion() {
        return protocolVersion;
    }
    
}
