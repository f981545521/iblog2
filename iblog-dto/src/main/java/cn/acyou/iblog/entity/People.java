package cn.acyou.iblog.entity;

import cn.acyou.iblog.constant.AppConstant;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author youfang
 * @date 2018-02-09 17:21
 **/
public class People implements Serializable{
    @Excel(name = "学号", orderNum = "10", type = 10)
    private Integer id;
    @Excel(name = "姓名", orderNum = "20", width = AppConstant.CELL_WIDTH)
    private String name;
    @Excel(name = "年龄", orderNum = "30", type = 10, width = AppConstant.CELL_WIDTH)
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public People(){

    }
    public People(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
