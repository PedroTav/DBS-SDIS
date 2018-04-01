package main.backup;

import java.util.ArrayList;

public class Message {
	
	public enum MsgType {
		PUTCHUNK, STORED,
		GETCHUNK, CHUNK,
		DELETE, REMOVED
	}
	
	private MsgType messageType;
	private String version;
	private String senderId;
	private String fileId;
	private String chunkNo;
	private String replicationDeg;
	private byte[] data;
	private String CRLF = "\r\n";
	
	public Message(MsgType messageType, String version, String senderId, String fileId, String chunkNo, String replicationDeg, byte[] data) {
	
        if(messageType == MsgType.PUTCHUNK) {
            this.setMessageType(messageType);
            this.setVersion(version);
            this.setSenderId(senderId);
            this.setFileId(fileId);
            this.setChunkNo(chunkNo);
            this.setReplicationDeg(replicationDeg);
            this.setData(data);
        } else {
            System.out.println("Wrong args for PUTCHUNK message");
        }
	}
		
	public Message(MsgType messageType, String version, String senderId, String fileId, String chunkNo) {
	
        if((messageType == MsgType.STORED) || (messageType == MsgType.GETCHUNK) || (messageType == MsgType.REMOVED)) {
            this.setMessageType(messageType);
            this.setVersion(version);
            this.setSenderId(senderId);
            this.setFileId(fileId);
            this.setChunkNo(chunkNo);
        } else {
            System.out.println("Wrong args for" + messageType + "message");
        }
	}
	
	public Message(MsgType messageType, String version, String senderId, String fileId, String chunkNo, byte[] data) {
	
        if(messageType == MsgType.CHUNK) {
            this.setMessageType(messageType);
            this.setVersion(version);
            this.setSenderId(senderId);
            this.setFileId(fileId);
            this.setChunkNo(chunkNo);
            this.setData(data);
        } else {
            System.out.println("Wrong args for CHUNK message");
        }
	}
	
	public Message(MsgType messageType, String version, String senderId, String fileId) {
	
        if(messageType == MsgType.DELETE) {
            this.setMessageType(messageType);
            this.setVersion(version);
            this.setSenderId(senderId);
            this.setFileId(fileId);
        } else {
            System.out.println("Wrong args for DELETE message");
        }
	}
	
	public MsgType getMessageType() {
		return this.messageType;
	}


	public void setMessageType(MsgType messageType) {
		this.messageType = messageType;
	}


	public String getVersion() {
		return this.version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getSenderId() {
		return this.senderId;
	}


	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public String getFileId() {
		return this.fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getChunkNo() {
		return this.chunkNo;
	}


	public void setChunkNo(String chunkNo) {
		this.chunkNo = chunkNo;
	}

	public String getReplicationDeg() {
		return this.replicationDeg;
	}

	public void setReplicationDeg(String replicationDeg) {
		this.replicationDeg = replicationDeg;
	}
	
	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String toString() {
	
        String temp = "";
	
        switch(this.messageType){
            case PUTCHUNK:
                temp = this.messageType + " " + this.version + " " + this.senderId + " " + this.fileId + " " + this.chunkNo + " " + this.replicationDeg + " " + this.CRLF + this.CRLF + this.data;
                break;
            case CHUNK:
                temp = messageType + " " + this.version + " " + this.senderId + " " + this.fileId + " " + this.CRLF + this.CRLF + this.data;
                break;
            case DELETE:
                temp = messageType + " " + this.version + " " + this.senderId + " " + this.CRLF + this.CRLF;
                break;
            case STORED:
            case GETCHUNK:
            case REMOVED:
                temp = this.messageType + " " + this.version + " " + this.senderId + " " + this.fileId + " " + this.chunkNo + " " + this.CRLF + this.CRLF;
                break;
            default:
                temp = null;
                break;
        }
        
        return temp;
	}
}
