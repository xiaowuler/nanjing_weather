var ArrayRegion = function (id,latitude,longitude,name) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.typeRegions = [];
    
    this.addData = function (type,regions) {
        if(this.typeRegions.length == 0){
            var typeRegion = new TypeRegion();
            typeRegion.setType(type);
            typeRegion.setValue(regions.value);
            if(regions.instantDirection != null){
                typeRegion.setInstantDirection(regions.instantDirection);
            }
            this.typeRegions.push(typeRegion);
        }else{
            var flag = false;
            $(this.typeRegions).each(function (index,typeRegion) {
                if(typeRegion.returnFlag(type)){
                    typeRegion.setValue(regions.value);
                    if(regions.instantDirection != null){
                        typeRegion.setInstantDirection(regions.instantDirection);
                    }
                    flag = true;
                }
            })
            if(!flag){
                var typeRegion = new TypeRegion();
                typeRegion.setType(type);
                typeRegion.setValue(regions.value);
                if(regions.instantDirection != null){
                    typeRegion.setInstantDirection(regions.instantDirection);
                }
                this.typeRegions.push(typeRegion);
            }
        }
    }

    this.returnFlag = function (id) {
        if(id == this.id)
            return true;
        return false;
    }
}