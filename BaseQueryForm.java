package com.liepin.h.web.common.form;

import com.liepin.swift.framework.form.BaseForm;

public class BaseQueryForm extends BaseForm {

    private static final long serialVersionUID = -3642259264420363692L;
    private Integer curPage = 0;
    private Integer pageSize = 10;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        if (curPage == null || curPage < 0) {
            curPage = 0;
        }
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

}
