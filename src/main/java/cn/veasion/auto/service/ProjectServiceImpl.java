package cn.veasion.auto.service;

import cn.veasion.auto.mapper.ProjectConfigMapper;
import cn.veasion.auto.mapper.ProjectMapper;
import cn.veasion.auto.model.ProjectConfigPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.utils.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    @Lazy
    @Resource
    private ApiExecuteStrategyService apiExecuteStrategyService;

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
    public void saveOrUpdate(ProjectPO projectPO) {
        if (projectPO.getId() == null) {
            projectPO.init();
            projectMapper.insert(projectPO);
        } else {
            ProjectPO old = projectMapper.queryById(projectPO.getId());
            projectPO.setUpdateTime(new Date());
            projectMapper.update(projectPO);
            if (Constants.YES.equals(projectPO.getIsDeleted())) {
                apiExecuteStrategyService.triggerCronUpdate(projectPO.getId(), false);
            } else if (projectPO.getIsAvailable() != null && !projectPO.getIsAvailable().equals(old.getIsAvailable())) {
                apiExecuteStrategyService.triggerCronUpdate(projectPO.getId(), Constants.YES.equals(projectPO.getIsAvailable()));
            }
        }
        ProjectConfigPO projectConfig = projectPO.getProjectConfig();
        if (projectConfig != null) {
            projectConfig.setProjectId(projectPO.getId());
            if (projectConfig.getId() == null) {
                projectConfig.init();
                projectConfigMapper.insert(projectConfig);
            } else {
                projectConfig.setUpdateTime(new Date());
                projectConfigMapper.update(projectConfig);
            }
        }
    }

    @Override
    public int delete(int id) {
        ProjectConfigPO projectConfigPO = projectConfigMapper.queryByProjectId(id);
        if (projectConfigPO != null) {
            projectConfigMapper.deleteById(projectConfigPO.getId());
        }
        apiExecuteStrategyService.triggerCronUpdate(id, false);
        return projectMapper.deleteById(id);
    }
}
