/*<![CDATA[*/
  $( function() {
	  $( ".datepickerroute" ).datepicker({
		  onSelect: function(dateText) {
			  $.get("/routeBepaling/date", {date:dateText}).then( function(response){
				  console.log(response)
				  if (response != ''){
				  	var htmlString = '<div><p>Selecteer vrachtwagen:</p><select name="catagoryList" id="catagoryList">';
				  	for (i = 0; i < response.length; i++){
				  		htmlString += ' <option value="Catagory ' + (i + 1) + '">' + response[i] + '</option>';  
				  	}
				  	htmlString += '</select></div>';
				  	document.getElementById("dropdown_menutrucks").innerHTML = htmlString;
				  	document.getElementById("btnBerekenRoute").innerHTML = '<div><p>Herbereken route></p></div>'; 
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
/*]]>*/
  