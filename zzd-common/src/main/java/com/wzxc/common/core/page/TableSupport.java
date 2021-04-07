package com.wzxc.common.core.page;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.wzxc.common.constant.Constants;
import com.wzxc.common.utils.ServletUtils;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 表格数据处理
 * 
 * @author ruoyi
 */
@Slf4j
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        Object a = ServletUtils.getRequest().getSession().getAttribute("pageNum");
        Object b = ServletUtils.getRequest().getSession().getAttribute("pageSize");
        Object c = ServletUtils.getRequest().getSession().getAttribute("isPage");
        if(ServletUtils.getParameterToInt(Constants.IS_PAGE) != null){
            pageDomain.setIsPage(ServletUtils.getParameterToInt(Constants.IS_PAGE));
        } else if(ServletUtils.getRequest().getSession().getAttribute(Constants.IS_PAGE) != null && StringUtils.isNumeric(ServletUtils.getRequest().getSession().getAttribute(Constants.IS_PAGE).toString())){
            pageDomain.setIsPage(Integer.valueOf(ServletUtils.getRequest().getSession().getAttribute(Constants.IS_PAGE).toString()));
        }
        if(ServletUtils.getParameterToInt(Constants.PAGE_NUM) != null){
            pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        } else if(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_NUM) != null && StringUtils.isNumeric(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_NUM).toString())){
            pageDomain.setPageNum(Integer.valueOf(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_NUM).toString()));
        }
        if(ServletUtils.getParameterToInt(Constants.PAGE_SIZE) != null){
            pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        }else if(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_SIZE) != null && StringUtils.isNumeric(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_SIZE).toString())){
            pageDomain.setPageSize(Integer.valueOf(ServletUtils.getRequest().getSession().getAttribute(Constants.PAGE_SIZE).toString()));
        }
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        if(ServletUtils.getParameter(Constants.IS_ASC) != null){
            pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        }
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
