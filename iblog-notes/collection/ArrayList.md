上来先问几个问题

ArrayList是否存在最大容量？
如果ArrayList存在最大容量 那么达到最大之后会如何处理
ArrayList的性能影响因素
ArrayList和LinkedList的实现区别和优劣，何时选用恰当的数据结构

本文尝试就以上等问题做出解答

惯例，首先贴出类图


注意着重标红了一个接口

该接口说明如下

/**
 * Marker interface used by <tt>List</tt> implementations to indicate that
 * they support fast (generally constant time) random access.  The primary
 * purpose of this interface is to allow generic algorithms to alter their
 * behavior to provide good performance when applied to either random or
 * sequential access lists.
 *
 * <p>The best algorithms for manipulating random access lists (such as
 * <tt>ArrayList</tt>) can produce quadratic behavior when applied to
 * sequential access lists (such as <tt>LinkedList</tt>).  Generic list
 * algorithms are encouraged to check whether the given list is an
 * <tt>instanceof</tt> this interface before applying an algorithm that would
 * provide poor performance if it were applied to a sequential access list,
 * and to alter their behavior if necessary to guarantee acceptable
 * performance.
 *
 * <p>It is recognized that the distinction between random and sequential
 * access is often fuzzy.  For example, some <tt>List</tt> implementations
 * provide asymptotically linear access times if they get huge, but constant
 * access times in practice.  Such a <tt>List</tt> implementation
 * should generally implement this interface.  As a rule of thumb, a
 * <tt>List</tt> implementation should implement this interface if,
 * for typical instances of the class, this loop:
 * <pre>
 *     for (int i=0, n=list.size(); i &lt; n; i++)
 *         list.get(i);
 * </pre>
 * runs faster than this loop:
 * <pre>
 *     for (Iterator i=list.iterator(); i.hasNext(); )
 *         i.next();
 * </pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.4
 */
public interface RandomAccess {
}

说明中提到了当随机访问性能即get(i)的性能优于迭代器的时候应当事先此接口。

事实上这也是数据结构课程上链表和数组的最大区别

链表提供较好的删除和插入性能，但是相对较差的随机访问能力
数组提供较好的随机访问性能 但是比较差的插入和删除性能
对于ArrayList来说其根源实现就是数组，那么自然提供了较好的随机访问性能 因此也就实现了

RandomAccess接口（该接口并没有任何方法）
对于集合来说Java提供两种常见的迭代方式

传统的for循环遍历，基于计数器的：

for(int i = 0 ; i < size ; i++) {
  system.out.println(list.get(i));
}


迭代器遍历，Iterator

Iterator it = list.iterator();
while(it.hasNext()) {
  System.ou.println(it.next);
}
当然foreach其实也是是编译器生成的Iterator

对于RandomAccess来说第一种迭代方式性能最好 但是对于非RandomAccess来说第一种迭代方式万万不可取（性能极其差）

继续来看ArrayList的构造函数

/**
 * The array buffer into which the elements of the ArrayList are stored.
 * The capacity of the ArrayList is the length of this array buffer.
 */
private transient Object[] elementData;

/**
 * The size of the ArrayList (the number of elements it contains).
 *
 * @serial
 */
private int size;

/**
 * Constructs an empty list with the specified initial capacity.
 *
 * @param  initialCapacity  the initial capacity of the list
 * @throws IllegalArgumentException if the specified initial capacity
 *         is negative
 */
public ArrayList(int initialCapacity) {
    super();
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal Capacity: "+
                                           initialCapacity);
    this.elementData = new Object[initialCapacity];
}

/**
 * Constructs an empty list with an initial capacity of ten.
 */
public ArrayList() {
    this(10);
}
可以知道默认情况下数组的size为10【数组的扩容也是相当昂贵的操作，因此一个合理的初始值相当有必要，特别是已知size的前提下】

当插入元素的时候

/**
 * Appends the specified element to the end of this list.
 *
 * @param e element to be appended to this list
 * @return <tt>true</tt> (as specified by {@link Collection#add})
 */
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
private void ensureCapacityInternal(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}


需要确保size+1一定要大于当前数组的长度 否则就会扩容

/**
 * Increases the capacity to ensure that it can hold at least the
 * number of elements specified by the minimum capacity argument.
 *
 * @param minCapacity the desired minimum capacity
 */
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
}

newCapacity会扩容变成oldCapacity + (oldCapacity >> 1) 注意此处运算优先级 也就是1.5倍的原数组的size。
newCapacity - minCapacity <0 的判断其实此时已经超过了int的最大表示区间
比如f6car > 最熟悉的ArrayList > image2017-9-29 22:43:10.png

这也是常见的溢出检测。

但是当随机插入到任意位置的时候

