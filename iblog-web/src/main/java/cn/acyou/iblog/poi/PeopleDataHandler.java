package cn.acyou.iblog.poi;

import cn.acyou.iblog.entity.People;

/**
 * @author youfang
 * @date 2018-03-12 15:20
 **/
public class PeopleDataHandler extends DataHandlerDefaultImpl<People>{
    private static final String ID = "学号";
    private static final String NAME = "姓名";
    private static final String AGE = "年龄";
    private static final String[] HANDLE_NAMES = new String[]{ID, NAME, AGE};

    public PeopleDataHandler(){
        super();
        setNeedHandlerFields(HANDLE_NAMES);
    }
    @Override
    public Object exportHandler(People obj, String name, Object value) {
        switch (name) {
            case ID:
                return obj.getId();
            case NAME:
                return obj.getName();
            case AGE:
                return obj.getAge();
            default:
                break;
        }
        return super.exportHandler(obj, name, value);
    }
}
