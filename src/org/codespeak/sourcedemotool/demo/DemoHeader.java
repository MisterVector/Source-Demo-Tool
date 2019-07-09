package org.codespeak.sourcedemotool.demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A class representing the header portion of a demo file
 *
 * @author Vector
 */
public class DemoHeader {

    private String headerName;
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
        int idx = input.indexOf("\\0");
        
        if (idx == -1) {
            return input;
        } else {
            return input.substring(idx > 0 ? idx - 1 : idx);            
        }
    }
    
    public DemoHeader(String headerName, int demoProtocol, int networkProtocol, String serverName, String clientName, String mapName, String gameDirectory,
                        float playbackTime, int ticks, int frames, int signOnLength) {
        this.headerName = headerName;
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
     * Gets the name of the header
     * @return name of the header
     */
    public String getHeaderName() {
        return stripString(headerName);
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

    /**
     * Gets this header as a series of bytes in little endian order
     * @return header as a series of bytes in little endian order
     */
    public byte[] getBytes() {
        ByteBuffer bb = ByteBuffer.allocate(1072);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(headerName.getBytes());
        bb.putInt(demoProtocol);
        bb.putInt(networkProtocol);
        bb.put(serverName.getBytes());
        bb.put(clientName.getBytes());
        bb.put(mapName.getBytes());
        bb.put(gameDirectory.getBytes());
        bb.putFloat(playbackTime);
        bb.putInt(ticks);
        bb.putInt(frames);
        bb.putInt(signOnLength);
        
        return bb.array();
    }
    
}
