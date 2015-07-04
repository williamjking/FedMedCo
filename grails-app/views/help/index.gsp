<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
	<h1 class="text-center">User Guide</h1>

	<p class="help-block text-justify">For details about the openFDA data set and query syntax, please visit <a title="Help" class="help" href="https://open.fda.gov" target="_blank">https://open.fda.gov</a> </p>

	<p class="help-block text-justify">There are six query types that can be run, selected by clicking on the desired query type. Below is a description of each with screen shots. </p>

	<h2 class="help-heading col-md-12 text-info">Find out patient deaths, by country, between two reporting years, related to adverse reaction from a drug.</h2>

	<p class="help-block col-md-12 text-justify">Input: Starting year and ending year. Select the starting year and the ending year from the selection list</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query0-input.png" alt="Select starting and ending year" class="img-responsive center-block img-thumbnail"/>
	</div>


	<p class="help-block col-md-12 text-justify">Output: A color coded world map that shows reported deaths by a country when the mouse is hovered over the country.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query0-output1.png" alt="A color coded world map" class="img-responsive center-block img-thumbnail"/>
	</div>

	<p class="help-block col-md-12 text-justify">The starting/ending years may be changed on the map itself to see the data reflected in real time.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query0-output2.png" alt="A color coded world map" class="img-responsive center-block img-thumbnail"/>
	</div>

	<h2 class="help-heading col-md-12 text-info">Find out all names for your medication.</h2>

	<p class="help-block col-md-12 text-justify">Input: A medicine name.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query1-input.png" alt="All names for a medicine input" class="img-responsive center-block img-thumbnail"/>
	</div>


	<p class="help-block col-md-12 text-justify">Output: other names for that medication.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query1-output.png" alt="All Medicine Names" class="img-responsive center-block img-thumbnail"/>
	 </div>

	<h2 class="help-heading col-md-12 text-info">Find out reported reactions to a drug.</h2>
	<p class="help-block col-md-12 text-justify">Input: A medicine name.
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query2-input.png" alt="All Medicine Names" class="img-responsive center-block img-thumbnail"/>
	</div>

	<p class="help-block col-md-12 text-justify">Output: The top three reactions to the drug, as well as all reactions broken down by severity.
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query2-output1.png" alt="All Medicine Names" class="img-responsive center-block img-thumbnail"/>
	</div>
	<p class="help-block col-md-12 text-justify">Click on each severity to expand that section.
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query2-output2.png" alt="All Medicine Names" class="img-responsive center-block img-thumbnail"/>
	</div>


	<h2 class="help-heading col-md-12 text-info">Find out interesting facts about your medication.</h2>

	<p class="help-block col-md-12 text-justify">Input: A medicine name.
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query3-input.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>
	<p class="help-block col-md-12 text-justify">Output: Interesting facts about the medication. </p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query3-output.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>

	<h2 class="help-heading col-md-12 text-info">Find out what food or drugs to be cautious about while taking your medication</h2>

	<p class="help-block col-md-12 text-justify">Input: A medicine name, and another medicine name or food.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query4-input.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>

	<p class="help-block col-md-12 text-justify">Output: Possible interactions (if any) between the given inputs.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query4-output.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>

	<h2 class="help-heading col-md-12 text-info">Perform generic search on openFDA</h2>

	<p class="help-block col-md-12 text-justify">Input: Select the Category, Subcategory, and fields from the drop downs. Enter the values you wish to search for in the query. A compound query can be created by clicking the "Add Another Field" button to the right.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query5-input.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>

	<p class="help-block col-md-12 text-justify">Output: The data returned from the query.</p>
	<div class="col-md-8 col-md-offset-2">
		<asset:image src="help/query5-output.png" alt="Interesting Facts" class="img-responsive center-block img-thumbnail"/>
	</div>
	</body>
</html>
