/**
 * Created by Administrator on 2019/1/7.
 */
var App = function () {

    this.Startup = function () {

        this.Relayout();
        this.WindSelect();
        this.RightScroll();
        this.Drag();
        //this.IntervalSelect();
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

    /*this.IntervalSelect = function () {
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
    };*/

    this.MillimeterSelect = function () {
        $('.millimeter-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                $(e.target).removeClass("action").siblings().removeClass("action");
                $('a').remove('.rain');
            }else {
                $(e.target).addClass("action").siblings().removeClass("action");
                if($('.layer-text a.rain').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="rain" val="rainfalls">雨</a>')
            }
            this.LayerTextSelect('rain');
        }.bind(this));
    };

    this.VariableSelect = function () {
        $('.variable-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                $(e.target).removeClass("action").siblings().removeClass("action");
                $('.temperature-select a.action').removeClass('action');
                $('a').remove('.temp');
            }else {
                $(e.target).addClass("action").siblings().removeClass("action");
                if(!$('.temperature-select a').hasClass('action'))
                    $('.temperature-select a:first').addClass("action").siblings().removeClass("action");
                if($('.layer-text a.temp').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="temp" val="temperatures">温</a>')
            }
            this.LayerTextSelect('temp');
        }.bind(this));
    };

    this.TemperatureSelect = function () {
        $('.temperature-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                $(e.target).removeClass("action").siblings().removeClass("action");
                $('.variable-select a.action').removeClass('action');
                $('a').remove('.temp');
            }else {
                $(e.target).addClass("action").siblings().removeClass("action");
                if(!$('.variable-select a').hasClass('action'))
                    $('.variable-select a:first').addClass("action").siblings().removeClass("action");
                if($('.layer-text a.temp').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="temp" val="temperatures">温</a>')
            }
            this.LayerTextSelect('temp');
        }.bind(this));
    };

    this.WindSelect = function () {
        $('.wind-select a').click(function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
            if($('.wind-select a').hasClass('action')){
                if($('.layer-text a.wind').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="wind" val="winds">风</a>')
            }else{
                $('a').remove('.wind');
            }
            this.LayerTextSelect('wind');
        }.bind(this));
    };

    this.GeothermalSelect = function () {
        $('.geothermal-select a').click(function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
            if($('.geothermal-select a').hasClass('action')){
                if($('.layer-text a.land').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="land" val="groundTemperature">地</a>')
            }else{
                $('a').remove('.land');
            }
            this.LayerTextSelect('land');
        }.bind(this));
    };

    this.AirPressureSelect = function () {
        $('.air-pressure-select a').click(function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
            if($('.air-pressure-select a').hasClass('action')){
                if($('.layer-text a.press').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="press" val="pressures">压</a>')
            }else{
                $('a').remove('.press');
            }
            this.LayerTextSelect('press');
        }.bind(this));
    };

    this.HumiditySelect = function () {
        $('.humidity-select a').click(function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
            if($('.humidity-select a').hasClass('action')){
                if($('.layer-text a.hum').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="hum" val="humidities">湿</a>')
            }else{
                $('a').remove('.hum');
            }
            this.LayerTextSelect('hum');
        }.bind(this));
    };

    this.LayerTextSelect = function () {
        $('.layer-text a').off('click').on('click',function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
        }.bind(this));
    };

    this.RightScroll = function () {
        $("#element").niceScroll({cursorborder:"",cursorcolor:"#d5d5d5",boxzoom:false});
    };

    this.Drag = function () {
        var oDiv = document.getElementById("drag");
        var disX = 0;
        var disY = 0;
        oDiv.onmousedown = function () {
            var e = e || window.event;
            disX = e.clientX - oDiv.offsetLeft;
            disY = e.clientY - oDiv.offsetTop;

            document.onmousemove = function (e) {
                var e = e || window.event;
                var leftX = e.clientX - disX;
                var topY = e.clientY - disY;

                if (leftX < 0) {
                    leftX = 0;
                }
                else if (leftX > document.documentElement.clientWidth - oDiv.offsetWidth) {
                    leftX = document.document.documentElement.clientWidth - oDiv.offsetWidth;
                }

                if (topY < 0) {
                    topY = 0;
                } else if (topY > document.documentElement.clientHeight - oDiv.offsetHeight) {
                    topY = document.documentElement.clientHeight - oDiv.offsetHeight;
                }
                oDiv.style.left = leftX + "px";
                oDiv.style.top = topY + "px";
            };
            document.onmouseup = function () {
                document.onmousemove = null;
                document.onmouseup = null;
            }
        }
    }

};

$(function () {
    var app = new App();
    app.Startup();
});