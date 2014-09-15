package org.jeecgframework.web.system.service.impl;

import com.ocyd.appfactory.pojo.TProduct;
import com.ocyd.appfactory.pojo.TShopInfo;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.ShopService;
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
        List<TProduct> products = commonDao.findByProperty(TProduct.class, "shopAccountId", shop.getId());

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
