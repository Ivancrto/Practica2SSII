
<?php
	$con = mysqli_connect('localhost','newuser','URJC2019!','test');
?>


<!DOCTYPE HTML>
<html>
<head>

 <meta charset="utf-8">
 <title>DashBoard</title>
 <style type="text/css">
 

	body {
	 background-color: #1a2326 !important;
	}
	
	.Header { 
	  background-color: #354853;
	  padding: 10px;
	  text-align: center;
	  font-size: 25px;
	  background-color: #354853;
	  border: 5px solid#1a2326;
	}
	
	.Pie { 
	  background-color: #354853;
	  padding: 10px;
	  text-align: center;
	  font-size: 15px;
	  background-color: #354853;
	  border: 5px solid#1a2326;
	}
	
	#Compuestos tr:hover {background-color: #00a279 !important; }
	#Compuestos tr:nth-child(even) {background-color: #354853;}
	#Compuestos tr:nth-child(odd) {background-color: #354853;}
	
	#Compuestos th {
	
	text-shadow: 1px 1px 5px #000;
	}
	
	#Compuestos th, #Compuestos  td {
	  font-size: 13px;
	  text-align: center;
	  color: white;
	  border-bottom: 2.5px solid #00f0b4 !important;
	  border-right: 1px solid #55b8fc !important;
	  border-left: 1px solid #55b8fc !important;
	 
	}
	
	

	#total_uci tr:nth-child(even), #total_1 tr:nth-child(even), #total_2 tr:nth-child(even), #total_3 tr:nth-child(even),  #total_4 tr:nth-child(even) {background-color: #354853;}
	#total_uci tr:nth-child(odd), #total_1 tr:nth-child(odd), #total_2 tr:nth-child(odd), #total_3 tr:nth-child(odd),  #total_4 tr:nth-child(odd) {background-color: #354853;}
	#total_uci td, #total_1 td, #total_2 td, #total_3 td, #total_4 td  { text-align: center; vertical-align: middle; font-size: 15px; border-right:none;}
	
	#total_uci td {text-shadow: 0.5px 0.5px 0 #ff1251; border-bottom:2px solid #ff1251; }
	#total_1 td {text-shadow: 0.5px 0.5px 0 #00f0b4; border-bottom:2px solid #00f0b4;}
	#total_2 td {text-shadow: 0.5px 0.5px 0 #55b8fc; border-bottom:2px solid #55b8fc;}
	#total_3 td {text-shadow: 0.5px 0.5px 0 #ff6812; border-bottom:2px solid #ff6812;}
	#total_4 td {text-shadow: 0.5px 0.5px 0 #ff1251; border-bottom:2px solid #ff1251;}
	
	#total_uci  thead, #total_1 thead, #total_2 thead, #total_3 thead, #total_4 thead  { display:none; }
	#total_uci, #total_1, #total_2, #total_3, #total_4{
		color: white;
		text-align: center;
		margin-left:auto; 
		margin-right:auto;
		font-weight: bold;
		font-size: 10px;
		padding: 2px;
	}
	
	#donut {
			padding: 3px;
		
	}
	
	.texto {
		  text-align: center;
		  color: white;
		  font-weight: bold;
		  font-size: 20px;
		  margin: 20px;
		  text-shadow: 1px 1px 5px #000;
	}
	
	.texto2 {
		  text-align: center;
		  color: white;
		  font-weight: bold;
		  font-size: 15px;
		  margin: 3px;
		  text-shadow: 1px 1px 5px #000;
	}
	
	.texto_wrap {
		  text-align: center;
		  color: white;
		  font-weight: bold;
		  font-size: 20px;
		  margin: 20px;
		 
	}
	
	.t {
		 text-align: center;
		  color: white;
		  font-weight: bold;
		  font-size: 15px;
		  margin: 10px;
	}
	
	.left, .right, .lmiddle, .rmiddle, .lbottom, .rbottom{
	  float: left;
	  width: 50%;
	  padding: 20px;
	  background-color: #354853;
	  border: 5px solid#1a2326;
	  position: relative;
	  height:620px;
	
	}
	

	.lbottom, .rbottom {
		height:820px;
	}
	
	.rowTop, .rowMiddle, .rowBottom{
		margin:0 !important;
		
	}
	

	.rowTop:after, .rowMiddle:after, .rowBottom:after {
	  content: "";
	  display: table;
	  clear: both;
	}
	
	svg g text{
	font-size: 15px;
	}
	
	
	
	
	@media (max-width: 600px) {
	  .left, .right, .lmiddle, .rmiddle, .rbottom, .lbottom {
		width: 100%;
	  }
	}

