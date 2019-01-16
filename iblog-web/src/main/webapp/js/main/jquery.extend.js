$(function () {
    $.extend({
        /**
         * 小数保留指定位数
         * @param val 值
         * @param precision 保留位数
         * @returns {number}
         */
        mathRound: function (val, precision) {
            'use strict';
            return Math.round(val * Math.pow(10, precision)) / Math.pow(10, precision);
        }
    });

});