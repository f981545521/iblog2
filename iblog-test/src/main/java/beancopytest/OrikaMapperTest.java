package beancopytest;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 同 {@link BeanCopyUtilTest} 比较而言
 * 0-2000 性能较好
 * 3000 + 相差不大
 * @author youfang
 * @version [1.0.0, 2019-01-26 上午 10:55]
 **/
public class OrikaMapperTest {

    public static void main(String[] args) {
        OrikaUtil orikaMapper = new OrikaUtil();
/*        CopyTestBean copyTestBean = new CopyTestBean();
        for (Integer i = 10; i < 11; i++) {
            copyTestBean.setIdCustomer(Long.valueOf(i));
            copyTestBean.setName("小米" + i);
            copyTestBean.setAddress("北京市" + i);
            copyTestBean.setArea("海淀区" + i + "号");
            copyTestBean.setBirthday("2018-11-23");
            copyTestBean.setCellPhone(String.valueOf(i));
            copyTestBean.setCompanyName("小米科技");
            copyTestBean.setCity("中关村");
            copyTestBean.setQq(String.valueOf(i + 11));
            copyTestBean.setDetailAddress("xxx");
        }
        Long start = System.currentTimeMillis();
        orikaMapper.convert(copyTestBean, CopyTestBeanVo.class);
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));*/


        List<CopyTestBean> copyTestBeanList = Lists.newArrayList();
        for (Integer i = 10; i < 3000; i++) {
            CopyTestBean copyTestBean = new CopyTestBean();
            copyTestBean.setIdCustomer(Long.valueOf(i));
            copyTestBean.setName("小米" + i);
            copyTestBean.setAddress("北京市" + i);
            copyTestBean.setArea("海淀区" + i + "号");
            copyTestBean.setBirthday("2018-11-23");
            copyTestBean.setCellPhone(String.valueOf(i));
            copyTestBean.setCompanyName("小米科技");
            copyTestBean.setCity("中关村");
            copyTestBean.setQq(String.valueOf(i + 11));
            copyTestBean.setDetailAddress("xxx");
            copyTestBeanList.add(copyTestBean);
        }
        Long start = System.currentTimeMillis();
        orikaMapper.convertList(copyTestBeanList, CopyTestBeanVo.class);
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }

}
