/**
 * Created by Administrator on 2018/11/22.
 */
var App = function () {
    this.Startup = function () {
        this.TabSlide();
        $('.detail-overview .detail-list:first-child').show();
    };

    this.TabSlide = function () {
        $('#slide ul li').click(function(){
            $(this).addClass("action").siblings().removeClass("action");
            var index = $(this).index();
            $('.detail-overview .detail-list').eq(index).css("display","block").siblings().css("display","none");
        })
    };
};
$(function () {
    var app = new App();
    app.Startup();

});