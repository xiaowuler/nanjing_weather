var RegionCode = function (areaName) {
    this.areaName = areaName;
    this.statu = null;
    
    this.init = function (lastDataArrivals,flag) {
        if(flag === 'one')
            this.statu = lastDataArrivals[0].timelinessCode;
        else
            this.statu = lastDataArrivals.timeliessCode;
    }
}