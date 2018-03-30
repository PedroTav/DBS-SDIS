package main.backup;

public class MessageHeader {
	
	private String MessageType;
	private String Version;
	private String SenderId;
	private String FileId;
	private String ChunkNo;
	private String ReplicationDeg;
	
	
	public MessageHeader(String MessageType, String Version, String SenderId, String FileId, String ChunkNo, String ReplicationDeg) {
	
		this.setMessageType(MessageType);
		this.setVersion(Version);
		this.setSenderId(SenderId);
		this.setFileId(FileId);
		this.setChunkNo(ChunkNo);
		this.setReplicationDeg(ReplicationDeg);

	}


	public String getMessageType() {
		return MessageType;
	}


	public void setMessageType(String messageType) {
		MessageType = messageType;
	}


	public String getVersion() {
		return Version;
	}


	public void setVersion(String version) {
		Version = version;
	}


	public String getSenderId() {
		return SenderId;
	}


	public void setSenderId(String senderId) {
		SenderId = senderId;
	}


	public String getFileId() {
		return FileId;
	}


	public void setFileId(String fileId) {
		FileId = fileId;
	}


	public String getChunkNo() {
		return ChunkNo;
	}


	public void setChunkNo(String chunkNo) {
		ChunkNo = chunkNo;
	}


	public String getReplicationDeg() {
		return ReplicationDeg;
	}


	public void setReplicationDeg(String replicationDeg) {
		ReplicationDeg = replicationDeg;
	}
}
