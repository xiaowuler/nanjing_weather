var App = function () {
    this.detail = $('.detail');
    this.tab = $('.detail-tab');
    this.aside = $('.aside-left');
    this.arrow = $('.slide-arrow');
    this.overview = $('.detail-overview');
    this.timer = null;

    this.Startup = function () {
        this.InitDateHour();
        this.InitRegions();
        this.LoadProducts();
        this.BindExportButtonClick();
        this.BindTimerEvents();
        this.BindProductSelectClick();//产品列表
        this.OnDirectionSelectClick();//产品类别
        this.BindAreaSelectClick();//地区
        this.BindHourButtonsClick();
        this.BindMinuteButtonsClick();
        this.BindQueryButtonClick();
        this.Relayout();
        window.onresize = this.Relayout.bind(this);
    };

    this.Relayout = function () {
        var height = $(window).height();
        var topHeight = $('.top').height();
        this.aside.height(height - topHeight);
        $('.aside-right').height(height - topHeight - 42);
        this.overview.height(this.aside.height() - 208);
    };

    this.InitDateHour = function () {
        var now = new Date().addHours(1);
        var hour = now.getHours().toString().substring(0,2);
        var minute = now.getMinutes().toString().substring(0,2);
        minute = parseInt(minute) - parseInt(minute % 6);
        $('#interval-hour').combobox('setValue', hour + '时');
        $('.interval-min').combobox('setValue', minute + '分钟');
    }

    this.InitRegions = function () {
        $.ajax({
            url: "ProductCategoryRegionRels/findByCategoryCode?categoryCode=feng-kuo-xian",
            async: false,
            success: function(regions){
                $(regions).each(function (index, region) {
                    var template='<li class="{0}" val="{1}">{2}</li>';
                    var element = template.format(index === 0 ? 'action' : '', region.categoryCode, region.name);
                    $('#Onregions').append(element);
                }.bind(this));
            }
        });
    }

    this.loadClick = function (flag) {
        if (flag)
            $("#map-loading").attr("style", "display: block")
        else
            $("#map-loading").attr("style", "display: none")
    }

    this.LoadProducts = function () {
        var params = this.GetSearchParams();
        var template = 'products/findByTime?type={0}&imagetime={1}&county={2}&windValue={3}&categoryCodeValue=feng-kuo-xian';

        $.ajax({
            url: template.format(params.ProductType, params.SearchTime, params.RegionCode,params.WindValue),
            beforeSend: function () {
                // show loading...
                this.loadClick(true);
            }.bind(this),
            error: function () {
                // hide loading
                this.loadClick(false);
            }.bind(this),
            success: function(products){
                this.loadClick(false);
                var show = new ProductShow(this.ActiveProductCallback.bind(this));
                show.Init('#detail-slide');
                show.Load(products);

            }.bind(this)
        });
    }

    this.GetSearchParams = function () {
        return {
            ProductType: $('.product-select a[class=action]').attr('val'),
            SearchTime: '{0}/{1}/{2}'.format($('#calendar').val(), $('#interval-hour').combobox('getText'),$('.interval-min').combobox('getText')),
            RegionCode: $('.area-list li[class=action]').attr('val'),
            WindValue:$('.direction-select a[class=action]').attr('val'),
        }
    }

    this.BindExportButtonClick = function () {
        $('#export').on('click', function () {
            // Get download url
            var imageUrl = $(".imgeUrl").find(".action").find("img").attr("src");
            var downloadUrl = 'products/downloadFile?url={0}'.format(imageUrl);

            // Trigger click event
            var link = $('#download-link');
            link.attr('href', downloadUrl);
            link[0].click();
        });
    }

    this.BindTimerEvents = function () {
        $('.speed-select a').click(function (e) {
            $(e.target).addClass("action").siblings().removeClass("action");
        }.bind(this));

        $('#pause').on('click', this.OnPlayButtonClick.bind(this));
        $('#play').on('click', this.OnPauseButtonClick.bind(this));
    }

    this.GetTimerInterval = function () {
        return $('.speed-select a[class=action]').attr('val');
    }

    this.OnPlayButtonClick = function () {
        this.UpdatePlayButtons(true);

        this.timer = setInterval(function(){
            var show = new ProductShow();
            show.Init('#detail-slide');
            show.ActiveNext();
        }.bind(this), this.GetTimerInterval());
    }

    this.OnPauseButtonClick = function () {
        this.UpdatePlayButtons(false);
        clearInterval(this.timer);
    };

    this.UpdatePlayButtons = function (isPlaying) {
        $('#play').toggle();
        $('#pause').toggle();
        $('#play-caption').text(isPlaying ? "暂停" : "播放");
    }

    this.BindProductSelectClick = function () {
        $('.product-select a').click(function(e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            var ProductValue= $(e.target).attr("val");
            if(ProductValue=='6-fen-zhong'){
             $('.half-minute-select').hide();
             $('.minute-select').show();
            }else if(ProductValue=='30-fen-zhong'){
                $('.minute-select').hide();
                $('.half-minute-select').show();
            }else{
                $('.half-minute-select').hide();
                $('.minute-select').hide();
            }
            this.InitDateHour();
            this.LoadProducts();
        }.bind(this))
    };

    this.OnDirectionSelectClick = function () {
        $('.direction-select a').click(function(e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            this.LoadProducts();
        }.bind(this));
    };

    this.BindAreaSelectClick = function () {
        $('#Onregions li').click(function(e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            this.LoadProducts();
        }.bind(this))
    };

    this.BindHourButtonsClick = function () {
        $('#interval-prev').on('click', this.AddQueryHour.bind(this, -1));
        $('#interval-next').on('click', this.AddQueryHour.bind(this, 1));
    }

    this.AddQueryHour = function (delta) {
        var hour = parseInt($('#interval-hour').combobox('getValue'));
        hour += delta;
        if (hour < 0 || hour > 23)
            return;

        $('#interval-hour').combobox('setValue', hour);
        this.LoadProducts();
    }

    this.BindMinuteButtonsClick = function () {
        $('#interval6-prev').on('click', this.AddQueryMinute.bind(this, -6));
        $('#interval6-next').on('click', this.AddQueryMinute.bind(this, 6));
    }
    
    this.AddQueryMinute=function (minuteNumber) {
        var minute= parseInt($('#interval-min').combobox('getValue'));
        minute += minuteNumber;
        if(minute < 6 || minute > 56)
            return;

        $('#interval-min').combobox('setValue',minute+"分钟");
        this.LoadProducts();
    }

    this.BindQueryButtonClick = function () {
        $('#query-button').on('click', this.LoadProducts.bind(this));
    }

    this.ActiveProductCallback = function (product) {
        var image = product.find('img:first');
        var url = image.attr('src');
        this.imgHtml2 = '<li style="display: list-item;"><div><div><img class="img1" src="{0}"></div></div></li>'.format(url);
        $('.imgeUrl2').html(this.imgHtml2);
    }
};

$(document).ready(function() {
    var app = new App();
    app.Startup();
});