/**
 * Created by anupam on 4/7/15.
 */

$(function(){
    var additional_fields = $('#additional_fields');
    var fieldCount = 0;
    var fieldCounter =  0;

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
        $("#additional_fields > p").remove();
    }


    $("#add_another_field").click(function(){
        if (fieldCount >= 5) {
            $("#add_another_field").hide();
            return false;
        }

        fieldCount++;
        fieldCounter++;
        $('<div class="groupParent"> ' +
            '<input type="hidden" class="hiddenClass" id="booleanOp_'+fieldCounter+'" name="booleanOp_' + fieldCounter +'" value="AND"/>' +
            '<div class="input-group">'+
            '<a href="#" name="'+fieldCounter+'" data-toggle="tooltip" title="Click to toggle" id="booleanSpan" class="input-group-addon" id="basic-addon1">AND</a>' +
            '<input type="text" id="fields" size="20" name="fields_' + fieldCounter +'" value="" placeholder="openFDA field name" aria-describedby="basic-addon1"/>' +
             '</div>' +
            '<label for="criteria">' +
            '<input type="text" id="criteria" size="20" name="criteria_' + fieldCounter +'" value="" placeholder="Search Criteria" />' +
            '</label> ' +
            '<label for="criteria_exact">' +
            '<input type="checkbox" id="criteria_exact" name="criteria_exact_' + fieldCounter +'"/> Exact' +
            '</label>' +
            '<span title="Remove Fields" id="remove_additional_fields" class="glyphicon glyphicon-remove-circle" aria-hidden="true">' +
            '</span>' +
            '</div>'
        ).appendTo(additional_fields);

        return false;
    });


    $('#additional_fields').on('click', '#remove_additional_fields', function(){
        if( fieldCount > 0 ) {
            $(this).parents('div.groupParent').remove();
            fieldCount--;
            if (fieldCount < 5) $("#add_another_field").show();
        }
        return false;
    });


    $('#additional_fields').on('click', '#booleanSpan', function(){
        var name = $(this).attr('name');
        console.log('name is ' + name);
        if ($(this).html() == 'AND') {
            $(this).html('OR ');
            $("#booleanOp_"+name).val('OR');
        }
        else{
            $(this).html('AND');
            $("#booleanOp_"+name).val('AND');
        }
    });


    $("button.reset").click(function(){
        reset();
    });
});