package cn.acyou.iblog.model;

import se.spagettikod.optimist.Identity;
import se.spagettikod.optimist.OptimisticLocking;
import se.spagettikod.optimist.Version;

/**
 * 文章
 * @author youfang
 * @date 2018-03-03 17:17
 **/
@OptimisticLocking("ib_blog")
public class Blog extends Po{

    private static final long serialVersionUID = -4434952304777985226L;
    /**
     * 主键ID
     */
    @Identity("id")
    private Integer id;
    private String title;
    private String content;
    private String excerpt;
    private String type;
    private String id_user;
    private String id_sort;
    private String id_attachment;
    private String top;
    private String hide;
    private String fabulous;
    private String comment_number;
    private String allow_comment;
    private String creationtime;
    private String modifiedtime;
    @Version("version")
    private String version;

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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_sort() {
        return id_sort;
    }

    public void setId_sort(String id_sort) {
        this.id_sort = id_sort;
    }

    public String getId_attachment() {
        return id_attachment;
    }

    public void setId_attachment(String id_attachment) {
        this.id_attachment = id_attachment;
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

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public String getComment_number() {
        return comment_number;
    }

    public void setComment_number(String comment_number) {
        this.comment_number = comment_number;
    }

    public String getAllow_comment() {
        return allow_comment;
    }

    public void setAllow_comment(String allow_comment) {
        this.allow_comment = allow_comment;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", type='" + type + '\'' +
                ", id_user='" + id_user + '\'' +
                ", id_sort='" + id_sort + '\'' +
                ", id_attachment='" + id_attachment + '\'' +
                ", top='" + top + '\'' +
                ", hide='" + hide + '\'' +
                ", fabulous='" + fabulous + '\'' +
                ", comment_number='" + comment_number + '\'' +
                ", allow_comment='" + allow_comment + '\'' +
                ", creationtime='" + creationtime + '\'' +
                ", modifiedtime='" + modifiedtime + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
