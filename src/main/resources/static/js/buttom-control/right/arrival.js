var Arrival = function (time) {
    this.time = time;
    this.regionCodes = [];
    
    this.init = function (centerDataArrival) {
        $(centerDataArrival).each(function (index,dataArrival) {
            var parm = '';
            if(dataArrival.productRegionCode != null)
                parm = dataArrival.productRegionCode
            var regionCode = new RegionCode(parm);
            regionCode.init(dataArrival.lastDataArrivals);
            this.regionCodes.push(regionCode)
        }.bind(this))
    }
    
    this.writeHtml = function (flag) {
        if(flag === 'start')
            return '<div class="swiper-slide"><div class="slide-time">{2}</div><div class="slide-des"><span class="color {0}">{1}</span></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num,this.time);
        else if(flag === 'end'){
            return '<div class="slide-des"><span class="color {0}">{1}</span></div></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num);
        }else
            return '<div class="slide-des"><span class="color {0}">{1}</span></div>'.format(this.getState(this.lastDatas[this.lastDatas.length-1].statu),this.lastDatas[this.lastDatas.length-1].num);
    }

}