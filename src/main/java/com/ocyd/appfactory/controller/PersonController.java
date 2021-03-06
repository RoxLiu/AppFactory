package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.appfactory.service.ShopService;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UploadFileService;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.common.model.json.DataGrid;
import com.ocyd.jeecgframework.core.constant.Globals;
import com.ocyd.jeecgframework.core.util.ResourceUtil;
import com.ocyd.jeecgframework.tag.core.easyui.TagUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 */
@Scope("prototype")
@Controller
@RequestMapping("/personController")
public class PersonController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(PersonController.class);

	private SystemService systemService;
    private ShopService shopService;
    private UploadFileService fileService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setFileService(UploadFileService fileService) {
        this.fileService = fileService;
    }
    /**
     * 个人对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "personList")
    public String shopList(HttpServletRequest request, int connectId) {
        request.setAttribute("connectId", connectId);
        return "person/personList";
    }


    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(int connectId, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TShopInfo.class, dataGrid);
        cq.eq("connectId", "" + connectId);
        cq.eq("status", TShopInfo.STATUS_NORMAL);
        cq.add();
        this.shopService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * easyuiAJAX请求数据： 用户选择增加或者商家
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addOrUpdate(HttpServletRequest req, TShopInfo shop) {
        if (shop.getId() > 0) {
            shop = shopService.getEntity(TShopInfo.class, shop.getId());
        }

        req.setAttribute("person", shop);
//        req.setAttribute("user", ResourceUtil.getCurrentSessionUser());
        return new ModelAndView("person/person");
    }


    /**
     * 对商家进行保存
     */
    @RequestMapping(params = "savePerson")
    @ResponseBody
    public AjaxJson saveShopInfo(HttpServletRequest req, TShopInfo shopInfo) {
        AjaxJson j = new AjaxJson();
        if (shopInfo.getId() > 0) {
            TShopInfo found = shopService.getEntity(TShopInfo.class, shopInfo.getId());
            found.setName(shopInfo.getName());
            found.setCompany(shopInfo.getCompany());
            found.setBrief(shopInfo.getBrief());
            found.setPhone(shopInfo.getPhone());
            found.setDescription(shopInfo.getDescription());

            //用户重新选择了图片，删除原来旧的图片。
            if(found.getIcon() != null && !found.getIcon().equals(shopInfo.getIcon())) {
                fileService.deleteFile(found.getIcon());
            }
            found.setIcon(shopInfo.getIcon());
            found.setProduct(shopInfo.getProduct());
            found.setAddress(shopInfo.getAddress());
            found.setLon(shopInfo.getLon());
            found.setLat(shopInfo.getLat());
            found.setPhotoList(shopInfo.getPhotoList());
            found.setWebLink(shopInfo.getWebLink());
            found.setSecondTitle(shopInfo.getSecondTitle());

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            found.setLastUpdate(date);

            shopService.updateEntity(found);
            message = "个人[" + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            shopInfo.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            shopInfo.setStatus(1); // 正常。
            shopInfo.setType(2); //设为普通用户

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            shopInfo.setCreateTime(date);
            shopInfo.setLastUpdate(date);
            shopService.save(shopInfo);
            message = "个人[" + shopInfo.getName() + "]添加成功";

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);

        return j;
    }


    /**
     * 删除商家
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(HttpServletRequest req, TShopInfo shop) {
        AjaxJson j = new AjaxJson();
        TShopInfo shopInfo = shopService.getEntity(TShopInfo.class, shop.getId());

//        //先删除对应的图标文件
//        fileService.deleteFile(shopInfo.getIcon());
        shopService.delete(shopInfo);


        message = "个人[" + shopInfo.getShopId() + "]删除成功.";
        j.setMsg(message);
        return j;
    }
}