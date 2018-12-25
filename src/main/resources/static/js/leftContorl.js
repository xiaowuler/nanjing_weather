var Product = function (product) {
    this.name = product.name;
    this.url = product.productData.url;
}

var Region = function () {
    this.name = null;
    this.products = [];

    this.Init = function (products) {
        this.name = products.name;
        for (var i = 0; i < products.productTypes.length; i++) {
            var product = new Product(products.productTypes[i]);
            this.products.push(product);
        }
    }

}

var Category = function () {
    this.name = null;
    this.regions = [];

    this.Init = function (category) {
        this.name = category.categoryCode;
        for (var i = 0; i < category.productRegion.length; i++) {
            var region = new Region();
            region.Init(category.productRegion[i])
            this.regions.push(region);
        }
    }

}