</style>

 <link rel="stylesheet" type="text/css" href="cmi.css">
 <script type="text/javascript" src="https://www.google.com/jsapi"></script>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 
   <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawSeriesChart);

    function drawSeriesChart() {

      var data = google.visualization.arrayToDataTable([
		
		['ID', 'Edad', 'Tratamiento', 'Estado',  'Cardiopatia'],
			<?php
				$query = "SELECT test.cluster_pacientes.id, 
						ROUND(test.cluster_pacientes.edad, 0) as edad, 
						test.cluster_pacientes.tratamiento, 
						test.cluster_pacientes.tipo, 
						ROUND(test.cluster_pacientes.cardiopatia, 2) as card 
						FROM test.cluster_pacientes
						where test.cluster_pacientes.edad < 100;";
											
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['id']."'
					,
					".$row['edad']."
					,
					".$row['tratamiento']."
					,
					'".$row['tipo']."'
					,
					".$row['card']."
					],";
				}
			?>
		
       
      ]);
	  
	  var dashboard = new google.visualization.Dashboard(
            document.getElementById('wrap_2'));
	  
	   var edadSlider = new google.visualization.ControlWrapper({
          'controlType': 'NumberRangeFilter',
          'containerId': 'filter_2',
          'options': {
			   'titleTextStyle': 'white',
            'filterColumnLabel': 'Edad'
          }
        });
	  

      //var chart = new google.visualization.BubbleChart(document.getElementById('agrupacion'));
      //chart.draw(data, options);
	  
	   var Chart2 = new google.visualization.ChartWrapper({
          'chartType': 'BubbleChart',
          'containerId': 'agrupacion',
		  'options': {colors: ['#00f0b4', '#ff1251', '#55b8fc', '#ffffff', '#f6c7b6'],
		height: 420,
          title : 'Relacion entre edad, tipo de tratamiento y tipo de cardiopatia',
		  titleTextStyle: {
					color: 'white',
					bold: true,
					  fontSize:20
				  },
		  legend: { textStyle: {
					fontSize: 15,
					color: '#ffffff',
					bold: false,
					italic: false
				  }
				  },
          vAxis: { title: 'Tipo de tratamiento',
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
		  },
          hAxis:  {
				  title: 'Edad del paciente',
				  
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
				},	   
						
			backgroundColor: { fill:'transparent' },
			
        bubble: {textStyle: {fontSize: 11}}   
          }
        
        });
		 dashboard.bind(edadSlider, Chart2);
		 dashboard.draw(data);
	  
	  
	  function resizeHandler () {
            dashboard.draw(data);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }
    }
	
	
    </script>

   <script type="text/javascript">
   
      //----------------------- GRAFICO DE BARRAS -------------------------
   // --------- REPRESENTANDO FALLECIDOS E INGRESADOS EN TODO EL PERIODO DE TIEMPO   ------------------
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawVisualization);

      function drawVisualization() {
        // Some raw data (not necessarily accurate)
        var data = google.visualization.arrayToDataTable([
          ['Fecha', 'Ingresados', 'UCI', 'Fallecidos con UCI', 'Fallecidos sin UCI'],
			<?php
				$query = "SELECT test.dimtiempo.id as 'id', 
					test.dimtiempo.fecha as 'Fecha',
					SUM(case when test.tablahechos.uci=0 and  test.tablahechos.fallecido=0 then 1 else 0 end) as 'Ingresados',
					SUM(case when test.tablahechos.UCI=1 and  test.tablahechos.fallecido=0 then 1 else 0 end) as 'UCI',
					SUM(case when test.tablahechos.fallecido=1 and  test.tablahechos.UCI=1  then 1 else 0 end) as 'Fallecidos con UCI',
                    SUM(case when test.tablahechos.fallecido=1 and  test.tablahechos.UCI=0  then 1 else 0 end) as 'Fallecidos sin UCI'
					FROM test.dimtiempo join test.tablahechos 
					on test.dimtiempo.id = test.tablahechos.fecha_ingreso_id
					group by test.dimtiempo.mes
					order by test.dimtiempo.id;";
					
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Fecha']."'
					,
					".$row['Ingresados']."
					,
					".$row['UCI']."
					,
					".$row['Fallecidos con UCI']."
					,
					".$row['Fallecidos sin UCI']."
					],";
				}
			?>
        ]);
		
		
		var dashboard = new google.visualization.Dashboard(
            document.getElementById('wrap_3'));
	  
	   var hospital = new google.visualization.ControlWrapper({
          'controlType': 'CategoryFilter',
          'containerId': 'filter_3',
          'options': {
			  
            'filterColumnLabel': 'Fecha'
          }
        });
	  


	   var Chart3 = new google.visualization.ChartWrapper({
          'chartType': 'ComboChart',
          'containerId': 'barras_div',
		  'options': { colors: ['#00f0b4', '#55b8fc', '#ff1251', '#ff6812', '#f6c7b6'],
		 height: 420,
          title : 'Fallecidos, ingresados y pacientes en UCI por FECHA',
		  legend: { textStyle: {
					fontSize: 15,
					color: '#ffffff',
					bold: false,
					italic: false
				  }
				  },
          vAxis: { title: 'Pacientes',
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
		  },
          hAxis:  {
				  title: 'Fecha',
				  format: 'dd:mm:yy',
				  viewWindow: {
					min: [7, 30, 0],
					max: [17, 30, 0]
				  },
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
				},	   
		  seriesType: 'bars',
		  backgroundColor: { fill:'transparent' },
		  titleTextStyle: {
					color: 'white',
					bold: true,
					  fontSize:20
				  },
          series: {
			  5: {type: 'line'}
		  }   
		  }
        
        });
		 dashboard.bind(hospital, Chart3);
		 dashboard.draw(data);

				
      //  var chart = new google.visualization.ComboChart(document.getElementById('barras_div'));
      //  chart.draw(data, options);
      
	  function resizeHandler () {
            dashboard.draw(data);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }
	  
	  
	  }
	  
	  
	  
    </script>
 
   <script type="text/javascript">
   //----------------------- GRAFICO DE LINEAS -------------------------
   // --------- REPRESENTANDO FALLECIDOS E INGRESADOS EN TODO EL PERIODO DE TIEMPO   ------------------
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Mes', 'Ingresados', 'UCI', 'Fallecidos'],
			<?php
				$query = "SELECT test.dimtiempo.id as 'id', 
					(case when test.dimtiempo.mes=1 then 'Enero' 
						when test.dimtiempo.mes=2 then 'Febrero' 
						when test.dimtiempo.mes=3 then 'Marzo' 
						when test.dimtiempo.mes=4 then 'Abril' 
						when test.dimtiempo.mes=5 then 'Mayo'
						when test.dimtiempo.mes=6 then 'Junio' 
						when test.dimtiempo.mes=7 then 'Julio' 
						when test.dimtiempo.mes=8 then 'Agosto' 
						when test.dimtiempo.mes=9 then 'Septiembre' 
						when test.dimtiempo.mes=10 then 'Octubre' 
						when test.dimtiempo.mes=11 then 'Noviembre'
						when test.dimtiempo.mes=12 then 'Diciembre' 
						end) as 'Mes',
					COUNT(test.tablahechos.id) as 'Ingresados',
					SUM(case when test.tablahechos.UCI=1 then 1 else 0 end) as 'UCI',
					SUM(case when test.tablahechos.fallecido=1 then 1 else 0 end) as 'Fallecidos'
					FROM test.dimtiempo join test.tablahechos 
					on test.dimtiempo.id = test.tablahechos.fecha_ingreso_id
					group by test.dimtiempo.mes
					order by test.dimtiempo.id;";
					
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Mes']."'
					,
					".$row['Ingresados']."
					,
					".$row['UCI']."
					,
					".$row['Fallecidos']."
					],";
				}
			?>
        ]);

   
        //var chart = new google.visualization.AreaChart(document.getElementById('curve_chart'));

        //chart.draw(data, options);
		
		var dashboard = new google.visualization.Dashboard(
            document.getElementById('wrap_5'));
	  
	   var hospital = new google.visualization.ControlWrapper({
          'controlType': 'CategoryFilter',
          'containerId': 'filter_5',
          'options': {
			  
            'filterColumnLabel': 'Mes'
          }
        });
	  


	   var Chart3 = new google.visualization.ChartWrapper({
          'chartType': 'AreaChart',
          'containerId': 'curve_chart',
		  'options': { 
			colors: ['#00f0b4', '#55b8fc', '#ff1251', '#ffffff', '#f6c7b6'],
		height: 420,
		
          title : 'SUMA de Fallecidos, ingresados y pacientes en UCI',
		  titleTextStyle: {
					color: 'white',
					bold: true,
					  fontSize:20
				  },
		  legend: { textStyle: {
					fontSize: 15,
					color: '#ffffff',
					bold: false,
					italic: false
				  }
				  },
          vAxis: { title: 'Pacientes',
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
		  },
          hAxis:  {
				  title: 'Mes',
				  format: 'dd:mm:yy',
				  viewWindow: {
					min: [7, 30, 0],
					max: [17, 30, 0]
				  },
				  textStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: false,
					italic: false
				  },
				  titleTextStyle: {
					fontSize: 18,
					color: '#ffffff',
					bold: true,
					italic: false
				  }
				},	   
						
			backgroundColor: { fill:'transparent' },
			
			
			trendlines: {
			  0: {
				type: 'polynomial',
				color: '#333',
				opacity: 1	
			  },
			  1: {
				type: 'polynomial',
				color: '#111',
				opacity: .3
			  }
			}
		  }
        });
		 dashboard.bind(hospital, Chart3);
		 dashboard.draw(data);

				
      //  var chart = new google.visualization.ComboChart(document.getElementById('barras_div'));
      //  chart.draw(data, options);
      
	  function resizeHandler () {
            dashboard.draw(data);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }
	  
	  
	  
	  }
    </script>
 

