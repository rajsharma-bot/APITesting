Feature: Validating Place API's 

@AddPlace @Regression
Scenario Outline: Verify if Place is being Add successfully added Using Add Api 
	Given Add Place Payload with "<name>" "<language>" "<address>" 
	When User calls "AddPlaceAPI" with "POST" http request 
	Then then API call is success with status code 200 
	And  "status" in response body is "OK" 
	And  "scope" in response body is "APP" 
	And verify place_id created maps to "<name>" using "getPlaceAPI" 
	
	Examples: 
	
		|name |language |address |
		|Raj |French |Pune |
		|Deepali |English |vikhroli parksite|
		|Dummy |Test |Test |

@DeletePlace @Regression
Scenario: Verify if Delete place functionality is working
Given DeletePlace payload
When User calls "deletePlaceAPI" with "POST" http request
Then then API call is success with status code 200 
And  "status" in response body is "OK" 