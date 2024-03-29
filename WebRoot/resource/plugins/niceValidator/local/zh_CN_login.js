/*********************************
 * Themes, rules, and i18n support
 * Locale: Chinese; 中文
 * note:增加了默认展示的样式风格，采用下方弹出浮动层提示 2015-03-02 fengjie
 *********************************/
(function(factory) {
    'function' === typeof define && (define.amd || define.cmd) ? define(function(require, exports, module){
        var $ = require('jquery'); $._VALIDATOR_URI = module.uri;
        require('../jquery.validator')($);
        factory($);
    }) : factory(jQuery);
}(function($) {

    /* Global configuration
     */
    $.validator.config({
        //stopOnError: true,
        //focusCleanup: true,
        //theme: 'yellow_right',
        //timely: 2,
        defaultMsg: "{0}格式不正确",
        loadingMsg: "正在验证...",
        
        // Custom rules
        rules: {
            digits: [/^\d+$/, "请输入数字"]
            ,letters: [/^[a-z]+$/i, "请输入字母"]
            ,date: [/^\d{4}-\d{2}-\d{2}$/, "请输入有效的日期，格式:yyyy-mm-dd"]
            ,time: [/^([01]\d|2[0-3])(:[0-5]\d){1,2}$/, "请输入有效的时间，00:00到23:59之间"]
            ,email: [/^[\w\+\-]+(\.[\w\+\-]+)*@[a-z\d\-]+(\.[a-z\d\-]+)*\.([a-z]{2,4})$/i, "请输入有效的邮箱"]
            ,url: [/^(https?|s?ftp):\/\/\S+$/i, "请输入有效的网址"]
            ,qq: [/^[1-9]\d{4,}$/, "请输入有效的QQ号"]
            ,IDcard: [/^\d{6}(19|2\d)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)?$/, "请输入正确的身份证号码"]
            ,tel: [/^(?:(?:0\d{2,3}[\- ]?[1-9]\d{6,7})|(?:[48]00[\- ]?[1-9]\d{6}))$/, "请输入有效的电话号码"]
            ,mobile: [/^1[3-9]\d{9}$/, "请输入有效的手机号"]
            ,zipcode: [/^\d{6}$/, "请检查邮政编码格式"]
            ,chinese: [/^[\u0391-\uFFE5]+$/, "请输入中文字符"]
            ,username: [/^\w{3,12}$/, "请输入3-12位数字、字母、下划线"]
            ,password: [/^[\S]{6,16}$/, "请输入6-16位字符，不能包含空格"]
            ,accept: function (element, params){
                if (!params) return true;
                var ext = params[0],
                    value = $(element).val();
                return (ext === '*') ||
                       (new RegExp(".(?:" + ext + ")$", "i")).test(value) ||
                       this.renderMsg("只接受{1}后缀的文件", ext.replace(/\|/g, ','));
            }
            
        },

        // Default error messages
        messages: {
            error: "网络异常",
            timeout: "请求超时",
            required: "{0}不能为空",
            remote: "{0}已被使用",
            integer: {
                '*': "请输入整数",
                '+': "请输入正整数",
                '+0': "请输入正整数或0",
                '-': "请输入负整数",
                '-0': "请输入负整数或0"
            },
            match: {
                eq: "{0}与{1}不一致",
                neq: "{0}与{1}不能相同",
                lt: "{0}必须小于{1}",
                gt: "{0}必须大于{1}",
                lte: "{0}不能大于{1}",
                gte: "{0}不能小于{1}"
            },
            range: {
                rg: "请输入{1}到{2}的数",
                gte: "请输入不小于{1}的数",
                lte: "请输入最大{1}的数"
            },
            checked: {
                eq: "请选择{1}项",
                rg: "请选择{1}到{2}项",
                gte: "请至少选择{1}项",
                lte: "请最多选择{1}项"
            },
            length: {
                eq: "请输入{1}个字符",
                rg: "请输入{1}到{2}个字符",
                gte: "请至少输入{1}个字符",
                lte: "请最多输入{1}个字符",
                eq_2: "",
                rg_2: "",
                gte_2: "",
                lte_2: ""
            }
        }
    });

    /* Themes
     */
    var TPL_ICON = '<span class="n-arrow"><b>◆</b><i>◆</i></span><span class="n-icon"></span>';
    $.validator.setTheme({
        'default': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgIcon : '<span class="n-arrow"><b>◆</b><i>◆</i></span>',
            msgShow: function($msgbox, type){
                var $el = $msgbox.children();
                if ($el.is(':animated')) return;
                $el.mouseup(function(){
                	$(this).hide(); 
                });
                $el.mouseenter(function(){
                	$(this).css({cursor : 'pointer'}); 
                });
            }
        },
        'simple_right': {
            formClass: 'n-simple',
            msgClass: 'n-right'
        },
        'simple_bottom': {
            formClass: 'n-simple',
            msgClass: 'n-bottom'
        },
        'yellow_top': {
            formClass: 'n-yellow',
            msgClass: 'n-top',
            msgIcon: TPL_ICON
        },
        'yellow_right': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgIcon: TPL_ICON
        },
        'yellow_bottom': {
            formClass: 'n-yellow',
            msgClass: 'n-bottom',
            msgIcon : TPL_ICON,
            msgShow: function($msgbox, type){
                var $el = $msgbox.children();
                if ($el.is(':animated')) return;
                $el.mouseup(function(){
                	$(this).hide(); 
                });
                $el.mouseenter(function(){
                	$(this).css({cursor : 'pointer'}); 
                });
            }
        },
        'yellow_right_effect': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgIcon: TPL_ICON,
            msgShow: function($msgbox, type){
                var $el = $msgbox.children();
                if ($el.is(':animated')) return;
                if (type === 'error') {
                    $el.css({left: '20px', opacity: 0})
                        .delay(100).show().stop()
                        .animate({left: '-4px', opacity: 1}, 150)
                        .animate({left: '3px'}, 80)
                        .animate({left: 0}, 80);
                } else {
                    $el.css({left: 0, opacity: 1}).fadeIn(200);
                }
            },
            msgHide: function($msgbox, type){
                var $el = $msgbox.children();
                $el.stop().delay(100).show()
                    .animate({left: '20px', opacity: 0}, 300, function(){
                        $msgbox.hide();
                    });
            }
        }
    });
}));