package main.backup;

import java.util.ArrayList;

public class MessageHeader {
	
	public enum MsgType {
		PUTCHUNK, STORED,
		GETCHUNK, CHUNK,
		DELETE, REMOVED
	}
	
	private MsgType MessageType;
	private byte[] Version = {1,0};
	private long SenderId;
	private int FileId;
	private long ChunkNo;
	private byte ReplicationDeg;
	private int CRLF = 0xDA;
	
	public MessageHeader(MsgType MessageType, byte[] Version, long SenderId, int FileId, long ChunkNo, byte ReplicationDeg) {
	
		this.setMessageType(MessageType);
		this.setVersion(Version);
		this.setSenderId(SenderId);
		this.setFileId(FileId);
		this.setChunkNo(ChunkNo);
		this.setReplicationDeg(ReplicationDeg);

	}
	
	public String makeSequence(String head) {
		return head + CRLF;
	}

	public MsgType getMessageType() {
		return MessageType;
	}


	public void setMessageType(MsgType messageType) {
		MessageType = messageType;
	}


	public byte[] getVersion() {
		return Version;
	}


	public void setVersion(byte[] version) {
		Version = version;
	}


	public long getSenderId() {
		return SenderId;
	}


	public void setSenderId(long senderId) {
		SenderId = senderId;
	}


	public int getFileId() {
		return FileId;
	}


	public void setFileId(int fileId) {
		FileId = fileId;
	}


	public long getChunkNo() {
		return ChunkNo;
	}


	public void setChunkNo(long chunkNo) {
		ChunkNo = chunkNo;
	}

	public byte getReplicationDeg() {
		return ReplicationDeg;
	}

	public void setReplicationDeg(byte replicationDeg) {
		ReplicationDeg = replicationDeg;
	}
	
	public int toString(ArrayList sequence) {
		return sequence.size();
	}
}
