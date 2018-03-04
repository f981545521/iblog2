package cn.acyou.iblog.vo;

import java.util.Date;

/**
 * @author youfang
 * @date 2018-03-04 12:26
 **/
public class BlogVo extends Vo{
    private static final long serialVersionUID = 6780198574263558292L;
    private Integer id;
    private String title;
    private String content;
    private String excerpt;
    private String type;
    private Integer idUser;
    private Integer idSort;
    private Integer idAttachment;
    private String top;
    private String hide;
    private Integer fabulous;
    private Integer commentNumber;
    private String allowComment;
    private Date creationtime;
    private Date modifiedtime;
    private String version;

    private String author;
    private String sortName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdSort() {
        return idSort;
    }

    public void setIdSort(Integer idSort) {
        this.idSort = idSort;
    }

    public Integer getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(Integer idAttachment) {
        this.idAttachment = idAttachment;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public Integer getFabulous() {
        return fabulous;
    }

    public void setFabulous(Integer fabulous) {
        this.fabulous = fabulous;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    public Date getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
