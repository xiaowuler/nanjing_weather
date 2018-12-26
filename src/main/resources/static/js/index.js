/**
 * Created by Administrator on 2018/9/26.
 */
var App = function () {

    this.BottomPanel = new BottomPanel(this);
    this.RightPanel = new RightPanel(this);
    this.LeftPanel = new LeftPanel(this);

    this.Startup = function () {
        this.LocalTime();
        this.OnRefreshButton();
        this.Tab();
        this.Date();
        this.Drag();
        this.DragTitle();
        this.CreateThreshold();
        this.Relayout();
        this.RightPanel.Startup();
        this.BottomPanel.Startup();
        this.LeftPanel.Startup();
        window.onresize = this.Relayout.bind(this);
    };

    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var bottomWidth = (width - 20);
        var slideWidth = (bottomWidth / 2) - 5;
        var leftHeight = $(".left").height();
        var radarHeight = $(".theme-radar").height();
        $(".map-wrapper").width(width);
        $(".map-wrapper,.main").height(height - 58);
        $(".bottom").width(bottomWidth);
        $(".slide").width(slideWidth);
        $(".slide-tab").width(slideWidth - 155);
        $(".theme-raindrop").height(leftHeight - radarHeight * 2);
    };

    //获取当前时间
    this.LocalTime = function () {
        function Time() {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var minutes = date.getMinutes();
            var second = date.getSeconds();
            var currentDay = date.getDay();
            var week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];

            hour = (hour < 10) ? "0" + hour : hour;
            minutes = (minutes < 10) ? "0" + minutes : minutes;
            second = (second < 10) ? "0" + second : second;
            var currentDate = year + "/" + month + "/" + day;
            var currentTime = hour + ":" + minutes + ":" + second;
            var currentWeek = week[currentDay];
            document.getElementById("date").innerHTML = currentDate;
            document.getElementById("current").innerHTML = currentTime;
            document.getElementById("week").innerHTML = currentWeek;
            setTimeout(Time, 500);
        }

        Time();
    };

    //刷新按钮
    this.OnRefreshButton = function () {
        $('.refresh a').click(function () {
            $(this).parent().toggleClass('refresh-off');
        });
    };

    //tab切换
    this.Tab = function () {
        $(".rainfall-tab ul li").click(function () {
            $(this).addClass("action").siblings().removeClass("action");
            var index = $(this).index();
            $(".rainfall-content .rainfall-information").eq(index).css("display", "block").siblings().css("display", "none");
        });
        $(".theme").each(function () {
            var tab = $(this).find(".title-tab li");
            var content = tab.parents(".title").next().find(".theme-chart");
            tab.click(function () {
                $(this).addClass("action").siblings().removeClass("action");
                var index = $(this).index();
                content.eq(index).css("display", "block").siblings().css("display", "none");
            })
        })
    };

    //开始时间和结束时间
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

    //创建阈值
    this.CreateThreshold = function () {
        $('#threshold').numberspinner({
            min: 0,
            editable: false
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

    this.DragTitle = function () {
        var oDiv = document.getElementById("map-info");
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