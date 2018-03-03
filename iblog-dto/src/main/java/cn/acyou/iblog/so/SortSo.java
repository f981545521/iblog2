package cn.acyou.iblog.so;

/**
 * @author youfang
 * @date 2018-03-03 22:52
 **/
public class SortSo extends So{

    private static final long serialVersionUID = 7615649805029039467L;

    /**
     * 主键ID
     */
    private Integer id;
    /**
     * UserId
     */
    private Integer idUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
