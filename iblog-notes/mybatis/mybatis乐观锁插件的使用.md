### Mybatis乐观锁插件的使用

#### 整合步骤

1. 导入Jar包
```
<dependency>
  <groupId>se.spagettikod</groupId>
  <artifactId>optimist</artifactId>
  <version>1.1.0</version>
</dependency>
```
2. 在PO类中增加注解
    - 类：`@OptimisticLocking("ib_sort")`
    - 注解字段：`@Identity("id")`
    - 版本号字段：`@Version(value = "version")`
3. mybatis-conf.xml中启用插件
```
    <plugin interceptor="se.spagettikod.optimist.impl.OptimisticLockingInterceptor"/>
```
4. 编写测试用例
```
    @Test
    public void test4(){
        Sort sort = sortMapper.findSortById(14);
        System.out.println(sort);
        sort.setDescription("修改的3");
        //sort.setVersion(2);
        sortMapper.updateSort(sort);
        Sort sortNew = sortMapper.findSortById(14);
        System.out.println(sortNew);
    }
```
