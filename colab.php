
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
	
	.texto {
		  text-align: center;
		  color: white;
		  font-weight: bold;
		  font-size: 20px;
		  margin: 20px;
		  
	text-shadow: 1px 1px 5px #000;
	}
	
	.left, .right, .lmiddle, .rmiddle{
	  float: left;
	  width: 50%;
	  padding: 20px;
	  background-color: #354853;
	  border: 5px solid#1a2326;
	  position: relative;
	  height:320px;
	
	}
	
	.rowTop, .rowMiddle{
		margin:0 !important;
		
	}

	.rowTop:after, .rowMiddle:after {
	  content: "";
	  display: table;
	  clear: both;
	}



	table {
		
		 
	}

	#tabla_1 tr:hover {background-color: #00a279 !important; }
	#tabla_2 tr:hover {background-color: #a0d7fd !important; }
	#tabla_3 tr:hover {background-color: #ff995f !important; }
	#tabla_4 tr:hover {background-color: #ff5f89 !important; }
	tr:nth-child(even) {background-color: #354853;}
	tr:nth-child(odd) {background-color: #354853;}
	
	th {
	
	text-shadow: 1px 1px 5px #000;
	}
	
	th, td {
	
	  padding: 15px;
	  text-align: center;
	  color: white;
	  font-size: 20px;
	  margin: 20px;
	  border-bottom: 2px solid #00f0b4 !important;
	  border-right: 1px solid #354853 !important;
	 
	}
		
	
	#tabla_1 th, #tabla_1  td {
	  font-size: 18px;
	  text-align: center;
	  color: white;
	  border-bottom: 2.5px solid #00f0b4 !important;
	 
	}
	
	#tabla_2 th, #tabla_2  td {
	  font-size: 18px;
	  text-align: center;
	  color: white;
	  border-bottom: 2.5px solid #55b8fc !important;
	 
	}
	
	#tabla_3 th, #tabla_3  td {
	  font-size: 18px;
	  text-align: center;
	  color: white;
	  border-bottom: 2.5px solid #ff6812 !important;
	 
	}
	
	#tabla_4 th, #tabla_4  td {
	  font-size: 18px;
	  text-align: center;
	  color: white;
	  border-bottom: 2.5px solid #ff1251 !important;
	 
	}
	
	
	.Pie { 
	  background-color: #354853;
	  padding: 10px;
	  text-align: center;
	  font-size: 15px;
	  background-color: #354853;
	  border: 5px solid#1a2326;
	}
	
	
	
	@media (max-width: 600px) {
	  .left, .right, .lmiddle, .rmiddle {
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
     //-------------GRAFICO DE TABLA 1- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
		  
		  
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Hospital');
        data.addColumn('number', 'ID-Compuesto');
		data.addColumn('number', 'Pacientes recomendados');
        data.addRows([
          	<?php
				$query = "SELECT test.dimhospital.nombre as 'Hospital', 
						test.hospital_compuesto.id_compuesto as 'ID-Compuesto',
                        test.hospital_compuesto.personas_recomendadas as 'Personas'
						FROM test.dimhospital join test.hospital_compuesto
						on test.dimhospital.id = test.hospital_compuesto.id_hosptila
                        where test.dimhospital.id = 1;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Hospital']."'
					,
					".$row['ID-Compuesto']."
					,
					".$row['Personas']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('tabla_1'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%'});
      }
    </script>



   <script type="text/javascript">
     //-------------GRAFICO DE TABLA 2- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
		  
		  
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Hospital');
        data.addColumn('number', 'ID-Compuesto');
		data.addColumn('number', 'Pacientes recomendados');
        data.addRows([
          	<?php
				$query = "SELECT test.dimhospital.nombre as 'Hospital', 
						test.hospital_compuesto.id_compuesto as 'ID-Compuesto',
                        test.hospital_compuesto.personas_recomendadas as 'Personas'
						FROM test.dimhospital join test.hospital_compuesto
						on test.dimhospital.id = test.hospital_compuesto.id_hosptila
                        where test.dimhospital.id = 2;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Hospital']."'
					,
					".$row['ID-Compuesto']."
					,
					".$row['Personas']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('tabla_2'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%'});
      }
    </script>


 <script type="text/javascript">
     //-------------GRAFICO DE TABLA 3- ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
		  
		  
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Hospital');
        data.addColumn('number', 'ID-Compuesto');
		data.addColumn('number', 'Pacientes recomendados');
        data.addRows([
          	<?php
				$query = "SELECT test.dimhospital.nombre as 'Hospital', 
						test.hospital_compuesto.id_compuesto as 'ID-Compuesto',
                        test.hospital_compuesto.personas_recomendadas as 'Personas'
						FROM test.dimhospital join test.hospital_compuesto
						on test.dimhospital.id = test.hospital_compuesto.id_hosptila
                        where test.dimhospital.id = 3;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Hospital']."'
					,
					".$row['ID-Compuesto']."
					,
					".$row['Personas']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('tabla_3'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%'});
      }
    </script>



   <script type="text/javascript">
     //-------------GRAFICO DE TABLA 4 ------------------
 
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
		  
		  
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Hospital');
        data.addColumn('number', 'ID-Compuesto');
		data.addColumn('number', 'Pacientes recomendados');
        data.addRows([
          	<?php
				$query = "SELECT test.dimhospital.nombre as 'Hospital', 
						test.hospital_compuesto.id_compuesto as 'ID-Compuesto',
                        test.hospital_compuesto.personas_recomendadas as 'Personas'
						FROM test.dimhospital join test.hospital_compuesto
						on test.dimhospital.id = test.hospital_compuesto.id_hosptila
                        where test.dimhospital.id = 4;";
				$exec = mysqli_query($con,$query);
				while($row = mysqli_fetch_array($exec)){
					echo "[
					'".$row['Hospital']."'
					,
					".$row['ID-Compuesto']."
					,
					".$row['Personas']."
				
					],";
				}
			?>
        ]);
	
        var table = new google.visualization.Table(document.getElementById('tabla_4'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%'});
      }
    </script>

</head>

<body>
	<!-- ENCABEZADO -->
	<div class="Header">

		<div class ="texto"> Panel informativo 4.4 </div>

	</div>
	
	<!-------------------------------------------------------------------------------------------------->
	<!-------------- TOP ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->

	
	<div class="rowTop">
	
	  	<!-- LEFT TOP -->
		<div class="left">
			
			
			<div class ="texto">  Hospital General</div>
			<div id="tabla_1" class="chart"></div>
			
		</div>
		
	  	<!-- RIGHT TOP -->
		<div class="right">
			<div class ="texto"> Clinica avicena </div>
			<div id="tabla_2" class="chart"></div>

		</div>
	</div>


	<!-------------------------------------------------------------------------------------------------->
	<!-------------- MIDDLE ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->
	
	
	
	<div class="rowMiddle">
	  	<!-- LEFT MIDDLE -->
		<div class="lmiddle">
			
			<div class ="texto"> Hospital Pasteur </div>
			<div id="tabla_3" class="chart"></div>
		
		</div>

	  	<!-- RIGHT MIDDLE -->
		<div class="rmiddle">
		 
			
			
			<div class ="texto"> Hospital Olmos </div>
			<div id="tabla_4" class="chart"></div>
		
		</div>
	</div>
	
	
	<!-------------------------------------------------------------------------------------------------->
	<!-------------- BOTTOM ---------------------------------------------------------------------------->
	<!-------------------------------------------------------------------------------------------------->
	<!--LEFT BOTTOM -->
	<div class="lbottom">
		<!-- TABLA DE ASOCIACION DIV -->
		<div id="table_div" class="chart"></div>
	</div>
	
	<!--RIGHT BOTTOM -->
	<div class="rbottom">
		<!-- GRAFICO DE ASOCIACION DIV -->
		<div id="dashboard_div">
			<div id="filter_div" class="chart"></div>
			<div id="chart_div" class="chart"></div>
		</div>
	</div>
	
	<div class="Pie">

		<div class ="texto"> Lara Camila Sánchez Correa - Iván Conde Carretero - GII </div>

	</div>
 
</body>
</html>