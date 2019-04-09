$(function () {
    var productId = getQueryString('productId');
    var productUrl = '/frontend/listproductdetailpageinfo?productId='
        + productId;

    $.getJSON(productUrl, function (data) {
        if (data.success) {
            //获取商品信息
            var product = data.product;
            //商品缩略图
            $('#product-img').attr('src', product.imgAddr);
            //商品更新时间
            $('#product-time').text(
                new Date(product.lastEditTime).format("Y-m-d"));
            //积分
            if (product.point != undefined) {
                $('#product-point').text('购买可得' + product.point + '积分');
            }
            //商品名称
            $('#product-name').text(product.productName);
            //商品描述
            $('#product-desc').text(product.productDesc);
            //商品价格展示逻辑,主要判断原价现价是否为空,所有都为空则不显示价格
            if (product.nomalPrice != undefined && product.promotionPrice != undefined) {
                //如果现价和原价都不为空,则都展示,且给原价加一个删除符号
                $('#price').show();
                $('#normalPrice').html('<del>' + '¥' + product.nomalPrice + '</del>');
                $('#promotionPrice').text('¥' + product.promotionPrice);
            }
            var imgListHtml = '';
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="'
                    + item.imgAddr + '"/></div>';
            });
            // 生成购买商品的二维码供商家扫描
            imgListHtml += '<div> <img src="/frontend/generateqrcode4product?productId='
                + product.productId + '"/></div>';
            $('#imgList').html(imgListHtml);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});