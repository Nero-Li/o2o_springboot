package com.lym.service.impl;

import com.lym.dao.ProductDao;
import com.lym.dao.ProductImgDao;
import com.lym.dto.ImageHolder;
import com.lym.dto.ProductExecution;
import com.lym.entity.Product;
import com.lym.entity.ProductImg;
import com.lym.enums.ProductStateEnum;
import com.lym.exception.ProductOperationException;
import com.lym.service.ProductService;
import com.lym.util.ImageUtil;
import com.lym.util.PageCalculator;
import com.lym.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Author lyming
 * @Date 2019/1/24 2:54 PM
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页面转换成数据库的行码,并调用dao层取回制定页码的商品列表
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //基于同样的查询条件下返回商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setCount(count);
        pe.setProductList(productList);
        return pe;
    }

    @Override
    @Transactional
    /**
     * 1.处理缩略图,获取缩略图相对路径并赋值给product
     * 2.向tb_product中写入商品信息,获取product_id
     * 3.结合product_id批量处理商品详情图
     * 4.将商品详情图批量插入tb_product_img中
     */
    public ProductExecution addProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认上架状态
            product.setEnableStatus(1);
            //如果商品缩略图不为null,则添加
            if (imageHolder != null && null != imageHolder.getImage()) {
                addThumbnail(product, imageHolder);
            }
            try {
                //创建商品信息
                int effectiveNum = productDao.insertProduct(product);
                if (effectiveNum <= 0) {
                    throw new ProductOperationException("添加商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败:" + e.getMessage());
            }
            //如果商品详情图不为null,则添加
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgs(product, imageHolderList);
            }

            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        return productDao.getProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList)
            throws ProductOperationException {
        /**
         * 1.若缩略图参数有值,则处理缩略图
         *   如果之前存在缩略图,则先删除再添加,之后获取缩略图的相对路径赋值给product对象
         * 2.若商品详情图列表参数有值,对商品详情图列表进行相同的操作
         * 3.将tb_product_img下面原先的商品详情图全部删除
         * 4.更新tb_product,tb_product_img的信息
         */
        //空值判断
        if (product != null && null != product.getShop() && null != product.getShop().getShopId()) {
            //给商品更新最后编辑时间
            product.setLastEditTime(new Date());
            //如果传入的缩略图不为null且原有缩略图不为null,则删除原有的缩略图并添加
            if (imageHolder != null) {
                //先从数据库获取之前的Product信息,从而获取缩略图地址
                Product tempProduct = productDao.getProductById(product.getProductId());
                if (null != tempProduct.getImgAddr()) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, imageHolder);
            }
            //如果有新存入的商品详情图,则将原有的删除,并添加新的图片
            if (null != imageHolderList && imageHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                //添加新的详情图
                addProductImgs(product, imageHolderList);
            }
            try {
                //更新product信息
                int effectiveNum = productDao.updateProduct(product);
                if (effectiveNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败!");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败:" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除某个商品下所有的详情图
     *
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取原有的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //删除之前的图片(硬盘上)
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //数据库的记录删除
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 生成缩略图
     *
     * @param product     商品实体类
     * @param imageHolder 图片处理的封装类
     */
    private void addThumbnail(Product product, ImageHolder imageHolder) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnal(imageHolder, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 添加详情图
     *
     * @param product         商品实体类
     * @param imageHolderList 图片处理的封装类集合
     */
    private void addProductImgs(Product product, List<ImageHolder> imageHolderList) {
        /** 获取图片存储路径,这里放在对应的shopId目录下 */
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder imageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNomalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList != null && productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
            }
        }
    }
}
