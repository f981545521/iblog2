package cn.acyou.iblog.mappers;

import cn.acyou.iblog.model.Sort;
import cn.acyou.iblog.so.SortSo;
import cn.acyou.iblog.vo.SortVo;

import java.util.List;

/**
 * 文章分类的数据库访问层
 * @author youfang
 * @date 2018-02-24 21:03
 **/
public interface SortMapper {

    /**文章分类：按照UID查找所属分类*/
    List<Sort> findsSortsByUid(Integer uid);

    /**添加分类*/
    int addSort(List<Sort> sortList);

    /**
     * 删除分类
     * @param id 要删除的分类的主键id
     * @return 返回影响的行数
     */
    int delSortById(Integer id);

    /**
     * 修改分类
     * @param sort 分类
     * @return
     */
    int updateSort(Sort sort);

    /**
     * 按照SID查找这个Sort
     * @param id
     * @return
     */
    Sort findSortById(Integer id);

    /**
     * 根据UID查找分类名和表述；添加的文章的时候需要用到
     * @param uid
     * @return
     */
    List<Sort> findSortNamesByUid(Integer uid);

    /**
     * 查找所有
     * @return
     */
    List<Sort> findAll();

    /**
     *
     */
    List<SortVo> getSortVoListByUid(SortSo sortSo);
}
