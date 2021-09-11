package cn.veasion.auto.service;

import cn.veasion.auto.model.ProjectPO;
import com.github.pagehelper.Page;

/**
 * ProjectService
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public interface ProjectService {

    ProjectPO getById(int id);

    Page<ProjectPO> listPage(ProjectPO projectPO, int pageIndex, int pageSize);

    void saveOrUpdate(ProjectPO projectPO);

    int delete(int id);

}
