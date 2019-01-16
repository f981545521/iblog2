(function ($) {
    var shade = "#556b2f";
    $.fn.popup = function (action, options) {
        var settings = $.extend({
            shade: shade,
            backgroundColor: "white"
        }, options);
        if (action === "open") {
            console.log("open ——————————>" + settings.backgroundColor)
        }
        if (action === "close") {
            console.log("close ——————————<")
        }
        return this.css({color: settings.backgroundColor});
    };
})(jQuery);