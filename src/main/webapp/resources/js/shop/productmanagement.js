$(function() {
    var shopId = 1;
    var listUrl = '/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=9999&shopId='
        + shopId;
    var deleteUrl = '/shopadmin/modifyproduct';
    getList();

    function getList() {
        //从后台获取此店铺的商品列表
        $.getJSON(listUrl, function(data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                //遍历每条商品信息,拼接成一行显示,列信息包括:
                //商品名称,优先级,上架/下架(含productId),编辑按钮(含productId)
                //预览(包含productId)
                productList.map(function(item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    //若状态为0,则表示是已下架的商品,操作变为上架
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    function deleteItem(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            $.ajax({
                url : deleteUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(product),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
    //将class为product-wrap里面的a标签绑定上点击事件
    $('.product-wrap').on('click', 'a', function (e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    //如果有class edit则点击就进入店铺信息编辑页面,并带有productId
                    window.location.href = '/shopadmin/productoperation?productId='
                        + e.currentTarget.dataset.id;
                    //如果有class status,则调用上下架功能,并带有productId
                } else if (target.hasClass('status')) {
                    deleteItem(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                    //如果class preview,则调用预览功能
                } else if (target.hasClass('preview')) {
                    window.location.href = '/frontend/productdetail?productId='
                        + e.currentTarget.dataset.id;
                }
            });

    $('#new').click(function() {
        window.location.href = '/shopadmin/productedit';
    });
});