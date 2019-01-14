var App = function () {
    this.time;
    this.timing;

    this.Startup = function () {
        this.InitDateHour();
        this.InitRegions();
        this.LoadProducts();
        this.BindExportButtonClick();
        this.BindTimerEvents();
        this.BindProductSelectClick();
        this.BindHourButtonsClick();
        this.BindQueryButtonClick();
    };

    this.InitDateHour = function () {
        var now = new Date().addHours(1);
        var hour = now.getHours().toString().substring(0,2);
        $('#interval-text').combobox('setValue',hour+'时');
        this.timing=$('.speed-select a[class=action]').attr('val');

    }

    this.InitRegions = function () {
        $.ajax({
            url: "ProductCategoryRegionRels/findByCategoryCode?categoryCode=ji-guang-lei-da",
            async: false,
            success: function(regions){
                $(regions).each(function (index, region) {
                    var template='<ul><li class="{0}" val="{1}">{2}</li></ul>';
                    var element = template.format(index === 0 ? 'action' : '', region.categoryCode, region.name);
                    $('#Onregions').append(element);
                }.bind(this));
            }
        });
    }

    this.LoadProducts = function () {
        var params = this.GetSearchParams();
        var template = 'products/findByTime?type={0}&imagetime={1}&county={2}&categoryCodeValue=ji-guang-lei-da';

        $.ajax({
            url: template.format(params.ProductType, params.SearchTime, params.RegionCode),
            success: function(products){
                var show = new ProductShow(this.ActiveProductCallback.bind(this));
                show.Init('#detail-slide');
                show.Load(products);
            }.bind(this)
        });
    }

    this.GetSearchParams = function () {
        return {
            ProductType: $('.product-border a[class=action]').attr('val'),
            SearchTime: '{0}/{1}'.format($('#calendar').val(), $('#interval-text').combobox('getValue')),
            RegionCode: $('.area-list li[class=action]').attr('val')
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
        this.SpeedSelect();
        $('#pause').on('click', this.OnPlayButtonClick.bind(this));
        $('#play').on('click', this.OnPauseButtonClick.bind(this));
    }

    this.SpeedSelect = function () {
        $('.speed-select a').click(function (e) {
            this.timing= $(e.target).attr('val');
            $(e.target).addClass("action").siblings().removeClass("action");
        }.bind(this));
    };

    this.OnPlayButtonClick = function () {
        $('#play').show();
        $('#pause').hide();
        $('.operate-control-text').html('暂停')
        this.time=setInterval(function(){
            var show = new ProductShow();
            show.Init('#detail-slide');
            show.ActiveNext();
        }.bind(this),this.timing);
    }

    this.OnPauseButtonClick = function () {
        $('.operate-control-text').html('播放')
        clearInterval(this.time);
        $('#play').hide();
        $('#pause').show();
    };

    this.BindProductSelectClick = function () {
        $('.product-border a').click(function(e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            this.LoadProducts();
        }.bind(this))
    };

    this.BindHourButtonsClick = function () {
        $('#interval-prev').on('click', this.OnPrevIntervalSelect.bind(this));
        $('#interval-next').on('click', this.OnNextIntervalSelect.bind(this));
    }

    this.Interval = function (i) {
          if(i<10){
              var hour='0{0}时'.format(i);
          }else{
              var hour='{0}时'.format(i);
          }
        $('#interval-text').combobox('setValue',hour);
    };

    this.OnPrevIntervalSelect = function () {
        this.index= $('#interval-text').combobox('getValue').substring(0,2);
        if (this.index >= 1) {
            --this.index;
           this.Interval(this.index);
        }
          this.i--;
        this.LoadProducts();
    };

    this.OnNextIntervalSelect = function () {
        this.index= $('#interval-text').combobox('getValue').substring(0,2);
        var iLength = document.getElementById("interval-text").length;
        if (this.index >= 0 && this.index <iLength-1) {
            ++this.index;
          this.Interval(this.index);
        } else {
           this.Interval(0);
            this.index=0;
             i=1;
        }
        this.LoadProducts();
    };

    this.BindQueryButtonClick = function () {
        $('#query-button').click(function(){
            this.LoadProducts();
        }.bind(this));
    }

    this.ActiveProductCallback = function (product) {
        var image = product.find('img:first');
        var url = image.attr('src');
        this.imgHtml2 = '<li style="display: list-item;"><div><img class="img1" src="{0}"></div></li>'.format(url);
        $('.imgeUrl2').html(this.imgHtml2);
    }
};

var app = null;
$(document).ready(function() {
    app = new App();
    app.Startup();
});