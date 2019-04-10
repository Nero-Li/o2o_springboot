package com.lym.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lym.dto.ImageHolder;
import com.lym.dto.ProductExecution;
import com.lym.dto.Result;
import com.lym.entity.Product;
import com.lym.entity.ProductCategory;
import com.lym.entity.Shop;
import com.lym.enums.ProductCategoryStateEnum;
import com.lym.enums.ProductStateEnum;
import com.lym.service.ProductCategoryService;
import com.lym.service.ProductService;
import com.lym.util.CodeUtil;
import com.lym.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProductManagementController
 * @Description 商品controller
 * @Author lyming
 * @Date 2019/1/25 6:52 PM
 **/
@Controller
@RequestMapping(value = "/shopadmin")
@Slf4j
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 支持最大商品详情图上传数量
     */
    private static final int IMAGEMAXCOUNT = 6;

    @ResponseBody
    @RequestMapping(value = "getproductlistbyshop",method = RequestMethod.GET)
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取前端传来的代码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取前端传来的每页的大小
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session中获取店铺信息,主要是为了获取shopId
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值判断
        if (pageIndex > -1 && pageSize > -1 && null != currentShop && null != currentShop.getShopId()) {
            //从前端获取索引条件
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            //传入查询条件进行查询,并返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 封装商品查询条件到Product实例当中
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //若有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //若有商品名模糊查询的要求,也添加进去
        if (null != productName) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

    /**
     * 修改商品
     *
     * @Param [request]
     * @Return java.util.Map
     **/
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        /** 判断是商品编辑的时候调用还是上下架操作的时候调用
         *  区别在于商品编辑的时候要输入验证码
         */
        //是否只是上下架,改变状态
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        /**验证码判断*/
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        /**接受前端参数的变量的初始化,包括商品,缩略图,详情图列表实体类*/
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImageList = new ArrayList<>();
        //从session中获取servletContext,相当于tomcat容器了,然后转换成spring的CommonsMultipartResolver
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //如果CommonsMultipartResolver里面有文件
            if (commonsMultipartResolver.isMultipart(request)) {
                //将servlet中的request转换成spring中的MultipartHttpServletRequest(spring)
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象,从MultipartHttpServletRequest中
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                if (thumbnailFile != null) {
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                //取出详情图列表并构建List<ImageHolder>,最多支持六张图片
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImageFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (null != productImageFile) {
                        //若取出的第i个详情图片文件流不为null,则将其加入详情图片列表
                        ImageHolder productImg = new ImageHolder(productImageFile.getOriginalFilename(), productImageFile.getInputStream());
                        productImageList.add(productImg);
                    } else {
                        //若取出的第i个详情图片文件流为null,则跳出循环
                        break;
                    }
                }
            }/* 上下架操作时,会走else
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为null");
                return modelMap;
            }*/
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            /** 从前端获取传来的表单String流,并转换成Product实体类 */
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        //非空判断
        if (null != product) {
            try {
                /** 从session中获取当前店铺的ID并赋值给product,减少对前端的依赖 */
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                /** 开始进行更新操作 */
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImageList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息!");
        }
        return modelMap;

    }


    /**
     * 获取商品信息
     *
     * @Param [productId] 商品id
     * @Return modelMap
     **/
    @RequestMapping(value = "getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        //非空判断
        if (productId > -1) {
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    /**
     * 获取商品类别列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.POST)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (null != currentShop && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }
    }

    /**
     * 添加商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        //接受前端参数的数量的初始化,包括商品,缩略图,详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        ImageHolder imageHolder = null;
        List<ImageHolder> imageHolderList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        /** 1.处理图片 */
        try {
            //若请求中存在文件流,则取出相关文件(包括详情图和缩略图)
            if (multipartResolver.isMultipart(request)) {
                //将servlet中的request转换成spring中的MultipartHttpServletRequest(spring)
                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //取出缩略图并构建ImageHolder对象,从MultipartHttpServletRequest中
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                if (thumbnailFile != null) {
                    imageHolder = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                //取出详情图列表并构建List<ImageHolder>,最多支持六张图片
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImageFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (null != productImageFile) {
                        //若取出的第i个详情图片文件流不为null,则将其加入详情图片列表
                        ImageHolder productImg = new ImageHolder(productImageFile.getOriginalFilename(), productImageFile.getInputStream());
                        imageHolderList.add(productImg);
                    } else {
                        //若取出的第i个详情图片文件流为null,则跳出循环
                        break;
                    }
                }
                log.info(imageHolder.getImageName());

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为null");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        /** 2.处理商品实体类 */
        try {
            //尝试从前端传来的表单String流并转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        /** 3.对商品进行添加操作 */
        //如果Product信息不为null,缩略图和详情图列表不为null,则开始进行商品添加操作
        if (null != product && null != imageHolder && imageHolderList.size() > 0) {
            try {
                //从session中获取当前店铺id,减少对前端的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, imageHolder, imageHolderList);
                if (ProductStateEnum.SUCCESS.getState() == pe.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }
}
