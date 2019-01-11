var TypeRegion = function () {
    this.type = null;
    this.value = null;
    this.instantDirection = null;

    this.setType = function (type) {
        this.type = type;
    }

    this.setValue = function (value) {
        this.value = value;
    }

    this.setInstantDirection = function (instantDirection) {
        this.instantDirection = instantDirection;
    }
    
    this.returnFlag = function (type) {
        return this.type == type;
    }
}