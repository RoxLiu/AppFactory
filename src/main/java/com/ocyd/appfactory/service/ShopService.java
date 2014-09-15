package com.ocyd.appfactory.service;

import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.jeecgframework.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface ShopService extends CommonService {
    //删除商家信息，同时把商家下的商品删除。
    public void delete(TShopInfo shop);
}
