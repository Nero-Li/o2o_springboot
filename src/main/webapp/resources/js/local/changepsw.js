$(function () {
    //修改平台密码的controller url
    var url = '/local/changelocalpwd';
    //从地址栏的URL中获取usertype
    //usertype=1位位consumer,其他为shopowner
    var usertype = getQueryString("usertype");
    $('#submit').click(function () {
        //获取账号
        var userName = $('#username').val();
        //获取原密码
        var password = $('#password').val();
        //获取新密码
        var newPassword = $('#newPassword').val();
        //获取确认密码
        var confirmPassword = $('#confirmPassword').val();
        if (newPassword != confirmPassword) {
            $.toast("两次密码不一致!");
            return;
        }
        //添加表单数据
        var formData = new FormData();
        formData.append("userName", userName);
        formData.append("password", password);
        formData.append("newPassword", newPassword);
        //获取验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: url,
            type: "post",
            data: formData,
            processData: false,
            cache: false,
            contentType: false,
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    if (usertype == 1) {
                        //如果用户在前端展示系统页面,则自动退回到前端展示系统首页
                        window.location.href = '/frontend/index';
                    }
                    else {
                        //若用户在店家管理系统页面,则自动退到店铺列表页面
                        window.location.href = '/shopadmin/shoplist';
                    }
                } else {
                    $.toast("提交失败:" + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function () {
        window.location.href = '/shopadmin/shoplist';
    });
})
;