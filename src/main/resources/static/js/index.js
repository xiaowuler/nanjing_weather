/**
 * Created by Administrator on 2018/9/26.
 */
var App = function () {

    this.title = $('.title h2');
    this.BottomPanel = new BottomPanel(this);
    this.RightPanel = new RightPanel(this);
    this.LeftPanel = new LeftPanel(this);

    this.Startup = function () {

        this.Relayout();
        this.WindSelect();
        this.RightScroll();
        this.IntervalSelect();
        this.HumiditySelect();
        this.VariableSelect();
        this.OnRefreshButton();
        this.LayerTextSelect('temp');
        this.GeothermalSelect();
        this.MillimeterSelect();
        this.AirPressureSelect();
        this.TemperatureSelect();
        this.Slide();
        this.Drag();
        this.OnShrinkLeftButton();
        this.OnShrinkRightButton();
        this.OnShrinkBottomButton();
        this.LeftPanel.Startup();
        this.BottomPanel.Startup();
        this.RightPanel.Startup();
        window.onresize = this.Relayout.bind(this);
    };

    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var slideWidth = width / 2;
        var timeHeight = $('.time').height();
        var layerHeight = $('.layer').height();
        var typeHeight = $('.theme').height();
        var bottomHeight = $('.bottom').height();
        $(".map-wrapper").width(width);
        $(".map-wrapper,.main").height(height - 50);
        $(".slide").width(slideWidth);
        $(".slide-tab").width(slideWidth - 160);
        $('.left,.right').height(height - bottomHeight - 50);
        $('#element,#Yscrollouter').height(height - bottomHeight - timeHeight - layerHeight - 65);

        this.title.css({
            'width': typeHeight,
            'height': '30px',
            'left': -(typeHeight / 2) + 17,
            'top': typeHeight / 2 - 15
        });
    };

    this.OnRefreshButton = function () {
        $('.refresh a').click(function () {
            $(this).parent().toggleClass('refresh-on');
        });
    };

    this.OnShrinkLeftButton = function () {
        var shrinkLeft = $('#shrink-left');
        shrinkLeft.click(function () {
            $('.left').toggleClass('left-toggle');
            $('.element-txt').toggleClass('element-txt-toggle');
            $('.element-value').toggleClass('element-value-toggle');
        });
        shrinkLeft.hover(function () {
            $(this).toggleClass('shrink-lefted');
            $('.left').toggleClass('left-line');
        });
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

    this.OnShrinkBottomButton = function () {
        var shrinkBottom = $('#shrink-bottom');
        shrinkBottom.click(function () {
            $('.bottom').toggleClass('bottom-toggle');
        });
        shrinkBottom.hover(function () {
            $(this).toggleClass('shrink-bottomed');
            $('.bottom').toggleClass('bottom-line');
        });
    };

    this.IntervalSelect = function () {
        $(".interval-select a").click(function (e) {
            if($(e.target).hasClass('action')){
                $(e.target).removeClass("action").siblings().removeClass("action");
                $('.millimeter-select a.action').removeClass('action');
                $('a').remove('.rain');
                //console.log($('.layer-text a.rain').length)
            }else {
                $(e.target).addClass("action").siblings().removeClass("action");
                if(!$('.millimeter-select a').hasClass('action'))
                    $('.millimeter-select a:first').addClass("action").siblings().removeClass("action");
                if($('.layer-text a.rain').length === 0)
                    $('.layer-text').append('<a href="javascript:;" class="rain" val="rainfalls">雨</a>')
            }
            this.LayerTextSelect('rain');
        }.bind(this));
    };

    this.MillimeterSelect = function () {
        $('.millimeter-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                $(e.target).removeClass("action").siblings().removeClass("action");
                $('.interval-select a.action').removeClass('action');
                $('a').remove('.rain');
            }else {
                $(e.target).addClass("action").siblings().removeClass("action");
                if(!$('.interval-select a').hasClass('action'))
                    $('.interval-select a:first').addClass("action").siblings().removeClass("action");
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

    this.LayerTextSelect = function (classname) {
        $('.layer-text a.'+classname).click(function (e) {
            $(e.target).toggleClass("action").siblings().removeClass("action");
        }.bind(this));
    };

    this.RightScroll = function () {
        $("#element").niceScroll({cursorborder:"",cursorcolor:"#d5d5d5",boxzoom:false});
    };

    this.Slide = function () {
        var swiper = new Swiper('.swiper-left', {
            slidesPerView: 5,
            loop: true,
            nextButton: '.swiper-next-left',
            prevButton: '.swiper-prev-left'
        });
        var swiper = new Swiper('.swiper-right', {
            slidesPerView: 5,
            loop: true,
            nextButton: '.swiper-next-right',
            prevButton: '.swiper-prev-right'
        });
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