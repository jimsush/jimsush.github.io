require.config({
    paths: {
        jquery: '../jquery-2.1.1.min'
    },
    baseUrl: 'js'
});
 
require(['jquery'], function($) {
    //alert($().jquery);
});

require(['selector'], function(modulereturn) {
    var els = modulereturn('.wrapper');
    console.log(els);
});
