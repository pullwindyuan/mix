/** index.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */

var tab;
var resourceConfig;
layui.config({
    base: 'js/',
    version: new Date().getTime()
}).use(['element', 'layer', 'navbar', 'tab'], function () {
    var element = layui.element(),
        $ = layui.jquery,
        layer = layui.layer,
        navbar = layui.navbar();
    tab = layui.tab({
        elem: '.admin-nav-card' //设置选项卡容器
        ,
        //maxSetting: {
        //	max: 5,
        //	tipMsg: '只能开5个哇，不能再开了。真的。'
        //},
        contextMenu: true,
        onSwitch: function (data) {
            console.log(data.id); //当前Tab的Id
            console.log(data.index); //得到当前Tab的所在下标
            console.log(data.elem); //得到当前的Tab大容器

            console.log(tab.getCurrentTabId())
        }
    });
    //iframe自适应
    $(window).on('resize', function () {
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();

    //设置navbar
    navbar.set({
        spreadOne: true,
        elem: '#admin-navbar-side',
        cached: true
        /*data: navs
		cached:true,
		url: 'datas/nav.json'*/
    });
    //渲染navbar
    navbar.render();
    //监听点击事件
    navbar.on('click(side)', function (data) {
        //tab.tabAdd(data.field);
        //debugger
        renderCustomer(data.field.nav)
    });
    //清除缓存
    $('#clearCached').on('click', function () {
        navbar.cleanCached();
        layer.alert('清除完成!', { icon: 1, title: '系统提示' }, function () {
            location.reload();//刷新
        });
    });

    resourceConfig = navbar.getConfig();
    createPopup(document.getElementById('root'));
    $('.admin-side-toggle').on('click', function () {
        var sideWidth = $('#admin-side').width();
        if (sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            }); //admin-footer
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    });
    $('.admin-side-full').on('click', function () {
        var docElm = document.documentElement;
        //W3C  
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox  
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等  
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11
        else if (elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        }
        layer.msg('按Esc即可退出全屏');
    });

    $('#setting').on('click', function () {
        tab.tabAdd({
            href: '/Manage/Account/Setting/',
            icon: 'fa-gear',
            title: '设置'
        });
    });

    //锁屏
    $(document).on('keydown', function () {
        var e = window.event;
        if (e.keyCode === 76 && e.altKey) {
            //alert("你按下了alt+l");
            lock($, layer);
        }
    });
    $('#lock').on('click', function () {
        lock($, layer);
    });

    //手机设备的简单适配
    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });

    var isShowLock = false;
    function lock($, layer) {
        if (isShowLock)
            return;
        //自定页
        layer.open({
            title: false,
            type: 1,
            closeBtn: 0,
            anim: 6,
            content: $('#lock-temp').html(),
            shade: [0.9, '#393D49'],
            success: function (layero, lockIndex) {
                isShowLock = true;
                //给显示用户名赋值
                //layero.find('div#lockUserName').text('admin');
                //layero.find('input[name=username]').val('admin');
                layero.find('input[name=password]').on('focus', function () {
                    var $this = $(this);
                    if ($this.val() === '输入密码解锁..') {
                        $this.val('').attr('type', 'password');
                    }
                })
                    .on('blur', function () {
                        var $this = $(this);
                        if ($this.val() === '' || $this.length === 0) {
                            $this.attr('type', 'text').val('输入密码解锁..');
                        }
                    });
                //在此处可以写一个请求到服务端删除相关身份认证，因为考虑到如果浏览器被强制刷新的时候，身份验证还存在的情况
                //do something...
                //e.g.

                $.getJSON('/Account/Logout', null, function (res) {
                    if (!res.rel) {
                        layer.msg(res.msg);
                    }
                }, 'json');

                //绑定解锁按钮的点击事件
                layero.find('button#unlock').on('click', function () {
                    var $lockBox = $('div#lock-box');

                    var userName = $lockBox.find('input[name=username]').val();
                    var pwd = $lockBox.find('input[name=password]').val();
                    if (pwd === '输入密码解锁..' || pwd.length === 0) {
                        layer.msg('请输入密码..', {
                            icon: 2,
                            time: 1000
                        });
                        return;
                    }
                    unlock(userName, pwd);
                });
                /**
                 * 解锁操作方法
                 * @param {String} 用户名
                 * @param {String} 密码
                 */
                var unlock = function (un, pwd) {
                    console.log(un, pwd);
                    //这里可以使用ajax方法解锁
                    $.post('/Account/UnLock', { userName: un, password: pwd }, function (res) {
                        //验证成功
                        if (res.rel) {
                            //关闭锁屏层
                            layer.close(lockIndex);
                            isShowLock = false;
                        } else {
                            layer.msg(res.msg, { icon: 2, time: 1000 });
                        }
                    }, 'json');
                    //isShowLock = false;
                    //演示：默认输入密码都算成功
                    //关闭锁屏层
                    //layer.close(lockIndex);
                };
            }
        });

    };
    var customerElement = document.getElementById("customer");
    function renderCustomer(resource) {
        customerElement.innerHTML = "";
        customerElement.innerHTML = '';
        if("ELEMENT" == resource.type) {
            //debugger
            genElementHtml(customerElement, resource, undefined, undefined);
            //debugger
        }else if("VIEW" == resource.type) {
            genViewHtml(resource, dataBinder);
        }else if("FUNCTION" == resource.type) {
            genFunctionHtml(resource);
        }else if("CELL" == resource.type) {
            genCellHtml(resource);
        }else if("SCENE" == resource.type) {
            genSceneHtml(resource);
        }
    };

    var pophtml;
    var dataBinderMap = Object;
    function genElementHtml(elem, resource, dataBindeKey, dataBinder) {
        var uiConfig = resourceConfig.data[resource.type];
        var currConfig = uiConfig[resource.value.id];
        var contents = resource.value.contents;
        var icon = resource.value.icon;
        var title = resource.value.title;
        var html = '';
        var element = document.createElement('div');
        if("QRC" == currConfig.type) {
            //debugger
            html += '<H3>' + title + '</H3>';
            html +=  '<div class="main" align="center">\n' +
                '            <h3>#扫码测试#</h3>\n' +
                '    </div>\n'
            element.innerHTML = html;
            elem.appendChild(element);
            var qrcodeElement = document.createElement('div')
            qrcodeElement.id = 'qrcode';
            qrcodeElement.className = 'qrcode';
            element.appendChild(qrcodeElement);
            makeQRCode(qrcodeElement, contents);
        }else if("ICON_TEXT_BUTTON" == currConfig.type) {
            if (icon !== undefined && icon !== '') {
                if (icon.indexOf('fa-') !== -1) {
                    html += '<div class="fa ' + icon + '" aria-hidden="true" data-icon="' + icon + '"></div>';
                } else {
                    html += '<div class="layui-icon" data-icon="' + icon + '">' + icon + '</div>';
                }
            }
            html += '<button>';
            html += '<span>' + contents + '</span>';
            html += '</button>';
            element.innerHTML = html;
            element.id = dataBindeKey;
            elem.appendChild(element);
        }else if("TEXT_BUTTON" == currConfig.type) {
            html += '<button>';
            html += '<span>' + contents + '</span>';
            html += '</button>';
            element.innerHTML = html;
            element.id = dataBindeKey;
            elem.appendChild(element);
        }else if("CHECKBOX_TEXT_BUTTON" == currConfig.type) {
            html += '<span className="el-checkbox__input"><span className="el-checkbox__inner"></span><input type="checkbox"\n' +
                '                                                                                                    aria-hidden="false"\n' +
                '                                                                                                    className="el-checkbox__original"\n' +
                '                                                                                                    value=""></span>';
            html += '<button>';
            html += '<span>' + contents + '</span>';
            html += '</button>';
            element.innerHTML = html;
            element.id = dataBindeKey;
            elem.appendChild(element);
        }else if("TEXT_INPUT" == currConfig.type) {
            html += '<H3>' + title + '</H3>';
            html += '<input id="'+ dataBindeKey +  '" name="username" type="text"  className="Input"  placeholder="用户登录名" value="13751003379">'
            element.innerHTML = html;
            elem.appendChild(element);
        }else if("PW_TEXT_INPUT" == currConfig.type) {
            html += '<H3>' + title + '</H3>';
            html += '<input id="'+ dataBindeKey +  '" name="password" type="password" className="Input"  placeholder="密码" value="xsws2zaq1">';
            element.innerHTML = html;
            elem.appendChild(element);
        }else if("RTF" == currConfig.type) {
            html += '<H3>' + title + '</H3>';
            html += '<h2 style="font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; white-space: normal;">\n' +
                '    Think Different\n' +
                '</h2>\n' +
                '<p style="overflow-wrap: break-word; font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; font-size: 12px; white-space: normal;">\n' +
                '    <span style="color: rgb(255, 153, 0);">Here’s to the crazy ones. The misfits. The rebels. The troublemakers. The round pegs in the square holes. The ones who see things differently. They’re not fond of rules. And they have no respect for the status quo. You can quote them, disagree with them, glorify or vilify them. About the only thing you can’t do is ignore them. Because they change things. They push the human race forward. And while some may see them as the crazy ones, we see genius. Because the people who are crazy enough to think they can change the world, are the ones who do.</span>\n' +
                '</p>\n' +
                '<p style="overflow-wrap: break-word; font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; font-size: 12px; white-space: normal;">\n' +
                '    <span style="text-decoration:underline;"><em>- Apple Inc.</em></span>\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>';
            element.innerHTML = html;
            element.id = dataBindeKey;
            elem.appendChild(element);
        }else if("IMAGE" == currConfig.type) {
            html += '<H3>' + title + '</H3>';
            html += '<img src="'+ contents + '">'
            element.innerHTML = html;
            element.id = dataBindeKey;
            elem.appendChild(element);
        }
        //debugger
        element = document.getElementById(dataBindeKey);
        //进行数据绑定
        if(dataBinder != undefined && dataBinder != null) {
            var binder = dataBinder[dataBindeKey];
            var dataBinderRule;
            var dataAction;
            if (binder != undefined && binder != null) {
                dataAction = binder.ACTION;
                dataBinderRule = dataAction.rule;
                element.id = dataBindeKey;
                if ('GET_DATA' == dataAction.type) {
                    element.onclick = postData;
                } else if ('POST_DATA' == dataAction.type) {
                    element.setAttribute('api', dataBinderRule.API);
                    dataBinderMap[dataBindeKey] = binder;
                    element.onclick = postData;
                }
            }

        }
        //行为绑定
        var rule = resource.rule;

        if(rule != undefined && rule != null) {
            var commonAction = rule.ACTION;
            var chance = rule.CHANCE;
            var popupTarget = rule.TAGET;
            if(commonAction != undefined && commonAction != null) {
                element.id = dataBindeKey;
                if('POPUP' == commonAction) {
                    pophtml = ''
                    pophtml += '<H3>' + title + '</H3>';
                    pophtml += '<h2 style="font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; white-space: normal;">\n' +
                        '    Think Different\n' +
                        '</h2>\n' +
                        '<p style="overflow-wrap: break-word; font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; font-size: 12px; white-space: normal;">\n' +
                        '    <span style="color: rgb(255, 153, 0);">Here’s to the crazy ones. The misfits. The rebels. The troublemakers. The round pegs in the square holes. The ones who see things differently. They’re not fond of rules. And they have no respect for the status quo. You can quote them, disagree with them, glorify or vilify them. About the only thing you can’t do is ignore them. Because they change things. They push the human race forward. And while some may see them as the crazy ones, we see genius. Because the people who are crazy enough to think they can change the world, are the ones who do.</span>\n' +
                        '</p>\n' +
                        '<p style="overflow-wrap: break-word; font-family: &quot;sans serif&quot;, tahoma, verdana, helvetica; font-size: 12px; white-space: normal;">\n' +
                        '    <span style="text-decoration:underline;"><em>- Apple Inc.</em></span>\n' +
                        '</p>\n' +
                        '<p>\n' +
                        '    <br/>\n' +
                        '</p>';
                    element.onclick = popUp;
                }
            }

            if(chance != undefined && chance != null) {
                if('BY_WAKE_UP' == chance) {
                    element.style.display = 'none';

                }
            }
        }
    };

    function genViewHtml(resource, dataBinder) {
        var uiConfig = resourceConfig.data[resource.type];
        var currConfig = uiConfig[resource.value.id];
        var contents = resource.value.contents;
        var icon = resource.value.icon;
        var title = resource.value.title;
        for(var key in currConfig.kv.element) {
            genElementHtml(customerElement, currConfig.kv.element[key], resource.value.id + '-' + key, dataBinder);
        }
    };

    function genFunctionHtml(resource) {
        var uiConfig = resourceConfig.data[resource.type];
        var currConfig = uiConfig[resource.value.id];
        var contents = resource.value.contents;
        var icon = resource.value.icon;
        var title = resource.value.title;
        //数据和接口的绑定配置,绑定的工作都是最终由element来承接
        var dataBinder = currConfig.kv.dataBinder;

        for(var key in currConfig.kv.view) {
            genViewHtml(currConfig.kv.view[key], dataBinder);
        }
        for(var key in currConfig.kv.element) {
            genElementHtml(customerElement, currConfig.kv.element[key], resource.value.id + '-' + key, dataBinder);
        }
    };

    function genCellHtml(resource) {
        var uiConfig = resourceConfig.data[resource.type];
        var currConfig = uiConfig[resource.value.id];
        var contents = resource.value.contents;
        var icon = resource.value.icon;
        var title = resource.value.title;
        for(var key in currConfig.kv.function) {
            var func = currConfig.kv.function[key]
            genFunctionHtml(func)
        }
    };

    function genSceneHtml(resource) {
        var uiConfig = resourceConfig.data[resource.type];
        var currConfig = uiConfig[resource.value.id];
        var contents = resource.value.contents;
        var icon = resource.value.icon;
        var title = resource.value.title;
        for(var key in currConfig.kv.function) {
            var func = currConfig.kv.function[key]
            genFunctionHtml(func)
        }

        for(var key in currConfig.kv.cell) {
            var cell = currConfig.kv.cell[key]
            genCellHtml(cell)
        }
    };

    function makeQRCode(qrcodeElement, content) {
        // var qrcode = new QRCode(document.getElementById(eleId), {
        //     width : 200,
        //     height : 200
        // });
        // qrcode.makeCode(content);
        //var element = document.getElementById(eleId);
        qrcodeElement.innerHTML="";
        new QRCode(qrcodeElement, {
            width: 200,
            height: 200,
            text: content,//url不能写死,
            //img_src: "${base}/theme/default/images/qrCodeIcon.png",
            //img_src: "https://develop.jinzhuzhu.com.cn/Uploads/Picture/9134720170718144549.png",
            // img_src: "user.jpg",
            //img_width: 60,
            // use_canvas: false // (是否使用canvas绘制，默认true)
            // 使用img_src，需要保证图片是支持跨域访问的，不然我也无能为力
        });
    };

    function popUp(e){
        setAndShowPopup(pophtml, "用户使用协议查看", "确定", "返回", null, null);
    };

    function postData(e) {
        var binder = dataBinderMap[e.currentTarget.id];
        var url = binder.ACTION.rule.API;
        var type = binder.ACTION.rule.METHOD;
        var inputData = JSON;
        for(var key in binder) {
            if(key != "ACTION") {
                var bindField = binder[key];
                var fieldValue = document.getElementById(bindField.rule.SOURCE).value;
                inputData[bindField.name] = fieldValue;
            }
        }
        $.ajax({
            type: type,//POST or GET
            url: url + '?' + new Date().getTime(),
            async: false, //_config.async,
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(inputData),
            success: function (result, status, xhr) {
                setAndShowPopup(JSON.stringify(result), "恭喜你，你所配置的接口自动适配调用成功", "我知道了", "返回", null, null);
            },
            error: function (xhr, status, error) {
                setAndShowPopup(JSON.stringify(error), "很遗憾，接口调用失败", "我知道了", "返回", null, null);
            },
            complete: function (xhr, status) {

            }
        });
    };
});

