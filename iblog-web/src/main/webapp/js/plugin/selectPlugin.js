(function ($, window, document, undefined) {

        var MySelect = function (elem, options) {
            var $this = this;
            $this.domid = Math.floor(Math.random() * 1000);
            $this.elem = elem;
            $this.$elem = $(elem);
            $this.options = $.extend({}, options);
            $this.options = options;
            $this.$win = $(window);
            $this.sections = {};
        };
        MySelect.prototype = {
            defaults: {
                navItems: 'a',
                currentClass: 'current',
                changeHash: false,
                easing: 'swing',
                filter: '',
                scrollSpeed: 750,
                scrollThreshold: 0.5,
                begin: false,
                end: false,
                scrollChange: false
            },
            init: function () {
                var $this = this;
                console.log($this);
                console.log("调用了init方法" + $this.domid);
                $this.destroy();
            },
            destroy: function (e) {
                var $this = this;
                console.log("调用了destroy方法" + $this.options.type)
            }
        };
        $.fn.mySelect = function (options) {
            var select = new MySelect(this, options);
            select.init();
            return select;
        };
    }
)(jQuery, window, document);
