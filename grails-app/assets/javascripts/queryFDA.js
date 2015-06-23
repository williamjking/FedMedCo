/**
 * Created by anupam on 4/7/15.
 */

$(function(){
    function clearErrorMessages(){
        $(".errors").remove();
        $(".message").remove();
    }

    function clearErrorMessages(){
        $(".errors").remove();
        $(".message").remove();
    }

    function reset(){
        clearErrorMessages();
        $("form > fieldset.form").find("input").val("");
        $("#searchResults").hide();
    }


    $("#searchFDA").bind("enterKey",function(e){
        clearErrorMessages();
        var openFDAURL = "https://api.fda.gov/";
        var category = $('#categorySelect').val();
        var subCategory = $('#subCategorySelect').val();
        var field = $('#queryField').val();
        var queryString = $('#searchFDA').val();
        //var completeQueryStr = openFDAURL + category + "/" + subCategory + ".json?api_key=yPvUuJPa1MmxW0vyVmQWcodfsBShL5fcJtEDBwmq&search=" + field + ":" + queryString;
        var completeQueryStr = openFDAURL + category + "/" + subCategory + ".json?search=" + field + ":" + queryString;

        $.ajax({
            url: completeQueryStr,
            dataType:"json",
            global:false,
            headers: {
                'Access-Control-Allow-Origin': 'https//api.fda.gov'
            },
            error: function(jqXHR, textStatus, errorThrown) {
                var errorMessage = jQuery.parseJSON( jqXHR.responseText );
                $( '<ul id="searchErrors" class="errors" role="alert"><li style="padding-left: 25px; text-indent:0;">'+errorMessage.Message+'</li></ul>' ).insertBefore( "#searchDiv" );
            },
            success: function(data, textStatus, jqXHR) {
                var response = jQuery.parseJSON( jqXHR.responseText );

                console.log ('Got results back ' + data);
                //if there are no results, show an error
                if (response.results.length == 0) {
                    $( '<ul id="searchErrors" class="errors" role="alert"><li style="padding-left: 25px; text-indent:0;">Unable to locate a contractor in SAM for the given search criteria. Please create a contractor by manually filling out the required fields.</li></ul>' ).insertBefore( "#searchDiv" );
                }
                else {
                    console.log(response.results);
                }
            }
        });

    });

    $("#searchFDA").keyup(function(e){
        if(e.which == 13)
        {
            $(this).trigger("enterKey");
        }
    });

    $("button.reset").click(function(){
        reset();
    });
});