var DataArrival = function (time) {
    this.time = time;
    this.regionCodes = [];
    
    this.init = function (centerDataArrival) {
        $(centerDataArrival).each(function (index,dataArrival) {
            var parm = '';
            if(dataArrival.productRegionCode != null)
                parm = dataArrival.productRegionCode
            var regionCode = new RegionCode(parm);
            if(dataArrival.lastDataArrivals != null)
                regionCode.init(dataArrival.lastDataArrivals,'one');
            else
                regionCode.init(dataArrival,'two')
            this.regionCodes.push(regionCode)
        }.bind(this))
    }
    
    this.writeHtml = function (flag) {
        var textHtml = '';
        for(var i = 0; i < this.regionCodes.length; i++) {
            if(this.regionCodes[i].areaName == ''){
                return '<div class="slide-des"><span class="color {0}">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>'.format(this.getState(this.regionCodes[i].statu))
            }else {
                if(this.regionCodes.length === 1){
                    if(flag === 'start')
                        textHtml += '<div class="swiper-slide"><div class="slide-time">{0}</div><div class="slide-des"><span class="color {2}" title= "{3}">{1}</span></div>'.format(this.time,this.regionCodes[i].areaName,this.getState(this.regionCodes[i].statu), this.getNameByAreaName(this.regionCodes[i].areaName));
                    else if(flag === 'end')
                        textHtml += '<div class="slide-des font-color">{0}</div><div class="slide-des"><span class="color {2}" title="{3}">{1}</span></div></div>'.format(this.time,this.regionCodes[i].areaName,this.getState(this.regionCodes[i].statu), this.getNameByAreaName(this.regionCodes[i].areaName));
                    else
                        textHtml += '<div class="slide-des"><span class="color {0}" title="{2}">{1}</span></div>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName, this.getNameByAreaName(this.regionCodes[i].areaName));
                }else {
                    if(i === 0){
                        if(flag === 'start')
                            textHtml += '<div class="swiper-slide"><div class="slide-time">{0}</div><div class="slide-des"><span class="color {2}" title="{3}">{1}</span>'.format(this.time,this.regionCodes[i].areaName,this.getState(this.regionCodes[i].statu),this.getNameByAreaName(this.regionCodes[i].areaName));
                        else if(flag === 'end')
                            textHtml += '<div class="slide-des font-color">{0}</div><div class="slide-des"><span class="color {2}" title="{3}">{1}</span>'.format(this.time,this.regionCodes[i].areaName,this.getState(this.regionCodes[i].statu),this.getNameByAreaName(this.regionCodes[i].areaName));
                        else
                            textHtml += '<div class="slide-des"><span class="color {0}" title="{2}">{1}</span>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName,this.getNameByAreaName(this.regionCodes[i].areaName));
                    }else if(i === this.regionCodes.length-1){
                        if(flag != 'end')
                            textHtml += '<span class="color {0}" title="{2}">{1}</span></div>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName, this.getNameByAreaName(this.regionCodes[i].areaName));
                        else
                            textHtml += '<span class="color {0}" title="{2}">{1}</span></div></div>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName,this.getNameByAreaName(this.regionCodes[i].areaName));
                    }else {
                        if(flag != 'end')
                            textHtml += '<span class="color {0}" title="{2}">{1}</span>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName,this.getNameByAreaName(this.regionCodes[i].areaName));
                        else
                            textHtml += '<span class="color {0}" title="{2}">{1}</span>'.format(this.getState(this.regionCodes[i].statu),this.regionCodes[i].areaName,this.getNameByAreaName(this.regionCodes[i].areaName));
                    }
                }
            }
        }
        /*console.log(textHtml)
        console.log('------------------------------------------------------------------')*/
        return textHtml;
    }

    this.getNameByAreaName = function (name) {
        if (name == 'GC')
            return '高淳';
        else if (name == 'JN')
            return '江宁';
        else if (name == 'PK')
            return '浦口';
        else if (name == 'LH')
            return '六合';
        else if (name == 'XW')
            return '玄武';
        else if (name == 'NJ')
            return '南京';
        else
            return '溧水';
    }

    this.getState = function (timeCodeState) {
        if (timeCodeState == "parts") {
            return "orange";
        } else if (timeCodeState == "all") {
            return "green";
        } else if (timeCodeState == "missing") {
            return "red";
        } else if (timeCodeState == "error") {
            return "red";
        } else if (timeCodeState == "lost") {
            return "gray";
        }
    }

}