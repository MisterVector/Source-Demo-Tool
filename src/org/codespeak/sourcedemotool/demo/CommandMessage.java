package org.codespeak.sourcedemotool.demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
    
    /**
     * Gets this command message as a series of bytes in little endian order
     * @param networkProtocol the network protocol to obtain the command ID from
     * @return an array of bytes in little endian order
     */
    public byte[] getBytes(int networkProtocol) {
        return getBytes(-1, networkProtocol);
    }

    /**
     * Gets this command message as a series of bytes in little endian order
     * @param alteredTickCount alternative tick count value to use when returning bytes
     * @param networkProtocol the network protocol to obtain the command ID from
     * @return an array of bytes in little endian order
     */
    public byte[] getBytes(int alteredTickCount, int networkProtocol) {
        int alloc = 5;
        
        if (data.length > 0) {
            alloc += 4;
        }
        
        byte commandId = commandType.getId(networkProtocol);
        ByteBuffer bb = ByteBuffer.allocate(alloc + startData.length + data.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(commandId);
        bb.putInt(alteredTickCount > -1 ? alteredTickCount : tickCount);
        bb.put(startData);
        
        if (data.length > 0) {
            bb.putInt(data.length);
            bb.put(data);
        }
        
        return bb.array();
    }

}
