package cn.acyou.iblog.so;

import java.util.List;

/**
 * @author youfang
 * @date 2018-03-04 12:49
 **/
public class UserSo extends So{
    private static final long serialVersionUID = 8017586569679922724L;

    private List<Integer> idList;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}
