/**
 * Created by Administrator on 2019/1/7.
 */
var App = function () {

    this.Startup = function () {

        this.Relayout();
        this.WindSelect();
        this.RightScroll();
        this.IntervalSelect();
        this.HumiditySelect();
        this.VariableSelect();
        this.LayerTextSelect();
        this.GeothermalSelect();
        this.MillimeterSelect();
        this.AirPressureSelect();
        this.TemperatureSelect();
        this.OnShrinkRightButton();
        window.onresize = this.Relayout.bind(this);
        //$('#shrink-left').on('click', this.OnShrinkLeftButton.bind(this));
        //$('#shrink-right').on('click', this.OnShrinkRightButton.bind(this));
        //$('#shrink-bottom').on('click', this.OnShrinkBottomButton.bind(this));
    };

    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var dateHeight = $('.date-select').height();
        var layerHeight = $('.layer').height();
        $(".map-wrapper").width(width);
        $(".map-wrapper,.main").height(height - 50);
        $('.right').height(height - 50);
        $('#element,#Yscrollouter').height(height - layerHeight - dateHeight - 50);
    };

    this.OnShrinkRightButton = function () {
        var shrinkRight = $('#shrink-right');
        shrinkRight.click(function () {
            $('.right').toggleClass('right-toggle');
        });
        shrinkRight.hover(function () {
            $(this).toggleClass('shrink-righted');
            $('.right').toggleClass('right-line');
        });
    };

    this.IntervalSelect = function () {
        $(".interval-select a").click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.MillimeterSelect = function () {
        $('.millimeter-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.VariableSelect = function () {
        $('.variable-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.TemperatureSelect = function () {
        $('.temperature-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.WindSelect = function () {
        $('.wind-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.GeothermalSelect = function () {
        $('.geothermal-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.AirPressureSelect = function () {
        $('.air-pressure-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.HumiditySelect = function () {
        $('.humidity-select a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.LayerTextSelect = function () {
        $('.layer-text a').click(function () {
            $(this).toggleClass("action").siblings().removeClass("action");
        });
    };

    this.RightScroll = function () {
        $("#element").niceScroll({cursorborder:"",cursorcolor:"#d5d5d5",boxzoom:true});
    };

};

$(function () {
    var app = new App();
    app.Startup();
});