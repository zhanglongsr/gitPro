package com.liepin.h.web.resume.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.liepin.h.web.common.annotation.ServiceAuth;
import com.liepin.h.web.common.controller.BaseController;
import com.liepin.h.web.common.util.TrapResumeUtil;
import com.liepin.h.web.resume.biz.ITrapResumeBiz;
import com.liepin.swift.core.exception.BizException;
import com.liepin.swift.core.log.MonitorLogger;

@ServiceAuth(sohoHunterCanVisit = true)
@RequestMapping("/ga")
@Controller
public class GaController extends BaseController {
    @Autowired
    ITrapResumeBiz trapResumeBiz;

    @RequestMapping("")
    public String ga(Model model) throws BizException {
        String queryString = getQueryString();
        String data = "";
        Long userId = getCurrentUserId();
        if (!TrapResumeUtil.validStaticId(queryString))// 若是非法请求
        {
            // ,返回默认数据，记log
            monitorLog.log("获取蜜罐简历，非法请求。userh_id:" + userId + ",ipaddr:" + getIpAddr());
            data = TrapResumeUtil.getDefaultTrapData();
        } else {
            String resEncodeId = "";
            try {
                resEncodeId = trapResumeBiz.getTrapResEncodeId(userId);
            } catch (BizException e) {
                monitorLog.log("获取蜜罐简历id异常，", e);
            }
            data = TrapResumeUtil.getTrapResumeData(resEncodeId);
        }
        model.addAttribute("json", data);
        return "common/json";

    }

    /**
     * 搜索简历，隐藏蜜罐简历成功发送通知
     * 
     * @param afterward
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "stat.json", method = RequestMethod.POST)
    public String stat(String afterward) {

        Long userId = getCurrentUserId();
        MonitorLogger.getInstance().log(
                "--- so resume set honey resume hidden success notice!user_id:" + userId + ",afterward=" + afterward);

        return "1";
    }
}
