package org.codespeak.sourcedemotool.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A class representing the contents of a demo
 *
 * @author Vector
 */
public class DemoContents {

    private String header;
    private int demoProtocol;
    private int networkProtocol;
    private String serverName;
    private String clientName;
    private String mapName;
    private String gameDirectory;
    private float playbackTime;
    private int ticks;
    private int frames;
    private int signOnLength;
    
    private String stripString(String input) {
        int idx = input.indexOf("\0");
        
        if (idx == -1) {
            return input;
        } else {
            return input.substring(idx > 0 ? idx - 1 : idx);            
        }
    }
    
    public DemoContents(String header, int demoProtocol, int networkProtocol, String serverName, String clientName, String mapName, String gameDirectory,
                        float playbackTime, int ticks, int frames, int signOnLength) {
        this.header = header;
        this.demoProtocol = demoProtocol;
        this.networkProtocol = networkProtocol;
        this.serverName = serverName;
        this.clientName = clientName;
        this.mapName = mapName;
        this.gameDirectory = gameDirectory;
        this.playbackTime = playbackTime;
        this.ticks = ticks;
        this.frames = frames;
        this.signOnLength = signOnLength;
    }
    
    /**
     * Gets the demo header
     * @return demo header
     */
    public String getHeader() {
        return stripString(header);
    }

    /**
     * Gets the demo protocol version
     * @return demo protocol version
     */
    public int getDemoProtocol() {
        return demoProtocol;
    }
    
    /**
     * Gets the network protocol version
     * @return network protocol version
     */
    public int getNetworkProtocol() {
        return networkProtocol;
    }
    
    /**
     * Gets the server name
     * @return 
     */
    public String getServerName() {
        return stripString(serverName);
    }
    
    /**
     * Gets the client name
     * @return client name
     */
    public String getClientName() {
        return stripString(clientName);
    }
    
     /**
      * Gets the map name
      * @return map name
      */
    public String getMapName() {
        return stripString(mapName);
    }
    
    /**
     * Gets the game directory
     * @return game directory
     */
    public String getGameDirectory() {
        return stripString(gameDirectory);
    }
    
    /**
     * Gets the playback time in seconds
     * @return playback time in seconds
     */
    public float getPlaybackTime() {
        return playbackTime;
    }
    
    /**
     * Gets the number of ticks
     * @return number of ticks
     */
    public int getTicks() {
        return ticks;
    }
    
    /**
     * Gets the number of frames
     * @return number of frames
     */
    public int getFrames() {
        return frames;
    }
    
    /**
     * Gets the sign on length
     * @return sign on length
     */
    public int getSignonLength() {
        return signOnLength;
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
    
    public static DemoContents getDemoContents(FileInputStream fis) {
        try {
            String header = readString(fis, 8);
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
            
            return new DemoContents(header, demoProtocol, networkProtocol, serverName, clientName, mapName,gameDirectory,
                                    playbackTime, ticks, frames, signOnLength);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}
