/*<![CDATA[*/
  $( function() {
	  $( ".datepickerroute" ).datepicker({
		  onSelect: function(dateText) {
			  $.get("/routeBepaling/date", {date:dateText}).then( function(response){
			  	//console.log(response)
				  if (response != ''){
				  	var htmlString = '<div><p>Selecteer vrachtwagen:</p><select name="catagoryList" id="catagoryList" ">';
					//  var htmlString = '<div><p>Selecteer vrachtwagen:</p><select name="catagoryList" id="catagoryList" onselect="selectRoute()">';
				  	for (i = 0; i < response.length; i++){
				  		htmlString += ' <option value="Catagory ' + (i + 1) + '">' + response[i] + '</option>';  
				  	}
				  	htmlString += '</select></div>';
				  	document.getElementById("dropdown_menutrucks").innerHTML = htmlString;
				  	document.getElementById("btnBerekenRoute").innerHTML = '<div><p>Herbereken route></p></div>'+
                    '<button name="btnHerberekenRoute" class="button_1">Herbereken route</button></div>'; 
				  } else {
					  document.getElementById("dropdown_menutrucks").innerHTML = '';
					  document.getElementById("btnBerekenRoute").innerHTML = '';
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
  
  function initMap() {
		var directionsService = new google.maps.DirectionsService;
		var directionsDisplay = new google.maps.DirectionsRenderer;
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 9,
			center: {lat: 52.011521, lng: 4.710463}});
  
		directionsDisplay.setMap(map);
  	
		document.getElementById('submit').addEventListener('click', function() {
    		calculateAndDisplayRoute(directionsService, directionsDisplay)});
}
 
  
  function calculateAndDisplayRoute(directionsService, directionsDisplay) {
		var waypts = [];
		var checkboxArray = document.getElementById('waypoints');
		for (var i = 0; i < checkboxArray.length; i++) {
			if (checkboxArray.options[i].selected) {
				waypts.push({
				location: checkboxArray[i].value,
				stopover: true});
			}
		}

      directionsService.route({
			origin:"Gouda, Nederland",
			destination: "gouda, Nederland",
			waypoints: waypts,
			optimizeWaypoints: true,
			travelMode: 'DRIVING'
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
				
				//summaryPanel.innerHTML +='<b>Reistijd:' + Math.floor(sumTime/3600) + ':' + Math.floor(sumTime/60)%60 + ':' + sumTime%60 + '</b><br>';
				//summaryPanel.innerHTML +='<b>Tijd met laden :' + Math.floor(sumWithLoading/3600) + ':' + Math.floor(sumWithLoading/60)%60 + ':' + sumWithLoading%60+'</b><br>';
				//summaryPanel.innerHTML +='<b>Lengte route   :' + Math.round( sumDistance/100) / 10 + ' km</b><br>';
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
		});
      
  }

  
  /*function selectRoute(){
	  $.get("/routeBepaling/date", {date:dateText}).then( function(response){
	  
	  }
  }*/
  
  
/*]]>*/
  