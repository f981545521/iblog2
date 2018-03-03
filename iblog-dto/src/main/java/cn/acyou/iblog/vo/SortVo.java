package cn.acyou.iblog.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author youfang
 * @date 2018-03-03 22:44
 **/
public class SortVo extends Vo{
    private static final long serialVersionUID = 2659976724834723269L;
    private Integer id;//主键ID
    private String sortName;//分类名称
    private Integer idUser;//所属用户ID
    private String description;//描述
    private Timestamp createtime;//创建时间
    private Date modifiedtime;
    private Integer version;
    private Integer totalCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Date getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
