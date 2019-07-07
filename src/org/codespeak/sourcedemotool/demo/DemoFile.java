package org.codespeak.sourcedemotool.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A class representing the contents of a demo file
 *
 * @author Vector
 */
public class DemoFile {

    private DemoHeader header;

    public DemoFile(DemoHeader header) {
        this.header = header;
    }

    /**
     * Gets the header of this demo
     * @return header of this demo
     */
    public DemoHeader getHeader() {
        return header;
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
            
            return new DemoFile(header);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}
