/**
 * Created by Administrator on 2018/9/26.
 */
var App = function () {

    this.title = $('.title h2');
    this.BottomPanel = new BottomPanel(this);
    //this.RightPanel = new RightPanel(this);
    this.LeftPanel = new LeftPanel(this);

    this.Startup = function () {

        this.Relayout();
        this.LocalTime();
        this.WindSelect();
        this.RightScroll();
        this.IntervalSelect();
        this.HumiditySelect();
        this.VariableSelect();
        this.OnRefreshButton();
        this.LayerTextSelect();
        this.GeothermalSelect();
        this.MillimeterSelect();
        this.AirPressureSelect();
        this.TemperatureSelect();
        this.Slide();
        this.OnShrinkLeftButton();
        this.OnShrinkRightButton();
        this.OnShrinkBottomButton();
        this.LeftPanel.Startup();
        this.BottomPanel.Startup();
        window.onresize = this.Relayout.bind(this);
        //$('#shrink-left').on('click', this.OnShrinkLeftButton.bind(this));
        //$('#shrink-right').on('click', this.OnShrinkRightButton.bind(this));
        //$('#shrink-bottom').on('click', this.OnShrinkBottomButton.bind(this));
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

    this.LocalTime = function () {
        function Time() {
            var date = new Date();
            var hour = date.getHours();
            var minutes = date.getMinutes();
            var nextMinute = minutes + 5;
            var second = date.getSeconds();

            hour = (hour < 10) ? "0" + hour : hour;
            minutes = (minutes < 10) ? "0" + minutes : minutes;
            nextMinute = (minutes < 10) ? "0" + nextMinute : nextMinute;
            second = (second < 10) ? "0" + second : second;
            var LastTime = hour + ":" + minutes + ":" + second;
            var nextTime = hour + ":" + nextMinute + ":" + second;
            document.getElementById("last-time").innerHTML = LastTime;
            document.getElementById("next-time").innerHTML = nextTime;
            setTimeout(Time, 500);
        }
        Time();
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
        $(".interval-select a").click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.MillimeterSelect = function () {
        $('.millimeter-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.VariableSelect = function () {
        $('.variable-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.TemperatureSelect = function () {
        $('.temperature-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.WindSelect = function () {
        $('.wind-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.GeothermalSelect = function () {
        $('.geothermal-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.AirPressureSelect = function () {
        $('.air-pressure-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.HumiditySelect = function () {
        $('.humidity-select a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.LayerTextSelect = function () {
        $('.layer-text a').click(function () {
            $(this).addClass("action").siblings().removeClass("action");
        });
    };

    this.RightScroll = function () {
        $("#element").niceScroll({cursorborder:"",cursorcolor:"#d5d5d5",boxzoom:true});
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

};

$(function () {
    var app = new App();
    app.Startup();
});