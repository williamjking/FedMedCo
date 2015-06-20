/**
 * Created by anupam on 4/7/15.
 */

$(function(){
    var additional_naics = $('#additional_naics');
    var count = 0;
    var i =  0;


    function clearErrorMessages(){
        $(".errors").remove();
        $(".message").remove();
    }

    function reset(){
        clearErrorMessages();
        $("#search").val("");
        $("form > fieldset.form").find("input").val("");
        $("div#naics > .panel-body").find('ul.list-group').remove();
        $("div#naics > .panel-body").find('input[type="hidden"]').remove();
        $("#additional_naics > p").remove();
    }

    function createOptionList(hits){
        var value;
        var loopLimit = hits.length;
        if (hits.length > 20) {
            $('<div class="message">Too many hits. Please be specific with your query to narrow down search results. Showing only 20 results.</div>').insertBefore("#searchDiv");
            loopLimit = 20;
        }
        else
            $('<div class="message">More than one contractor found. Please select the right one.</div>').insertBefore("#searchDiv");

        $('#searchText').hide();

        $('#queryResults').append('<option value="default" style="display:none;" selected disabled="disabled">Select one</option>');

        for (var cnt = 0; cnt < loopLimit; cnt++) {
            value = hits[cnt];
            $('#queryResults').append('<option value="'+value.links[0].href+'">'+value.legalBusinessName + ' (duns:' + value.duns + ')' +'</option>');
        }

        $('#queryResults').append('<option value="None">None of the above</option>');

        $('#queryResults').show('fast');

    }

    $('#queryResults').change(function(){

        clearErrorMessages();
        if(this.value !== "None"){
            populateContractorFromSAM(this.value);
        }

        $('#queryResults').find('option').remove();
        $('#queryResults').hide('fast');
        $('#searchText').show('fast');
    });


    function populateContractorFromSAM(href)  {
        $.ajax({
            /*url: href + "?api_key=PimLk45xd4U001woEvn42vzamsUQTjx31bWSU5W3", */
            url: href + "?api_key=yPvUuJPa1MmxW0vyVmQWcodfsBShL5fcJtEDBwmq",
            dataType: 'json',
            global:false,
            error: function(jqXHR, textStatus, errorThrown) {
                var errorMessage = jQuery.parseJSON( jqXHR.responseText );
                $( '<ul id="searchErrors" class="errors" role="alert"><li style="padding-left: 25px; text-indent:0;">'+errorMessage.Message+'</li></ul>' ).insertBefore( "#searchDiv" );
            },
            success: function(data, textStatus, jqXHR) {
                reset();
                var response = jQuery.parseJSON( jqXHR.responseText );
                var samData = response.sam_data.registration;
                var govtBusinessPOC = samData.govtBusinessPoc;
                var altGovtBusinessPOC = samData.altGovtBusinessPoc;
                $("#search").val("");

                $('[name="duns"]').val(samData.duns);
                $('[name="duns4"]').val(samData.dunsPlus4);
                $('[name="cage"]').val(samData.cage);
                $('[name="legalBusinessName"]').val(samData.legalBusinessName);

                if (samData.hasOwnProperty('govtBusinessPoc')) {
                    $("div#govtBusinessPoc").find('input[id="firstName"]').val(govtBusinessPOC.firstName);
                    $("div#govtBusinessPoc").find('input[id="lastName"]').val(govtBusinessPOC.lastName);
                    $("div#govtBusinessPoc").find('input[id="email"]').val(govtBusinessPOC.email);
                    $("div#govtBusinessPoc").find('input[id="fax"]').val(govtBusinessPOC.fax);
                    $("div#govtBusinessPoc").find('input[id="phone"]').val(govtBusinessPOC.usPhone);
                    $("div#govtBusinessPoc").find('input[id="line1"]').val(govtBusinessPOC.address.Line1);
                    $("div#govtBusinessPoc").find('input[id="line2"]').val(govtBusinessPOC.address.Line2);
                    $("div#govtBusinessPoc").find('input[id="city"]').val(govtBusinessPOC.address.City);
                    $("div#govtBusinessPoc").find('input[id="stateProvince"]').val(govtBusinessPOC.address.stateorProvince);
                    $("div#govtBusinessPoc").find('input[id="country"]').val(govtBusinessPOC.address.Country);
                    $("div#govtBusinessPoc").find('input[id="zip"]').val(govtBusinessPOC.address.Zip);
                    $("div#govtBusinessPoc").find('input[id="zip4"]').val(govtBusinessPOC.address.Zip4);
                }


                if (samData.hasOwnProperty('altGovtBusinessPoc')) {
                    $("div#altGovtBusinessPoc").find('input[id="firstName"]').val(altGovtBusinessPOC.firstName);
                    $("div#altGovtBusinessPoc").find('input[id="lastName"]').val(altGovtBusinessPOC.lastName);
                    $("div#altGovtBusinessPoc").find('input[id="email"]').val(altGovtBusinessPOC.email);
                    $("div#altGovtBusinessPoc").find('input[id="fax"]').val(altGovtBusinessPOC.fax);
                    $("div#altGovtBusinessPoc").find('input[id="phone"]').val(altGovtBusinessPOC.usPhone);
                    $("div#altGovtBusinessPoc").find('input[id="line1"]').val(altGovtBusinessPOC.address.Line1);
                    $("div#altGovtBusinessPoc").find('input[id="line2"]').val(altGovtBusinessPOC.address.Line2);
                    $("div#altGovtBusinessPoc").find('input[id="city"]').val(altGovtBusinessPOC.address.City);
                    $("div#altGovtBusinessPoc").find('input[id="stateProvince"]').val(altGovtBusinessPOC.address.stateorProvince);
                    $("div#altGovtBusinessPoc").find('input[id="country"]').val(altGovtBusinessPOC.address.Country);
                    $("div#altGovtBusinessPoc").find('input[id="zip"]').val(altGovtBusinessPOC.address.Zip);
                    $("div#altGovtBusinessPoc").find('input[id="zip4"]').val(altGovtBusinessPOC.address.Zip4);
                }

                if (samData.hasOwnProperty('samAddress')) {
                    $("div#address").find('input[id="line1"]').val(samData.samAddress.Line1);
                    $("div#address").find('input[id="line2"]').val(samData.samAddress.Line2);
                    $("div#address").find('input[id="city"]').val(samData.samAddress.City);
                    $("div#address").find('input[id="stateProvince"]').val(samData.samAddress.stateorProvince);
                    $("div#address").find('input[id="country"]').val(samData.samAddress.Country);
                    $("div#address").find('input[id="zip"]').val(samData.samAddress.Zip);
                    $("div#address").find('input[id="zip4"]').val(samData.samAddress.Zip4);
                }


                if (samData.hasOwnProperty('mailingAddress')) {
                    $("div#mailingAddress").find('input[id="line1"]').val(samData.mailingAddress.Line1);
                    $("div#mailingAddress").find('input[id="line2"]').val(samData.mailingAddress.Line2);
                    $("div#mailingAddress").find('input[id="city"]').val(samData.mailingAddress.City);
                    $("div#mailingAddress").find('input[id="stateProvince"]').val(samData.mailingAddress.stateorProvince);
                    $("div#mailingAddress").find('input[id="country"]').val(samData.mailingAddress.Country);
                    $("div#mailingAddress").find('input[id="zip"]').val(samData.mailingAddress.Zip);
                    $("div#mailingAddress").find('input[id="zip4"]').val(samData.mailingAddress.Zip4);
                }

                if (samData.naics && samData.naics.length > 0){
                    $('<ul class="list-group"></ul>').insertBefore("#additional_naics");
                    $.each(samData.naics, function( index, value ) {
                        var liClass = "list-group-item";
                        var hiddenClass = "";
                        if (value.isPrimary) {
                            liClass = "list-group-item active primaryNaics";
                            hiddenClass = "primaryNaics";
                        }

                        $("div#naics > .panel-body").find('ul').append('<li class="'+liClass+'">'+value.naicsCode+': '+ value.naicsName+'</li>');

                        $("div#naics > .panel-body").append('<input type="hidden" class="'+hiddenClass+'" name="naics'+index+'" value="isPrimary:'+
                        value.isPrimary +
                        ', naicsCode:' +value.naicsCode + ', naicsName:'+ value.naicsName +'"\>');
                    });
                }

            },
            contentType: 'application/json'
        });
    }

    $("#search").bind("enterKey",function(e){
        clearErrorMessages();
        var queryString = $('#search').val();
        $.ajax({
            url: "https://api.data.gov/sam/v1/registrations?qterms=" + queryString +"&api_key=PimLk45xd4U001woEvn42vzamsUQTjx31bWSU5W3",
            dataType: 'json',
            global:false,
            error: function(jqXHR, textStatus, errorThrown) {
                var errorMessage = jQuery.parseJSON( jqXHR.responseText );
                $( '<ul id="searchErrors" class="errors" role="alert"><li style="padding-left: 25px; text-indent:0;">'+errorMessage.Message+'</li></ul>' ).insertBefore( "#searchDiv" );
            },
            success: function(data, textStatus, jqXHR) {
                var response = jQuery.parseJSON( jqXHR.responseText );

                //if there are no results, show an error
                if (response.results.length == 0) {
                    $( '<ul id="searchErrors" class="errors" role="alert"><li style="padding-left: 25px; text-indent:0;">Unable to locate a contractor in SAM for the given search criteria. Please create a contractor by manually filling out the required fields.</li></ul>' ).insertBefore( "#searchDiv" );
                }
                else if (response.results.length == 1) {
                    populateContractorFromSAM(response.results[0].links[0].href);
                }
                else {
                    createOptionList(response.results);
                }
            },
            contentType: 'application/json'
        });
    });
    $("#search").keyup(function(e){
        if(e.which == 13)
        {
            $(this).trigger("enterKey");
        }
    });

    $("form input").change(
        function(){clearErrorMessages();}
    );

    $(".buttons a.reset").click(function(){
       reset();
    });

    $("#add_another_naics").click(function(){
        $('<p> ' +
            '<label for="additional_naics_code">' +
            '<input type="text" id="additional_naics_code" size="20" name="additional_naics_code_' + i +'" value="" placeholder="NAICS Code" />' +
            '</label> ' +
            '<label for="additional_naics_name">' +
            '<input type="text" id="additional_naics_name" size="20" name="additional_naics_name_' + i +'" value="" placeholder="NAICS Name" />' +
            '</label> ' +
            '<label for="additional_naics_primary">' +
            '<input type="checkbox" id="additional_naics_primary" name="additional_naics_primary_' + i +'"/> Primary' +
            '</label>' +
            '<span title="Remove Attribute" id="remove_additional_naics" class="glyphicon glyphicon-remove-circle" aria-hidden="true">' +
            '</span>' +
            '</p>'
        ).appendTo(additional_naics);
        count++;
        i++;
        return false;
    });


    $('#additional_naics').on('click', '#remove_additional_naics', function(){
        if( count > 0 ) {
            if ($(this).parent().find('input:checked').length > 0){
                var hiddenPrimary = $("div#naics > .panel-body").find('input.primaryNaics');
                var hiddenPrimaryValue;

                $("div#naics > .panel-body").find('ul > li.primaryNaics').toggleClass("active");

                if (hiddenPrimary.length > 0) {
                    hiddenPrimaryValue = hiddenPrimary.val().replace("false", "true");
                    hiddenPrimary.val(hiddenPrimaryValue);
                }
            }
            $(this).parents('p').remove();
            count--;
        }
        return false;
    });

    $('#additional_naics').on('click', '#additional_naics_primary', function(){
        var selectedName = this.name;
        var hiddenPrimary = $("div#naics > .panel-body").find('input.primaryNaics');
        var hiddenPrimaryValue;
        if (this.checked){
            $('#additional_naics').find('input:checked').each(
                function() {
                    if ($(this).attr('name') !== selectedName){
                        $(this).attr('checked', false);
                    }
                }
            );

            $("div#naics > .panel-body").find('ul > li.primaryNaics.active').toggleClass("active");

            if (hiddenPrimary.length > 0) {
                hiddenPrimaryValue = hiddenPrimary.val().replace("true", "false");
                hiddenPrimary.val(hiddenPrimaryValue);
            }

        }
        else {
            $("div#naics > .panel-body").find('ul > li.primaryNaics').toggleClass("active");

            if (hiddenPrimary.length > 0) {
                hiddenPrimaryValue = hiddenPrimary.val().replace("false", "true");
                hiddenPrimary.val(hiddenPrimaryValue);
            }
        }

        return true;
    });




});