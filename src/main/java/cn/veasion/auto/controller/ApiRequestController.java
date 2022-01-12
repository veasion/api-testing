package cn.veasion.auto.controller;

import cn.veasion.auto.exception.BusinessException;
import cn.veasion.auto.model.ApiRequestVO;
import cn.veasion.auto.model.Page;
import cn.veasion.auto.model.ApiRequestPO;
import cn.veasion.auto.model.ProjectPO;
import cn.veasion.auto.model.R;
import cn.veasion.auto.service.ApiRequestService;
import cn.veasion.auto.service.ProjectService;
import cn.veasion.auto.utils.Constants;
import cn.veasion.auto.utils.ExcelExportUtils;
import cn.veasion.auto.utils.ExcelImportUtils;
import cn.veasion.auto.utils.StringUtils;
import cn.veasion.auto.utils.SwaggerUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ApiRequestController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/api/apiRequest")
public class ApiRequestController extends BaseController {

    @Resource
    private ProjectService projectService;
    @Resource
    private ApiRequestService apiRequestService;

    @GetMapping("/listPage")
    public Page<ApiRequestVO> listPage(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                                       ApiRequestVO apiRequestVO) {
        return Page.ok(apiRequestService.listPage(apiRequestVO, pageNo, pageSize));
    }

    @GetMapping("/list")
    public R<List<ApiRequestVO>> list(ApiRequestVO apiRequestVO) {
        return R.ok(apiRequestService.listPage(apiRequestVO, 1, Constants.MAX_PAGE_SIZE));
    }

    @GetMapping("/getById")
    public R<ApiRequestPO> getById(@RequestParam("id") Integer id) {
        return R.ok(apiRequestService.getById(id));
    }

    @GetMapping("/queryByApiName")
    public R<ApiRequestPO> queryByApiName(@RequestParam("apiName") String apiName,
                                          @RequestParam(value = "projectId", required = false) Integer projectId) {
        return R.ok(apiRequestService.queryByApiName(apiName, projectId));
    }

    @PostMapping("/add")
    public R<Object> add(@RequestBody ApiRequestPO apiRequestPO) {
        apiRequestPO.setId(null);
        notNull(apiRequestPO.getProjectId(), "项目不能为空");
        notEmpty(apiRequestPO.getApiName(), "命名不能为空");
        notEmpty(apiRequestPO.getApiDesc(), "请求描述不能为空");
        apiRequestPO.setApiName(apiRequestPO.getApiName().trim());
        apiRequestService.saveOrUpdate(apiRequestPO);
        return R.ok(apiRequestPO.getId());
    }

    @PostMapping("/update")
    public R<Object> update(@RequestBody ApiRequestPO apiRequestPO) {
        notNull(apiRequestPO.getId(), "id不能为空");
        apiRequestService.saveOrUpdate(apiRequestPO);
        return R.ok();
    }

    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Integer id) {
        apiRequestService.delete(id);
        return R.ok();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, ApiRequestVO apiRequest) throws IOException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (apiRequest.getProjectId() == null) {
            map.put("projectName", "项目名称");
        }
        map.putAll(exportMap);
        ExcelExportUtils.export(response, "API接口.xlsx", map, apiRequestService.listPage(apiRequest, 1, Constants.MAX_EXPORT_SIZE));
    }

    @GetMapping("/downloadSwaggerApi")
    public void downloadSwaggerApi(HttpServletResponse response, String swaggerUrl, @RequestParam(required = false) String apiGroup) throws Exception {
        List<ApiRequestPO> list = SwaggerUtils.swaggerToApiRequest(swaggerUrl);
        if (apiGroup != null && !"".equals(apiGroup)) {
            list.forEach(s -> s.setApiGroup(apiGroup));
        }
        ExcelExportUtils.export(response, "swagger接口.xlsx", exportMap, list);
    }

    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        ExcelExportUtils.export(response, "API接口导入模板.xlsx", exportMap.values().toArray(new String[]{}));
    }

    @PostMapping("/import")
    public R<Object> importExcel(@RequestParam("file") MultipartFile file,
                                 @RequestParam("projectId") Integer projectId,
                                 @RequestParam(required = false, defaultValue = "false") boolean autoCase) throws IOException {
        ProjectPO projectPO = projectService.getById(projectId);
        if (projectPO == null) {
            throw new BusinessException("项目不存在");
        }
        LinkedHashMap<String, String> importMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : exportMap.entrySet()) {
            importMap.put(entry.getValue(), entry.getKey());
        }
        List<ApiRequestPO> list = ExcelImportUtils.parse(file.getInputStream(), importMap, ApiRequestPO.class);
        if (list == null || list.isEmpty()) {
            throw new BusinessException("数据不能为空");
        }
        for (int i = 0; i < list.size(); i++) {
            ApiRequestPO apiRequestPO = list.get(i);
            if (!StringUtils.hasText(apiRequestPO.getApiName())) {
                throw new BusinessException("第" + (i + 1) + "行命名为空");
            }
            if (!StringUtils.hasText(apiRequestPO.getUrl())) {
                throw new BusinessException("第" + (i + 1) + "url为空");
            }
            if (!StringUtils.hasText(apiRequestPO.getApiDesc())) {
                throw new BusinessException("第" + (i + 1) + "描述为空");
            }
            apiRequestPO.init();
            apiRequestPO.setProjectId(projectId);
        }
        int count = apiRequestService.importWithTx(projectPO, list, autoCase);
        return R.ok(count);
    }

    private static LinkedHashMap<String, String> exportMap = new LinkedHashMap<String, String>() {
        {
            put("apiName", "命名");
            put("apiGroup", "接口分组");
            put("apiDesc", "接口描述");
            put("method", "请求方法");
            put("url", "请求URL");
            put("headersJson", "请求头");
            put("body", "请求Body");
        }
    };

}