<script type="text/javascript">
     //-------------PORCENTAJE UCI- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

       function drawTable() {
		  
        var data = new google.visualization.DataTable();
     
        data.addColumn('number', 'Total Ingresados en UCI');
	
        data.addRows([
          	<?php
				$query = "SELECT ROUND(SUM(case when test.tablahechos.UCI=1 then 1 else 0 end) * 100.0 / SUM(case when test.tablahechos.UCI=0 then 1 else 1 end), 2) as 'UCI' FROM test.tablahechos;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					".$row['UCI']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('total_uci'));

        table.draw(data, {showRowNumber: false, width: '50%', height: '50%'});
      }
    </script>
	
	<script type="text/javascript">
     //-------------TOTAL UCI- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

       function drawTable() {
		  
        var data = new google.visualization.DataTable();
     
        data.addColumn('number', 'Total Ingresados en UCI');
	
        data.addRows([
          	<?php
				$query = "SELECT COUNT(test.tablahechos.fallecido) as 'UCI' FROM test.tablahechos WHERE test.tablahechos.uci=1 and test.tablahechos.fallecido=0;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					".$row['UCI']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('total_2'));

        table.draw(data, {showRowNumber: false, width: '50%', height: '50%'});
      }
    </script>

<script type="text/javascript">
     //-------------TOTAL ESTABLES- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

       function drawTable() {
		  
        var data = new google.visualization.DataTable();
     
        data.addColumn('number', 'Total Estables');
	
        data.addRows([
          	<?php
				$query = "SELECT COUNT(test.tablahechos.fallecido) as 'Estables' FROM test.tablahechos WHERE test.tablahechos.uci=0 and test.tablahechos.fallecido=0;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					".$row['Estables']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('total_1'));

        table.draw(data, {showRowNumber: false, width: '50%', height: '50%'});
      }
    </script>

<script type="text/javascript">
     //-------------TOTAL RIP CON UCI- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

       function drawTable() {
		  
        var data = new google.visualization.DataTable();
     
        data.addColumn('number', 'Total RIP');
	
        data.addRows([
          	<?php
				$query = "SELECT COUNT(test.tablahechos.fallecido) as 'RIP' FROM test.tablahechos WHERE test.tablahechos.uci=1 and test.tablahechos.fallecido=1;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					".$row['RIP']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('total_4'));

        table.draw(data, {showRowNumber: false, width: '50%', height: '50%'});
      }
    </script>
	
	
<script type="text/javascript">
     //-------------TOTAL RIP SIN UCI- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

       function drawTable() {
		  
        var data = new google.visualization.DataTable();
     
        data.addColumn('number', 'Total RIP');
	
        data.addRows([
          	<?php
				$query = "SELECT COUNT(test.tablahechos.fallecido) as 'RIP' FROM test.tablahechos WHERE test.tablahechos.uci=0 and test.tablahechos.fallecido=1;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					".$row['RIP']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('total_3'));

        table.draw(data, {showRowNumber: false, width: '50%', height: '50%'});
      }
    </script>

 
   <script type="text/javascript">
  //-------------GRAFICO DE AGUJAS-------------------
 //-----------1 GRAFICO POR CADA HOSPITAL (4) REPRESENTANDO LA CANTIDAD DE INGRESADOS EN UCI -----------
 
      
	  google.charts.load('current', {'packages':['gauge']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['Hospital', 'UCI'],
			<?php
				$query = "SELECT test.dimhospital.nombre as 'Hospital', 
						COUNT(test.tablahechos.uci) as 'UCI' 
						FROM test.dimhospital join test.tablahechos 
						on test.dimhospital.id = test.tablahechos.hospital_id
						where test.tablahechos.uci = 1
						group by test.dimhospital.nombre;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "['".$row['Hospital']."',".$row['UCI']."],";
				}
			?>
        ]);

	
       // var chart = new google.visualization.Gauge(document.getElementById('gauge_div'));
		// chart.draw(data, options);
		
		
		var dashboard = new google.visualization.Dashboard(
          document.getElementById('wrap_1'));
	  
	   var hospital = new google.visualization.ControlWrapper({
          'controlType': 'CategoryFilter',
          'containerId': 'filter_1',
          'options': {
			  
            'filterColumnLabel': 'Hospital'
          }
        });
	  


	   var Chart1 = new google.visualization.ChartWrapper({
          'chartType': 'Gauge',
          'containerId': 'gauge_div',
		  'options': { height: 220,
		  min: 0, max:55,
          redFrom: 35, redTo: 55,
		  yellowFrom:15, yellowTo:35,
		  redColor: "rgb(252, 61, 107)",
		  yellowColor: "rgb(85, 184, 252)",
		  greenColor: "rgb(103, 253, 236)",
          greenFrom:0, greenTo: 15,
          minorTicks: 31
		  
		  }
        
        });
		 dashboard.bind(hospital, Chart1);
		 dashboard.draw(data);

		
		function resizeHandler () {
            dashboard.draw(data);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }

      }
    </script>

 <script type="text/javascript">
     //-------------GRAFICO DE TABLA 1- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
		  
		  
        var data = new google.visualization.DataTable();

        data.addColumn('string', 'C1');
		data.addColumn('string', 'C2');
		data.addColumn('string', 'C3');
		data.addColumn('string', 'C4');
		data.addColumn('string', 'C5');
		data.addColumn('string', 'C6');
		data.addColumn('string', 'C7');
		data.addColumn('string', 'C8');
		data.addColumn('string', 'C9');
		data.addColumn('string', 'C10');
		data.addColumn('string', 'C11');
		data.addColumn('string', 'C12');
		data.addColumn('string', 'C13');
		data.addColumn('string', 'C14');
		data.addColumn('string', 'C15');
		data.addColumn('string', 'C16');
		data.addColumn('string', 'C17');
		data.addColumn('string', 'C18');
		data.addColumn('string', 'C19');
		data.addColumn('string', 'C20');
		data.addColumn('number', 'Confianza');
		data.addColumn('boolean', 'Exito');
		data.addColumn('number', 'Lift');
		
        data.addRows([
          	<?php
				$query = "SELECT  
				(case when test.asociacion_compuesto.c1='NO' then ' ' else (case when test.asociacion_compuesto.c1='C' then 'C' else 'A' end )end) as 'c1',
				(case when test.asociacion_compuesto.c2='NO' then ' ' else (case when test.asociacion_compuesto.c2='C' then 'C' else 'A' end )end) as 'c2',
				(case when test.asociacion_compuesto.c3='NO' then ' ' else (case when test.asociacion_compuesto.c3='C' then 'C' else 'A' end )end) as 'c3',
                (case when test.asociacion_compuesto.c4='NO' then ' ' else (case when test.asociacion_compuesto.c4='C' then 'C' else 'A' end )end) as 'c4',
				(case when test.asociacion_compuesto.c5='NO' then ' ' else (case when test.asociacion_compuesto.c5='C' then 'C' else 'A' end )end) as 'c5',
				(case when test.asociacion_compuesto.c6='NO' then ' ' else (case when test.asociacion_compuesto.c6='C' then 'C' else 'A' end )end) as 'c6',
				(case when test.asociacion_compuesto.c7='NO' then ' ' else (case when test.asociacion_compuesto.c7='C' then 'C' else 'A' end )end) as 'c7',
				(case when test.asociacion_compuesto.c8='NO' then ' ' else (case when test.asociacion_compuesto.c8='C' then 'C' else 'A' end )end) as 'c8',
				(case when test.asociacion_compuesto.c9='NO' then ' ' else (case when test.asociacion_compuesto.c9='C' then 'C' else 'A' end )end) as 'c9',
                (case when test.asociacion_compuesto.c10='NO' then ' ' else (case when test.asociacion_compuesto.c10='C' then 'C' else 'A' end )end) as 'c10',
				(case when test.asociacion_compuesto.c11='NO' then ' ' else (case when test.asociacion_compuesto.c11='C' then 'C' else 'A' end )end) as 'c11',
				(case when test.asociacion_compuesto.c12='NO' then ' ' else (case when test.asociacion_compuesto.c12='C' then 'C' else 'A' end )end) as 'c12',
				(case when test.asociacion_compuesto.c13='NO' then ' ' else (case when test.asociacion_compuesto.c13='C' then 'C' else 'A' end )end) as 'c13',
				(case when test.asociacion_compuesto.c14='NO' then ' ' else (case when test.asociacion_compuesto.c14='C' then 'C' else 'A' end )end) as 'c14',
				(case when test.asociacion_compuesto.c15='NO' then ' ' else (case when test.asociacion_compuesto.c15='C' then 'C' else 'A' end )end) as 'c15',
                (case when test.asociacion_compuesto.c16='NO' then ' ' else (case when test.asociacion_compuesto.c16='C' then 'C' else 'A' end )end) as 'c16',
				(case when test.asociacion_compuesto.c17='NO' then ' ' else (case when test.asociacion_compuesto.c17='C' then 'C' else 'A' end )end) as 'c17',
				(case when test.asociacion_compuesto.c18='NO' then ' ' else (case when test.asociacion_compuesto.c18='C' then 'C' else 'A' end )end) as 'c18',
                (case when test.asociacion_compuesto.c19='NO' then ' ' else (case when test.asociacion_compuesto.c19='C' then 'C' else 'A' end )end) as 'c19',
                (case when test.asociacion_compuesto.c20='NO' then ' ' else (case when test.asociacion_compuesto.c20='C' then 'C' else 'A' end )end) as 'c20',
				(case when test.asociacion_compuesto.conf>=0.99 then 0 else ROUND(test.asociacion_compuesto.conf, 2) end) as 'CONF',
				(case when test.asociacion_compuesto.es_exito=1 then 'true' else 'false' end) as 'exito',
				test.asociacion_compuesto.lift as 'Lift'
				FROM test.asociacion_compuesto;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					
					'".$row['c1']."'
					,
					'".$row['c2']."'
					,
					'".$row['c3']."'
					,
					'".$row['c4']."'
					,
					'".$row['c5']."'
					,
					'".$row['c6']."'
					,
					'".$row['c7']."'
					,
					'".$row['c8']."'
					,
					'".$row['c9']."'
					,
					'".$row['c10']."'
					,
					'".$row['c11']."'
					,
					'".$row['c12']."'
					,
					'".$row['c13']."'
					,
					'".$row['c14']."'
					,
					'".$row['c15']."'
					,
					'".$row['c16']."'
					,
					'".$row['c17']."'
					,
				    '".$row['c18']."'
					,
					'".$row['c19']."'
					,
					'".$row['c20']."'
					,
					".$row['CONF']."
					,
					".$row['exito']."
					,
					".$row['Lift']."
				
					],";
				}
			?>
        ]);
	
        //var table = new google.visualization.Table(document.getElementById('Compuestos'));

        //table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
		
		
		var dashboard = new google.visualization.Dashboard(
          document.getElementById('wrap_4'));
	  
	   var hospital = new google.visualization.ControlWrapper({
          'controlType': 'CategoryFilter',
          'containerId': 'filter_4',
          'options': {
			  
            'filterColumnLabel': 'Lift'
          }
        });
	  


	   var Chart1 = new google.visualization.ChartWrapper({
          'chartType': 'Table',
          'containerId': 'Compuestos',
		  'options': { showRowNumber: true, 
		  width: '100%', 
		  height: '100%'
		  
		  }
        
        });
		 dashboard.bind(hospital, Chart1);
		 dashboard.draw(data);

		
		function resizeHandler () {
            dashboard.draw(data);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }
		
      }
