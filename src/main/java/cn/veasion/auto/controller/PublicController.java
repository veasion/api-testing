package cn.veasion.auto.controller;

import cn.veasion.auto.config.SpringBeanUtils;
import cn.veasion.auto.job.CheckStrategyJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PublicController
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@RestController
@RequestMapping("/public")
public class PublicController extends BaseController {

    @GetMapping("/job/check")
    public void jobCheck() {
        SpringBeanUtils.getBean(CheckStrategyJob.class).check();
    }

}
