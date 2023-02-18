/** navbar.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['element', 'common'], function (exports) {
    "use strict";
    var $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element(),
        common = layui.common,
        cacheName = 'tb_navbar';

    var Navbar = function () {
		/**
		 *  默认配置 
		 */
        this.config = {
            customerConfig: Object,
            elem: undefined, //容器
            data: undefined, //数据源
            url: '/config/all', //数据源地址
            type: 'POST', //读取方式
            cached: false, //是否使用缓存
            spreadOne: false //设置是否只展开一个二级菜单
        };
        this.v = '1.0.0';
    };
    //渲染
    Navbar.prototype.render = function () {
        var _that = this;
        var _config = _that.config;
        if (typeof (_config.elem) !== 'string' && typeof (_config.elem) !== 'object') {
            common.throwError('Navbar error: elem参数未定义或设置出错，具体设置格式请参考文档API.');
        }
        var $container;
        if (typeof (_config.elem) === 'string') {
            $container = $('' + _config.elem + '');
        }
        if (typeof (_config.elem) === 'object') {
            $container = _config.elem;
        }
        if ($container.length === 0) {
            common.throwError('Navbar error:找不到elem参数配置的容器，请检查.');
        }
        if (_config.data === undefined && _config.url === undefined) {
            common.throwError('Navbar error:请为Navbar配置数据源.')
        }
        if (_config.data !== undefined && typeof (_config.data) === 'object') {
            var html = getHtml(_config.data, _config);
            $container.html(html);
            element.init();
            _that.config.elem = $container;
        } else {
            if (_config.cached) {
                var cacheNavbar = layui.data(cacheName);
                if (cacheNavbar.navbar === undefined) {
                    $.ajax({
                        type: _config.type,
                        url: _config.url,
                        async: false, //_config.async,
                        dataType: 'json',
                        success: function (result, status, xhr) {
                            //添加缓存
                            layui.data(cacheName, {
                                key: 'navbar',
                                value: result
                            });
                            _config.data = result;
                            var html = getHtml(result, _config);
                            $container.html(html);
                            element.init();
                        },
                        error: function (xhr, status, error) {
                            common.msgError('Navbar error:' + error);
                        },
                        complete: function (xhr, status) {
                            _that.config.elem = $container;
                        }
                    });
                } else {
                    var html = getHtml(cacheNavbar.navbar, _config);
                    $container.html(html);
                    element.init();
                    _that.config.elem = $container;
                }
            } else {
                //清空缓存
                layui.data(cacheName, null);
                $.ajax({
                    type: _config.type,
                    url: _config.url + new Date().getTime(),
                    async: false, //_config.async,
                    dataType: 'json',
                    success: function (result, status, xhr) {
                        _config.data = result;
                        var html = getHtml(result, _config);
                        $container.html(html);
                        element.init();
                    },
                    error: function (xhr, status, error) {
                        common.msgError('Navbar error:' + error);
                    },
                    complete: function (xhr, status) {
                        _that.config.elem = $container;
                    }
                });
            }
        }

        //只展开一个二级菜单
        if (_config.spreadOne) {
            var $ul = $container.children('ul');
            $ul.find('li.layui-nav-item').each(function () {
                $(this).on('click', function () {
                    $(this).siblings().removeClass('layui-nav-itemed');
                });
            });
        }
        return _that;
    };
	/**
	 * 配置Navbar
	 * @param {Object} options
	 */
    Navbar.prototype.set = function (options) {
        var that = this;
        that.config.data = undefined;
        $.extend(true, that.config, options);
        return that;
    };

    /**
     * 配置Navbar
     * @param {Object} options
     */
    Navbar.prototype.getConfig = function () {
        var that = this;
        return that.config;
    };

	/**
	 * 绑定事件
	 * @param {String} events
	 * @param {Function} callback
	 */
    Navbar.prototype.on = function (events, callback) {
        //debugger
        var that = this;
        var _con = that.config.elem;
        if (typeof (events) !== 'string') {
            common.throwError('Navbar error:事件名配置出错，请参考API文档.');
        }
        var lIndex = events.indexOf('(');
        var eventName = events.substr(0, lIndex);
        var filter = events.substring(lIndex + 1, events.indexOf(')'));
        if (eventName === 'click') {
            if (_con.attr('lay-filter') !== undefined) {
                _con.children('ul').find('li').each(function () {
                    var $this = $(this);
                    if ($this.find('dl').length > 0) {
                        var $dd = $this.find('dd').each(function () {
                            $(this).on('click', function () {
                                var $a = $(this).children('a');
                                var href = $a.data('url');
                                var navIndex = $a.children("menu").data('nav');
                                var nav = that.config.customerConfig[navIndex];
                                //debugger
                                var icon = $a.children('i:first').data('icon');
                                var title = $a.children('cite').text();
                                var data = {
                                    elem: $a,
                                    field: {
                                        nav: nav,
                                        href: href,
                                        icon: icon,
                                        title: title
                                    }
                                }
                                callback(data);
                            });
                        });
                    } else {
                        $this.on('click', function () {
                            var $a = $this.children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('cite').text();
                            var data = {
                                elem: $a,
                                field: {
                                    href: href,
                                    icon: icon,
                                    title: title
                                }
                            }
                            callback(data);
                        });
                    }
                });
            }
        }
    };
	/**
	 * 清除缓存
	 */
    Navbar.prototype.cleanCached = function () {
        layui.data(cacheName, null);
    };
	/**
	 * 获取html字符串
	 * @param {Object} menu
	 */
    function getHtml(menu, config) {
        //debugger;
        config.data = menu.data;
        var ulHtml = '<ul class="layui-nav layui-nav-tree beg-navbar">';
        var data = menu.data["MENU"];
        for (var i in data) {
            ulHtml += '<li class="layui-nav-item">';
            var child = data[i].gd;
            if (data[i] !== undefined && data[i] !== null) {
                ulHtml += '<a href="javascript:;">';
                var curr = data[i].kv.element.value;
                if (curr.icon !== undefined && curr.icon !== '') {
                    if (curr.icon.indexOf('fa-') !== -1) {
                        ulHtml += '<i class="fa ' + curr.icon + '" aria-hidden="true" data-icon="' + curr.icon + '"></i>';
                    } else {
                        ulHtml += '<i class="layui-icon" data-icon="' + curr.icon + '">' + curr.icon + '</i>';
                    }
                }
                ulHtml += '<cite>' + curr.contents + '</cite>'
                ulHtml += '</a>';
                ulHtml += '<dl class="layui-nav-child">'
                for(var key in child) {
                    var elem = child[key].kv.element;
                    var menuData = child[key].kv.shortcut;
                    config.customerConfig[key] = menuData;
                    ulHtml += '<dd title="' + elem.value.contents + '">';
                    ulHtml += '<a href="javascript:;" data-url="' + elem.value.uri + '">';
                    ulHtml += '<menu data-nav="' + key + '"></menu>';
                    if (elem.value.icon !== undefined && elem.value.icon !== '') {
                        if (elem.value.icon.indexOf('fa-') !== -1) {
                            ulHtml += '<i class="fa ' + elem.value.icon + '" data-icon="' + elem.value.icon + '" aria-hidden="true"></i>';
                        } else {
                            ulHtml += '<i class="layui-icon" data-icon="' + elem.kvalue.icon + '">' + elem.value.icon + '</i>';
                        }
                    }
                    ulHtml += '<cite>' + elem.value.contents + '</cite>';
                    ulHtml += '</a>';
                    ulHtml += '</dd>';
                }
                ulHtml += '</dl>';
            } else {
                var dataUrl = (data[i].kv.element.uri !== undefined && data[i].kv.element.uri !== '') ? 'data-url="' + data[i].kv.element.uri + '"' : '';
                ulHtml += '<a href="javascript:;" ' + dataUrl + '>';
                if (data[i].kv.element.icon !== undefined && data[i].kv.element.icon !== '') {
                    if (data[i].kv.element.icon.indexOf('fa-') !== -1) {
                        ulHtml += '<i class="fa ' + data[i].kv.element.icon + '" aria-hidden="true" data-icon="' + data[i].kv.element.icon + '"></i>';
                    } else {
                        ulHtml += '<i class="layui-icon" data-icon="' + data[i].kv.element.icon + '">' + data[i].kv.element.icon + '</i>';
                    }
                }
                ulHtml += '<cite>' + data[i].name + '</cite>'
                ulHtml += '</a>';
            }
            ulHtml += '</li>';
        }
        ulHtml += '</ul>';
        return ulHtml;
    }

    var navbar = new Navbar();

    exports('navbar', function (options) {
        return navbar.set(options);
    });
});