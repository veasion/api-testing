package cn.veasion.auto.controller;

import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.R;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.service.ProjectService;
import com.sun.org.apache.bcel.internal.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProjectController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseController {

    @Resource
    private ProjectService projectService;

    @GetMapping("/listPage")
    public Page<ProjectPO> listPage(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize,
                                    ProjectPO projectPO) {
        return Page.ok(projectService.listPage(projectPO, pageNo, pageSize));
    }

    @GetMapping("/list")
    public R<List<ProjectPO>> list(ProjectPO projectPO) {
        return R.ok(projectService.listPage(projectPO, 1, Constants.MAX_CODE_SIZE));
    }

    @GetMapping("/getById")
    public R<ProjectPO> getById(@RequestParam("id") Integer id) {
        return R.ok(projectService.getById(id));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ProjectPO projectPO) {
        projectPO.setId(null);
        notEmpty(projectPO.getName(), "项目名称不能为空");
        projectPO.setName(projectPO.getName().trim());
        projectService.saveOrUpdate(projectPO);
        return R.ok(projectPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ProjectPO projectPO) {
        notNull(projectPO.getId(), "id不能为空");
        projectService.saveOrUpdate(projectPO);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Integer id) {
        projectService.delete(id);
        return R.ok();
    }

}