/**
 * Inserts the specified element at the specified position in this
 * list. Shifts the element currently at that position (if any) and
 * any subsequent elements to the right (adds one to their indices).
 *
 * @param index index at which the specified element is to be inserted
 * @param element element to be inserted
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
此时都需要执行System.arraycopy完成数组的拷贝【与链表更改指针想比较相对比较耗费资源】

当执行remove的时候

/**
 * Removes the element at the specified position in this list.
 * Shifts any subsequent elements to the left (subtracts one from their
 * indices).
 *
 * @param index the index of the element to be removed
 * @return the element that was removed from the list
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public E remove(int index) {
    rangeCheck(index);

    modCount++;
    E oldValue = elementData(index);

    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // Let gc do its work

    return oldValue;
}
如果当移除的元素并不是最后一个的时候仍然需要执行System.arraycopy

而取元素的方法却是常量级

/**
 * Returns the element at the specified position in this list.
 *
 * @param  index index of the element to return
 * @return the element at the specified position in this list
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public E get(int index) {
    rangeCheck(index);

    return elementData(index);
}
简直是要多快有多快！

@Test
public void  testArrayList1(){
    List<Integer> list =new ArrayList<>();
    Stopwatch stopwatch=Stopwatch.createStarted();
    for(int i=0;i< size;i++){
        list.add(i);
    }
    System.out.println("cost "+stopwatch.elapsed(TimeUnit.MILLISECONDS)+"ms");

}


@Test
public void  testArrayList12(){
    List<Integer> list =new ArrayList(size);
    Stopwatch stopwatch=Stopwatch.createStarted();
    for(int i=0;i< size;i++){
        list.add(i);
    }
    System.out.println("cost "+stopwatch.elapsed(TimeUnit.MILLISECONDS)+"ms");

}
如上两个评测在指定初始化大小之后 size为

int size = 10000000;


分别消耗

cost 3419ms
cost 1549ms
提升一倍的性能

选择合适的数据结构是可以带来指数级别的性能提升

设置size为100000

@Test
public void testArrayList3() {
    List<Integer> list = new ArrayList(size);
    for (int i = 0; i < size; i++) {
        list.add(i);
    }
    List<Integer> list2 = new ArrayList<>(size / 2);
    for (int i = 0; i < size / 2; i++) {
        list2.add(i);
    }
    Stopwatch stopwatch = Stopwatch.createStarted();
    list.removeAll(list2);
    System.out.println(list.size());
    System.out.println("cost " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

}


@Test
public void testArrayList4() {
    List<Integer> list = new ArrayList(size);
    for (int i = 0; i < size; i++) {
        list.add(i);
    }
    List<Integer> list2 = new ArrayList<>(size / 2);
    for (int i = 0; i < size / 2; i++) {
        list2.add(i);
    }
    Stopwatch stopwatch = Stopwatch.createStarted();
    HashSet<Integer> hashSet = new HashSet<>(list);
    LinkedList<Integer> linkedList = new LinkedList<>(list2);
    for (Integer integer : linkedList) {
        hashSet.remove(integer);
    }
    System.out.println(hashSet.size());
    System.out.println("cost " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

}
主要由于ArrayList的结构决定了removeAll必须走多次的ArrayCopy 因此考虑将对应的数据转成hash和链表

hash的查询速率快！而链表的插入删除节点快，这样可以得到最优的结果！（必须要注意这边的情况下元素不要重复 set）

50000
cost 5444ms
50000
cost 79ms
提高接近百倍！因此合理选择容器！远离性能烦恼！！！

当然有些小伙伴觉得换成HashSet可能导致原有集合不一样（数据重复）

那么作如下改动

@Test
public void testArrayList6() {
    List<Integer> list = new ArrayList(size);
    for (int i = 0; i < size; i++) {
        list.add(i);
    }
    List<Integer> list2 = new ArrayList<>(size / 2);
    for (int i = 0; i < size / 2; i++) {
        list2.add(i);
    }
    Stopwatch stopwatch = Stopwatch.createStarted();
    LinkedList<Integer> linkedList = new LinkedList<>(list);
    HashSet<Integer> hashSet = new HashSet<>(list2);
    linkedList.removeAll(hashSet);
    System.out.println(linkedList.size());
    System.out.println("cost " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

}
我们具体看一下ArrayList的removeAll

private boolean batchRemove(Collection<?> c, boolean complement) {
    final Object[] elementData = this.elementData;
    int r = 0, w = 0;
    boolean modified = false;
    try {
        for (; r < size; r++)
            if (c.contains(elementData[r]) == complement)
                elementData[w++] = elementData[r];
    } finally {
        // Preserve behavioral compatibility with AbstractCollection,
        // even if c.contains() throws.
        if (r != size) {
            System.arraycopy(elementData, r,
                             elementData, w,
                             size - r);
            w += size - r;
        }
        if (w != size) {
            for (int i = w; i < size; i++)
                elementData[i] = null;
            modCount += size - w;
            size = w;
            modified = true;
        }
    }
    return modified;
}
大量的arrayCopy导致性能低下

而LinkedList的removeAll直接

public boolean removeAll(Collection<?> c) {
    boolean modified = false;
    Iterator<?> it = iterator();
    while (it.hasNext()) {
        if (c.contains(it.next())) {
            it.remove();
            modified = true;
        }
    }
    return modified;
}

对于LinkedList来说remove代价比较低，当使用hashSet的数据结构contains的代价也很低。

因此可以作为常规性能改善点