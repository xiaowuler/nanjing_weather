//风廓线
var App = function (){
    var index = 0;
    var i = 1;
    var interval = $('#interval-select li');
    var interval6 = $('#interval-select6 li');
    var interval30 = $('#interval-select30 li');
    var broad = $('#broad-select li');
    var hour;
    var date;
    var Minute;
    var imagetime='';
    var timing=3000;
    var county=''; //获取当前的地区
    var imgHtml2='';
    var  productValue='6-fen-zhong';
    var windValue='contour';
    var categoryCodeHtml = '';
    var  categoryCodeValue="feng-kuo-xian";
    this.Startup = function () {
        this.OnBroadClick();
        this.On30MinuteClick();
        this.On60MinuteClick();
        this.OnDirectionSelectClick();
        this.getDdatTime();
        this.StopPropagation();
       // $(document).on("click", this.DocumentHide);
        $("#6minute").trigger('click');
        $('#export').on('click', this.OnexportButtonClick.bind(this));//图片导出
        $('#aerological').on('click', this.OnSecondIntervalSelect.bind(this));//定时器时间间隔
        $('#pause').on('click', this.OnPlayButtonClick.bind(this));//定时器的暂停
        $('#play').on('click', this.OnPauseButtonClick.bind(this));//定时器的开始
        $('#interval-text').on('click', this.OnHourIntervalSelect.bind(this));
        $('#interval-text6').on('click', this.OnHourIntervalSelect.bind(this));
        $('#interval-text30').on('click', this.OnHourIntervalSelect.bind(this));
        $('#broad-text').on('click', this.OnBroadHourSelect.bind(this));
        $('#interval-prev').on('click', this.OnPrevIntervalSelect.bind(this));//小时框左
        $('#interval-next').on('click', this.OnNextIntervalSelect.bind(this));//小时框右
        //$('#broad-prev').on('click', this.OnPrevBroadIntervalSelect.bind(this));
        //$('#broad-next').on('click', this.OnNextBroadIntervalSelect.bind(this));
        this.current();
        this.Onregion();//获取区域
        this.OnProductSelectClick();//产品列表的点击事件
        $('#query-button').click(function(){ //查询按钮
            this.serachDataList();
        }.bind(this));

    }

    this.OnBroadClick = function () {
        $('.level-broad .aside-title,#6minute').click(function () {
            Minute= $('#interval-text6').text();
            date = $('#calendar').val();
            hour=$('#broad-text').text();
            imagetime = date +'/'+hour+'/'+Minute;

            $('.direction').show();
            $('.minute-element').show();
            $('.hour-element,.minute-element30').hide();
            $('.broad-hour-select,.broad-element').show();
            $('#hour-slide').addClass('arrow-hide');
        }.bind(this))
    };

    this.On30MinuteClick = function () {
        $('#30minute').click(function () {
            Minute= $('#interval-text30').text();
            date = $('#calendar').val();
            hour=$('#broad-text').text();
            imagetime = date +'/'+hour+'/'+Minute;

            $('.direction').show();
            $('.minute-element30').show();
            $('.broad-hour-select,.broad-element').show();
            $('.hour-element,.minute-element').hide();
        }.bind(this))
    };
    this.On60MinuteClick = function () {
        $('#60minute').click(function () {
            Minute=null;
            date = $('#calendar').val();
            hour= $('#interval-text').text();

            imagetime = date +'/'+hour;
            $('.hour-element').show();
            $('.minute-element,.minute-element30,.broad-hour-select').hide();
            $('#hour-slide').removeClass('arrow-hide');
        })
    };

    this.CalendarControl = function () {
        $('#calendar').datebox({
            panelWidth: 190,
            panelHeight: 222
        });
    };

    this.OnDirectionSelectClick = function () {
        $('.direction-select a').click(function(e) {
            // 点击事件
            $('#interval-text').html(this.imagHour);
            $('#broad-text').html(this.imagHour);
            windValue=$(e.target).attr('val');
            app.serachDataList();
            $(e.target).addClass('action').siblings().removeClass('action');
        })
    };

    this.OnexportButtonClick = function () {
        // Get download url
        var imageUrl = $(".imgeUrl").find(".action").find("img").attr("src");
        var downloadUrl = 'products/downloadFile?url={0}'.format(imageUrl);

        // Trigger click event
        var link = $('#download-link');
        link.attr('href', downloadUrl);
        link[0].click();
    };

    this.OnSecondIntervalSelect = function () {
        $('#aerological').addClass('select-arrow');
        $('#select').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                timing= $(this).attr('val');
                $(this).addClass('action').siblings().removeClass('action');
                $(this).parents('#select').siblings().text($(this).text()).attr("val", $(this).text());
                $(this).parents('#select').siblings().removeClass('select-arrow');
                $(this).parents('#select').fadeOut();
            })
        });
    };
    this.OnPlayButtonClick=function () {
        $('#play').show();
        $('#pause').hide();
        this.timer=setInterval(function(){
            var show = new ProductShow();
            show.Init('#detail-slide');
            show.ActiveNext();
        },timing);
    }

    this.OnPauseButtonClick = function () {
        clearInterval(this.timer);
        $('#play').hide();
        $('#pause').show();
    };

    this.OnHourIntervalSelect = function () {
        $('#interval-text').toggleClass('select-arrow');
        $('#interval-select').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                index  =  interval.index(this);
                var text = interval.eq(index).html();
                $('#interval-text').html(text);
                interval.eq(index).addClass('action').siblings().removeClass('action');
                $(this).parents('#interval-select').siblings('span').removeClass('select-arrow');
                $(this).parents('#interval-select').fadeOut();
            })
        });

        $('#interval-text6').toggleClass('select-arrow');
        $('#interval-select6').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                index  =  interval6.index(this);
                var text = interval6.eq(index).html();
                $('#interval-text6').html(text);
                interval6.eq(index).addClass('action').siblings().removeClass('action');
                $(this).parents('#interval-select6').siblings('span').removeClass('select-arrow');
                $(this).parents('#interval-select6').fadeOut();
            })
        });
        $('#interval-text30').toggleClass('select-arrow');
        $('#interval-select30').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                index  =  interval30.index(this);
                var text = interval30.eq(index).html();
                $('#interval-text30').html(text);
                interval30.eq(index).addClass('action').siblings().removeClass('action');
                $(this).parents('#interval-select30').siblings('span').removeClass('select-arrow');
                $(this).parents('#interval-select30').fadeOut();
            })
        });
    };
    this.OnBroadHourSelect = function () {
        $('#broad-text').toggleClass('select-arrow');
        $('#broad-select').fadeToggle(300, function() {
            $(this).find('li').click(function (){
                index  =  broad.index(this);
                var text = broad.eq(index).html();
                $('#broad-text').html(text);
                broad.eq(index).addClass('action').siblings().removeClass('action');
                $(this).parents('#broad-select').siblings('span').removeClass('select-arrow');
                $(this).parents('#broad-select').fadeOut();
            })
        });
    };

    this.Interval = function (i) {
        var interval = $('#interval-select li');
        var text = interval.eq(i).html();
        $('#interval-text').html(text);
        interval.eq(i).addClass('action').siblings().removeClass('action');
    };

    this.OnPrevIntervalSelect = function () {
        //左按钮
        if (index >= 1) {
            --index;
            this.Interval(index);
        }
        i--;
        this.serachDataList();
    };

    this.OnNextIntervalSelect = function () {
        //右按钮
        var iLength = $('#interval-select li').length;
        if (index >= 0 && index < iLength - 1) {
            ++index;
            this.Interval(index);
        } else {
            this.Interval(0);
            index=0;
            i=1;
        }
        this.serachDataList();
    };

    this.OnPrevBroadIntervalSelect = function () {
        //左按钮
        if (index >= 1) {
            --index;
            this.BroadInterval(index);
        }
        i--;
        this.serachDataList();
    };

    this.OnNextBroadIntervalSelect = function () {
        //右按钮
        if (index >= 0 && index < iLength - 1) {
            ++index;
            this.BroadInterval(index);
        } else {
            this.BroadInterval(0);
            index=0;
            i=1;
        }
        this.serachDataList();
    };

    this.BroadInterval = function (i) {
        var interval = $('#interval-select li');
        var text = interval.eq(i).html();
        $('#broad-text').html(text);
        interval.eq(i).addClass('action').siblings().removeClass('action');
    };

    this.current=function () {
        var startH= $('#interval-text').text();
        var index1=startH.split("时")
        index=index1[0];
    }

    //获取系统当前时间的小时
    this.imagHour = function CurentTime(){
        var now = new Date();
        now = now.addHours(1);
        var hh = now.getHours();
        var clock = "";
        if(hh < 10)
            clock += "0";
        clock += hh + "时";
        return(clock);
    }

    //获取最新分钟
    this.minute=function CurentTime(){
        var now = new Date();
        var mm = now.getMinutes();
        var clock = "";
        if (mm < 10) clock += '0';
        clock += mm+'分钟';
        return(clock);
    }

    this.getDdatTime=function () {

        $('#broad-text').html(this.imagHour);
        $('#interval-text6').html(this.minute);
        $('#interval-text').html(this.imagHour);
        $('#interval-text30').html(this.minute);

    }

    this.StopPropagation = function () {
        $('#interval-text').click(function (e) {
            e.stopPropagation()
        });
        $('#interval-text6').click(function (e) {
            e.stopPropagation()
        });
        $('#interval-text30').click(function (e) {
            e.stopPropagation()
        });
        $('#aerological').click(function (e) {
            e.stopPropagation()
        });
        $('#broad-text').click(function (e) {
            e.stopPropagation()
        });
    };

    this.DocumentHide = function () {
        if($("#interval-text").hasClass("select-arrow")){
            $('#interval-select').siblings('span').removeClass('select-arrow');
            $('#interval-select').fadeOut();
        }
        if($("#interval-text6").hasClass("select-arrow")){
            $('#interval-select6').siblings('span').removeClass('select-arrow');
            $('#interval-select6').fadeOut();
        }
        if($("#interval-text30").hasClass("select-arrow")){
            $('#interval-select30').siblings('span').removeClass('select-arrow');
            $('#interval-select30').fadeOut();
        }
        if ($('#aerological').hasClass('select-arrow')){
            $('#select').siblings().removeClass('select-arrow');
            $('#select').fadeOut();
        }
        if ($('#broad-text').hasClass('select-arrow')){
            $('#broad-select').siblings().removeClass('select-arrow');
            $('#broad-select').fadeOut();
        }
    };

    // 获取当前页面的年月日时
    this.serachDataList = function(){
        // 获取时间日
        date = $('#calendar').val();
        // 获取时
        hour= $('#interval-text').text();
        //获取分钟
        if(Minute == null){
            imagetime = date +'/'+hour;
        }else{
            hour= $('#broad-text').text();
            if(productValue=='6-fen-zhong'){
                Minute = $('#interval-text6').text();
            }
            if(productValue=='30-fen-zhong'){
                Minute = $('#interval-text30').text();
            }
            imagetime = date +'/'+hour +'/'+Minute;
        }
        this.OnSmallpicture(); //加载图片
    }

    this.OnProductSelectClick = function () {//产品列表点击事件
        $('.product-select a').click(function(e) {
            // 点击事件
            $('#broad-text').html(hour);
            productValue=$(e.target).attr('val');
            app.serachDataList();
            $(e.target).addClass('action').siblings().removeClass('action');
        }.bind(this))
    };

    this.Onregion = function () {
        $(function (){
            $.post("ProductCategoryRegionRels/findByCategoryCode?categoryCode="+categoryCodeValue, function (data){
                $(data).each(function (index,data) {
                    if(index==0){
                        categoryCodeHtml+='<a href="javascript:;" class="action" val="'+ data.categoryCode+'">'+data.name+'</a>'
                    }else {
                        categoryCodeHtml+='<a href="javascript:;" val="'+ data.categoryCode+'">'+data.name+'</a>'
                    }
                });
                $('#Onregions').html(categoryCodeHtml);
                app.OnAreaSelectClick();
                county= data[0].categoryCode;
                app.serachDataList();
            });
            categoryCodeHtml = '';
        });
    }


    this.ActiveProductCallback = function (product) {
        var image = product.find('img:first');
        var url=image.attr('src');
        // console.log(url);
        imgHtml2 = '<li style="display: list-item;"><div><img class="img1" src="{0}"></div></li>'.format(url)
        $('.imgeUrl2').html(imgHtml2);
    }

    this.OnAreaSelectClick = function () {
        $('.area-select a').click(function() {//选择产品点击事件
            county=$(this).attr('val');
            app.serachDataList();
            $(this).addClass('action').siblings().removeClass('action');
        })
    };

    this.OnSmallpicture = function () {
        $.post("products/findByTime?type="+productValue+'&imagetime='+imagetime+'&county='+county+'&windValue='+windValue+'&categoryCodeValue='+categoryCodeValue, function (data){
            var show = new ProductShow(this.ActiveProductCallback.bind(this));
            show.Init('#detail-slide');
            show.Load(data);

        }.bind(this));
    }

}

$(document).ready(function() {
    var app = new App();
    app.Startup();
});