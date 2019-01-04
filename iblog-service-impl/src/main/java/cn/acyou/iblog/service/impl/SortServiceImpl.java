package cn.acyou.iblog.service.impl;


import cn.acyou.iblog.exception.BusinessException;
import cn.acyou.iblog.mappers.SortMapper;
import cn.acyou.iblog.mappers.UserMapper;
import cn.acyou.iblog.model.Sort;
import cn.acyou.iblog.model.User;
import cn.acyou.iblog.service.SortService;
import cn.acyou.iblog.so.SortSo;
import cn.acyou.iblog.utility.IbStatic;
import cn.acyou.iblog.vo.SortVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分类管理服务层
 *
 * @author youfang
 * @date 2017年8月21日 下午3:11:29
 */
@Service("sortService")
public class SortServiceImpl implements SortService {
    @Resource
    private SortMapper sortMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 按照uid列出：分类列表
     *
     * @param id
     * @return
     * @author youfang
     * @date 2017年8月21日 下午3:34:45
     */
    public List<Sort> listSorts(Integer id) {
        if (id == null) {
            throw new BusinessException("用户ID不能为空!");
        }
        User user = userMapper.findUserBuId(id);
        if (user == null) {
            throw new BusinessException("用户不存在!");
        }
        List<Sort> sorts = sortMapper.findsSortsByUid(id);
        return sorts;
    }

    /**
     * 增加分类
     *
     * @param sortName
     * @param uid
     * @param description
     * @return
     * @author youfang
     * @date 2017年8月21日 下午3:34:28
     */
    public int addSort(String sortName, Integer uid, String description) {
        Sort sort = new Sort();
        sort.setSortName(sortName);
        sort.setIdUser(uid);
        sort.setDescription(description);
        List<Sort> sortList = Lists.newArrayList();
        sortList.add(sort);
        int n = sortMapper.addSort(sortList);
        return n;
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     * @author youfang
     * @date 2017年9月7日 上午10:15:12
     */
    public int delSort(Integer id) {
        int n = sortMapper.delSortById(id);
        return n;
    }

    /**
     * 修改分类
     *
     * @param id          要修改的分类ID
     * @param sortName    分类名称
     * @param description 描述
     * @return 影响的行数
     * @author youfang
     * @date 2017年9月7日 下午7:11:55
     */
    public Sort updateSort(Integer id, String sortName, String description) {
        Sort sort = new Sort();
        sort.setId(id);
        sort.setSortName(sortName);
        sort.setDescription(description);
        sort.setVersion(0);
        int n = sortMapper.updateSort(sort);
        if (n == 1) {
            return sortMapper.findSortById(id);
        }
        //这地方还要改善：修改不成功告诉Controller
        return null;
    }

    @Override
    public Sort updateSort(Integer id) {
        Sort sort = new Sort();
        sort.setId(id);
        sort.setSortName("乐观的");
        sort.setDescription("测试大法");
        sort.setCreatetime(new Timestamp(System.currentTimeMillis()));
        sort.setModifiedtime(new Date());
        sort.setIdUser(6);
        sort.setVersion(0);
        int n = sortMapper.updateSort(sort);
        return sortMapper.findSortById(id);

    }

    /**
     * 添加文章的时候使用：获取用户所有分类名与描述
     *
     * @param uid
     * @return
     * @author youfang
     * @date 2017年9月14日 下午4:40:10
     */
    public List<Sort> listSortNames(Integer uid) {
        return sortMapper.findSortNamesByUid(uid);
    }

    @Override
    public List<SortVo> getSortVoListByUid(Integer uid) {
        SortSo sortSo = new SortSo();
        sortSo.setIdUser(IbStatic.getUser());
        return sortMapper.getSortVoListByUid(sortSo);
    }

    @Override
    public Map<Integer, Sort> getSortMapByIds(SortSo sortSo) {
        List<Sort> sortList = sortMapper.getSortsBySo(sortSo);
        Map<Integer, Sort> sortMap = Maps.newHashMapWithExpectedSize(sortList.size());
        for (Sort sort : sortList) {
            sortMap.put(sort.getId(), sort);
        }
        return sortMap;
    }
}
