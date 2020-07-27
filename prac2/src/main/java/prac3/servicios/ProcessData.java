package prac3.servicios;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import prac3.bbdd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

//NECESARIO PARA EL 4.5
import weka.associations.Apriori;
import weka.classifiers.trees.ht.Split;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.StringToNominal;

@Component
public class ProcessData {

    @Autowired
    HospitalService hospitalService;
    @Autowired
    HechoService hechoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    TiempoService tiempoService;
    @Autowired
    ClusterPacientesService clusterPacientesService;
    @Autowired
    HospitalCompuestoService hospitalCompuestoService;
    @Autowired
    AsociacionCompuestoService asociacionCompuestoService;


    @PostConstruct
    //-------------------------------------  4.2 ETL------------------------------------
    public void process() {
        //Insertamos en la tabla dimension tiempo todas las filas de dimTIEMPO.csv
        try {
            BufferedReader br = new BufferedReader(new FileReader("SSII_practicas3_datos/dimTIEMPO.csv"));
            String line =  br.readLine(); //Leemos la primera linea que no nos interesa
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                dimTIEMPO t = AnalizartIiempo(parts);//Introduce la fecha en el formato correcto
                tiempoService.saveTiempo(t);//Almacenamos el objeto en la tabla dimTiempo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Insertamos en la tabla dimension Hospital todas las filas de dimLugar.csv
        try {
            BufferedReader br = new BufferedReader(new FileReader("SSII_practicas3_datos/dimLugar.csv"));
            String line =  br.readLine(); //Leemos la primera linea que no nos interesa
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                dimHOSPITAL h = AnalizarLugar(parts);
                hospitalService.saveHospital(h);//Almacenamos el objeto en la tabla dimHospital
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File dirHn = new File("SSII_practicas3_datos/Hn");
        String[]  ficherosHn = dirHn.list();
        File dirPn = new File("SSII_practicas3_datos/Pn");
        String[]  ficherosPn = dirPn.list();
        int idH = 1;
        //Leeremos los datos de hechos y pacientes a la vez, para facilitar la insercion en las tablas
        for(int i= 0; i<=ficherosPn.length-1; i++) {
            try {
                BufferedReader brp = new BufferedReader(new FileReader("SSII_practicas3_datos/Pn/"+ficherosPn[i]));
                BufferedReader brh = new BufferedReader(new FileReader("SSII_practicas3_datos/Hn/"+ficherosHn[i]));
                String linep = brp.readLine(); //Leemos la primera linea que no nos interesa
                String lineh = brh.readLine(); //Leemos la primera linea que no nos interesa
                while ((linep = brp.readLine()) != null && (lineh = brh.readLine()) != null) {
                	
                    String[] partsp = linep.split(";");
                    String[] partsh = lineh.split(";");
                    
                    int id = Integer.parseInt(partsh[1]);
                    dimPACIENTE p = AnalizarPaciente(partsp, id);
                    pacienteService.savePaciente(p);
                    tablaHECHOS th = AnalizarHechos(partsh, idH);
                    hechoService.saveHecho(th);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            idH++;
        }
        //Abajo despues del apartado 4.4, 4.5 y 4.6, encontraremos los metodos usados para la implementacion del ETL
        //Dichos metodos estan comentados para una mejor claridad.


        // 2º PARTE DE LA PRACTICA 2

        //-------------------------------------  4.4 FILTRADO C ------------------------------------

        File fileFiltrado = new File("SSII_practicas3_datos/Asociacion4_5/datos_filtrado_colaborativo.csv");
        String filtradoColaborativoTxt = "";
        File file = new File("SSII_practicas3_datos/Asociacion4_5/datos_filtrado_colaborativo.csv");
        /*Desde esta linea, hasta la linea 164 vamos a leer los cuatro datos de filtrado colaborativo en formato csv y lo
        vamos a añadir en unico csv llamado datos_filtrado_colaborativo.csv, en el formato necesario, para despues realizar
        el filtrado colaborativo correctamente*/
        FileWriter fw = null;
        try {
            fileFiltrado.delete();
            fileFiltrado.createNewFile();
            int idCliente = 1;
            for(int i=1; i<=4; i++) {

                        fw = new FileWriter(fileFiltrado, true);

                fw = new FileWriter(file, true);

                try {

                    BufferedReader br = new BufferedReader(new FileReader("SSII_practicas3_datos/FiltradoColaborativo/datos_filtrado_colaborativo_"+i+".csv"));
                    String line = br.readLine(); //Leemos la primera linea que no nos interesa
                    if(i==1){
                        //fw.write(line.substring(3,line.length()) + "\n");
                    }
                    while ((line = br.readLine()) != null) {
                        String[] division = line.substring(line.indexOf(',')+1 , line.length()).split(",");
                        int subInd = 1;
                        for (String indDivision: division) {
                            if(!indDivision.equals("0")){
                                  fw.write(idCliente +"," + subInd +"," +indDivision+"\n");
                            }
                            subInd++;
                        }
                        idCliente++;
                        filtradoColaborativoTxt += line.substring(line.indexOf(',')+1 , line.length()) +"\n";

                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*Tenemos un Array por hospital, y cada array tiene 20 posiciones que sera cada compuesto, en cada espacio sumaremos uno
        cuando dicho compuesto sea recomendado
         */
        int[] votacionUno = new int[20];
        int[] votacionDos = new int[20];
        int[] votacionTres = new int[20];
        int[] votacionCuatro = new int[20];
    /*Realizamos el filtrado colaborativo, hemos realizado realmente Sist. recomendador basado en los usuarios
    hemos realizado el basado en usuarios ya que hemos pensado como en el ejemplo de los libros, dicho usuario han probado unos
    compuestos y tienen unas valoraciones, con esa base recomendamos a los demas usuarios*/
        DataModel model =
                null;
        try {
            model = new FileDataModel(new File("SSII_practicas3_datos/Asociacion4_5/datos_filtrado_colaborativo.csv"));
            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood =
                    new ThresholdUserNeighborhood(0.1, similarity, model);
            UserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);
            for(int i=1; i<=255; i++){
                List<RecommendedItem> recommendations = recommender.recommend(i, 3);
                for (RecommendedItem recommendation : recommendations) {
                    int p = (int) recommendation.getItemID();
                    if(i>=1 && i<=50){
                        votacionUno[p-1] += 1;
                    }else if(i>50 && i<=125){
                        votacionDos[p-1] += 1;
                    }else if(i>125 && i<=225){
                        votacionTres[p-1] += 1;
                    }else{
                        votacionCuatro[p-1] += 1;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }

        //Vamos a buscar los tres compuestos mas recomendados en cada hospital
        String maximos1 = "";
        for(int i=0; i<3; i++){

            int maximo = 0;
            int posA = 0;
            int posM = 0;
            for (int valor: votacionUno) {
                if(valor>maximo){
                    maximo = valor;
                    posM = posA;
                }
                posA++;
            }
            maximos1 += posM+1 + "," + votacionUno[posM]  + "\n";
            hospitalCompuestoService.saveHC(new HospitalCompuesto(1,posM+1, votacionUno[posM]));
            votacionUno[posM] = 0;
        }

        String maximos2 = "";
        for(int i=0; i<3; i++){

            int maximo = 0;
            int posA = 0;
            int posM = 0;
            for (int valor: votacionDos) {
                if(valor>maximo){
                    maximo = valor;
                    posM = posA;
                }
                posA++;
            }
            maximos2 += posM+1 + "," + votacionDos[posM]  + "\n";
            hospitalCompuestoService.saveHC(new HospitalCompuesto(2,posM+1, votacionDos[posM]));
            votacionDos[posM] = 0;
        }


        String maximos3 = "";
        for(int i=0; i<3; i++){

            int maximo = 0;
            int posA = 0;
            int posM = 0;
            for (int valor: votacionTres) {
                if(valor>maximo){
                    maximo = valor;
                    posM = posA;
                }
                posA++;
            }
            maximos3 += posM+1 + "," + votacionTres[posM]  + "\n";
            hospitalCompuestoService.saveHC(new HospitalCompuesto(3,posM+1, votacionTres[posM]));
            votacionTres[posM] = 0;
        }


        String maximos4 = "";
        for(int i=0; i<3; i++){

            int maximo = 0;
            int posA = 0;
            int posM = 0;
            for (int valor: votacionCuatro) {
                if(valor>maximo){
                    maximo = valor;
                    posM = posA;
                }
                posA++;
            }
            maximos4 += posM+1 + "," + votacionCuatro[posM]  + "\n";
            hospitalCompuestoService.saveHC(new HospitalCompuesto(4,posM+1, votacionCuatro[posM]));
            votacionCuatro[posM] = 0;
        }

    //Guardamos los filtrados colaborativos de los cuatro hospitales, cada uno en un csv distinto
        File fcH1=  new File("Resultados/filtradoColaborativoH1");
        File fcH2=  new File("Resultados/filtradoColaborativoH2");
        File fcH3=  new File("Resultados/filtradoColaborativoH3");
        File fcH4=  new File("Resultados/filtradoColaborativoH4");
        try {
            FileWriter fwfc1 = new FileWriter(fcH1);
            fwfc1.write("N_Compuesto, Total_Recomendado");
            fwfc1.write(maximos1);
            fwfc1.close();

            FileWriter fwfc2 = new FileWriter(fcH2);
            fwfc2.write("N_Compuesto, Total_Recomendado");
            fwfc2.write(maximos2);
            fwfc2.close();

            FileWriter fwfc3 = new FileWriter(fcH3);
            fwfc3.write("N_Compuesto, Total_Recomendado\n");
            fwfc3.write(maximos3);
            fwfc3.close();

            FileWriter fwfc4 = new FileWriter(fcH4);
            fwfc4 .write("N_Compuesto, Total_Recomendado \n");
            fwfc4.write(maximos4);
            fwfc4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //-------------------------------------  4.5 ASOCIACION ------------------------------------

        //Realizamos un csv para asociativo exito, los 4 y 5 seran t, y 1, 2 y 3 sera ?
        File fileAsociativoE = new File("SSII_practicas3_datos/Asociacion4_5/asociativoExito.csv");
        String asociativoE = filtradoColaborativoTxt.replaceAll("0", "?");
        asociativoE = asociativoE.replaceAll("1", "?");
        asociativoE = asociativoE.replaceAll("2", "?");
        asociativoE = asociativoE.replaceAll("3", "?");
        asociativoE = asociativoE.replaceAll("4", "t");
        asociativoE = asociativoE.replaceAll("5", "t");
        try {
            fileAsociativoE.delete();
            fileAsociativoE.createNewFile();
            FileWriter fwAsE = new FileWriter(fileAsociativoE, true);
            fwAsE.write("c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20\n");
            fwAsE.write(asociativoE);
            fwAsE.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //En el siguiente metodo se realiza la asociación, dicho metodo sera explicado mas adelante
        asociacion(fileAsociativoE, "Resultados/reglasExito.txt");
        //Los resultados obtenidos los insertamos en la bbdd con el siguiente metodo
        asociativaBBDD("Resultados/reglasExito.txt",1);

        //Realizamos un csv para asociativo exito, los 4 y 5 seran ?, y 1, 2 y 3 sera t
        File fileAsociativoF = new File("SSII_practicas3_datos/Asociacion4_5/asociativoFallos.csv");
        String asociativoF = filtradoColaborativoTxt.replaceAll("0", "t");
        asociativoF = asociativoF.replaceAll("1", "t");
        asociativoF = asociativoF.replaceAll("2", "t");
        asociativoF = asociativoF.replaceAll("3", "t");
        asociativoF = asociativoF.replaceAll("4", "?");
        asociativoF = asociativoF.replaceAll("5", "?");
        try {
            fileAsociativoF.delete();
            fileAsociativoF.createNewFile();
            FileWriter fwAsF = new FileWriter(fileAsociativoF, true);
            fwAsF.write("c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20\n");
            fwAsF.write(asociativoF);
            fwAsF.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //En el siguiente metodo se realiza la asociación, dicho metodo sera explicado mas adelante
        asociacion(fileAsociativoF, "Resultados/reglasFallos.txt");
        //Los resultados obtenidos los insertamos en la bbdd con el siguiente metodo
        asociativaBBDD("Resultados/reglasFallos.txt",0);



        //-------------------------------------  4.6 CLUSTERING ------------------------------------

        //cluster 1
        File fileClusterOne = new File("SSII_practicas3_datos/datosCluster/cluster1.csv");
        String clusterOne = hechoService.info1();

        try {
            fileClusterOne.delete();
            fileClusterOne.createNewFile();
            FileWriter fwC1 = new FileWriter(fileClusterOne, true);
            fwC1.write(clusterOne);
            fwC1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clusterin(fileClusterOne,"Resultados/protoResto.txt" , "null");

        //cluster 2
        File fileClusterTwo = new File("SSII_practicas3_datos/datosCluster/cluster2.csv");
        String clusterTwo = hechoService.info2();

        try {
            fileClusterTwo.delete();
            fileClusterTwo.createNewFile();
            FileWriter fwC2 = new FileWriter(fileClusterTwo, true);
            fwC2.write(clusterTwo);
            fwC2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clusterin(fileClusterTwo,"Resultados/protoFallecido.txt", "FALLECIDO");

        //cluster 3
        File fileClusterTree = new File("SSII_practicas3_datos/datosCluster/cluster3.csv");
        String clusterTree = hechoService.info3();

        try {
            fileClusterTree.delete();
            fileClusterTree.createNewFile();
            FileWriter fwC3 = new FileWriter(fileClusterTree, true);
            fwC3.write(clusterTree);
            fwC3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clusterin(fileClusterTree, "Resultados/protoUCI.txt", "UCI");



    }

    //-------------------------------------  METODO ASOCIACION 4.5 ------------------------------------


    //Metodo para realizar la asociacion y guardarlo en un txt
    public void asociacion(File f, String nombreFichero){

        File ficheroAsociacionHecha = new File(nombreFichero);
        //- Crear el cargador de CSV
        CSVLoader loader = new CSVLoader();
        //- Especificar las caracteristicas del CSV
        loader.setFieldSeparator(",");
        loader.setNoHeaderRowPresent(false);
        // get instances object
        try {
            ficheroAsociacionHecha.delete();
            ficheroAsociacionHecha.createNewFile();
            FileWriter fw = new FileWriter(ficheroAsociacionHecha, true);
            loader.setSource(f);
            Instances data = loader.getDataSet();
            //- String to Nominal
            StringToNominal filter1 = new StringToNominal();
            filter1.setAttributeRange("first-last");
            filter1.setInputFormat(data);
            data  = Filter.useFilter(data, filter1);

            //- Discretizar en 2 cubos
            Discretize  filter2 = new Discretize();
            filter2.setBins(2);
            filter2.setInputFormat(data);
            data = Filter.useFilter(data, filter2);

            //- Mostrar en pantalla los atributos de los datos
            fw.write(loader.getStructure()+" ...\n\n");
            // the Apriori alg.
            Apriori model = new Apriori();

            // build model
            model.buildAssociations(data);
            fw.write(model.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //Metodo para meter los datos de la Asociacion en la bbdd
    public void asociativaBBDD(String fA, int esExito){
        BufferedReader brAsEx = null;
        try {
            brAsEx = new BufferedReader(new FileReader(fA));
            String line =  brAsEx.readLine();
            while ((line = brAsEx.readLine()) != null) {
                AsociacionCompuesto ac = new AsociacionCompuesto(esExito);
                if(line.contains("==>")){

                    for(String i: line.substring(3,line.indexOf("==>")).replaceAll("t","").split("=")){
                        añadirValoresA(i.trim(),"A" ,ac);
                    }
                    for(String i: line.substring(line.indexOf("==>")+3 , line.indexOf("<")).replaceAll("t","").split("=")){

                        añadirValoresA(i.trim(),"C" ,ac);
                    }
                    ac.setConf(Float.parseFloat(line.substring(line.indexOf("(")+1 , line.indexOf(")"))));
                    String stringAux = line.substring(line.indexOf(")")+1);
                    ac.setLift(Float.parseFloat(stringAux.substring(stringAux.indexOf("(")+1 , stringAux.indexOf(")"))));
                    asociacionCompuestoService.saveAC(ac);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void añadirValoresA(String i, String esAntecedente, AsociacionCompuesto ac){

        switch (i){
            case "c1":
               ac.setC1(esAntecedente);
                break;
            case "c2":
                ac.setC2(esAntecedente);
                break;
            case "c3":
                ac.setC3(esAntecedente);
                break;
            case "c4":
                ac.setC4(esAntecedente);
                break;
            case "c5":
                ac.setC5(esAntecedente);
                break;
            case "c6":
                ac.setC6(esAntecedente);
                break;
            case "c7":
                ac.setC7(esAntecedente);
                break;
            case "c8":
                ac.setC8(esAntecedente);
                break;
            case "c9":
                ac.setC9(esAntecedente);
                break;
            case "c10":
                ac.setC10(esAntecedente);
                break;
            case "c11":
                ac.setC11(esAntecedente);
                break;
            case "c12":
                ac.setC12(esAntecedente);
                break;
            case "c13":
                ac.setC13(esAntecedente);
                break;
            case "c14":
                ac.setC14(esAntecedente);
                break;
            case "c15":
                ac.setC15(esAntecedente);
                break;
            case "c16":
                ac.setC16(esAntecedente);
                break;
            case "c17":
                ac.setC17(esAntecedente);
                break;
            case "c18":
                ac.setC18(esAntecedente);
                break;
            case "c19":
                ac.setC19(esAntecedente);
                break;
            case "c20":
                ac.setC20(esAntecedente);
                break;
            default:

        }

    }


    //-------------------------------------   METODO PARA REALIZAR CLUSTERING 4.6------------------------------------
    //Metodo para hacer clustering y guargamos la informacion en un txt y en la BBDD
    public void clusterin(File FileCsv, String nombreFichero, String tipo){

        File ficheroTxtCluster = new File(nombreFichero);

        //- Número de clusters que vamos a hacer
        int K = 3;
        //- max. num. de iteraciones
        int maxIteration = 13;
        //- fichero CSV:
        // * Este fichero tiene datos numéricos y nominales
        // * Los atributos de cada ejemplo están separados por ";"
        // * La primera fila es el nombre de los atributos

        //- Crear el cargador de CSV

        CSVLoader loader = new CSVLoader();
        //- Especificar las caracteristicas del CSV (ver arriba)
        loader.setFieldSeparator(",");
        loader.setNoHeaderRowPresent(false);
        //- Cargar los datos
        try {
            ficheroTxtCluster.delete();
            ficheroTxtCluster.createNewFile();
            FileWriter fw = new FileWriter(ficheroTxtCluster, true);
            loader.setSource(FileCsv);
            Instances data = loader.getDataSet();
            //- Mostrar en pantalla los atributos de los datos

            //System.out.println(loader.getStructure()+" ...\n\n");

            //- Crear un objeto 'K-Means',
            //  que es el método que vamos a utilizar
            SimpleKMeans kmeans = new SimpleKMeans();
            //- Especificar las características del método
            kmeans.setNumClusters(K);
            kmeans.setMaxIterations(maxIteration);
            kmeans.setPreserveInstancesOrder(true);
            //- Ejecutar el agrupamiento sobre los datos
            kmeans.buildClusterer(data);

            //- Mostrar en pantalla los prototipos de cada grupo
            //    (también se les llama 'centroides'
            Instances centroids = kmeans.getClusterCentroids();

            //System.out.println("RESULTADO --------------------------------------------");
            for (int i = 0; i < K; i++) {
                fw.write("Cluster " + i + " tamaño: " + kmeans.getClusterSizes()[i] + "\n");
                fw.write(" Prototipo: " + centroids.instance(i) +"\n");
                String textoCluster = String.valueOf(centroids.instance(i));
                String[] divisionTexto = textoCluster.split(",");
                if(tipo=="UCI"){
                    clusterPacientesService.saveCP(new ClusterPacientes(
                            "UCI"
                            , divisionTexto[10]
                            , Float.parseFloat(divisionTexto[0])
                            , Float.parseFloat(divisionTexto[1])
                            , Float.parseFloat(divisionTexto[2])
                            , Float.parseFloat(divisionTexto[3])
                            , Float.parseFloat(divisionTexto[4])
                            , Float.parseFloat(divisionTexto[5])
                            , Float.parseFloat(divisionTexto[6])
                            , Float.parseFloat(divisionTexto[7])
                            , Float.parseFloat(divisionTexto[8])
                            , Float.parseFloat(divisionTexto[9])
                            , Float.parseFloat(divisionTexto[11])
                            , Float.parseFloat(divisionTexto[12])
                            , Float.parseFloat(divisionTexto[13])
                            , 1
                            , Float.parseFloat(divisionTexto[14])
                           ));
                }else if(tipo=="FALLECIDO"){
                    clusterPacientesService.saveCP(new ClusterPacientes(
                            "Facllecido"
                            , divisionTexto[10]
                            , Float.parseFloat(divisionTexto[0])
                            , Float.parseFloat(divisionTexto[1])
                            , Float.parseFloat(divisionTexto[2])
                            , Float.parseFloat(divisionTexto[3])
                            , Float.parseFloat(divisionTexto[4])
                            , Float.parseFloat(divisionTexto[5])
                            , Float.parseFloat(divisionTexto[6])
                            , Float.parseFloat(divisionTexto[7])
                            , Float.parseFloat(divisionTexto[8])
                            , Float.parseFloat(divisionTexto[9])
                            , Float.parseFloat(divisionTexto[11])
                            , Float.parseFloat(divisionTexto[12])
                            , Float.parseFloat(divisionTexto[13])
                            , Float.parseFloat(divisionTexto[14])
                            , 1
                           ));

                }else{
                    clusterPacientesService.saveCP(new ClusterPacientes(
                            "Resto"
                            , divisionTexto[10]
                            , Float.parseFloat(divisionTexto[0])
                            , Float.parseFloat(divisionTexto[1])
                            , Float.parseFloat(divisionTexto[2])
                            , Float.parseFloat(divisionTexto[3])
                            , Float.parseFloat(divisionTexto[4])
                            , Float.parseFloat(divisionTexto[5])
                            , Float.parseFloat(divisionTexto[6])
                            , Float.parseFloat(divisionTexto[7])
                            , Float.parseFloat(divisionTexto[8])
                            , Float.parseFloat(divisionTexto[9])
                            , Float.parseFloat(divisionTexto[11])
                            , Float.parseFloat(divisionTexto[12])
                            , Float.parseFloat(divisionTexto[13])
                            , 0
                            , 0
                           ));

                }
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    //-----------------4.2 METODOS AUXILIARES PARA REALIZAR EL ETL

    /*Hemos realizado un split para dividir la fila del csv y así insertar mejores los valores en el objeto tablaHECHOS
    Ademas dicho metodo nos ayuda a que todas las columnas tengan valores con la misma magnitud, por ejemplo, en la columna UCI(esta en Uci)
    tendremos '0' si no esta o '1' si esta en UCI, y siempre estará con la misma nomenclatura, no veremos S/N
     */
    public tablaHECHOS AnalizarHechos(String[] partes, int idHospital){
        tablaHECHOS th = new tablaHECHOS();
        th.setDuracion(Integer.parseInt(partes[3]));
        if(partes[4].charAt(0) == 'S'){
            th.setUCI(1);
        }else{
            th.setUCI(0);
        }
        if(partes[5].charAt(0) == 'S'){
            th.setFallecido(1);
        }else{
            th.setFallecido(0);
        }
        th.setTratamiento(Integer.parseInt(partes[6]));
        th.setCliente_id(pacienteService.getPaciente(Integer.parseInt(partes[1])));
        if(partes[2].length()>8){

            th.setFechaIngreso_id(tiempoService.getTiempo((Integer.parseInt(partes[2].substring(0,2))),Integer.parseInt(partes[2].substring(3,5)),Integer.parseInt(partes[2].substring(6,10))));}
        else{
            String fechatxt = partes[2].substring(0,6) +"/20" +partes[2].substring(6,8);
            th.setFechaIngreso_id(tiempoService.getTiempo((Integer.parseInt(partes[2].substring(0,2))),Integer.parseInt(partes[2].substring(3,5)),Integer.parseInt("20" +partes[2].substring(6,8))));
        }
        th.setHospital_id(hospitalService.getHospitals(idHospital));


        return th;
    }

    //Hemos realizado un split para dividir la fila del csv y así insertar mejores los valores en el objeto dimension Lugar
    public dimHOSPITAL AnalizarLugar(String[] partes){
        dimHOSPITAL h = new dimHOSPITAL();
        h.setId(Integer.parseInt(partes[0].substring(1 ,partes[0].length())));
        h.setNombre(partes[1]);
        h.setCpostal(Integer.parseInt(partes[2]));
        h.setAutopista(partes[3]);
        h.setGestor(partes[4]);
        return h;
    }

    //Hemos realizado un split para dividir la fila del csv y así insertar mejores los valores en el objeto dimension Tiempo
    public dimTIEMPO AnalizartIiempo(String[] partes){
        dimTIEMPO t = new dimTIEMPO();
        t.setFecha(partes[1].substring(0,10));
        t.setDia(Integer.parseInt(partes[2]));
        t.setMes((Integer.parseInt(partes[3])));
        t.setAño((Integer.parseInt(partes[4])));
        t.setCuatrim((Integer.parseInt(partes[5])));
        t.setDisemana(partes[6]);
        t.setEsfinde((Integer.parseInt(partes[7])));
        return t;
    }
    /*Hemos realizado un split para dividir la fila del csv y así insertar mejores los valores en el objeto dimPaciemte
    ademas dicho metodo nos ayuda a que todas las columnas tengan valores con la misma magnitud, es decir, en la columna sexo
    tendremos '0' si es mujes o '1' si es hombre, y siempre estará con la misma nomenclatura, no veremos H/M o V/H */
    public dimPACIENTE AnalizarPaciente(String[] partes, int id){
        dimPACIENTE p = new dimPACIENTE();
        p.setId(id);
        if(Integer.parseInt(partes[1])>120) {
        	p.setEdad(pacienteService.mediaEdad());
        }else {
        	 p.setEdad(Integer.parseInt(partes[1]));
        }
       
        //SEXO
        if(partes[2].equals("0")){
            p.setSexo("M");
        }else if(partes[2].equals("1")){
            p.setSexo("H");
        }else{
            p.setSexo(partes[2]);
        }

        p.setIMC(Integer.parseInt(partes[3]));
        p.setFormaFisica(Integer.parseInt(partes[4]));

        //tabaquismo
        if(partes[5].equals("No")){
            p.setTabaquismo(0);
        }else if(partes[5].equals("Si")){
            p.setTabaquismo(1);
        }else{
            p.setTabaquismo(Integer.parseInt(partes[5]));
        }
        //colesterol
        if(partes[6].equals("No")){
            p.setColesterol(0);
        }else if(partes[6].equals("Si")){
            p.setColesterol(1);
        }else{
            p.setColesterol(Integer.parseInt(partes[6]));
        }
        //hipertension
        if(partes[7].equals("No")){
            p.setHipertension(0);
        }else if(partes[7].equals("Si")){
            p.setHipertension(1);
        }else{
            p.setHipertension(Integer.parseInt(partes[7]));
        }
        //cardiopatia
        if(partes[8].equals("No")){
            p.setCardiopatia(0);
        }else if(partes[8].equals("Si")){
            p.setCardiopatia(1);
        }else{
            p.setCardiopatia(Integer.parseInt(partes[8]));
        }
        //reuma
        if(partes[9].equals("No")){
            p.setReuma(0);
        }else if(partes[9].equals("Si")){
            p.setReuma(1);
        }else{
            p.setReuma(Integer.parseInt(partes[9]));
        }
        //EPOC
        if(partes[10].equals("No")){
            p.setEPOC(0);
        }else if(partes[10].equals("Si")){
            p.setEPOC(1);
        }else{
            p.setEPOC(Integer.parseInt(partes[10]));
        }
        //hepatitis
        if(partes[11].equals("No")){
            p.setHepatitis(0);
        }else if(partes[11].equals("Si")){
            p.setHepatitis(1);
        }else{
            p.setHepatitis(Integer.parseInt(partes[11]));
        }
        //cancer
        if(partes[12].equals("No")){
            p.setCancer(0);
        }else if(partes[12].equals("Si")){
            p.setCancer(1);
        }else{
            p.setCancer(Integer.parseInt(partes[12]));
        }
        return p;
    }




}
