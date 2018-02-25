package cn.acyou.iblog.model;


import cn.acyou.iblog.baseenum.BaseData;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论的实体类
 * @author youfang
 * @createTime 2017年7月27日 上午10:20:26
 */
public class Comment implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer cid;//评论的主键ID
	private Integer bid;//所属博客ID
	private String nickName;//昵称
	private String commentary;//评论内容
	private String qq;//qq
	private String ip;//ip地址
	private BaseData hide;//是否隐藏
	private Timestamp createTime;//创建时间
	private Integer fabulous;//赞
	private Integer tread;//踩
	public Comment() {
		super();
	}
	public Comment(Integer cid, Integer bid, String nickName, String commentary, String qq, String ip, BaseData hide,
			Timestamp createTime, Integer fabulous, Integer tread) {
		super();
		this.cid = cid;
		this.bid = bid;
		this.nickName = nickName;
		this.commentary = commentary;
		this.qq = qq;
		this.ip = ip;
		this.hide = hide;
		this.createTime = createTime;
		this.fabulous = fabulous;
		this.tread = tread;
	}
	@Override
	public String toString() {
		return "Comment [cid=" + cid + ", bid=" + bid + ", nickName=" + nickName + ", commentary=" + commentary
				+ ", qq=" + qq + ", ip=" + ip + ", hide=" + hide + ", createTime=" + createTime + ", fabulous="
				+ fabulous + ", tread=" + tread + "]";
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCommentary() {
		return commentary;
	}
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public BaseData getHide() {
		return hide;
	}
	public void setHide(BaseData hide) {
		this.hide = hide;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getFabulous() {
		return fabulous;
	}
	public void setFabulous(Integer fabulous) {
		this.fabulous = fabulous;
	}
	public Integer getTread() {
		return tread;
	}
	public void setTread(Integer tread) {
		this.tread = tread;
	}
	



}
