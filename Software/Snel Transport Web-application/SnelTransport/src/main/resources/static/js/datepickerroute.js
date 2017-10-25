/*<![CDATA[*/
  $( function() {
	  $( ".datepickerroute" ).datepicker({
		  onSelect: function(dateText) {
			  document.getElementById("calcRouteButton").disabled = false;
			  $.get("/routeBepaling/date", {date:dateText}).then( function(response){
				  if (response != ''){
					document.getElementById("right-panel-status").innerHTML = '';
				  	var htmlString = "<div><p>Selecteer vrachtwagen:</p><select name='licencePlate' id='licencePlate' >";
				  	htmlString += ' <option> </option>';
				  	for (i = 0; i < response.length; i++){
				  		htmlString += ' <option value=\'' + response[i] + '\'>' + response[i] + '</option>';
				  	}
				  	htmlString += '</select></div><br>';
				  	document.getElementById("dropdown_menutrucks").innerHTML = htmlString;

				  } else {
					  document.getElementById("right-panel-status").innerHTML = '<p>Nog geen routes bekend voor deze dag</p>';
					  document.getElementById("dropdown_menutrucks").innerHTML = ''; 
					  document.getElementById("directions-panel").innerHTML = ''; 

				  }
					  
			})}});
	  $.datepicker.regional['nl'] = {clearText: 'Effacer', clearStatus: '',
			    closeText: 'sluiten', closeStatus: 'Onveranderd sluiten ',
			    prevText: '<vorige', prevStatus: 'Zie de vorige maand',
			    nextText: 'volgende>', nextStatus: 'Zie de volgende maand',
			    currentText: 'Huidige', currentStatus: 'Bekijk de huidige maand',
			    monthNames: ['januari','februari','maart','april','mei','juni',
			    'juli','augustus','september','oktober','november','december'],
			    monthNamesShort: ['jan','feb','mrt','apr','mei','jun',
			    'jul','aug','sep','okt','nov','dec'],
			    monthStatus: 'Bekijk een andere maand', yearStatus: 'Bekijk nog een jaar',
			    weekHeader: 'Sm', weekStatus: '',
			    dayNames: ['zondag','maandag','dinsdag','woensdag','donderdag','vrijdag','zaterdag'],
			    dayNamesShort: ['zo', 'ma','di','wo','do','vr','za'],
			    dayNamesMin: ['zo', 'ma','di','wo','do','vr','za'],
			    dayStatus: 'Gebruik DD als de eerste dag van de week', dateStatus: 'Kies DD, MM d',
			    dateFormat: 'dd-mm-yy', firstDay: 1, 
			    initStatus: 'Kies een datum', isRTL: false};
			$.datepicker.setDefaults($.datepicker.regional['nl']);
	
			
  } );
  
  function updateRouteAtDate(){
	  var time = document.getElementById('date').value;
	  if (time != ""){
		  document.getElementById("calcRouteButton").disabled = true;
		  var time = 
		  $.get("/calcRoute/date", {date:time}).then( function(response){
		  switch(response){
		  	case "updatedValues":
		  		document.getElementById("right-panel-status").innerHTML = '<p>De routes zijn geupdated</p>';
		  		document.getElementById("directions-panel").innerHTML = '';
		  		break;
		  	case "exceededKeyQuota":
		  		document.getElementById("right-panel-status").innerHTML = '<p>De quota voor aantal routeberekening is overschreden, overweeg een upgrade of probeer morgen weer	</p>';
		  		document.getElementById("directions-panel").innerHTML = '';
		  		break;
		  	case "noOrders":
		  		document.getElementById("right-panel-status").innerHTML = '<p>Er zijn geen bestellingen te leveren voor deze datum</p>';
		  		document.getElementById("directions-panel").innerHTML = '';
		  		break;
		  	default:
		  		document.getElementById("right-panel-status").innerHTML = '<p>' + response + '</p>';
	  			document.getElementById("directions-panel").innerHTML = '';
		  }})
	  } else {
		  document.getElementById("right-panel-status").innerHTML = '<p>Selecteer eerst een datum waarvoor de routes berekend moeten worden</p>';
	  }
	 
  }
  
  function initMap() {
		var directionsService = new google.maps.DirectionsService;
		var directionsDisplay = new google.maps.DirectionsRenderer;
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 9,
			center: {lat: 52.011521, lng: 4.710463}});
  
		directionsDisplay.setMap(map);
		document.getElementById('dropdown_menutrucks').addEventListener('change', function() {
			calculateAndDisplayRoute(directionsService, directionsDisplay)});
  }
 
  
  function calculateAndDisplayRoute(directionsService, directionsDisplay) {
	  
	  var truck = document.getElementById('licencePlate').value;
	  var time = document.getElementById('date').value;
	  if(licencePlate != ""){
		  	$.get("/getRouteByTruckAndDate", {date:time,licencePlate:truck}).then( function(response){
		  		var waypts = [];
		  		var warning = false;
		  		for (var i = 1; i < response.length - 1; i++) {
		  			if 	(warning){
		  				if ((response[i].indexOf('Middelburg') != -1) ||
		  					(response[i].indexOf('Zierikzee') != -1)){
		  					waypts.push({
		  						location:'A58+BredaA73',
		  						stopover: false});
		  				} else if (response[i].indexOf('Enschede') != -1){
		  					waypts.push({
		  						location:'A73+Nijmegen',
		  						stopover: false});
		  				}
		  				warning = false;
		  			} 
		  			if(	(response[i].indexOf('Maastricht') != -1) || 
			  				(response[i].indexOf('Roermond') != -1) || 
			  				(response[i].indexOf('Sittard') != -1)){
			  				warning = true;
			  			}
		  			waypts.push({
		  				location:response[i],
		  				stopover: true});
		  		}	
						
		  		directionsService.route({
					origin:response[0],
					destination: response[response.length - 1],
					waypoints: waypts,
					optimizeWaypoints: true,
					travelMode: 'DRIVING',
					region : 'NL'
				}, function(response, status) {
					if (status === 'OK') {
						directionsDisplay.setDirections(response);
						var route = response.routes[0];
						var summaryPanel = document.getElementById('directions-panel');
						summaryPanel.innerHTML = '';
						
						var sumTime = 0;
						var sumDistance = 0;
						for (var i = 0; i < route.legs.length; i++) {
							sumTime += route.legs[i].duration.value;
							sumDistance += route.legs[i].distance.value;
						}
						var sumWithLoading = sumTime + (route.legs.length - 1)*1800;
						
						
						summaryPanel.innerHTML += '<table class = "tableRB"><tr><td><b>Reistijd</b></td><td><b>'+ 
													Math.floor(sumTime/3600) + ':' + Math.floor(sumTime/60)%60 + ':' + sumTime%60 + 
													'</b></td></tr><tr><td><b>Tijd met laden</b></td><td><b>'+ 
													Math.floor(sumWithLoading/3600) + ':' + Math.floor(sumWithLoading/60)%60 + ':' + sumWithLoading%60+
													'</b></td></tr><tr><td><b>Lengte route</b></td><td><b>'+ 
													Math.round( sumDistance/100) / 10 + ' km</b></td></tr></table><br>';
						for (var i = 0; i < route.legs.length; i++) {
							var routeSegment = i + 1;
							summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment 
							+ ' Reistijd: ' + route.legs[i].duration.text + '</b><br>';
							summaryPanel.innerHTML += 'Van ' + route.legs[i].start_address + ' naar ';
							summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
							summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
						}
					} else {
						window.alert('Directions request failed due to ' + status);
					}
				})
		  });
		}};
             
  
/*]]>*/
  