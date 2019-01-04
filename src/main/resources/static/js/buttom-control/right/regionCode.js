var RegionCode = function (areaName) {
    this.areaName = areaName;
    this.statu = null;
    
    this.init = function (lastDataArrivals) {
        this.statu = lastDataArrivals[0].timelinessCode;
    }
}