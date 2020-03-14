package org.codespeak.sourcedemotool.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.codespeak.sourcedemotool.SourceDemoTool;

/**
 * A class representing the contents of a demo file
 *
 * @author Vector
 */
public class DemoFile {

    private DemoHeader header;
    private List<CommandMessage> commandMessages = new LinkedList<CommandMessage>();

    public DemoFile(DemoHeader header, List<CommandMessage> commandMessages) {
        this.header = header;
        this.commandMessages = commandMessages;
    }
    /**
     * Gets the header of this demo
     * @return header of this demo
     */
    public DemoHeader getHeader() {
        return header;
    }
    
    /**
     * Gets all command messages from this demo
     * @return all command messages from this demo
     */
    public List<CommandMessage> getCommandMessages() {
        return commandMessages;
    }
    
    private static byte readByte(FileInputStream fis) throws IOException {
        return (byte) fis.read();
    }
    
    private static int readInt(FileInputStream fis) throws IOException {
        byte[] bytes = new byte[4];
        fis.read(bytes);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
    
    private static float readFloat(FileInputStream fis) throws IOException {
        byte[] bytes = new byte[4];
        fis.read(bytes);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getFloat();
    }
    
    private static String readString(FileInputStream fis, int numBytes) throws IOException {
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        return new String(bytes);
    }
    
    /**
     * Gets the contents of a demo file
     * @param demoFile file object of a demo file
     * @return contents of a demo file
     */
    public static DemoFile getDemoFile(File demoFile) {
        try (FileInputStream fis = new FileInputStream(demoFile)) {
            String headerName = readString(fis, 8);
            int demoProtocol = readInt(fis);
            int networkProtocol = readInt(fis);
            String serverName = readString(fis, 260);
            String clientName = readString(fis, 260);
            String mapName = readString(fis, 260);
            String gameDirectory = readString(fis, 260);
            float playbackTime = readFloat(fis);
            int ticks = readInt(fis);
            int frames = readInt(fis);
            int signOnLength = readInt(fis);

            DemoHeader header = new DemoHeader(headerName, demoProtocol, networkProtocol, serverName, clientName, mapName,gameDirectory,
                                    playbackTime, ticks, frames, signOnLength);
            
            List<CommandMessage> commandMessages = new LinkedList<CommandMessage>();
            boolean quitLoop = false;

            while (true) {
                byte commandId = readByte(fis);
                CommandTypes command = CommandTypes.getCommand(commandId, networkProtocol);
                int gameTick = readInt(fis);
                byte[] startData = new byte[0];
                byte[] bytes = new byte[0];

                switch (command) {
                    case DEM_SYNCTICK:
                        
                        break;
                    case DEM_SIGNON:
                    case DEM_PACKET:
                    case DEM_CONSOLECMD:
                    case DEM_CUSTOMDATA:
                    case DEM_USERCMD:
                    case DEM_DATATABLES:
                    case DEM_STRINGTABLES:
                        int startByteCount = 0;

                        if (command == CommandTypes.DEM_PACKET || command == CommandTypes.DEM_SIGNON) {
                            startByteCount = 84;
                        } else if (command == CommandTypes.DEM_USERCMD) {
                            startByteCount = 4;
                        }

                        if (startByteCount > 0) {
                            startData = new byte[startByteCount];
                            fis.read(startData);
                        }
                        
                        int commandMessageLength = readInt(fis);
                        bytes = new byte[commandMessageLength];
                        fis.read(bytes);

                        break;
                    case DEM_STOP:
                        quitLoop = true;
                        
                        break;
                    default:
                        System.out.println("Found an invalid command ID. Breaking with current tick: " + gameTick);
                        quitLoop = true;
                        
                        break;
                }

                CommandMessage commandMessage = new CommandMessage(command, gameTick, startData, bytes);
                commandMessages.add(commandMessage);

                if (quitLoop) {
                    break;
                }
            }
            
            return new DemoFile(header, commandMessages);
        } catch (IOException ex) {
            SourceDemoTool.getLogger().log(Level.WARNING, "Error when loading demo file!", ex);
        }
        
        return null;
    }
    
}
