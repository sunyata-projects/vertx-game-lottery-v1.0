package org.sunyata.octopus;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusInMessage {
    private int cmd;//0 4
    private int serial;//1 4
    private float version;//2 4
    private int length;//3 4
    private byte[] bytes;

    public int getLength() {
        return length;
    }

    public OctopusInMessage setLength(int length) {
        this.length = length;
        return this;
    }


    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getCmd() {
        return cmd;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getSerial() {
        return serial;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public float getVersion() {
        return version;
    }

    public void setBody(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBody() {
        return bytes;
    }
}
