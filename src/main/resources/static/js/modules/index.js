layui.config({
    version: false,
    debug: false,
    base: 'js/modules/'
}).use(['element', 'layer'], function () {
    var element = layui.element,
        $ = layui.$;
    //应该将一些公共的模块抽离出去，目前现考虑实现功能
    element.on('nav(nav)', function (element) {
        var $element = $(element),
            addurl = $element.children().data().url;
        //添加面包屑导航
        addBreadcrumb($element);

        ajaxhtml(addurl);

    });

    /**
     * 添加面包屑导航
     * @param {*传入被点击菜单的jquery对象}
     */
    function addBreadcrumb($element) {
        var navtitle = $element.text(),
            localname = $element.context.localName;
        $layui_breadcrumb = $('.layui-breadcrumb');
        if (localname === 'li') {
            $layui_breadcrumb.html("<a href='javascript:;'>" + navtitle + "</a>")
        } else if (localname === 'dd') {
            var bdarr = [], bdstr;
            var curtext = $($element.parent().parent().children()[0]).text();
            bdarr.push(curtext);
            bdarr.push(navtitle);
            for (var i = 0, len = bdarr.length; i < len; i++) {
                bdstr += "<a href='javascript:;'>" + bdarr[i] + "</a>";
            }
            $layui_breadcrumb.html(bdstr);
            element.init();
        }
    }

    // $.ajax({
    //     url: 'menu/getMenus.htmls',
    //     data: {},
    //     success: function (data) {
    //         var html = "";
    //         if (data.data != null) {
    //             $.each(data.data, function (i, d) {
    //                 html += "<li class='layui-nav-item layui-nav-itemed'>";
    //                 html += "<a href='javascript:;' data-url='" + d.menuurl + "'>" + d.name + "</a>";
    //                 if (d.children != null && d.children.length > 0) {
    //                     html += "<dl class='layui-nav-child'>";
    //                     $.each(d.children, function (j, item) {
    //                         html += "<dd><a href='javascript:;' data-url='" + item.menuurl + "'>" + item.name + "</a></dd>";
    //                     })
    //                     html += "</dl>";
    //                 }
    //                 html += "</li>";
    //             });
    //         }
    //         $("#menus").empty();
    //         $("#menus").append(html);
    //         element.init();
    //         initHome();
    //     }
    // });

    /**
     * 初始化首页内容加载
     */
    function initHome() {
        // 获取点击菜单上的链接
        var $layui_nav_one = $('.layui-nav-tree').children()[0],
            one_url = $($layui_nav_one).children().data().url;
        if (one_url !== "") {
            ajaxhtml(one_url);
        }
    };

    function ajaxhtml(url) {
        $layui_body_container = $('.layui-body-container')
        $layui_body_container.html("");
        if (url === "" || undefined) return;
        //显示加载loading动画
        layer.load(2);
        //请求成功，关闭loading动画
        $layui_body_container.load(url, function (response, status, xhr) {
            if (status === "success") {
                layer.closeAll('loading');
            } else if (status === "error") {
                layer.closeAll('loading');
                layer.msg("加载失败，请检查网络链接是否正常！");
            }
        });
    }

});