$(function () {
    console.log("开始执行代码！")
    var TodayWeather = function ($element, option){
        var _this = this;
        this.$element = $element;
        this.option = option;
        this.option.data = this.option.data || {};
        if (typeof (_this.option.ajax) === "undefined") {
            _this.option.ajax = true;
        }
        _this.data = _this.option.data;
        _this._rowTemplate = "<h2>HELLO , Template </h2>";

        var _panel = this.$element.find(".panel-heading");
        _panel.on("click", function () {
            _this.refreshData();
        });
        return this;
    };

    TodayWeather.prototype.refreshData = function () {
        console.log("执行：refreshData()方法")
    };

    TodayWeather.prototype._addItem = function (item) {
        var _this = this;
        var $row = $(_this._rowTemplate);
        console.log("执行：_addItem()方法|" + JSON.stringify($row));
    };

    $.fn.todayWeather = function (option) {
        var instanceOptions = $.extend({}, option, true);
        return new TodayWeather($(this), instanceOptions);
    }

});