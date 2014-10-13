package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.pojo.TProduct;
import com.ocyd.appfactory.pojo.TUser;
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
@RequestMapping("/productController")
public class ProductController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ProductController.class);

	private SystemService systemService;
    private UploadFileService fileService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

    @Autowired
    public void setFileService(UploadFileService fileService) {
        this.fileService = fileService;
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
    public void datagrid(int connectId, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TProduct.class, dataGrid);
        cq.eq("connectId", "" + connectId);
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
    public String productListOfShop(HttpServletRequest request, int connectId) {
        request.setAttribute("connectId", "" + connectId);
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
    public ModelAndView addOrUpdate(HttpServletRequest req, TProduct product) {
        if (product.getId() > 0) {
            product = systemService.getEntity(TProduct.class, product.getId());

            String relative = product.getWebLink();

            if(relative != null) {
                product.setWebLink(fileService.readHtmlFile(relative));
            }
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

            //内容保存为html，然后将相对路径保存到数据库。
            String relative = fileService.saveHtmlFile(found.getWebLink(), product.getWebLink());
            found.setWebLink(relative);

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            found.setLastUpdateTime(date);

            systemService.updateEntity(found);
            message = "商品[: " + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            product.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            product.setStatus(1); // 正常。

            //将提交上来的content保存为文件，然后将路径存放到content字段。
            if(product.getWebLink() != null) {
                String relative = fileService.saveHtmlFile(null, product.getWebLink());
                product.setWebLink(relative);
            }

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
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
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "productListOfPerson")
    public String productListOfPerson(HttpServletRequest request, int connectId) {
        request.setAttribute("connectId", "" + connectId);
        return "product/productListOfPerson";
    }


    /**
     * easyuiAJAX请求数据： 用户选择角色列表
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdatepersonproduct")
    public ModelAndView addOrUpdatePersonProduct(HttpServletRequest req, TProduct product) {
        if (product.getId() > 0) {
            product = systemService.getEntity(TProduct.class, product.getId());

            String relative = product.getWebLink();

            if(relative != null) {
                product.setWebLink(fileService.readHtmlFile(relative));
            }
        }

        req.setAttribute("product", product);
        return new ModelAndView("product/personProduct");
    }


    /**
     * 对商品进行保存
     */
    @RequestMapping(params = "savePersonProduct")
    @ResponseBody
    public AjaxJson savePersonProduct(HttpServletRequest req, TProduct product) {
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

            //内容保存为html，然后将相对路径保存到数据库。
            String relative = fileService.saveHtmlFile(found.getWebLink(), product.getWebLink());
            found.setWebLink(relative);

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            found.setLastUpdateTime(date);

            systemService.updateEntity(found);
            message = "商品[: " + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            product.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            product.setStatus(1); // 正常。

            //将提交上来的content保存为文件，然后将路径存放到content字段。
            if(product.getWebLink() != null) {
                String relative = fileService.saveHtmlFile(null, product.getWebLink());
                product.setWebLink(relative);
            }

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
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
        systemService.save(existed);
        message = "商品[" + existed.getName() + "]删除成功";
        j.setSuccess(true);

        j.setMsg(message);
        return j;
    }
}