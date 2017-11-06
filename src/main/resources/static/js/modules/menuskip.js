layui.define([
    'layer'
], function(exports) {
    var obj = {
        pageskip:function(itemtext){
            $('.layui-this').removeClass('layui-this');
            $('#menus').find('a').each(function () {
                var $curthis = $(this),
                    thistext = $curthis.text();
                if(thistext==itemtext){
                    $(this).parent().addClass('layui-this');
                }
            })
        }
    };
    exports('menuskip',obj);
});