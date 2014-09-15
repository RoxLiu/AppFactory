package com.ocyd.appfactory.controller.core;

import com.ocyd.appfactory.pojo.TProduct;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UserService;
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
@RequestMapping("/productController")
public class ProductController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ProductController.class);

	private UserService userService;
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


    /**
     * 商品列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "productionList")
    public String user(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        request.setAttribute("user", user);
        return "product/productList";
    }


    /**
     * 当前商家用户对应的商品列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(int shopId, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TProduct.class, dataGrid);
        cq.eq("shopAccountId", shopId);
        cq.eq("status", TProduct.STATUS_NORMAL);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "productListOfShop")
    public String user(HttpServletRequest request, int shopId) {
        request.setAttribute("shopId", shopId);
        return "product/productListOfShop";
    }


    /**
     * easyuiAJAX请求数据： 用户选择角色列表
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addOrUpdate(HttpServletRequest req, TProduct product, int shopId) {
        if (product.getId() > 0) {
            product = systemService.getEntity(TProduct.class, product.getId());

            req.setAttribute("shopId", product.getShopAccountId());
        } else {
            req.setAttribute("shopId", shopId);
        }

        req.setAttribute("product", product);
        return new ModelAndView("product/product");
    }


    /**
     * 对商品进行保存
     */
    @RequestMapping(params = "saveProduct")
    @ResponseBody
    public AjaxJson saveProduct(HttpServletRequest req, TProduct product) {
        String message = null;
        AjaxJson j = new AjaxJson();
        if (product.getId() > 0) {
            TProduct found = systemService.getEntity(TProduct.class, product.getId());
            found.setName(product.getName());
            found.setBrandId(product.getBrandId());
            found.setColor(product.getColor());
            found.setSize(product.getSize());
            found.setSexual(product.getSexual());
            found.setPicture(product.getPicture());
            found.setIcon(product.getIcon());
            found.setNormalPrice(product.getNormalPrice());
            found.setNowPrice(product.getNowPrice());
            found.setBrief(product.getBrief());
            found.setDescription(product.getDescription());
            found.setStoreAmount(product.getStoreAmount());
            found.setSecondTitle(product.getSecondTitle());
            found.setSn(product.getSn());
            found.setDescription(product.getDescription());
            found.setIcon(product.getIcon());
            found.setStartDiscountTime(product.getStartDiscountTime());
            found.setEndDiscountTime(product.getEndDiscountTime());
            found.setOrderable(product.getOrderable());
            found.setWebLink(product.getWebLink());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdateTime(date);

            systemService.updateEntity(found);
            message = "商品[: " + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            product.setStatus(1); // 正常。

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            product.setAddTime(date);
            product.setLastUpdateTime(date);

            product.setStartDiscountTime(date);
            product.setEndDiscountTime(date);
            systemService.save(product);
            message = "商品[: " + product.getName() + "]添加成功";

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);

        return j;
    }


    /**
     * 用户信息录入和更新
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(HttpServletRequest req, TProduct product) {
        String message = null;
        AjaxJson j = new AjaxJson();
        TProduct existed = systemService.getEntity(TProduct.class, product.getId());

        existed.setStatus(-1); //将状态置为-1表示删除，不直接从数据库中删除记录。
        userService.save(existed);
        message = "商品[" + existed.getName() + "]删除成功";
        j.setSuccess(true);

        j.setMsg(message);
        return j;
    }
}