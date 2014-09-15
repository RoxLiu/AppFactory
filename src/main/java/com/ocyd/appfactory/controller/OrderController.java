package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.pojo.TOrder;
import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.ShopService;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UserService;
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
@RequestMapping("/orderController")
public class OrderController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(OrderController.class);

	private UserService userService;
	private SystemService systemService;
    private ShopService shopService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
    /**
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "orderList")
    public String user(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        request.setAttribute("user", user);
        return "order/orderList";
    }


    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TUser user, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOrder.class, dataGrid);
        //查询条件组装器
        HqlGenerateUtil.installHql(cq, user);

        cq.eq("shopId", user.getShopId());
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
    public AjaxJson saveUser(HttpServletRequest req, TShopInfo shopInfo) {
        AjaxJson j = new AjaxJson();
        if (shopInfo.getId() > 0) {
            TShopInfo found = shopService.getEntity(TShopInfo.class, shopInfo.getId());
            found.setPhone(shopInfo.getPhone());
            found.setDescription(shopInfo.getDescription());
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
            message = "商家[ " + found.getShopId() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            shopInfo.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            shopInfo.setStatus(1); // 正常。
            shopInfo.setType(2); //设为普通用户

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            shopInfo.setCreateTime(date);
            shopInfo.setLastUpdate(date);
            shopService.save(shopInfo);
            message = "商家[ " + shopInfo.getShopId() + "]添加成功";

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
        shopService.delete(shopInfo);

        message = "商家[" + shopInfo.getShopId() + "]删除成功.";
        j.setMsg(message);
        return j;
    }
}