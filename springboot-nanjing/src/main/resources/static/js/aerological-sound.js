/**
 * Created by Administrator on 2018/11/22.
 */
var App = function () {
    var overview = $('#overview');
    var length = overview.find('ul li').length;
    var tab = $('#tab');
    var dWidth = $('.detail-tab').width();
    var tabNum = tab.find('ul li').length;
    var tabWidth = dWidth / 7;
    var number = 0;
    var index = 0;
    var showNum = 7;
    var time;
    var i = 1;
    var interval = $('#interval-select li');
    var interval6 = $('#interval-select6 li');
    var interval30 = $('#interval-select30 li');
    var broad = $('#broad-select li');
    var iLength = $('#interval-select li').length;


    this.slide = $('#slide ul');
    this.slideList = this.slide.find('li');
    this.Startup = function () {
        this.ElementsTab();
        this.OnMenuSelect();
        this.OnBroadClick();
        this.On30MinuteClick();
        this.On60MinuteClick();
        this.CalendarControl();
        this.OnAreaSelectClick();
        this.OnHourElementClick();
        this.OnProductSelectClick();
        this.OnDirectionSelectClick();
        this.OnLevelFirstMenuClick();
        this.StopPropagation();
        //this.OnexportButtonClick();
        $(document).on("click", this.DocumentHide);
        this.Onregion();
        this.current();
        overview.find('ul li').eq(0).show();
        this.slideList.eq(0).addClass("action");
        window.onresize = this.Relayout.bind(this);
        $('.aside .level-first-menu:first-child ul').show();
        $('#aerological').on('click', this.OnSecondIntervalSelect.bind(this));
        $('#interval-text').on('click', this.OnHourIntervalSelect.bind(this));
        $('#interval-text6').on('click', this.OnHourIntervalSelect.bind(this));
        $('#interval-text30').on('click', this.OnHourIntervalSelect.bind(this));
        $('#broad-text').on('click', this.OnBroadHourSelect.bind(this));
        $('#interval-prev').on('click', this.OnPrevIntervalSelect.bind(this));
        $('#interval-next').on('click', this.OnNextIntervalSelect.bind(this));
        $('#broad-prev').on('click', this.OnPrevBroadIntervalSelect.bind(this));
        $('#broad-next').on('click', this.OnNextBroadIntervalSelect.bind(this));
        $('#pause').on('click', this.OnPlayButtonClick.bind(this));
        $('#play').on('click', this.OnPauseButtonClick.bind(this));

        $('#export').on('click', this.OnexportButtonClick.bind(this));
        $('#prev').on('click', this.OnPrevButtonClick.bind(this));
        $('#next').on('click', this.OnNextButtonClick.bind(this));
        $('#query-button').click(function(){
          this.serachDataList();
        }.bind(this))

        setTimeout(this.ChangeProduct.bind(this), 500);
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

    this.current=function () {
        var startH= $('#interval-text').text();
        var index1=startH.split("时")
           index=index1[0];
    }

   this.Relayout = function () {
        var windowWidth = $(window).width();
        var height = $(window).height();
        var topHeight = $('.top').height() + 10;
        var dWidth = $('.detail-tab').width();
        var mainHeight = height - topHeight;
        var tabWidth = dWidth / 7;

        $('.main,.content,.aside').height(mainHeight);
        $('#overview ul li').width(windowWidth - 580);
        $('#overview ul').width(length * (windowWidth - 580));

        $('.detail').width(windowWidth - 580);
        $('.detail-tab ul').width(tabNum * tabWidth);
        $('.detail-tab ul li').width(tabWidth - 1);
        $('.aerological-right').height(mainHeight - 52);
        $('#overview,#overview ul,#overview ul li img').height(mainHeight - 257);

    };

    this.OnLevelFirstMenuClick = function () {
        $('.first-menu-click .aside-title').click(function () {
            $(this).parents('.first-menu-click').toggleClass('action').siblings().removeClass('action');
            $(this).parents('.first-menu-click').find('ul').slideToggle().parents('.first-menu-click').siblings().find('ul').slideUp();
        })
    };
    var hour = null;
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
    this.OnHourElementClick = function () {
        $('.level-radar .aside-title,.level-microwave .aside-title,.level-raindrop .aside-title,.level-gps .aside-title').click(function () {
            $('.direction').hide();
            $('.minute-element,.minute-element30,.broad-hour-select').hide();
            $('.hour-element').show();
            $('#hour-slide').removeClass('arrow-hide');
        })
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

    this.BroadInterval = function (i) {
        var interval = $('#interval-select li');
        var text = interval.eq(i).html();
        $('#broad-text').html(text);
        interval.eq(i).addClass('action').siblings().removeClass('action');
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
    var timing=3000;
    this.OnPlayButtonClick = function () {
        $('#play').show();
        $('#pause').hide();
        time = setInterval(function () {
            var num = index - showNum + 2;
            var tWidth = -(num * tabWidth);
            tab.find('ul li').css('float','left');
            if (tabNum > 7) {
                if (index < 6) {
                    tWidth = 0;
                }
                if (index == tabNum - 1) {
                    tWidth = -(num - 1) * tabWidth;
                }
                tab.find('ul').stop().animate({'left': tWidth}, 600);
            }
            overview.find('ul li').eq(number).css("display","block").siblings().css("display","none");
            tab.find('ul li').eq(number).addClass('action').siblings(this).removeClass('action');
            number++;
            index++;
            if(number == length)
                number = 0;
            if(index == tabNum)
                index = 0;
        },timing)
    };

    this.OnPauseButtonClick = function () {
        clearInterval(time);
        $('#play').hide();
        $('#pause').show();
    };

    this.CalendarControl = function () {
        $('#calendar').datebox({
            panelWidth: 190,
            panelHeight: 222
        });
    };

    this.SmallElementsTab = function(index) {
        var num = index - showNum + 2;
        var tWidth = -(num * tabWidth);
        tab.find('ul li').css('float','left');
        if (tabNum > 7) {
            if (index < 6) {
                tWidth = 0;
            }
            if (index == tabNum - 1) {
                tWidth = -(num - 1) * tabWidth;
            }
            tab.find('ul').stop().animate({'left': tWidth}, 600);
        }
    };

    this.ElementsSlide = function (number) {
        overview.find('ul li').eq(number).css("display","block").siblings().css("display","none");
        tab.find('ul li').eq(number).addClass('action').siblings(this).removeClass('action');
    };

    this.ElementsTab = function () {
        //小图片的点击事件
        tab.find('ul li').click(function () {
            number = index = tab.find('ul li').index(this);
            var num = index - showNum + 2;
            var tWidth = -(num * tabWidth);
            tab.find('ul li').css('float','left');
            tab.find('ul li').eq(index).addClass('action').siblings(this).removeClass('action');
            overview.find('ul li').eq(number).css("display","block").siblings().css("display","none");
            if (tabNum > 7) {
                if (index < 6) {
                    tWidth = 0;
                }
                if (index == tabNum - 1) {
                    tWidth = -(num - 1) * tabWidth;
                }
                tab.find('ul').stop().animate({'left': tWidth}, 600);
            }
            //app.Relayout();
        })
    };

    this.PrevElement = function () {
        if (number == 0) {
            number = length;
        }
        if (index == 0) {
            index = length
        }
        number --;
        index --;
        this.ElementsSlide(number);
        this.SmallElementsTab(index);
    };

    this.NextElement = function (){
        if (number == length - 1) {
            number =- 1;
        }
        if (index == length - 1) {
            index =- 1;
        }
        index ++;
        this.SmallElementsTab(index);
        number ++;
        this.ElementsSlide(number);
    };

    this.OnPrevButtonClick = function () {
        this.PrevElement();
    };

    this.OnNextButtonClick = function () {
        this.NextElement();
    };

    this.OnAreaSelectClick = function () {
        $('.area-select a').click(function() {

            county=$(this).attr('val');
            app.serachDataList();
            $(this).addClass('action').siblings().removeClass('action');
        })
    };
//获取系统当前时间的小时
   var imagHour = function CurentTime(){
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
    var minute=function CurentTime(){
        var now = new Date();
        var mm = now.getMinutes();
        var clock = "";
        if (mm < 10) clock += '0';
        clock += mm+'分钟';
        return(clock);
    }


    $('#interval-text').html(imagHour);
    $('#broad-text').html(imagHour);


// 获取当前页面的年月日时
    var imagetime='';
    var hour;
    var date;
    var Minute;
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
         this.Addimage();
    }

    var county=''; //获取当前的地区
    var imgHtml='';
    var imgHtml2='';
    var productValue='xiao-guang';
    var categoryCodeValue='ji-guang-lei-da';
    var imgHtml='';
     this.OnSmallpicture = function () {
     $(function () {
        $.post("products/findByTime?type="+productValue+'&imagetime='+imagetime+'&county='+county+'&windValue='+windValue+'&categoryCodeValue='+categoryCodeValue, function (data){
            $(data).each(function (index,data) {
                imgHtml = '<li ><div>'+'<img src="'+data.url+'" class="width100">'+'</div></li>'+ imgHtml;
                imgHtml2 = '<li ><div><div>'+'<img src="'+data.url+'">'+'</div></div></li>' + imgHtml2;
            });
            $('.imgeUrl2').html(imgHtml2);
            app.Relayout();
            length = overview.find('ul li').length;

            $('.imgeUrl').html(imgHtml);

            $(".imgeUrl").find(".action").trigger('click');

            $('.bigImg').attr('src',data[0]);
            tabNum = tab.find('ul li').length;

            $('.imgeUrl').width(($('.imgeUrl li').length) * tabWidth);
            $('.imgeUrl').find('li').width(tabWidth - 1);
            $('.imgeUrl').find('li').eq(tabNum - 1).addClass("action");
            overview.find('ul li').eq(tabNum - 1).show();


            var num = tabNum - showNum;
            var tWidth = -(num * tabWidth);
            tab.find('ul').stop().animate({'left': tWidth}, 600);
          //app.ElementsTab();
        })
    })
}

    this.Addimage = function () {
         imgHtml ='';
         imgHtml2='';
        this.OnSmallpicture();
     }

    this.OnProductSelectClick = function () {
        $('.product-select a').click(function() {
          // 点击事件
            $('#interval-text').html(imagHour);
            $('#broad-text').html(imagHour);
            productValue=$(this).attr('val');
            app.serachDataList();
            $(this).addClass('action').siblings().removeClass('action');
        })
    };
    var windValue='';
    this.OnDirectionSelectClick = function () {
        $('.direction-select a').click(function() {
            // 点击事件
            $('#interval-text').html(imagHour);
            $('#broad-text').html(imagHour);
            windValue=$(this).attr('val');
            app.serachDataList();
            $(this).addClass('action').siblings().removeClass('action');
        })
    };

    var categoryCodeHtml = '';
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

    this.OnMenuSelect = function () {
        $('.product-select').eq(0).show();
        $('.level-radar .aside-title').click(function () {
            categoryCodeValue="ji-guang-lei-da";
            app.Onregion();
            productValue='xiao-guang';
            windValue="";
            $('#interval-text').html(imagHour);
            Minute=null;
            $('.product-select').eq(0).show().siblings('.product-select').hide();
            $('.product-radar a').eq(0).addClass('action').siblings().removeClass('action');
        });
        $('.level-broad .aside-title').click(function () {
            categoryCodeValue="feng-kuo-xian";
            app.Onregion();
            $('#broad-text').html(imagHour);
            $('#interval-text6').html(minute);
            $('#interval-text30').html(minute);
            productValue='6-fen-zhong';
            windValue='contour';
            $('.product-select').eq(1).show().siblings('.product-select').hide();
            $('.product-broad a').eq(0).addClass('action').siblings().removeClass('action');
            $('.direction-select a').eq(0).addClass('action').siblings().removeClass('action');
        });
        $('.level-microwave .aside-title').click(function () {
            categoryCodeValue="wei-bo-fu-she";
            app.Onregion();
            productValue='3d-tu';
            windValue="";
            $('#interval-text').html(imagHour);
            Minute=null;
            $('.product-select').eq(2).show().siblings('.product-select').hide();
            $('.product-microwave a').eq(0).addClass('action').siblings().removeClass('action');
        });
        $('.level-raindrop .aside-title').click(function () {
            categoryCodeValue="yu-di-pu";
            app.Onregion();
            productValue='yu-di-pu';
            windValue="";
            $('#interval-text').html(imagHour);
            Minute=null;
            $('.product-select').eq(3).show().siblings('.product-select').hide();
        });
        $('.level-gps .aside-title').click(function () {
            categoryCodeValue="gps/met";
            app.Onregion();
            productValue='gps/met';
            windValue="";
            $('#interval-text').html(imagHour);
            Minute=null;
            $('.product-select').eq(4).show().siblings('.product-select').hide();
        });
    }

    this.GetUrlParam = function (name) {
        var url = new URL(window.location.href);
        return url.searchParams.get(name);
    }

    this.ChangeProduct = function () {
        var category = this.GetUrlParam('category');
        $("span[val='" + category + "']").trigger('click');
    }
};

var app = null;
$(document).ready(function() {
    app = new App();
    app.Startup();

});