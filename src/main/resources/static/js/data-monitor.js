/**
 * Created by Administrator on 2018/11/23.
 */



var App = function () {
    this.DataMonitorGrid = $('#data-monitor-grid');

    var livalue ="rainfall";
    $("li[name='li']").click(function(){
     // 下面这行代码就是ID属性
        livalue = $(this).attr("val");

        app.LoadList();
    });
    //重新采集
    window.Collecting = function(){
       app.LoadList();
    }
// 根据时间段的查询列表
    var startTime ='';
    var endTime ='';

    this.serachDataList  = function(){
        // 获取开始时间日
        var startTime1 = $('#calendar').val();
        // 获取开始时分秒
        var startH= $('.start-hour').text();
        startTime = startTime1 +'/'+startH;
        // 获取结束时间
        var endTime2 = $('#calendar').val();
        // 获取开始时分秒
        var startH= $('.end-hour').text();
        endTime = endTime2 +'/'+startH;
        this.LoadList();
    }

   this.LoadList = function () {
        $('#data-monitor-grid').datagrid({
            columns: [[
                { field: 'routine', title: '资料时间', align: 'center', width: 180 },
                { field: 'begin', title: '采集开始时间', align: 'center', width:180 },
                { field: 'end', title: '采集结束时间', align: 'center', width: 180 },
                { field: 'timeliness_code', title: '采集结果', align: 'center', width: 150,
                    formatter: function (val) {
                        if (val == "all") {
                            return '<span class="rectangle success">采集成功</span>';
                        } else if (val == "missing") {
                            return '<span class="rectangle failure">采集失败</span>';
                        }else {
                            return '<span class="rectangle gather">正在采集</span>';
                        }
                    }
                },
                { field: 'description', title: '备注', align: 'center', width: 160 },
                { field: 'Operate',  title: '操作', align: 'center', width: 120,
                    formatter: function (value, row){
                    if (row.timeliness_code =='all'){
                        var btn='<button class="rectangle rectangle-refresh">重新采集</button>';
                        return btn;
                    } else {
                        var btn='<button class="rectangle rectangle-refresh" onclick="Collecting()">重新采集</button>';
                        return btn;
                    }

                    }
                }
            ]],
            striped: true,
            singleSelect: true,
            url: 'dataArrivals/findByType?type='+livalue +'&startTime='+ startTime +'&endTime=' +endTime,
            pagination : true,
            fitColumns: true,
            fit: true,
            //选择当前页
            pageNumber : 1,
            //页面显示几个
            pageSize : 25,
            //可选页面显示几个
            pageList : [25,35,50]
        });
       startTime ='';
       endTime ='';
    }



    this.Startup = function () {
        this.Relayout();
        window.onresize = this.Relayout.bind(this);
        this.LoadList();
        this.InitDataMonitorGrid();
        this.CalendarControl();
        this.OnLevelFirstMenuClick();
        this.OnLevelSecondMenuClick();
        this.StopPropagation();
        $(document).on("click", this.DocumentHide);
        $('.aside .level-first-menu:first-child ul').show();
        $('#start-text').on('click', this.OnStartTimeIntervalSelect.bind(this));
        $('#end-text').on('click', this.OnEndTimeIntervalSelect.bind(this));
        $('#query-button').click(function(){
            this.serachDataList();
        }.bind(this))
    };
    this.DocumentHide = function () {
        if($("#start-text").hasClass("select-arrow")){
            $('#start-select').siblings('span').removeClass('select-arrow');
            $('#start-select').fadeOut();
        }
        if ($('#end-text').hasClass('select-arrow')){
            $('#end-select').siblings().removeClass('select-arrow');
            $('#end-select').fadeOut();
        }
    };

    this.StopPropagation = function () {
        $('#start-text').click(function (e) {
            e.stopPropagation()
        });
        $('#end-text').click(function (e) {
            e.stopPropagation()
        });
    };
    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var topHeight = $('.top').height() + 10;
        var mainHeight = height - topHeight;
        $('.main,.content,.aside').height(mainHeight);
        $('.content-monitor').height(mainHeight - 50);
        $('.monitor').height(mainHeight - 50);
    };

    this.InitDataMonitorGrid = function () {
        this.DataMonitorGrid.datagrid({
        });
    };
    this.OnLevelFirstMenuClick = function () {
        $('.first-menu-click .aside-title').click(function () {

            $(this).parents('.first-menu-click').toggleClass('action').siblings().removeClass('action');
            $(this).parents('.first-menu-click').find('ul').slideToggle().parents('.first-menu-click').siblings().find('ul').slideUp();
        })
    };


    this.OnLevelSecondMenuClick = function () {
        $('.first-menu-click').find('ul li').click(function () {
            $(this).addClass('active').siblings().removeClass('active');
        })
    };

    this.OnStartTimeIntervalSelect = function () {
        $('#start-text').toggleClass('select-arrow');
        $('#start-select').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                $(this).addClass('action').siblings().removeClass('action');
                $(this).parents('#start-select').siblings().text($(this).text()).attr("val", $(this).text());
                $(this).parents('#start-select').siblings().removeClass('select-arrow');
                $(this).parents('#start-select').fadeOut();
            })
        });
    };

    this.OnEndTimeIntervalSelect = function () {
        $('#end-text').toggleClass('select-arrow');
        $('#end-select').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                $(this).addClass('action').siblings().removeClass('action');
                $(this).parents('#end-select').siblings().text($(this).text()).attr("val", $(this).text());
                $(this).parents('#end-select').siblings().removeClass('select-arrow');
                $(this).parents('#end-select').fadeOut();
            })
        });
    };

    this.CalendarControl = function () {
        $('#calendar,#end-calendar').datebox({
            panelWidth: 190,
            panelHeight: 222
        });
    }

};

var app = null;
$(document).ready(function() {
    app = new App();
    app.Startup();
});


