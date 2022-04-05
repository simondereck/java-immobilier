$(function () {
    var explorer = window.navigator.userAgent ;
    if (explorer.indexOf("MSIE") >= 0) {


    }
    else if (explorer.indexOf("Firefox") >= 0) {

    }
    else if(explorer.indexOf("Chrome") >= 0){
    }
    else if(explorer.indexOf("Opera") >= 0){

    }
    else if(explorer.indexOf("Safari") >= 0){
        var dateClass='.datechk';
        $(document).ready(function ()
        {
            if (!document.querySelector(dateClass)){
                return;
            }
            if (document.querySelector(dateClass).type !== 'date')
            {
                var oCSS = document.createElement('link');
                oCSS.type='text/css'; oCSS.rel='stylesheet';
                oCSS.href='//ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css';
                oCSS.onload=function()
                {
                    var oJS = document.createElement('script');
                    oJS.type='text/javascript';
                    oJS.src='//ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js';
                    oJS.onload=function()
                    {
                        $(dateClass).datepicker({ dateFormat: 'yy-mm-dd' });
                    }
                    document.body.appendChild(oJS);
                }
                document.body.appendChild(oCSS);
            }
        });
        var dateTimeClass='.dateTimeChk';

        $(document).ready(function ()
        {
            if (!document.querySelector(dateTimeClass)){
                if (document.querySelector(dateTimeClass).type !== 'datetime-local')
                {
                    var oCSS = document.createElement('link');
                    oCSS.type='text/css'; oCSS.rel='stylesheet';
                    oCSS.href='//ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css';
                    oCSS.onload=function()
                    {
                        var oJS = document.createElement('script');
                        oJS.type='text/javascript';
                        oJS.src='//ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js';
                        oJS.onload=function()
                        {
                            $(dateTimeClass).datepicker({ dateFormat: 'yy/mm/dd T hh:mm:ss ' });
                        }
                        document.body.appendChild(oJS);
                    }
                    document.body.appendChild(oCSS);
                }
            }

        });
    }


})