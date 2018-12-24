var LeftPanel = function () {

    this.requestPath = '';
    this.laserRadarIndex = 0;
    this.laserRadarTime = 0;

    this.windProfileIndex = 0;
    this.windProfileTime = 0;

    this.gpsMetIndex = 0;
    this.gpsMetTime = 0;

    this.microwaveRadiationIndex = 0;
    this.microwaveRadiationTime = 0;

    this.raindropSpectrumIndex = 0;
    this.raindropSpectrumTime = 0;

    this.result = null;

    this.Startup = function () {

        this.winLoadPicture();
        this.autoChang();
    }

    this.winLoadPicture = function () {
        this.onLoadGetPicture();
    }

    this.onLoadGetPicture = function () {
        this.getPicture();
    }

    this.getPicture = function () {
        $.post(this.requestPath+"products/findAllByTypeAndArea",function (data) {
            LeftPanel.result = data;
            this.result = data;
            for(var i=0;i<data.length;i++){
                if(data[i].categoryCode === "ji-guang-lei-da"){
                    var textHtml = this.forEachProductRegion(data[i].productRegion);
                    $("#laserRadarArea").html(textHtml);
                    var textParm = this.forEachProductType(data[i].productRegion[0].productTypes);
                    $("#laserRadarParm").html(textParm);
                    $("#laserRadarImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart2" style="display: block;">' + '<img src="'+data[i].productRegion[0].productTypes[0].productData.url+'">' + '</div>' + '</div>')
                }else if(data[i].categoryCode === "wei-bo-fu-she"){
                    var textHtml = this.forEachProductRegion(data[i].productRegion);
                    $("#microwaveRadiationArea").html(textHtml);
                    var textParm = this.forEachProductType(data[i].productRegion[0].productTypes);
                    $("#microwaveRadiationParm").html(textParm);
                    $("#microwaveRadiationImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart3" style="display: block;">' + '<img src="'+data[i].productRegion[0].productTypes[0].productData.url+'">' + '</div>' + '</div>')
                }else if(data[i].categoryCode === "feng-kuo-xian"){
                    var textHtml = this.forEachProductRegion(data[i].productRegion);
                    $("#windProfileArea").html(textHtml);
                    var textParm = this.forEachProductType(data[i].productRegion[0].productTypes);
                    $("#windProfileParm").html(textParm);
                    $("#windProfileImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart0" style="display: block;">' + '<img src="'+data[i].productRegion[0].productTypes[0].productData.url+'">' + '</div>' + '</div>')
                }else if(data[i].categoryCode === "yu-di-pu"){
                    var textHtml = this.forEachProductRegion(data[i].productRegion);
                    $("#raindropSpectrumArea").html(textHtml);
                    var textParm = this.forEachProductType(data[i].productRegion[0].productTypes);
                    $("#raindropSpectrumParm").html(textParm);
                    $("#raindropSpectrumImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart4" style="display: block;">' + '<img src="'+data[i].productRegion[0].productTypes[0].productData.url+'">' + '</div>' + '</div>')
                }else if(data[i].categoryCode === "gps/met"){
                    var textHtml = this.forEachProductRegion(data[i].productRegion);
                    $("#gpsMetArea").html(textHtml);
                    var textParm = this.forEachProductType(data[i].productRegion[0].productTypes);
                    $("#gpsMetParm").html(textParm);
                    $("#gpsMetImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart1" style="display: block;">' + '<img src="'+data[i].productRegion[0].productTypes[0].productData.url+'">' + '</div>' + '</div>')
                }
            }
            this.windProfileWheel();
            this.Tab();
            this.TabChang();
            this.clickImg();
        }.bind(this))
    }

    this.windProfileWheel = function () {
        setInterval(function () {
            this.processingData(0);
            this.processingData(1);
            this.processingData(2);
            this.processingData(3);
            this.processingData(4);
        }.bind(this),5000)
    }

    this.processingData = function (type) {
        var time;
        var index;
        var data

        if(type == '0'){
            time = this.windProfileTime;
            index = this.windProfileIndex;
            data = this.result[type];
        }else if(type == '1'){
            index = this.gpsMetIndex;
            time = this.gpsMetTime;
            data = this.result[type];
        }else if(type == '2'){
            index = this.laserRadarIndex;
            time = this.laserRadarTime;
            data = this.result[type];
        }else if(type == '3'){
            index = this.microwaveRadiationIndex;
            time = this.microwaveRadiationTime;
            data = this.result[type];
        }else if(type == '4'){
            index = this.raindropSpectrumIndex;
            time = this.raindropSpectrumTime;
            data = this.result[type];
        }

        time ++;
        if(time>data.productRegion[0].productTypes.length-1){
            time = 0;
            index +=1;
            if(index>data.productRegion.length-1){
                index =0;
            }
        }

        if(type == '0'){
            this.windProfileTime = time;
            this.windProfileIndex = index;
        }else if(type == '1'){
            this.gpsMetIndex = index;
            this.gpsMetTime = time;
        }else if(type == '2'){
            this.laserRadarIndex = index;
            this.laserRadarTime = time;
        }else if(type == '3'){
            this.microwaveRadiationIndex = index;
            this.microwaveRadiationTime = time;
        }else if(type == '4'){
            this.raindropSpectrumIndex = index;
            this.raindropSpectrumTime = time;
        }

        this.playData(time,index,type);
    }

    this.playData = function (time,index,type) {
        var data = this.result;
        if(type=='0'){
            $("#windProfileParm").parents(".theme-title").find(".theme-area-table a").eq(time).addClass("action").siblings().removeClass("action");
            $("#windProfileArea").parents(".theme-area").find(".theme-area-table a").eq(index).addClass("action").siblings().removeClass("action");
            $("#windProfileImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart0" style="display: block;">' + '<img src="'+data[0].productRegion[index].productTypes[time].productData.url+'">' + '</div>' + '</div>')
        }else if(type=='1'){
            $("#gpsMetParm").parents(".theme-title").find(".theme-area-table a").eq(time).addClass("action").siblings().removeClass("action");
            $("#gpsMetArea").parents(".theme-area").find(".theme-area-table a").eq(index).addClass("action").siblings().removeClass("action");
            $("#gpsMetImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart1" style="display: block;">' + '<img src="'+data[1].productRegion[index].productTypes[time].productData.url+'">' + '</div>' + '</div>')
        }else if(type=='2'){
            $("#laserRadarParm").parents(".theme-title").find(".theme-area-table a").eq(time).addClass("action").siblings().removeClass("action");
            $("#laserRadarArea").parents(".theme-area").find(".theme-area-table a").eq(index).addClass("action").siblings().removeClass("action");
            $("#laserRadarImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart2" style="display: block;">' + '<img src="'+data[2].productRegion[index].productTypes[time].productData.url+'">' + '</div>' + '</div>')
        }else if(type=='3'){
            $("#microwaveRadiationParm").parents(".theme-title").find(".theme-area-table a").eq(time).addClass("action").siblings().removeClass("action");
            $("#microwaveRadiationArea").parents(".theme-area").find(".theme-area-table a").eq(index).addClass("action").siblings().removeClass("action");
            $("#microwaveRadiationImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart3" style="display: block;">' + '<img src="'+data[3].productRegion[index].productTypes[time].productData.url+'">' + '</div>' + '</div>')
        }else if(type=='4'){
            $("#raindropSpectrumParm").parents(".theme-title").find(".theme-area-table a").eq(time).addClass("action").siblings().removeClass("action");
            $("#raindropSpectrumArea").parents(".theme-area").find(".theme-area-table a").eq(index).addClass("action").siblings().removeClass("action");
            $("#raindropSpectrumImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart4" style="display: block;">' + '<img src="'+data[4].productRegion[index].productTypes[time].productData.url+'">' + '</div>' + '</div>')
        }
        this.clickImg();
    }

    this.forEachProductRegion = function (data) {
        var textHtml = '';
        for(var i=0;i<data.length;i++){
            if(data.length==1){
                if(i == 0){
                    textHtml += '<a href="javascript:;" class="action theme-border">'+data[i].name+'</a>'
                }else{
                    textHtml += '<a href="javascript:;" class="theme-border">'+data[i].name+'</a>'
                }
            }else {
                if(i == 0){
                    textHtml += '<a href="javascript:;" class="action">'+data[i].name+'</a>'
                }else{
                    textHtml += '<a href="javascript:;">'+data[i].name+'</a>'
                }
            }
        }
        return textHtml;
    }

    this.forEachProductType = function (data) {
        var textHtml = '';
        for(var i=0;i<data.length;i++){
            if(data.length==1){
                if(i == 0){
                    textHtml += '<a href="javascript:;" class="action theme-border">'+data[i].name+'</a>'
                }else{
                    textHtml += '<a href="javascript:;" class="theme-border">'+data[i].name+'</a>'
                }
            }else {
                if(i == 0){
                    textHtml += '<a href="javascript:;" class="action">'+data[i].name+'</a>'
                }else{
                    textHtml += '<a href="javascript:;">'+data[i].name+'</a>'
                }
            }
        }
        return textHtml;
    }

    this.Tab = function () {
        $(".theme").each(function () {
            var tab = $(".theme-area a").on('click', function (e) {
                $(e.target).addClass("action").siblings().removeClass("action");
                $(e.target).parents(".theme-area").next().find(".theme-area-table a").eq(0).addClass("action").siblings().removeClass("action");
                var parentIndex = $(e.target).parents(".theme").index();
                var index = $(e.target).index();
                if(parentIndex == '0'){
                    this.laserRadarIndex = index;
                    this.laserRadarTime = 0;
                }else if(parentIndex == '1'){
                    this.windProfileIndex = index;
                    this.windProfileTime = 0;
                }else if(parentIndex == '2'){
                    this.gpsMetIndex = index;
                    this.gpsMetTime = 0;
                }else if(parentIndex == '3'){
                    this.microwaveRadiationIndex = index;
                    this.microwaveRadiationTime = 0;
                }else if(parentIndex == '4'){
                    this.raindropSpectrumIndex = index;
                    this.raindropSpectrumTime = 0;
                }
                this.change(parentIndex);
                this.clickImg();
            }.bind(this))
        }.bind(this))
    };

    this.autoChang = function(){
        setInterval(function () {
            $.post(this.requestPath+"products/findAllByTypeAndArea",function (data) {
                this.result = data;
            })
        }.bind(this),1000*60*5)
    }

    this.OpenProductPage = function (category) {
        var url = 'aerological-sound.html?category={0}'.format(category);
        window.open(url, '_blank');
    }

    this.clickImg = function () {
        var parentValue = '';
        var parmOne = '';
        var parmTwo = '';
        $(".theme-chart0 img").on('click', function (e) {
            //alert($(e.target).attr("src"))
            parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            this.OpenProductPage(parentValue);
        }.bind(this))
        $(".theme-chart1 img").on('click', function (e) {
            //alert($(e.target).attr("src"))
            parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            this.OpenProductPage(parentValue);
        }.bind(this))
        $(".theme-chart2 img").on('click', function (e) {
            //alert($(e.target).attr("src"))
            parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            //window.location.href = "products/findModelAndView?type="+parmOne+"&providers="+parmTwo+"&category="+parentValue;
            this.OpenProductPage(parentValue);
        }.bind(this))
        $(".theme-chart3 img").on('click', function (e) {
            //alert($(e.target).attr("src"))
            parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            //window.location.href = "products/findModelAndView?type="+parmOne+"&providers="+parmTwo+"&category="+parentValue;
            this.OpenProductPage(parentValue);
        }.bind(this))
        $(".theme-chart4 img").on('click', function (e) {
            //alert($(e.target).attr("src"))
            parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            //window.location.href = "products/findModelAndView?type="+parmOne+"&providers="+parmTwo+"&category="+parentValue;
            this.OpenProductPage(parentValue);
        }.bind(this))

    }

    this.TabChang = function () {

        $(".theme").each(function () {
            var tab = $(".theme-title a").on('click', function (e) {
                $(e.target).addClass("action").siblings().removeClass("action");
                var time = $(e.target).index();
                var parentIndex = $(e.target).parents(".theme").index();
                if(parentIndex == '0'){
                    this.laserRadarTime = time;
                }else if(parentIndex == '1'){
                    this.windProfileTime = time;
                }else if(parentIndex == '2'){
                    this.gpsMetTime = time;
                }else if(parentIndex == '3'){
                    this.microwaveRadiationTime = time;
                }else if(parentIndex == '4'){
                    this.raindropSpectrumTime = time;
                }
                this.change(parentIndex);
                this.clickImg();
            }.bind(this))
        }.bind(this))
    };
    
    this.change = function (times) {
        var data = this.result;
        if(times == '0'){
            $("#laserRadarImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart2" style="display: block;">' + '<img src="'+data[2].productRegion[this.laserRadarIndex].productTypes[this.laserRadarTime].productData.url+'">' + '</div>' + '</div>')
        }else if(times == '1'){
            $("#windProfileImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart  theme-chart0" style="display: block;">' + '<img src="'+data[0].productRegion[this.windProfileIndex].productTypes[this.windProfileTime].productData.url+'">' + '</div>' + '</div>')
        }else if(times == '2'){
            $("#gpsMetImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart1" style="display: block;">' + '<img src="'+data[1].productRegion[this.gpsMetIndex].productTypes[this.gpsMetTime].productData.url+'">' + '</div>' + '</div>')
        }else if(times == '3'){
            $("#microwaveRadiationImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart3" style="display: block;">' + '<img src="'+data[3].productRegion[this.microwaveRadiationIndex].productTypes[this.microwaveRadiationTime].productData.url+'">' + '</div>' + '</div>')
        }else if(times == '4'){
            $("#raindropSpectrumImg").html('<div class="theme-area-chart" style="display: block;">' + '<div class="theme-chart theme-chart4" style="display: block;">' + '<img src="'+data[4].productRegion[this.raindropSpectrumIndex].productTypes[this.raindropSpectrumTime].productData.url+'">' + '</div>' + '</div>')
        }

    }
}
