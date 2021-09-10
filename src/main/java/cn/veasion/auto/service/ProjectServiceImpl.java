package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ProjectConfigMapper;
import cn.veasion.auto.mapper.ProjectMapper;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ProjectServiceImpl
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectConfigMapper projectConfigMapper;

    @Override
    public ProjectPO getById(int id) {
        ProjectPO projectPO = projectMapper.queryById(id);
        if (projectPO != null) {
            projectPO.setProjectConfig(projectConfigMapper.queryByProjectId(id));
        }
        return projectPO;
    }

    @Override
    public Page<ProjectPO> listPage(ProjectPO projectPO, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return (Page<ProjectPO>) projectMapper.queryList(projectPO);
    }

    @Override
    public void saveOrUpdateWithTx(ProjectPO projectPO) {
        if (projectPO.getId() == null) {
            projectMapper.insert(projectPO);
        } else {
            projectMapper.update(projectPO);
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        if (projectConfig != null) {
            projectConfig.setProjectId(projectPO.getId());
            if (projectConfig.getId() == null) {
                projectConfigMapper.insert(projectConfig);
            } else {
                projectConfigMapper.update(projectConfig);
            }
        }
    }
}
