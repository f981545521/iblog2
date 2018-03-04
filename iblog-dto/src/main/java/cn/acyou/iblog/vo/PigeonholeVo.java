package cn.acyou.iblog.vo;

/**
 * 文章归档
 * @author youfang
 * @date 2018-03-03 23:48
 **/
public class PigeonholeVo extends Vo{
    private static final long serialVersionUID = 7619649286405882596L;
    private String pigeonhole;
    private Integer countTotal;

    public String getPigeonhole() {
        return pigeonhole;
    }

    public void setPigeonhole(String pigeonhole) {
        this.pigeonhole = pigeonhole;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }
}
