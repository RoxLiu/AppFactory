package com.ocyd.appfactory.service.impl;

import com.ocyd.appfactory.pojo.TProduct;
import com.ocyd.appfactory.pojo.TShopInfo;
import com.ocyd.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.ocyd.appfactory.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author  张代浩
 *
 */
@Service("shopService")
@Transactional
public class ShopServiceImpl extends CommonServiceImpl implements ShopService {

    @Override
    public void delete(TShopInfo shop) {
        List<TProduct> products = commonDao.findByProperty(TProduct.class, "shopId", String.valueOf(shop.getId()));

        //设置删除状态，不从数据库中删除
        for(TProduct product : products) {
            product.setStatus(TProduct.STATUS_DELETED);
        }
        commonDao.batchUpdate(products);

        //设置删除状态，不从数据库中删除
        shop.setStatus(TShopInfo.STATUS_DELETED);
        commonDao.updateEntity(shop);
    }
}
