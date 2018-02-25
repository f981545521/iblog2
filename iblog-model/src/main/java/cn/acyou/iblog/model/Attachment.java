package cn.acyou.iblog.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**附件的实体类*/
public class Attachment implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer aid;
	private Integer uid;
	private Integer bid;
	private String fileName;
	private Long fileSize;
	private String filePath;
	private String description;
	private Timestamp createTime;
	private Timestamp downloadTime;
	public Attachment() {
		super();
	}
	public Attachment(Integer aid, Integer uid, Integer bid, String fileName, Long fileSize, String filePath,
			String description, Timestamp createTime, Timestamp downloadTime) {
		super();
		this.aid = aid;
		this.uid = uid;
		this.bid = bid;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.description = description;
		this.createTime = createTime;
		this.downloadTime = downloadTime;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(Timestamp downloadTime) {
		this.downloadTime = downloadTime;
	}
	@Override
	public String toString() {
		return "Attachment [aid=" + aid + ", uid=" + uid + ", bid=" + bid + ", fileName=" + fileName + ", fileSize="
				+ fileSize + ", filePath=" + filePath + ", description=" + description + ", createTime=" + createTime
				+ ", downloadTime=" + downloadTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
		return true;
	}
	
	

}
