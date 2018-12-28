var ProductShow = function (activeProductCallback) {
    this.PlayTimer = null;
    this.LeftScroller = null;
    this.RightScroller = null;
    this.ProductContainer = null;
    this.ActiveProductCallback = activeProductCallback;

    this.Init = function (id) {
        var control = $(id);
        this.LeftScroller = control.find('a.arrow-left:first');
        this.RightScroller = control.find('a.arrow-right:first');
        this.ProductContainer = control.find('ul.imgeUrl:first');

        this.LeftScroller.on('click', this.ActivePrevious.bind(this));
        this.LeftScroller.on('mousedown', this.OnLeftScrollerMouseDown.bind(this));
        this.LeftScroller.on('mouseup', this.ClearTimer.bind(this));

        this.RightScroller.on('click', this.ActiveNext.bind(this));
        this.RightScroller.on('mousedown', this.OnRightScrollerMouseDown.bind(this));
        this.RightScroller.on('mouseup', this.ClearTimer.bind(this));
    }

    this.OnLeftScrollerMouseDown = function () {
        this.ClearTimer();
        this.StartTimer(this.ActivePrevious.bind(this));
    }

    this.OnRightScrollerMouseDown = function () {
        this.ClearTimer();
        this.StartTimer(this.ActiveNext.bind(this));
    }

    this.ClearTimer = function () {
        if (this.PlayTimer !== null)
            clearInterval(this.PlayTimer);
    }

    this.StartTimer = function (callback) {
        this.ClearTimer();
        this.PlayTimer = setInterval(function () {
            callback();
        }.bind(this), 200);
    }

    this.OnProductClick = function (e) {
        var product = $(e.target.parentNode);
        this.SetActiveProduct(product);
    }

    this.Load = function (products) {
        if (products === null || products.length === 0)
            return;

        // Initialize products
        for (var i = products.length - 1; i >= 0; i--) {
            var child = this.CreateProduct(products[i]);
            this.ProductContainer.append(child);
        }
        /*$(products).each(function (index, product) {
            var child = this.CreateProduct(product);
            this.ProductContainer.append(child);

        }.bind(this));*/

        // Active the last product
        var first = products[0];
        this.SetActiveProduct($('#' + first.id));

        // Bind product event
        this.ProductContainer.find('li').on('click', this.OnProductClick.bind(this));
    }

    this.ActivePrevious = function () {
        var product = this.GetActiveProduct();
        var previous = product.prev().find('img:first');
        if (previous != null)
            previous.trigger('click');
    }

    this.ActiveNext = function () {
        var product = this.GetActiveProduct();
        var next = product.next().find('img:first');
        if (next != null)
            next.trigger('click');
    }

    this.CreateProduct = function (product) {
        var url = product.url;
        var arr = url.split("/")[url.split("/").length - 1].split(".")[0];
        var time = arr.substr(0, 2);
        var template = '<li id="{0}"><img src="{1}" class="width100"><p>{2}时</p></li>';
        return template.format(product.id, product.url, time);
    }

    this.GetActiveProduct = function () {
        var elements = this.ProductContainer.find('li.action');
        return elements.length > 0 ? $(elements[0]) : null;
    }

    this.ClearActiveProduct = function () {
        var product = this.GetActiveProduct();
        if (product != null)
            product.removeClass('action');
    }

    this.SetActiveProduct = function (product) {
        this.ClearActiveProduct();
        product.addClass('action');
        this.MakeProductVisible(product);

        if (this.ActiveProductCallback !== null)
            this.ActiveProductCallback(product);
    }

    this.MakeProductVisible = function (product) {
        var productOffset = this.GetElementOffset(product);
        var leftScrollerOffset = this.GetElementOffset(this.LeftScroller);
        var rightScrollerOffset = this.GetElementOffset(this.RightScroller);
        var containerPosition = this.ProductContainer.position();

        if (productOffset.left < leftScrollerOffset.right) {
            var left = containerPosition.left + (leftScrollerOffset.right - productOffset.left);
            this.ProductContainer.css('left', left);
        }
        else if (productOffset.right > rightScrollerOffset.left) {
            var left = containerPosition.left + (rightScrollerOffset.left - productOffset.right);
            this.ProductContainer.css('left', left);
        }
    }

    this.GetElementOffset = function (element) {
        var position = element.offset();
        position.right = position.left + element.width();
        position.bottom = position.top + element.height();
        return position;
    }
}