$(function () {
    //登录验证的controller的url
    var loginUrl = '/local/loginchek';
    // 从地址栏URL获取usertype
    var userType = getQueryString("usertype");
    //usertype=1则为customer,其余为shopowner
    //登录此数,累计登录三次失败以后自动弹出验证码要求输入
    var loginCount = 0;
    $('#submit').click(function () {
        //获取输入的账号
        var userName = $('#username').val();
        //获取输入的密码
        var password = $('#psw').val();
        //获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();
        //是否需要验证码验证,默认为false,即不需要
        var needVerify = false;
        //若果登录三次都失败
        if (loginCount >= 3) {
            //那么需要验证码校验
            if (!verifyCodeActual) {
                $.toast("请输入验证码!");
                return;
            } else {
                needVerify = true;
            }
        }

        //访问后台进行登录验证
        $.ajax({
            url: loginUrl,
            type: "post",
            async: false,
            cache: false,
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast("登陆成功!");
                    if (userType == 1) {
                        //若用户在前端展示系统页面则自动连接到前端展示系统首页
                        window.location.href = '/fronten/index';
                    } else {
                        window.location.href = '/shopadmin/shoplist';
                    }
                } else {
                    $.toast("登录失败!");
                    loginCount++;
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                }
            }
        })
    });
});