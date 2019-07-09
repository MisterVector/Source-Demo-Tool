package org.codespeak.sourcedemotool.demo;

/**
 * A class representing a command message
 *
 * @author Vector
 */
public class CommandMessage {
    
    private CommandTypes commandType;
    private int tickCount;
    private byte[] startData;
    private byte[] data;
    
    public CommandMessage(CommandTypes commandType, int tickCount, byte[] startData, byte[] data) {
        this.commandType = commandType;
        this.tickCount = tickCount;
        this.startData = startData;
        this.data = data;
    }

    /**
     * Gets the command type from this message
     * @return command type from this message
     */
    public CommandTypes getCommandType() {
        return commandType;
    }
    
    /**
     * Gets the tick count from this message
     * @return tick count from this message
     */
    public int getTickCount() {
        return tickCount;
    }
    
    /**
     * Gets the start data from this message
     * @return start data from this message
     */
    public byte[] getStartData() {
        return startData;
    }
    
    /**
     * Gets the data from this message
     * @return data from this message
     */
    public byte[] getData() {
        return data;
    }
    
}
