/**
 * Created by Administrator on 2018/12/18.
 */
var App = function () {
    this.Startup = function () {
        this.Tab();
        this.Relayout();
        this.StopPropagation();
        this.CalendarControl();
        window.onresize = this.Relayout.bind(this);

        $('#start-text').on('click', this.HourStartIntervalSelect.bind(this));
        $('#end-text').on('click', this.HourEndIntervalSelect.bind(this));
        $(document).on("click", this.OnBlankClick);

    };

    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        $(".map-wrapper").width(width);
        $(".map-wrapper,.main").height(height - 58);
        //$('.color-wrapper').height(height - 240);
    };

    this.Tab = function () {
        $(".rainfall-tab ul li").click(function () {
            $(this).addClass("action").siblings().removeClass("action");
            var index = $(this).index();
            $(".rainfall-content .rainfall-information").eq(index).css("display", "block").siblings().css("display", "none");
        });
    };

    this.Date = function () {
        $("#date-start").datebox({
            onSelect: function (beginDate) {
                $('#ate-end').datebox({disabled: false});
                $('#date-end').datebox().datebox('calendar').calendar({
                    validator: function (date) {
                        //return beginDate<date;
                        var d1 = new Date(beginDate.getFullYear(), beginDate.getMonth(), beginDate.getDate());
                        var d2 = new Date(beginDate.getFullYear(), beginDate.getMonth(), beginDate.getDate());
                        d2.setMonth(d1.getMonth() + 1);
                        return d1 <= date && date <= d2;
                    }
                });
            }
        });
    };

    this.HourStartIntervalSelect = function () {
        $('#start-text').toggleClass('select-arrow');
        $('#start-select').fadeToggle(300, function () {
            $(this).find('li').click(function () {
                var text = $(this).html();
                $('#start-text').html(text);
                $(this).addClass('action').siblings().removeClass('action');
                $(this).parents('#start-select').siblings('span').removeClass('select-arrow');
                $(this).parents('#start-select').fadeOut();
            })
        });
    };

    this.HourEndIntervalSelect = function () {
        $('#end-text').toggleClass('select-arrow');
        $('#end-select').fadeToggle(300, function () {
            $(this).find('li').click(function () {
                var text = $(this).html();
                $('#end-text').html(text);
                $(this).addClass('action').siblings().removeClass('action');
                $(this).parents('#end-select').siblings('span').removeClass('select-arrow');
                $(this).parents('#end-select').fadeOut();
            })
        });
    };

    this.OnBlankClick = function () {
        if ($("#start-text").hasClass("select-arrow")) {
            $('#start-select').siblings('span').removeClass('select-arrow');
            $('#start-select').fadeOut();
        }
        if ($("#end-text").hasClass("select-arrow")) {
            $('#end-select').siblings('span').removeClass('select-arrow');
            $('#end-select').fadeOut();
        }
    };

    this.StopPropagation = function () {
        $('#start-text,#end-text').click(function (e) {
            e.stopPropagation()
        });
    };

    this.CalendarControl = function () {
        $('#date-start').datebox({
            panelWidth: 128,
            panelHeight: 222
        });
    };

};
$(function () {
    var app = new App();
    app.Startup();
});