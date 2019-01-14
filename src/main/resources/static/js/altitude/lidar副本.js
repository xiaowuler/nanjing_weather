//激光雷达
   var app = null;
   $(document).ready(function() {
        app = new App();
        app.Startup();
   });

    var App = function (){
        this.time;
        this.index = 0;
        this.i = 1;
        this.iLength = $('#interval-select li').length;
        this.interval = $('#interval-select li');
        this.hour;
        this.date;
        this.timing = '3000';
        this.categoryCodeHtml = '';
        this.categoryCodeValue='ji-guang-lei-da';
        this.imagetime='';
        this.county='';
        this.imgHtml2='';
        this.productValue='xiao-guang';
        this.windValue='';

        this.Startup = function () {
            $('#export').on('click', this.OnexportButtonClick.bind(this));//图片导出
            $('#aerological').on('click', this.OnSecondIntervalSelect.bind(this));//定时器时间间隔
            $('#pause').on('click', this.OnPlayButtonClick.bind(this));//定时器的暂停
            $('#play').on('click', this.OnPauseButtonClick.bind(this));//定时器的开始
            $('#interval-text').on('click', this.OnHourIntervalSelect.bind(this));//小时框的点击事件
            $('#interval-prev').on('click', this.OnPrevIntervalSelect.bind(this));//小时框左
            $('#interval-next').on('click', this.OnNextIntervalSelect.bind(this));//小时框右
            this.current();
            this.Onregion();//获取区域
            this.OnProductSelectClick();//产品列表的点击事件
            $('#query-button').click(function(){ //查询按钮
                this.serachDataList();
            }.bind(this));
        }

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
                $('#select').find('li').click(function (e){
                    this.timing= $(e.target).attr('val');
                    $(e.target).addClass('action').siblings().removeClass('action');
                    $(e.target).parents('#select').siblings().text($(e.target).text()).attr("val", $(e.target).text());
                    $(e.target).parents('#select').siblings().removeClass('select-arrow');
                    $(e.target).parents('#select').fadeOut();
                }.bind(this));
            }.bind(this));
        };

        this.OnPlayButtonClick = function () {
            $('#play').show();
            $('#pause').hide();
           this.time = setInterval(function(){
                var show = new ProductShow();
                show.Init('#detail-slide');
                show.ActiveNext();
            }.bind(this), this.timing);
        }

        this.OnPauseButtonClick = function () {
            clearInterval(this.time);
            $('#play').hide();
            $('#pause').show();
        };

        this.OnHourIntervalSelect = function () {
            $('#interval-text').toggleClass('select-arrow');
            $('#interval-select').fadeToggle(300, function() {
                $('#interval-select').find('li').click(function (e){
                   this.index = this.interval.index(e.target);
                    var text = this.interval.eq(this.index).html();
                    $('#interval-text').html(text);
                    this.interval.eq(this.index).addClass('action').siblings().removeClass('action');
                    $(e.target).parents('#interval-select').siblings('span').removeClass('select-arrow');
                    $(e.target).parents('#interval-select').fadeOut();
                }.bind(this))
            }.bind(this));
        };

        this.Interval = function (i) {
            this.interval = $('#interval-select li');
            var text = this.interval.eq(i).html();
            $('#interval-text').html(text);
            this.interval.eq(this.i).addClass('action').siblings().removeClass('action');
        };

        this.OnPrevIntervalSelect = function () {
            //左按钮
            if (this.index >= 1) {
                --this.index;
                this.Interval(this.index);
            }
            this.i--;
            this.serachDataList();
        };

        this.OnNextIntervalSelect = function () {
            //右按钮
            if (this.index >= 0 && this.index <this.iLength - 1) {
                ++this.index;
                this.Interval(this.index);
            } else {
                this.Interval(0);
               this.index=0;
               this.i=1;
            }
            this.serachDataList();
        };

        this.current=function () {
            var startH= $('#interval-text').text();
            var index1=startH.split("时")
           this.index=index1[0];
        }

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
        $('#interval-text').html(this.imagHour);

        this.serachDataList = function(){
            this.date = $('#calendar').val();
           this.hour= $('#interval-text').text();
            this.OnSmallpicture();
        }

        this.OnProductSelectClick = function () {
            $('.product-select a').click(function(e) {
                $('#broad-text').html(this.imagHour);
               this.productValue=$(e.target).attr('val');
                app.serachDataList();
                $(e.target).addClass('action').siblings().removeClass('action');
            }.bind(this))
        };

        this.Onregion = function () {
            $.post("ProductCategoryRegionRels/findByCategoryCode?categoryCode=" + this.categoryCodeValue, function (data){
                $(data).each(function (index,data) {
                    if(index==0){
                       this.categoryCodeHtml+='<a href="javascript:;" class="action" val={0}>{1}</a>'.format(data.categoryCode,data.name);
                    }else {
                      this.categoryCodeHtml+='<a href="javascript:;" val={0}>{1}</a>'.format(data.categoryCode,data.name);
                    }
                }.bind(this));
                $('#Onregions').html(this.categoryCodeHtml);
                app.OnAreaSelectClick();
              this.county= data[0].categoryCode;
                app.serachDataList();
            }.bind(this));
            this.categoryCodeHtml = '';
        }

        // 获取当前页面的年月日时
        this.serachDataList = function(){
            // 获取时间日
           this.date = $('#calendar').val();
            // 获取时
            this.hour= $('#interval-text').text();
           this.imagetime =this.date +'/'+this.hour;
            this.OnSmallpicture();
        }

        this.ActiveProductCallback = function (product) {
            var image = product.find('img:first');
            var url=image.attr('src');
            this.imgHtml2 = '<li style="display: list-item;"><div><img class="img1" src="{0}"></div></li>'.format(url)
            $('.imgeUrl2').html(this.imgHtml2);
        }

        this.OnAreaSelectClick = function () {
            $('.area-select a').click(function(e) {
              this.county=$(e.target).attr('val');
              app.serachDataList();
              $(e.target).addClass('action').siblings().removeClass('action');
            }.bind(this))
        };

        this.OnSmallpicture = function () {
            $.post("products/findByTime?type="+this.productValue+'&imagetime='+this.imagetime+'&county='+this.county+'&windValue='+this.windValue+'&categoryCodeValue='+this.categoryCodeValue, function (data){
                var show = new ProductShow(this.ActiveProductCallback.bind(this));
                show.Init('#detail-slide');
                show.Load(data);
            }.bind(this));
        }

    }