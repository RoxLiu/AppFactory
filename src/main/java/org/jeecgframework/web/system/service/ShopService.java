package org.jeecgframework.web.system.service;

import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.appfactory.pojo.TUser;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**
 * 
 * @author  张代浩
 *
 */
public interface ShopService extends CommonService{
    //删除商家信息，同时把商家下的商品删除。
    public void delete(TShopInfo shop);
}
