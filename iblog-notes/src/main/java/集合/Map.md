### Map的一些知识

#### 由于需要两个值才能确定唯一的value，所以需要两个Map

```
    @Test
    public void test15(){
        Map<String,Map<String,Double>> map = new HashMap<>();
        Map<String,Double> doubleMap = new HashMap<>();
        doubleMap.put("123",9.99);
        doubleMap.put("456",8.88);
        map.put("GG",doubleMap);
        System.out.println(map.get("GG").get("123"));
        System.out.println(map.get("GG").get("456"));
        map.get("GG").put("789",6.66);
        System.out.println(map.get("GG").get("789"));
    }
```
圆满解决，外层Map的key一样，内层的key不一样，构建好内层的Map后put进去即可。

#### 这时候发现一个问题

    @Test
    public void test16(){
        Map<int,Double> map = new HashMap<>();
    }

定义的时候发现基本类型不能作为key？

    @Test
    public void test16(){
        Map map1 = new HashMap();
        int k = 2,v = 33;
        map1.put(k,v);
        System.out.println(map1);
    }

这样就可以了，为什么？

 1. Map的key不能是基本类型；
 2. 从Map的方法中我们会发现：`V get(Object key);` 我们可以看到Key是Object类型；
 3. 这就容易理解了：它需要一个对象，而基本类型不是对象；
 4. Map没有指定泛型的时候，这里它会自动包装成Integer类型；