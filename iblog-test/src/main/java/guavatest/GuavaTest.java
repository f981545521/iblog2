package guavatest;

import com.google.common.base.Function;
import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author youfang
 * @date 2018-02-07 16:41
 **/
public class GuavaTest {


    @Test
    public void test1(){
        List<User> userList = new ArrayList<User>();
        userList.add(new User(23,"张三",24));
        userList.add(new User(24,"李四",15));
        userList.add(new User(25,"王五",53));
        List<String> nameList = Lists.transform(userList, new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getName();
            }
        });
        System.out.println(nameList);

        Set<User> userSet = Sets.newHashSet(userList);
        System.out.println(userList);
    }
}
