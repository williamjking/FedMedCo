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
            '<select id="fieldsSelect_' + fieldCounter +'" name="fields_' + fieldCounter +'" aria-describedby="basic-addon1"></select>' +
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
        
        updateOptions($('#fieldsSelect_' + fieldCounter));

        $('[data-toggle="tooltip"]').tooltip();

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
    
    $('#categorySelect').change(function(){
    	enableDisableSubCategories(this.value);
    	getFields();
    });
    
    $('#subCategorySelect').change(function(){
    	getFields()
    });

});

function enableDisableSubCategories(category) {
	var subCategorySelect = $('#subCategorySelect');
	subCategorySelect.empty();
	if (category == "drug") {
		appendOption(subCategorySelect, "event");
		appendOption(subCategorySelect, "label");
		appendOption(subCategorySelect, "enforcement");
	} else if (category == "device") {
		appendOption(subCategorySelect, "event");
		appendOption(subCategorySelect, "enforcement");
	} else if (category == "food") {
		appendOption(subCategorySelect, "enforcement");
	}
}

function updateFields(newValues) {
	$.fieldOptions = eval("(" + newValues + ")");
	var $field = $("[id^=fieldsSelect_]");
	updateOptions($field);
}

function updateOptions(field) {
	field.empty();
	if ($.fieldOptions.length > 0) {
		$.each($.fieldOptions, function(index, value) {
			appendOption(field, value);
		});
	} else {
		appendOption(field, "");
	}
}

function appendOption(field, value) {
	field.append($("<option></option>").attr("value", value).text(value));
}

function getFields() {
	$.ajax({
		type: 'POST',
		data: 'category=' + escape($('#categorySelect').val()) + '&subcategory=' + escape($('#subCategorySelect').val()),
		url: '/FedMedCo/query/populateFields',
		success: function(data,textStatus){
			updateFields(data);
		},
		error: function(XMLHttpRequest,textStatus,errorThrown){}}
	);
}
