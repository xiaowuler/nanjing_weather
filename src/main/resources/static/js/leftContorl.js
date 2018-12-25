var Product = function (product) {
    this.name = product.name;
    this.url = product.productData.url;
}

var Region = function () {
    this.name = null;
    this.products = [];
    this.productPlayIndex = 0;

    this.Init = function (products) {
        this.name = products.name;
        for (var i = 0; i < products.productTypes.length; i++) {
            var product = new Product(products.productTypes[i]);
            this.products.push(product);
        }
    }

    this.Play = function (playCompleteCallback) {
        this.productPlayIndex++;

        if (this.productPlayIndex >= this.products.count()){
            playCompleteCallback();
            this.productPlayIndex = 0;
        }

    }
}

var Category = function () {
    this.name = null;
    this.regions = [];
    this.regionPlayIndex = 0;

    this.Init = function (category) {
        this.name = category.categoryCode;
        for (var i = 0; i < category.productRegion.length; i++) {
            var region = new Region();
            region.Init(category.productRegion[i])
            this.regions.push(region);
        }
    }

    this.Play = function (){
        this.regions[this.regionPlayIndex].play(this.RegionPlayCompleteCallback);
    }

    this.RegionPlayCompleteCallback = function () {
        this.regionPlayIndex ++;
        if(this.regionPlayIndex >= regions.count()){
            this.regionPlayIndex = 0;
        }
    }
}