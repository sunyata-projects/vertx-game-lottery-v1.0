package org.sunyata.octopus;

/**
 * Created by leo on 17/4/17.
 */
public class OctopusOutMessage {
    private int cmd;//0 4
    private int serial;//1 8
    private int code;//2 4
    private int length;//3 4
    private byte[] body;

    public int getLength() {
        return length;
    }

    public OctopusOutMessage setLength(int length) {
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

    public void setBody(byte[] bytes) {
        this.body = bytes;
        this.length = bytes.length;
    }

    public byte[] getBody() {
        return body;
    }


    public int getCode() {
        return code;
    }

    public OctopusOutMessage setCode(int code) {
        this.code = code;
        return this;
    }

}
