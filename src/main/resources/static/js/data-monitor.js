/**
 * Created by Administrator on 2018/11/23.
 */
var App = function () {
   this.productValue ="rainfall";
    this.startTime = '';
    this.endTime = '';
    this.categoryCodeValue = '';
    this.element = '';
    this.RegionCode='';
    this.Startup = function () {
        this.Relayout();
        this.AsideMenu();//产品下拉框事件
        this.CalendarControl();
        window.onresize = this.Relayout.bind(this);
        $('.first-menu:first-child ul').show();
        this.LoadList();
        this.BindProductSelectClick();//产品列表
        this.BindQueryButtonClick();

    };
        this.InitDateHour = function () {
           var startDay = $('#calendar').val();
           var startHour = $('#start-select').combobox('getValue');
           this.startTime = startDay + '/' + startHour;

           var endDay=  $('#end-date').val();
           var startHour = $('#end-select').combobox('getValue');
           this.endTime  = endDay + '/' + startHour;
           this.RegionCode=$('#region a[class=action]').attr('val');
           this.LoadList();
    }

    this.Relayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var topHeight = $('.top').height();
        var mainHeight = height - topHeight;
        $('.main,.content,.aside').height(mainHeight);
        $('.monitor').height(mainHeight - 42);
        $('.content').width(width - 285);
    };

    this.CalendarControl = function () {
        $('#calendar,#end-date').datebox({
            panelWidth: 190,
            panelHeight: 200
        });
    }

    this.AsideMenu = function () {
        $(".first-menu .first-menu-title").click(function(e){
            $(e.target).parents().toggleClass("selected").siblings().removeClass("selected");
            this.categoryCodeValue=$(e.target).attr("val");
            this.showArea();//show regions
            $(e.target).parents().siblings().find(".second-nav").slideUp();
            $(e.target).siblings(".second-nav").slideToggle();
        }.bind(this));
    }

    this.BindProductSelectClick = function () {//产品列表
        $(".second-nav > li > a").click(function(e){
            $(e.target).parents('li').addClass("active").siblings().removeClass("active");
            this.productValue= $(e.target).parents('li').attr('val');
            this.RegionCode=$('#region a[class=action]').attr('val');
            this.LoadList();
        }.bind(this))
    }

    this.BindAreaSelectClick=function () {//地区的点击事件
        $('.operate-border a').click(function (e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            this.RegionCode= $(e.target).attr("val");
            //this.productValue=$('.second-nav li[class=action]').attr('val');
            this.LoadList();
        }.bind(this))
    }

    this.showArea = function () {
        if(this.categoryCodeValue =="qi-xiang-liu-yao-su"){
            $('.operate-left').hide();
        }else{
            $('.operate-left').show();
             this.InitRegions();
        }
    }

    this.InitRegions = function () {
        $.ajax({
            url: "ProductCategoryRegionRels/findByCategoryCode?categoryCode="+this.categoryCodeValue,
            async: false,
            success: function(regions){
                $('.operate-border').html('');
                $(regions).each(function (index, region) {
                    var template='<a href="javascript:;" class="{0}" val="{1}">{2}</a>';
                    this.element = template.format(index === 0 ? 'action' : '', region.categoryCode, region.name);
                    $('.operate-border').append(this.element);
                }.bind(this));
            }
        });
        this.BindAreaSelectClick();
    }

    this.LoadList = function () {
        /* var params = this.GetSearchParams();
        var template= 'dataArrivals/findByType?type={0}&startTime={1}&endTime={2}&regionCode={3}';*/
        $('#data-monitor-grid').datagrid({
            columns: [[
                {field: 'routine', title: '资料时间', align: 'center', width: 180},
                {field: 'begin', title: '采集开始时间', align: 'center', width: 180},
                {field: 'end', title: '采集结束时间', align: 'center', width: 180},
                {
                    field: 'timeliness_code', title: '采集结果', align: 'center', width: 150,
                    formatter: function (val) {
                        if (val == "all") {
                            return '<span class="collect success">采集成功</span>';
                        } else if (val == "missing") {
                            return '<span class="collect failure">采集失败</span>';
                        } else {
                            return '<span class="collect gather">正在采集</span>';
                        }
                    }
                },
                {field: 'description', title: '采集描述', align: 'center', width: 160}

                // 隐藏这一列，后期处理
                /*{
                    field: 'Operate', title: '操作', align: 'center', width: 120,
                    formatter: function (value, row) {
                        if (row.timeliness_code == 'all') {
                            var btn = '<button class="collect-refresh">重新采集</button>';
                            return btn;
                        } else {
                            var btn = '<button class="collect-refresh" onclick="Collecting()">重新采集</button>';
                            return btn;
                        }
                    }
                }*/
            ]],
            striped: true,
            singleSelect: true,
            url: 'dataArrivals/findByType?type='+this.productValue + '&startTime=' + this.startTime + '&endTime=' + this.endTime +'&regionCode='+this.RegionCode,
            //url:template.format(params.productValue,params.startTime,params.endTime,params.RegionCode),
            pagination: true,
            fitColumns: true,
            fit: true,
            //选择当前页
            pageNumber: 1,
            //页面显示几个
            pageSize: 25,
            //可选页面显示几个
            pageList: [25, 35, 50]
        });
        this.startTime = '';
        this.endTime = '';
    }

   /*  this.GetSearchParams=function () {
        return {
            productValue: $('.second-nav li[class=active]').attr('val'),

            RegionCode: $('#region a[class=action]').attr('val'),
        }

        return {
            ProductType: $('.product-border a[class=action]').attr('val'),
            SearchTime: '{0}/{1}'.format($('#calendar').val(), $('#interval-text').combobox('getText')),
            RegionCode: $('.area-list li[class=action]').attr('val')
        }
    }*/
    this.BindQueryButtonClick = function () {
        $('#query-button').on('click', this.InitDateHour.bind(this));
    }

}
$(document).ready(function () {
    var app = new App();
    app.Startup();
});


