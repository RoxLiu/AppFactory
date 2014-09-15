package com.ocyd.appfactory.controller.core;

import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.appfactory.pojo.TShopModule;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.ShopService;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UploadFileService;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.common.model.json.DataGrid;
import com.ocyd.jeecgframework.core.constant.Globals;
import com.ocyd.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
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
@RequestMapping("/shopController")
public class ShopController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ShopController.class);

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
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "shopList")
    public String shopList(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        request.setAttribute("user", user);
        return "shop/shopList";
    }


    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TUser user, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TShopInfo.class, dataGrid);
        //查询条件组装器
        HqlGenerateUtil.installHql(cq, user);

        cq.eq("accountId", user.getShopId());
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

        req.setAttribute("shopInfo", shop);
//        req.setAttribute("user", ResourceUtil.getCurrentSessionUser());
        return new ModelAndView("shop/shopInfo");
    }


    /**
     * 对商家进行保存
     */
    @RequestMapping(params = "saveShopInfo")
    @ResponseBody
    public AjaxJson saveShopInfo(HttpServletRequest req, TShopInfo shopInfo) {
        AjaxJson j = new AjaxJson();
        if (shopInfo.getId() > 0) {
            TShopInfo found = shopService.getEntity(TShopInfo.class, shopInfo.getId());
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdate(date);

            shopService.updateEntity(found);
            message = "商家[: " + found.getAccountId() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            shopInfo.setAccountId(ResourceUtil.getCurrentSessionUser().getShopId());
            shopInfo.setStatus(1); // 正常。
            shopInfo.setType(2); //设为普通用户

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            shopInfo.setCreateTime(date);
            shopInfo.setLastUpdate(date);
            shopService.save(shopInfo);
            message = "商家[: " + shopInfo.getAccountId() + "]添加成功";

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

        //先删除对应的图标文件
        fileService.deleteFile(shopInfo.getIcon());
        shopService.delete(shopInfo);


        message = "商家[" + shopInfo.getAccountId() + "]删除成功.";
        j.setMsg(message);
        return j;
    }

    /**
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "shopModuleList")
    public String shopModuleList(HttpServletRequest request, TUser user) {
        request.setAttribute("shopId", user.getShopId());
        return "shop/shopModuleList";
    }

    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "shopmoduledatagrid")
    public void shopModuleDataGrid(int shopId, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TShopModule.class, dataGrid);

        cq.eq("shopId", shopId);
        cq.eq("status", TShopModule.STATUS_NORMAL);
        cq.add();
        this.shopService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * easyuiAJAX请求数据： 用户选择增加或者商家
     */
    @RequestMapping(params = "addorupdateshopmodule")
    public ModelAndView addOrUpdateShopModule(HttpServletRequest req, TShopModule module) {
        if (module.getId() > 0) {
            module = shopService.getEntity(TShopModule.class, module.getId());
        }

        req.setAttribute("shopModule", module);
//        req.setAttribute("user", ResourceUtil.getCurrentSessionUser());
        return new ModelAndView("shop/shopModule");
    }


    /**
     * 对模块进行保存
     */
    @RequestMapping(params = "saveShopModule")
    @ResponseBody
    public AjaxJson saveShopModule(HttpServletRequest req, TShopModule shopModule) {
        AjaxJson j = new AjaxJson();
        if (shopModule.getId() > 0) {
            TShopModule found = shopService.getEntity(TShopModule.class, shopModule.getId());
            found.setModuleName(shopModule.getModuleName());
            found.setModuleId(shopModule.getModuleId());
            found.setDescription(shopModule.getDescription());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdate(date);

            shopService.updateEntity(found);
            message = "模块[ " + found.getModuleName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            shopModule.setStatus(TShopModule.STATUS_NORMAL); // 正常。

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            shopModule.setCreateTime(date);
            shopModule.setLastUpdate(date);
            shopService.save(shopModule);
            message = "模块[ " + shopModule.getModuleName() + "]添加成功";

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
    @RequestMapping(params = "deleteshopmodule")
    @ResponseBody
    public AjaxJson delete(HttpServletRequest req, TShopModule module) {
        AjaxJson j = new AjaxJson();
        TShopModule found = shopService.getEntity(TShopModule.class, module.getId());
        found.setStatus(TShopModule.STATUS_DELETED);
        shopService.save(found);

        message = "模块[" + found.getModuleName() + "]删除成功.";
        j.setMsg(message);
        return j;
    }
}