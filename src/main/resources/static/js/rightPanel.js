var RightPanel = function () {

    this.result = new ArrayData();
    this.rainFallResult = null;
    this.windResult = null;
    this.temperatureResult = null;
    this.groundTemperatureResult = null;
    this.pressureResult = null;
    this.humidityResult = null;
    this.requestPath = '';
    this.flag = true;
    this.timer = null;
    this.count = 0;
    this.MapInfo = new MapInfo(this);
    this.colorControl = new ColorContorl();

    this.Startup = function () {

        this.handlerRefreshButtonCheck(true);
        this.playCode('温度','≥0','temperatures');

        this.MapInfo.CreateEasyMap();
        this.MapInfo.Startup();

        this.layerInfoRainFallButtonClick();
        this.layerInfoTemperButtonClick();
        this.layerInfoGoundTermpButtonClick();
        this.layerInfoPressButtonClick();
        this.layerInfoWindButtonClick();
        this.layerInfoHumButtonClick();

        $('#auto-refresh').on('click', this.handlerRefreshButtonCheck.bind(this));
        this.isolineAndSplashMapClick();
        this.optionCheck();

        this.basePlotClick();
        this.encryPlotClick();
        this.plotNameClick();
        this.plotValueClick();

        this.SetTimeText();
    }

    this.handlerRefreshButtonCheck = function () {
        later.date.localTime();
        var cron = later.parse.cron('0 0/5 * * * ?', true);

        if (this.IsAutoRefresh()){
            this.SetTimeText();
            this.timer = later.setInterval(this.OnTimerTick.bind(this), cron);
        }else{
            this.SetTimeText();
            this.timer.clear();
        }
    }

    this.OnTimerTick = function () {
        this.SetTimeText();
        this.ReloadData();
    }
    
    this.SetTimeText = function () {
        if (!this.IsAutoRefresh()) {
            $('#next-time').html('-- : -- : --');
            return;
        }

        $('#last-time').html(this.getTimeByLater("last"));
        setTimeout(function () {
            $('#next-time').html(this.getTimeByLater("next"))
        }.bind(this), 1000);
    }

    this.getTimeByLater = function (flag) {
        var date = null;
        var second = null;

        if (flag == 'next'){
            var cron = later.parse.cron('0 0/5 * * * ?', true);
            var date = later.schedule(cron).next(1);
            second = "00";
        }else {
            date = new Date();
            second = date.getSeconds();
            second = (second < 10) ? '0' + second : second;
        }

        var hour = date.getHours();
        hour = (hour < 10) ? '0' + hour : hour;
        var minute = date.getMinutes();
        minute = (minute < 10) ? '0' + minute : minute;
        return hour + ':' + minute + ':' + second;
    }

    this.ReloadData = function () {
        if($('.interval-select a.action').attr('val') != null)
            this.playCode($('.interval-select a.action').attr('val'),$('.millimeter-select a.action').attr('val'),'rainfalls');

        if($('.variable-select a.action').attr('val') != null)
            this.playCode($('.variable-select a.action').attr('val'),$('.temperature-select a.action').attr('val'),'temperatures');

        if($('.wind-select a.action').attr('val') != null)
            this.playCode($('.wind-select a.action').attr('val'),null,'winds');

        if($('.geothermal-select a.action').attr('val') != null)
            this.playCode($('.geothermal-select a.action').attr('val'),null,'groundTemperature');

        if($('.air-pressure-select a.action').attr('val') != null)
            this.playCode($('.air-pressure-select a.action').attr('val'),null,'pressures');

        if($('.humidity-select a.action').attr('val') != null)
            this.playCode($('.humidity-select a.action').attr('val'),null,'humidities');
    }

    this.IsAutoRefresh = function () {
        return $('#auto-refresh').hasClass('refresh-on');
    }

    this.layerInfoRainFallButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $(".interval-select a").click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                parmTwo = $(".millimeter-select a.action").attr('val');
                if(parmOne != null && parmTwo != null)
                    this.playCode(parmOne,parmTwo,'rainfalls');
            }else{
                this.rainFallResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));

        $('.millimeter-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmTwo = $(e.target).attr('val');
                parmOne = $(".interval-select a.action").attr('val');
                if(parmOne != null && parmTwo != null)
                    this.playCode(parmOne,parmTwo,'rainfalls');
            }else {
                this.rainFallResult = null;
                this.insertData();
                this.getCheckLayerInfo();

            }
        }.bind(this));
    }

    this.layerInfoTemperButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $('.variable-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                parmTwo = $('.temperature-select a.action').attr('val');
                if(parmOne != null && parmTwo != null)
                    this.playCode(parmOne,parmTwo,'temperatures');
            }else{
                this.temperatureResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));

        $('.temperature-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmTwo = $(e.target).attr('val');
                parmOne = $('.variable-select a.action').attr('val');
                if(parmOne != null && parmTwo != null)
                    this.playCode(parmOne,parmTwo,'temperatures');
            }else{
                this.temperatureResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));
    }

    this.layerInfoWindButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $('.wind-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                if(parmOne != null)
                    this.playCode(parmOne,parmTwo,'winds');
            }else{
                this.windResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));
    }

    this.layerInfoGoundTermpButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $('.geothermal-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                if(parmOne != null)
                    this.playCode(parmOne,parmTwo,'groundTemperature');
            }else{
                this.groundTemperatureResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));
    }

    this.layerInfoPressButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $('.air-pressure-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                if(parmOne != null)
                    this.playCode(parmOne,parmTwo,'pressures');
            }else{
                this.pressureResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));
    }

    this.layerInfoHumButtonClick = function () {
        var parmOne = null;
        var parmTwo = null;
        $('.humidity-select a').click(function (e) {
            if($(e.target).hasClass('action')){
                parmOne = $(e.target).attr('val');
                if(parmOne != null)
                    this.playCode(parmOne,parmTwo,'humidities');
            }else{
                this.humidityResult = null;
                this.insertData();
                this.getCheckLayerInfo();
            }
        }.bind(this));
    }

    this.loadClick = function (flag) {
        if (flag) {
            $("#map-loading").attr("style", "display: block")
        } else {
            $("#map-loading").attr("style", "display: none")
        }
    }

    this.playCode = function (parmOne,parmTwo,requestValue) {
        if (this.flag) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: this.requestPath + requestValue + "/findAllBySomeTerm",
                data: {
                    parmOne: parmOne,
                    parmTwo: parmTwo
                },
                beforeSend: function () {
                    // show loading...
                    this.loadClick(true);
                }.bind(this),
                error: function () {
                    // hide loading
                    this.loadClick(false);
                    this.flag = true;
                }.bind(this),
                success: function (data) {
                    // hide loading...
                    this.loadClick(false);
                    this.flag = true;

                    if(requestValue == 'temperatures'){
                        this.temperatureResult = data;
                    }else if(requestValue == 'rainfalls'){
                        this.rainFallResult = data;
                    }else if(requestValue == 'winds'){
                        this.windResult = data;
                    }else if(requestValue == 'groundTemperature'){
                        this.groundTemperatureResult = data;
                    }else if(requestValue == 'pressures'){
                        this.pressureResult = data;
                    }else if(requestValue == 'humidities'){
                        this.humidityResult = data;
                    }

                    this.insertData();
                    this.getCheckLayerInfo();
                    this.getCheckLayerOfChart(requestValue,data);
                }.bind(this)
            });
        } else {

        }
    }

    this.insertData = function () {

        this.checkNull();
        this.clearPointValue();

        var array = [];
        if(this.rainFallResult != null)
            array.push(new Array('rainfalls',this.rainFallResult.valuePoints));
        if (this.temperatureResult != null)
            array.push(new Array('temperatures',this.temperatureResult.valuePoints));
        if (this.windResult != null)
            array.push(new Array('winds',this.windResult.valuePoints));
        if (this.groundTemperatureResult != null)
            array.push(new Array('groundTemperature',this.groundTemperatureResult.valuePoints));
        if (this.pressureResult != null)
            array.push(new Array('pressures',this.pressureResult.valuePoints));
        if (this.humidityResult != null)
            array.push(new Array('humidities',this.humidityResult.valuePoints));
        this.result.addData(array)
    }

    this.clearPointValue = function () {
        if (this.MapInfo.encryPlotValueValue != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
        if (this.MapInfo.encryptionPointNameValue != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);

        if (this.MapInfo.basePlotValueValue != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
        if (this.MapInfo.basePoltDirNameValue != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
    }

    this.basePlotClick = function () {
        $('#basic').on('click',function () {
            this.basePoltSense();
        }.bind(this))
    }

    this.encryPlotClick = function () {
        $('#encrypt').on('click',function () {
            this.encryPlotSense();
        }.bind(this))
    }

    this.plotNameClick = function () {
        $('#station').on('click',function () {
            this.plotNameSense();
        }.bind(this))
    }

    this.plotValueClick = function () {
        $('#value').on('click',function () {
            this.plotValueSense();
        }.bind(this))
    }

    this.plotValueSense = function () {
        if($('#value').prop('checked')){
            if($('#basic').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePlotValue(this.result.regions);
            }

            if($('#encrypt').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryPlotValue(this.result.regions);
            }
        }else {
            if (this.MapInfo.basePlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);

            if (this.MapInfo.encryPlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);

        }
    }

    this.plotNameSense = function () {
        if($('#station').prop('checked')){
            if($('#basic').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePoltDirName(this.result.regions);
            }

            if($('#encrypt').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryptionPointName(this.result.regions);
            }
        }else {
            if (this.MapInfo.basePoltDirNameValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
            if (this.MapInfo.encryptionPointNameValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
        }
    }

    this.basePoltSense = function () {
        if($('#basic').prop('checked')){
            if($('#station').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePoltDirName(this.result.regions);
            }

            if($('#value').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePlotValue(this.result.regions);
            }
        }else{
            if (this.MapInfo.basePlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
            if (this.MapInfo.basePoltDirNameValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
        }
    }

    this.encryPlotSense = function () {
        if($('#encrypt').prop('checked')){
            if($('#station').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryptionPointName(this.result.regions);
            }

            if($('#value').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryPlotValue(this.result.regions);
            }
        }else {
            if (this.MapInfo.encryPlotValueValue != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
            }
            if (this.MapInfo.encryptionPointNameValue != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
            }
        }
    }

    this.getCheckLayerInfo = function () {
        this.plotNameSense();
        this.plotValueSense();
        this.encryPlotSense();
        this.basePoltSense();
        this.getCheckedByLayerText();
    }

    this.getCheckedByLayerText = function () {
        if($('.layer-text a.action').length === 0)
            this.checkNull();
    }

    this.getCheckLayerOfChart = function (checkValue,result) {
        /*if(checkValue == 'temperatures')
            this.temperatureResult = result;*/
        if($('.layer-text a.action').attr('val') === checkValue){
            if(result != null){
                if($('#contour').prop('checked')){
                    this.MapInfo.CreateContourLayer(result.contourPolylines);
                }else {
                    if (this.MapInfo.ContourLayer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
                    }
                }

                if ($("#color-figure").prop('checked')) {
                    this.MapInfo.CreateSpotLayer(result.spotPolygons, result.legendLevels)
                    //this.MapInfo.PlotColor(result.legendLevels);
                    $(".color-control").attr("style", "display: block")
                    this.colorControl.setColor(result.legendLevels[0].type,this.returnColor(result.legendLevels));
                    this.colorControl.Startup();
                }else {
                    if (this.MapInfo.layer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                    }
                    $(".color-control").attr("style", "display: none")
                }
            }
        }

    }

    this.returnColor = function (colors) {
        var array = []
        var arrayColor = [];
        var arrayValue = [];
        $(colors).each(function (index,color) {
            if(index == 0){
                //arrayColor.push(new Array(color.Color,color.Color));
                arrayValue.push(color.BeginValue);
                arrayValue.push(color.EndValue);
            }else{
                //arrayColor.push(new Array(colors[index-1].Color,color.Color));
                arrayValue.push(color.EndValue);
            }
        }.bind(this));
        for(var i = colors.length -1 ; i >= 0; i--){
            if(i == 0){
                arrayColor.push(new Array(colors[i].Color,colors[i].Color));
            }else{
                arrayColor.push(new Array(colors[i-1].Color,colors[i].Color));
            }
        }
        array.push(arrayColor);
        array.push(arrayValue);
        return array;
    }
    
    this.optionCheck = function () {
        $('.layer-text').off('click').on('click',function (e) {
            if (this.MapInfo.ContourLayer != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);

            if (this.MapInfo.layer != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.layer);

            $(".color-control").attr("style", "display: none")

            if($(e.target).hasClass('action'))
                this.defineArray($(e.target).attr('val'));
        }.bind(this))
    }

    this.defineArray = function (value) {
        var arr = ['rainfalls','temperatures','winds','groundTemperature','pressures','humidities'];
        var index = $.inArray(value,arr);
        if(index === 0)
            this.getCheckLayerOfChart(arr[index],this.rainFallResult);
        else if (index === 1)
            this.getCheckLayerOfChart(arr[index],this.temperatureResult);
        else if (index === 2)
            this.getCheckLayerOfChart(arr[index],this.windResult);
        else if (index === 3)
            this.getCheckLayerOfChart(arr[index],this.groundTemperatureResult);
        else if (index === 4)
            this.getCheckLayerOfChart(arr[index],this.pressureResult);
        else if (index === 5)
            this.getCheckLayerOfChart(arr[index],this.humidityResult);
    }

    this.isolineAndSplashMapClick = function () {
        $('#contour').off('click').on('click',function (e) {
            if($(e.target).attr('checked')){
                if($('.layer-text a.action').attr('val') != null){
                    if (this.MapInfo.ContourLayer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
                    }
                    this.defineArray($('.layer-text a.action').attr('val'));
                }
            }else{
                if (this.MapInfo.ContourLayer != null)
                    this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
            }
        }.bind(this))

        $('#color-figure').off('click').on('click',function (e) {
            if($(e.target).attr('checked')){
                if($('.layer-text a.action').attr('val') != null)
                    if (this.MapInfo.layer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                    }
                //this.MapInfo.PlotColor('');
                $(".color-control").attr("style", "display: none")
                this.defineArray($('.layer-text a.action').attr('val'));
            }else{
                if (this.MapInfo.layer != null)
                    this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                //this.MapInfo.PlotColor('');
                $(".color-control").attr("style", "display: none")
            }
        }.bind(this))
    }

    this.checkNull = function () {
        if (this.MapInfo.ContourLayer != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);

        if (this.MapInfo.layer != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.layer);

        $(".color-control").attr("style", "display: none")
    }

}