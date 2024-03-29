package softexcel.fedmedco

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import grails.converters.JSON


@Secured(['ROLE_QUERY'])
@Transactional(readOnly = true)
class QueryController {

    def queryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Query queryInstance, String errorMessage, String queryResults) {
        Query aQuery = queryInstance
        try {
            if (errorMessage == null) {
                render view:"index", model:[queryResults:queryResults]
            }
            else {
                if (aQuery == null) {
					aQuery = new Query()			
				}
				aQuery.errors.rejectValue('queryField', 'an.error.message')		
                render view:"index", model:[queryInstance:aQuery, errorMessage:errorMessage]
            }
        } catch (Exception e) {
            log.error e.message, e
            aQuery = new Query(params)
            aQuery.errors.rejectValue ('queryField', 'an.error.message')
            render view:"index", model:[queryInstance:aQuery]
        }
    }

	def populateFields(String category, String subcategory) {
		def name
		if (category == "drug" && subcategory == "event") {
			name = "faers"
		} else if (category == "drug" && subcategory == "label") {
			name = "spl"
		} else if (category == "drug" && subcategory == "enforcement") {
			name = "res"
		} else if (category == "device" && subcategory == "event") {
			name = "maude"
		} else if (category == "device" && subcategory == "enforcement") {
			name = "res"
		} else if (category == "food" && subcategory == "enforcement") {
			name = "res"
		}
		
		def fieldList = []
		if (name != null) {
			fieldList = FieldList.findBySchemaName(name).fields
		}
		render fieldList
	}

    def search() {
        try {
            String category = params.category
            String subCategory = params.subcategory
            String openFDAURLBase = "https://api.fda.gov/"
            String openFDAURLPath = category + '/' + subCategory + '.json'
            String assembleAllFields = null

            params.each ({key, value ->
                def matcher = key =~ /fields_(.*)/

                if (matcher && value) {
                   def index = matcher[0][1]

                    try {
                        def criteria = queryService.replaceSpaceWithPlus(params.get("criteria_"+index))
                        if (params.get("criteria_exact_"+index) != null) criteria =  '"' + criteria +'"'

                        if (assembleAllFields == null) assembleAllFields = value + ':' + criteria
                        else if (params.get("booleanOp_"+index) == 'OR') assembleAllFields = assembleAllFields + '+' +  value + ':' + criteria
                        else assembleAllFields = assembleAllFields + '+AND+' +  value + ':' + criteria
                    }
                    catch (NullPointerException npe){

                    }
                    catch (Exception e){
                        log.error(e.message, e)
                    }
                }
            })


            def completeQuery = [search:assembleAllFields]

            if (params.count && params.count != '') {
                if (params.exactCount) completeQuery.put('count', params.count + '.exact')
                else completeQuery.put('count', params.count)
            }
            if (params.limit && params.limit != '' && params.limit?.isInteger()) completeQuery.put('limit', params.limit)
            if (params.skip && params.skip != '' && params.skip?.isInteger()) completeQuery.put('skip', params.skip)

            def url = openFDAURLBase + openFDAURLPath + "?" + completeQuery.collect { it }.join('&')

			def data = getURLContent(url);
			if (data != null) {
		        def parsedData = new JsonSlurper().parseText(data)
	            render view:"show", model: [
	                    disclaimer: parsedData?.meta?.disclaimer,
	                    lastUpdated: parsedData?.meta?.last_updated,
	                    totalResults: parsedData?.meta?.results?.total,
	                    queryResults: data
	            ]
			}

        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def medicineReactions() {
        try {
            String category = 'drug'
            String subCategory = 'event'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String medicineQuery = "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    'patient.drug.openfda.generic_name:'+medicine+'+patient.drug.openfda.brand_name:'+medicine +
                    '+patient.drug.openfda.pharm_class_epc:'+medicine+
                    '&count=patient.reaction.reactionmeddrapt.exact'
					
			def content = getURLContent(medicineQuery);
			if (content != null) {
	            def data = new JsonSlurper().parseText(content)
	
	            def mapToAnalyzeData = [:]
	
	            data.results.each{mapToAnalyzeData.put(it.count, it.term)}
	            def min = mapToAnalyzeData.min {it.key}
	            def max = mapToAnalyzeData.max {it.key}
	
	            Integer oneThird = (max.key - min.key)/3;
	
	            Map lowerThird = mapToAnalyzeData.subMap(min.key..min.key+oneThird)
	            Map middleThird = mapToAnalyzeData.subMap(min.key+oneThird+1..min.key+oneThird+oneThird)
	            Map topThird = mapToAnalyzeData.subMap(min.key+oneThird+oneThird+1..max.key+1)
	
	            def topThreeReactions = []
	
	            def i = 0
	
	            topThird.reverseEach {
	                if (i<3) topThreeReactions.add(it.value)
	                i++
	            }
	
	            DrugReactions drugReactions = new DrugReactions()
	
	            drugReactions.medicine = params.medicine
	            drugReactions.totalReportedReactions = mapToAnalyzeData.size()
	            drugReactions.topThreeReactions =  topThreeReactions
	            drugReactions.severeReactions = topThird
	            drugReactions.moderateReactions = middleThird
	            drugReactions.mildReactions = lowerThird
	
	            render(view:'medicineReactions', model:[drugReactions:drugReactions])
			}
        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def interestingFacts(){
        try {
            String category = 'drug'
            String subCategory = 'label'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String medicineQuery = "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    'openfda.generic_name:'+medicine+'+openfda.brand_name:'+medicine +
                    '+openfda.pharm_class_epc:'+medicine

			def content = getURLContent(medicineQuery);
			if (content != null) {
				def data = new JsonSlurper().parseText(content)
	            def facts = data.results
	
	            if (facts == null) throw new Exception ("Could not find any interesting data for the drug " + params.medicine)
	
	            InterestingFacts interestingFacts = new InterestingFacts()
	            interestingFacts.medicine = params.medicine
	            interestingFacts.message = 'Information of interest about the drug'
	
	            if (facts.purpose && facts.purpose[0])interestingFacts.facts['Purpose'] = facts.purpose[0][0]
	            if (facts.stop_use && facts.stop_use[0])interestingFacts.facts['Stop Using if'] = facts.stop_use[0][0]
	            if (facts.storage_and_handling && facts.storage_and_handling[0])interestingFacts.facts['Storage and Handling'] = facts.storage_and_handling[0][0]
	            if (facts.ask_doctor && facts.ask_doctor[0])interestingFacts.facts['What to ask a doctor'] = facts.ask_doctor[0][0]
	            else if (facts.ask_doctor_pharmasist && facts.ask_doctor_pharmacist[0])interestingFacts.facts['What to ask a pharmacist'] = facts.ask_doctor_pharmacist[0][0]
	            if (facts.boxed_warning && facts.boxed_warning[0])interestingFacts.facts['Serious Warnings'] = facts.boxed_warning[0][0]
	            if (facts.warnings_and_precautions && facts.warnings_and_precautions[0])interestingFacts.facts['Warnings and Precautions'] = facts.warnings_and_precautions[0][0]
	            else if (facts.warnings && facts.warnings[0])interestingFacts.facts['Warnings'] = facts.warnings[0][0]
	            else if (facts.precautions && facts.precautions[0])interestingFacts.facts['Precautions'] = facts.precautions[0][0]
	            if (facts.pregnancy_or_breast_feeding && facts.pregnancy_or_breast_feeding[0])interestingFacts.facts['Pregnancy or Breast Feeding'] = facts.pregnancy_or_breast_feeding[0][0]
	            else if (facts.pregnancy && facts.pregnancy[0])interestingFacts.facts['Pregnancy'] = facts.pregnancy[0][0]
	
	            render(view:'interestingFacts', model:[facts:interestingFacts])
			}
        }
        catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def drugNames(){
        try {
            String category = 'drug'
            String subCategory = 'label'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String medicineQuery = "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    'openfda.generic_name:'+medicine+'+openfda.brand_name:'+medicine +
                    '+openfda.pharm_class_epc:'+medicine

			def content = getURLContent(medicineQuery);
			if (content != null) {
	            def data = new JsonSlurper().parseText(content)
	            def facts = data.results
	
	            if (facts == null) throw new Exception ("Could not find any drug names " + params.medicine)
	
	            InterestingFacts interestingFacts = new InterestingFacts()
	            interestingFacts.medicine = params.medicine
	            interestingFacts.message = 'All the known names for the drug '
	
	            if (facts.openfda?.brand_name && facts.openfda?.brand_name[0])interestingFacts.facts['Brand Name(s)'] = facts.openfda?.brand_name[0][0]
	            if (facts.openfda?.generic_name && facts.openfda?.generic_name[0])interestingFacts.facts['Generic Name(s)'] = facts.openfda?.generic_name[0][0]
	            if (facts.openfda?.pharm_class_epc && facts.openfda?.pharm_class_epc[0])interestingFacts.facts['Established pharmacologic class'] = facts.openfda?.pharm_class_epc[0][0]
	
	            render(view:'interestingFacts', model:[facts:interestingFacts])
			}
        }
        catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'an.error.message')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def drugFoodInteractions(){
        try{
            String category = 'drug'
            String subCategory = 'label'
            String medicine = '"' + queryService.replaceSpaceWithPlus(params.medicine) +'"'
            String food = '"' + queryService.replaceSpaceWithPlus(params.food) +'"'

            def jsonSlurper = new JsonSlurper();

            String baseQuery =  "https://api.fda.gov/" +
                    category +
                    '/' +
                    subCategory +
                    '.json?search=' +
                    '(openfda.generic_name:'+medicine+'+openfda.brand_name:'+ medicine +
                    '+openfda.pharm_class_epc:'+medicine

            String drugInteractionQuery =  baseQuery + ')+AND+drug_interactions:' + food

            DrugInteractions drugInteractions = new DrugInteractions()
            drugInteractions.medicine = params.medicine
            drugInteractions.otherDrugOrFood = params.food

            try{
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.drug_interactions[0])
                    drugInteractions.interactions = response?.results?.drug_interactions[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}

            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+adverse_reactions:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.adverse_reactions[0])
                    drugInteractions.adverseReactions = response?.results?.adverse_reactions[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+boxed_warning:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.boxed_warning[0])
                    drugInteractions.boxedWarnings = response?.results?.boxed_warning[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }

            try{
                drugInteractionQuery = baseQuery + ')+AND+user_safety_warnings:' + food
                def response = jsonSlurper.parse(drugInteractionQuery.toURL())
                if (response?.results?.user_safety_warnings[0])
                    drugInteractions.warningAndPrecautions = response?.results?.user_safety_warnings[0][0].replaceAll(~"(?i)(${params.food})"){all, text -> "<mark>${text}</mark>"}
            }
            catch (Exception e){
            }


            render(view:'drugInteractions', model:[drugInteractions: drugInteractions])
        }
        catch(NullPointerException npe){
            log.error npe.message, npe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'required.fields.error')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: npe.message])
        }
        catch (Exception e){
            log.error e.message, e
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue('queryField', 'an.error.message')
            render(view: "index", model: [queryInstance: queryParams, errorMessage: e.message])
        }

    }


    def refreshPatientDeaths(){
        try {
            def data = getPatientDeathData(params.beginDate, params.endDate) as JSON
            if (data == null){
                response.status = 404
                render ([error:"Unable to retrieve data"] as JSON).toString()
            }
            else {
                render data.toString()
            }
        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    def patientDeaths() {
        try {
            def data = getPatientDeathData(params.beginDate, params.endDate)
            if (data == null){
                def errorMessage = "Unable to retrieve data from the server. Please make sure valid data was provided to the server"
                Query queryParams = new Query()
                queryParams.errors.rejectValue('queryField', 'query.error.message', [errorMessage] as Object[], "")
                render view:"index", model:[queryInstance:queryParams]
            }
            else {
                def fillKeysAsJSON = data.fillKeys as JSON
                def patientData = data.queryResults as JSON


                def dataAsJson = [fillKeys    : fillKeysAsJSON.toString(),
                                  queryResults: patientData.toString(),
                                  beginDate   : data.beginDate,
                                  endDate     : data.endDate
                ]
                render view: "map", model: dataAsJson
            }
        } catch (Exception ioe) {
            log.error ioe.message, ioe
            Query queryParams = new Query(params)
            queryParams.errors.rejectValue ('queryField', 'an.error.message')
            render (view: "index", model:[queryInstance: queryParams, errorMessage: ioe.message])
        }
    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'query.label', default: 'Query'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
	
	private String getURLContent(String url) {
		def connection = url.toURL().openConnection()
		if (connection?.responseCode > 400) {
            def errorMessage
            if (connection?.errorStream != null){
			    def data = connection.errorStream.text
			    def parsedData = new JsonSlurper().parseText(data)
			    errorMessage = parsedData?.error?.message
            }
            else{
                errorMessage = 'openFDA service api.fda.gov is temporarily not available. Please try again later. '
            }

			Query queryParams = new Query()
			queryParams.errors.rejectValue('queryField', 'query.error.message', [errorMessage] as Object[], "")
			render view:"index", model:[queryInstance:queryParams]
			return null
		} else {
			return connection?.content?.text
		}
	}


    private def getColorMap(){
        def colors = DataMap.hundredColorMap
        if (colors == null) {
            colors = [:]
            DataMap.colorGradient.eachWithIndex { String entry, int i -> colors.put(i, entry) }
            colors.put('defaultFill', '#A0A0A0')
            colors.put('UNKNOWN', '#8597C5')
            DataMap.hundredColorMap = colors
        }
        colors
    }



    private def getPatientDeathData(def beginDate, def endDate){
        if (!beginDate || beginDate == '') beginDate = 2000
        if (!endDate || endDate == '') endDate = 2016

        if (beginDate.toInteger() >= endDate.toInteger()) beginDate = 2000

        def colors = getColorMap()

        String category = 'drug'
        String subCategory = 'event'
        String deathsQuery = "https://api.fda.gov/" +
                category +
                '/' +
                subCategory +
                '.json?search=' +
                'receivedate:['+beginDate+'0101+TO+'+endDate+'0101]+AND+_exists_:seriousnessdeath&count=occurcountry.exact'


        def content = getURLContent(deathsQuery);
        def rtrn = null
        if (content != null) {
            def data = new JsonSlurper().parseText(content)

            def mapToAnalyzeData = [:]
            def patientDeathData =[:]
            data.results.each{mapToAnalyzeData.put(it.term, it.count)}
            def totalDeaths = 0
            mapToAnalyzeData.each{totalDeaths += it.value}

            mapToAnalyzeData.each{k, v ->
                if (DataMap.countryCodeMapping.containsKey(k)){
                    def percentageOfTotalDeaths = Math.round((v/totalDeaths) * 100)
                    def countryInfo = [fillKey:percentageOfTotalDeaths.toString(), numberOfThings: v]
                    patientDeathData.put(DataMap.countryCodeMapping[k],countryInfo)
                }
            }

            rtrn = [ "fillKeys": colors,
              "queryResults": patientDeathData,
              "beginDate":beginDate, "endDate":endDate
            ]
        }
        rtrn
    }
}
