layui.define([
    'layer'
], function(exports) {
    var $ = layui.jquery;
    var obj = {
        pageLoad:function(url){
            $layui_body_container = $('admin-container')
            $layui_body_container.html("");
            if (url === "" || undefined) return;
            //显示加载loading动画
            layer.load(2);
            //请求成功，关闭loading动画
            $layui_body_container.load(url, function(response, status, xhr) {
                if (status === "success") {
                    layer.closeAll('loading');
                } else if (status === "error") {
                    layer.closeAll('loading');
                    layer.msg("加载失败，请检查网络链接是否正常！");
                }
            });
        }
    };
    exports('ajaxhtml',obj);
});