</script>

<script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
       var data = google.visualization.arrayToDataTable([
          ['Estado', 'Totales'],
			<?php
				$query = "SELECT 
				'UCI' as 'Estado',
				COUNT(test.tablahechos.uci) as 'Total' FROM test.tablahechos WHERE test.tablahechos.uci=1 and test.tablahechos.fallecido=0 
				UNION
				SELECT 
				'Fallecido sin UCI' as 'Estado',
				COUNT(test.tablahechos.fallecido) FROM test.tablahechos WHERE test.tablahechos.uci=0 and test.tablahechos.fallecido=1
				UNION
				SELECT 
				'Fallecido con UCI' as 'Estado',
				COUNT(test.tablahechos.fallecido) FROM test.tablahechos WHERE test.tablahechos.uci=1 and test.tablahechos.fallecido=1
				UNION
				SELECT 
				'Estable' as 'Estado',
				COUNT(test.tablahechos.fallecido) FROM test.tablahechos WHERE test.tablahechos.uci=0 and test.tablahechos.fallecido=0;";
					
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Estado']."'
					,
					".$row['Total']."
					],";
				}
			?>
        ]);

        var options = {
          
          colors: ['#55b8fc', '#ff6812', '#ff1251', '#00f0b4', '#f6c7b6'],
		 height: 320,
          
		  legend: { textStyle: {
					fontSize: 15,
					color: '#ffffff',
					bold: false,
					italic: false
				  }
				  },
     
		  backgroundColor: { fill:'transparent' },
		  titleTextStyle: {
					color: 'white',
					bold: true,
					  fontSize:20
				  }   
        };

        var chart = new google.visualization.PieChart(document.getElementById('donut'));
        chart.draw(data, options);
		
		function resizeHandler () {
            chart.draw(data, options);
        }
        if (window.addEventListener) {
            window.addEventListener('resize', resizeHandler, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onresize', resizeHandler);
        }
		
      }
    </script>

 

   <script type="text/javascript">
     
      google.charts.load('current', {'packages':['corechart', 'controls']});

      google.charts.setOnLoadCallback(drawDashboard);

     
    </script>
 
