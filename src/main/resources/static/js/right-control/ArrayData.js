var ArrayData = function () {

    this.regions = [];

    this.addData = function (result) {
        this.regions.splice(0, this.regions.length);
        $(result).each(function (index,data) {
            if(this.regions.length == 0){
                $(data[1]).each(function (index,region) {
                    var arrayRegion = new ArrayRegion(region.id,region.latitude,region.longitude,region.name);
                    arrayRegion.addData(data[0],region);
                    this.regions.push(arrayRegion);
                }.bind(this))
            }else{
                $(data[1]).each(function (index,region) {
                    var flag = false;
                    $(this.regions).each(function (index,arrayRegion) {
                        if(arrayRegion.returnFlag(region.id)){
                            arrayRegion.addData(data[0],region);
                            flag = true;
                        }
                    }.bind(this))
                    if(!flag){
                        var arrayRegion = new ArrayRegion(region.id,region.latitude,region.longitude,region.name);
                        arrayRegion.addData(data[0],region);
                        this.regions.push(arrayRegion);
                    }
                }.bind(this))
            }
        }.bind(this))
    }
}