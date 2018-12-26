var Region = function (typeName, regionId, regionImg) {

    this.name = null;
    this.typeName = typeName.substring(0, 3);
    this.products = [];
    this.productPlayIndex = -1;
    this.regionId = regionId;
    this.regionImg = regionImg;

    this.setProductPlayIndex = function (productIndex) {
        this.productPlayIndex = productIndex;
        $(this.regionImg).html('<div class="theme-area-chart" style="display: block;"><div class="theme-chart {1}" style="display: block;"><img src="{0}"></div></div>'.format(this.products[this.productPlayIndex].url, this.typeName))
        this.cliclImg();
    }

    this.Init = function (products) {
        var textHtml = '';
        this.name = products.name;

        $(products.productTypes).each(function (index, productType) {
            var product = new Product(productType);
            this.products.push(product);
            if (products.productTypes.length == 1) {
                if (index == 0) {
                    textHtml += '<a href="javascript:;" class="action theme-border">{0}</a>'.format(productType.name)
                } else {
                    textHtml += '<a href="javascript:;" class="theme-border">{0}</a>'.format(productType.name)
                }
            } else {
                if (index == 0)
                    textHtml += '<a href="javascript:;" class="action">{0}</a>'.format(productType.name)
                else
                    textHtml += '<a href="javascript:;">{0}</a>'.format(productType.name)
            }
        }.bind(this));

        $(this.regionId).html(textHtml);
        $(this.regionImg).html('<div class="theme-area-chart" style="display: block;"><div class="theme-chart {1}" style="display: block;"><img src="{0}"></div></div>'.format(this.products[this.productPlayIndex + 1].url, this.typeName))
        this.cliclImg();
    }

    this.Play = function (callback) {
        this.productPlayIndex++;

        if (this.productPlayIndex > this.products.length - 1) {
            this.productPlayIndex = -1;
            callback();
        } else {
            $(this.regionId).parents(".theme-title").find(".theme-area-table a").eq(this.productPlayIndex).addClass("action").siblings().removeClass("action");
            $(this.regionImg).html('<div class="theme-area-chart" style="display: block;"><div class="theme-chart {1}" style="display: block;"><img src="{0}"></div></div>'.format(this.products[this.productPlayIndex].url, this.typeName))
            this.cliclImg();
        }
    }

    this.cliclImg = function () {
        $("." + this.typeName + " img").on('click', function (e) {
            //var parmOne = $(e.target).parents(".theme-content").prev().find(".theme-area-table a.action").text();
            //var parmTwo = $(e.target).parents(".theme-content").prev().prev().find(".theme-area-table a.action").text();
            var parentValue = $(e.target).parents(".theme").find(".title h2").attr('category');
            this.OpenProductPage(parentValue);
        }.bind(this))
    }

    this.OpenProductPage = function (category) {
        var url = 'aerological-sound.html?category={0}'.format(category);
        window.open(url, '_blank');
    }
}