</head>

<body>
	<!-- ENCABEZADO -->
	<div class="Header">

		<div class ="texto"> Panel informativo 4.3 </div>

	</div>
	
	<!-------------------------------------------------------------------------------------------------->
	<!-------------- TOP ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->

	
	<div class="rowTop">
	
	  	<!-- LEFT TOP -->
		<div class="left">
		
		<div class ="texto"> Porcentaje total de Ingresados en UCI </div>
		<div id="total_uci"  class="chart"> </div>
		
			<div id="wrap_1" >
				<!-- GRAFICO DE AGUJAS DIV -->
				<div class ="texto"> Ingresados en UCI por Hospital </div>
				
				<div id="gauge_div" class="chart"></div>
				<div  class ="texto_wrap" id="filter_1"></div>
			</div>
			
		</div>
	  	<!-- RIGHT TOP -->
		<div class="right">
			<div id="wrap_2">
				<div class ="texto"> Gráfico agrupamiento 4.6 pacientes protipo</div>
				<div id="agrupacion" class="chart"></div>
				<div  class ="texto_wrap"  id="filter_2"></div>
			</div>
		</div>
	</div>


	<!-------------------------------------------------------------------------------------------------->
	<!-------------- MIDDLE ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->
	
	
	
	<div class="rowMiddle">
	  	<!-- LEFT MIDDLE -->
		<div class="lmiddle">
			<div id="wrap_3">
			<!-- GRAFICO DE COMB DIV -->
				<div class ="texto"> Gráfico temporal de barras </div>
				<div id="barras_div" class="chart"></div>
				<div  class ="texto_wrap" id="filter_3"></div>
			</div>
		</div>

	  	<!-- RIGHT MIDDLE -->
		<div class="rmiddle">
			<div class ="texto"> Totales </div>
			<div class ="texto2"> Pacientes Estables </div>
			<div id="total_1"  class="chart"> </div>
			<div class ="texto2"> Pacientes en UCI </div>
			<div id="total_2"  class="chart"> </div>
			<div class ="texto2"> Pacientes Fallecidos sin UCI </div>
			<div id="total_3"  class="chart"> </div>
			<div class ="texto2"> Pacientes Fallecidos con UCI</div>
			<div id="total_4"  class="chart"> </div>
			<div class="rmiddler">
			<div id="donut"  class="chart"> </div>
			</div>
			
		</div>
		
		
	</div>
	
	
	<!-------------------------------------------------------------------------------------------------->
	<!-------------- BOTTOM ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->
	
	<div class="rowBottom">
	
	
				<!--LEFT BOTTOM -->
				<div class="lbottom">
				<div class ="texto"> Gráfico temporal de área Junio-19 - Febrero-20 </div>
					<div id="wrap_5" >
					<!-- GRAFICO DE LINEAS DIV -->
					<div id="curve_chart" class="chart"></div>
					<div  class ="texto_wrap" id="filter_5"></div>
					
					</div>
				</div>
				
				<!--RIGHT BOTTOM -->
				<div class="rbottom">
				<div class ="texto"> Compuestos aplicados 4.5 </div>	
					<div id="wrap_4" >
					<!-- GRAFICO DE TABLA -->
						<div  class ="texto_wrap" id="filter_4"></div>
						
						<div id="Compuestos" class="chart"></div>
						<div   class="t">Los compuestos A van acompañados con el compuesto C</div>
						<div  	class="t">Antecedente -> Consecuente</div>
						
					</div>
					
					
				</div>

	</div>
	
	<div class="Pie">

		<div class ="texto"> Lara Camila Sánchez Correa - Iván Conde Carretero - GII </div>

	</div>
	
 
</body>
</html>