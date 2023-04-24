import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

/*@AUTORES:
 * Eduardo Morales Calvo
 * Jorge Pérez de Dios
*/


public class PI {

	public static void llamada(JPanel panel,String lugar,JLabel Titulo,JLabel Imagen,JLabel climaLabel,JLabel descriptionLabel,JLabel temperatureLabel, 
			JLabel humidityLabel,JLabel temperatureminLabel,JLabel temperaturemaxLabel,JLabel pressureLabel,JLabel feelslikeLabel,
			JLabel visibilityLabel,JLabel speedLabel,JLabel degreeLabel,JLabel countryLabel,JLabel timeLabel, JLabel ubicacionLabel,
			JLabel peligro,JLabel peligro1,JLabel peligro2,JLabel peligro8,JLabel peligro9) {

		final String apiKey = "6ea77bd143e19e458988263e9217c4b2"; //Llave de la api
		String lat = ""; //Latitud 
		String lon = ""; //Longitud 

		switch(lugar) {
		case "TorreEspaña":
			lat = "40.420512"; //Latitud 
			lon = "-3.664196"; //Longitud 
			Titulo.setText("TorreEspaña");
			break;

		case "Torreta de Guardamar":
			lat = "38.071667";
			lon = "-0.664444"; 
			Titulo.setText("Torreta de Guardamar");
			break;

		}

		String apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric" + "&lang=sp";

		try {

			Date fecha = new Date(); //obtiene la fecha actual
			SimpleDateFormat hr = new SimpleDateFormat("EEEE, dd-MM-YYYY HH:mm:ss");
			URL url = new URL(apiUrl); //crea objeto de la URL
			HttpURLConnection conectar = (HttpURLConnection) url.openConnection(); //crea objeto de conexion
			conectar.setRequestMethod("GET"); //Metodo de conexion
			conectar.connect(); //conexion

			int respuestaAPI = conectar.getResponseCode(); //respuesta de la API

			if (respuestaAPI != 200) {
				throw new RuntimeException("ERROR: " + respuestaAPI); //Error en caso de no haber respuesta
			} else {

				Scanner p = new Scanner(url.openStream()); //Escanea el output de la API
				String respuesta = p.nextLine(); //Guarda output en una variable

				//System.out.println(respuesta); Imprime los datos output de la API

				JSONObject ob = new JSONObject(respuesta); //objeto de toda la string del texto en JSON "{ STRING }"
				JSONArray arr = ob.getJSONArray("weather");//crea un array del array que hay dentro del string JSON "[ ARRAY ]"

				//Array
				String CodigoIcono = arr.getJSONObject(0).getString("icon"); //guarda el apartado de icon
				String tiempo = arr.getJSONObject(0).getString("main"); //guarda el apartado de tiempo
				String descripcion = arr.getJSONObject(0).getString("description"); //guarda el apartado de descripción


				//crea un objeto imagen con la url de iconos de la API
				ImageIcon icono = new ImageIcon(new URL("https://openweathermap.org/img/wn/" + CodigoIcono + "@2x.png")); 

			    // Obtener la imagen del ImageIcon
				Image input_Image = icono.getImage();
				// Redimensionar imagen
				Image output_Image = input_Image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

				// Crear nuevo ImageIcon con la imagen redimensionada
				ImageIcon outputIcon = new ImageIcon(output_Image);

				Imagen.setIcon(outputIcon); // se establece la imagen al label

				float temperatura = ob.getJSONObject("main").getFloat("temp"); //guarda el apartado de temperatura que estaba en main
				float humedad = ob.getJSONObject("main").getFloat("humidity"); //guarda el apartado de humedad que estaba en main
				float temperaturaminima = ob.getJSONObject("main").getInt("temp_min");//guarda el apartado de temperatura minima que estaba en main
				float temperaturamaxima = ob.getJSONObject("main").getInt("temp_max");//guarda el apartado de temperatura maxima que estaba en main
				float sensacióntermica = ob.getJSONObject("main").getInt("feels_like");//guarda el apartado de presión que estaba en main
				float presión = ob.getJSONObject("main").getInt("pressure");//guarda el apartado de presión que estaba en main
				int visibility = (int) ob.getInt("visibility");//guarda la visibilidad
				float velocidadviento = ob.getJSONObject("wind").getInt("speed");//guarda el apartado de velocidad del viento que estaba en wind
				int direcciónviento = ob.getJSONObject("wind").getInt("deg");//guarda el apartado de la dirrección del viento en grados que estaba en wind
				String pais = ob.getJSONObject("sys").getString("country");//guarda el apartado del pais que estaba en sys
				String name = (String) ob.getString("name");//guarda la ubicación en la que se encuentra



				//switch case para la traduccion del clima
				switch(tiempo) {
				case "Thunderstorm":
					climaLabel.setText("Tormenta Electrica");
					break;
				case "Drizzle":
					climaLabel.setText("Llovizna");
					break;
				case "Rain":
					climaLabel.setText("Lluvia");
					break;
				case "snow":
					climaLabel.setText("Nieve");
					break;
				case "Clear":
					climaLabel.setText("Claro");
					break;
				case "Clouds":
					climaLabel.setText("Nubes");
					break;
				case "Mist":
					climaLabel.setText("Neblina");
					break;
				case "Smoke":
					climaLabel.setText("Humo");
					break;
				case "Haze":
					climaLabel.setText("Bruma");
					break;
				case "Dust":
					climaLabel.setText("Polvo");
					break;
				case "Fog":
					climaLabel.setText("Niebla");
					break;
				case "Sand":
					climaLabel.setText("Arena");
					break;
				case "Ash":
					climaLabel.setText("Ceniza");
					break;
				case "Squall":
					climaLabel.setText("Chubasco");
					break;
				case "Tornado":
					climaLabel.setText("Tornado");
					break;
				default:
					climaLabel.setText("Error Traduccion");
					break;

				}

				// se estable el texto al label

				//descripción ya traducida por la API
				descriptionLabel.setText(descripcion);
				temperatureLabel.setText(temperatura + " ºC");
				humidityLabel.setText(humedad + " %");
				temperatureminLabel.setText(temperaturaminima + " ºC"); //imprime temperatura minima
				temperaturemaxLabel.setText(temperaturamaxima + " ºC"); //imprime temperatura maxima
				pressureLabel.setText(presión + " hPa"); //imprime presión
				feelslikeLabel.setText(sensacióntermica + " ºC"); //imprime velocidad del viento
				visibilityLabel.setText(visibility + " m"); //imprime visibilidad
				speedLabel.setText(velocidadviento + " m/s"); //imprime velocidad del viento
				degreeLabel.setText(direcciónviento + " º"); //imprime dirección del viento en grados
				countryLabel.setText(pais); //imprime el pais en el que esta la torre
				ubicacionLabel.setText(name);
				timeLabel.setText(hr.format(fecha).toString());

				/*
		        System.out.println("temperatura: " + temperatura + " ºC"); //imprime temperatura
		        System.out.println("humedad: " + humedad + " g/m3"); //imprime humedad
		        System.out.println("temperatura mínima: " + temperaturaminima + " ºC"); //imprime temperatura minima
		        System.out.println("temperatura máxima: " + temperaturamaxima + " ºC"); //imprime temperatura maxima
		        System.out.println("presión: " + presión + " hPa"); //imprime presión
		        System.out.println("sensación termica: " + sensacióntermica + " ºC"); //imprime velocidad del viento
		        System.out.println("visibilidad: " + visibility + " m"); //imprime visibilidad
		        System.out.println("velocidad del viento: " + velocidadviento + " m/s"); //imprime velocidad del viento
		        System.out.println("dirección del viento: " + direcciónviento + " º"); //imprime dirección del viento en grados
		        System.out.println("pais: " + pais ); //imprime el pais en el que esta la torre
		        System.out.println("zona horaria: " + timezone); //imprime visibilidad
		        System.out.println("ubicación: " + name); //imprime ubicación
				System.out.println (fecha);
				 */

				//Recomendaciones y avisos
				if(temperatura < 5) {
					peligro2.setToolTipText("Riesgo: Temperatura menor a 5ºC");
					panel.add(peligro2);
				}

				if(temperatura > 30) {
					peligro2.setToolTipText("Riesgo: Temperatura mayor a 30ºC");
					panel.add(peligro2);

				}

				p.close();	//se cierra el escaner
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}





	public static void main(String[] args) {

		System.setProperty("sun.java2d.uiScale", "100%");



		String torres[]= {"TorreEspaña",
				"Torreta de Guardamar",
				"Torre de Zaragoza",
				"Torre Tavira II",
				"Faro de Moncloa",
				"Torre de Collserola",
				"Torre de Montjuïc",
		"Torre de Gerona"};
		//URL de la API con los diferentes parametros como latitud,longitud,la llave de la API,la unidad metrica y el idioma

		//Inicio frame que contendra el panel con los labels
		JFrame frame = new JFrame("ClimaTowers");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Logo.png"));
		frame.setResizable(false); // La ventana no será redimensionable
		//frame.setUndecorated(true); // Ocultar el marco
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //establezco que el programa termina presionando la X de la ventana
		frame.setSize(450,500); //tamaño del frame
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla


		//Inicio panel que incluye los labels
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(100, 100, 450, 500);
		panel.setBackground(new Color(42, 50, 60));
		panel.setBorder(null);


		//Titulo
		JLabel Titulo = new JLabel();
		Titulo.setForeground(Color.BLACK);
		Titulo.setFont(new Font("Arial", Font.BOLD, 28));
		Titulo.setBounds(10, 67, 305, 43);
		Titulo.setPreferredSize(new Dimension(305, 43));
		panel.add(Titulo);

		//Imagen
		JLabel Imagen = new JLabel();
		Imagen.setBounds(225, 100, 220, 220);
		Imagen.setPreferredSize(new Dimension(220, 220));//168,132
		panel.add(Imagen);


		// Clima
		JLabel climaLabel1 = new JLabel("Tiempo: ");
		climaLabel1.setForeground(Color.BLACK);
		climaLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		climaLabel1.setBounds(10, 127, 83, 14);
		climaLabel1.setPreferredSize(new Dimension(83, 14));
		panel.add(climaLabel1);

		JLabel climaLabel = new JLabel();
		climaLabel.setForeground(Color.BLACK);
		climaLabel.setFont(new Font("Arial", Font.BOLD, 15));
		climaLabel.setBounds(70, 127, 210, 14);
		climaLabel.setPreferredSize(new Dimension(210, 14));
		panel.add(climaLabel);

		JLabel peligro = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro.setBounds(120, 125, 16, 16);
		peligro.setPreferredSize(new Dimension(16, 16));
		//panel.add(peligro);

		// Descripción
		JLabel descriptionLabel1 = new JLabel("Descripción: ");
		descriptionLabel1.setForeground(Color.BLACK);
		descriptionLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		descriptionLabel1.setBounds(10, 152, 100, 14);
		descriptionLabel1.setPreferredSize(new Dimension(100, 14));
		panel.add(descriptionLabel1);

		JLabel descriptionLabel = new JLabel();
		descriptionLabel.setForeground(Color.BLACK);
		descriptionLabel.setFont(new Font("Arial", Font.BOLD, 15));
		descriptionLabel.setBounds(103, 152, 210, 14);
		descriptionLabel.setPreferredSize(new Dimension(210, 14));
		panel.add(descriptionLabel);

		JLabel peligro1 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro1.setBounds(190, 150, 16, 16);
		peligro1.setPreferredSize(new Dimension(16, 16));
		//panel.add(peligro1);

		//Temperatura
		JLabel temperatureLabel1 = new JLabel("Temperatura: ");
		temperatureLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		temperatureLabel1.setForeground(Color.BLACK);
		temperatureLabel1.setBounds(10, 177, 110, 14);
		temperatureLabel1.setPreferredSize(new Dimension(110, 14));
		panel.add(temperatureLabel1);

		JLabel temperatureLabel = new JLabel();
		temperatureLabel.setFont(new Font("Arial", Font.BOLD, 15));
		temperatureLabel.setForeground(Color.BLACK);
		temperatureLabel.setBounds(110, 177, 110, 14);
		temperatureLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(temperatureLabel);

		JLabel peligro2 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro2.setBounds(190, 175, 16, 16);
		peligro2.setPreferredSize(new Dimension(16, 16));
		//panel.add(peligro2);

		//Temperatura
		JLabel temperatureminLabel1  = new JLabel("Temperatura mín: ");
		temperatureminLabel1.setForeground(Color.BLACK);
		temperatureminLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		temperatureminLabel1.setBounds(10, 202, 130, 14);
		temperatureminLabel1.setPreferredSize(new Dimension(130, 14));
		panel.add(temperatureminLabel1);

		JLabel temperatureminLabel  = new JLabel();
		temperatureminLabel.setForeground(Color.BLACK);
		temperatureminLabel.setFont(new Font("Arial", Font.BOLD, 15));
		temperatureminLabel.setBounds(139, 202, 110, 14);
		temperatureminLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(temperatureminLabel);
		/*
		JLabel peligro3 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro3.setBounds(200, 200, 16, 16);
		panel.add(peligro3);
		 */
		//Temperatura
		JLabel temperaturemaxLabel1 = new JLabel("Temperatura máx: ");
		temperaturemaxLabel1.setForeground(Color.BLACK);
		temperaturemaxLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		temperaturemaxLabel1.setBounds(10, 227, 135, 14);
		temperaturemaxLabel1.setPreferredSize(new Dimension(135, 14));
		panel.add(temperaturemaxLabel1);

		JLabel temperaturemaxLabel = new JLabel();
		temperaturemaxLabel.setForeground(Color.BLACK);
		temperaturemaxLabel.setFont(new Font("Arial", Font.BOLD, 15));
		temperaturemaxLabel.setBounds(141, 227, 110, 14);
		temperaturemaxLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(temperaturemaxLabel);
		/*
		JLabel peligro4 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro4.setBounds(200, 225, 16, 16);
		panel.add(peligro4);
		 */
		//sensación térmica
		JLabel feelslikeLabel1 = new JLabel("Sensación Térmica:");
		feelslikeLabel1.setForeground(Color.BLACK);
		feelslikeLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		feelslikeLabel1.setBounds(10, 252, 150, 14);
		feelslikeLabel1.setPreferredSize(new Dimension(135, 14));
		panel.add(feelslikeLabel1);

		JLabel feelslikeLabel = new JLabel();
		feelslikeLabel.setForeground(Color.BLACK);
		feelslikeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		feelslikeLabel.setBounds(153, 252, 135, 14);
		feelslikeLabel.setPreferredSize(new Dimension(135, 14));
		panel.add(feelslikeLabel);
		/*
		JLabel peligro5 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro5.setBounds(210, 250, 16, 16);
		panel.add(peligro5);
		 */
		//Humedad
		JLabel humidityLabel1 = new JLabel("Humedad: ");
		humidityLabel1.setForeground(Color.BLACK);
		humidityLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		humidityLabel1.setBounds(10, 277, 110, 14);
		humidityLabel1.setPreferredSize(new Dimension(110, 14));
		panel.add(humidityLabel1);

		JLabel humidityLabel = new JLabel();
		humidityLabel.setForeground(Color.BLACK);
		humidityLabel.setFont(new Font("Arial", Font.BOLD, 15));
		humidityLabel.setBounds(85, 277, 110, 14);
		humidityLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(humidityLabel);
		/*
		JLabel peligro6 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro6.setBounds(140, 275, 16, 16);
		panel.add(peligro6);
		 */
		//presión
		JLabel pressureLabel1 = new JLabel("Presión: ");
		pressureLabel1.setForeground(Color.BLACK);
		pressureLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		pressureLabel1.setBounds(10, 302, 110, 14);
		pressureLabel1.setPreferredSize(new Dimension(110, 14));
		panel.add(pressureLabel1);

		JLabel pressureLabel = new JLabel();
		pressureLabel.setForeground(Color.BLACK);
		pressureLabel.setFont(new Font("Arial", Font.BOLD, 15));
		pressureLabel.setBounds(73, 302, 150, 14);
		pressureLabel.setPreferredSize(new Dimension(150, 14));
		panel.add(pressureLabel);
		/*
		JLabel peligro7 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro7.setBounds(160, 300, 16, 16);
		panel.add(peligro7);
		 */
		//visibilidad
		JLabel visibilityLabel1 = new JLabel("Visibilidad: ");
		visibilityLabel1.setForeground(Color.BLACK);
		visibilityLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		visibilityLabel1.setBounds(10, 327, 110, 14);
		visibilityLabel1.setPreferredSize(new Dimension(110, 14));
		panel.add(visibilityLabel1);

		JLabel visibilityLabel = new JLabel();
		visibilityLabel.setForeground(Color.BLACK);
		visibilityLabel.setFont(new Font("Arial", Font.BOLD, 15));
		visibilityLabel.setBounds(89, 327, 110, 14);
		visibilityLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(visibilityLabel);

		JLabel peligro8 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro8.setBounds(160, 325, 16, 16);
		peligro8.setPreferredSize(new Dimension(16, 16));
		//panel.add(peligro8);

		//velocidad viento
		JLabel speedLabel1 = new JLabel("Velocidad del viento: ");
		speedLabel1.setForeground(Color.BLACK);
		speedLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		speedLabel1.setBounds(10, 352, 170, 14);
		speedLabel1.setPreferredSize(new Dimension(170, 14));
		panel.add(speedLabel1);

		JLabel speedLabel = new JLabel();
		speedLabel.setForeground(Color.BLACK);
		speedLabel.setFont(new Font("Arial", Font.BOLD, 15));
		speedLabel.setBounds(163, 352, 110, 14);
		speedLabel.setPreferredSize(new Dimension(110, 14));
		panel.add(speedLabel);

		JLabel peligro9 = new JLabel(new ImageIcon("Img/peligro.png"));
		peligro9.setBounds(220, 350, 16, 16);
		peligro9.setPreferredSize(new Dimension(16, 16));
		//panel.add(peligro9);

		//ángulo viento
		JLabel degreeLabel1 = new JLabel("Ángulo del viento: ");
		degreeLabel1.setForeground(Color.BLACK);
		degreeLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		degreeLabel1.setBounds(10, 377, 170, 16);
		degreeLabel1.setPreferredSize(new Dimension(170, 16));
		panel.add(degreeLabel1);

		JLabel degreeLabel = new JLabel();
		degreeLabel.setForeground(Color.BLACK);
		degreeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		degreeLabel.setBounds(143, 377, 110, 16);
		degreeLabel.setPreferredSize(new Dimension(110, 16));
		panel.add(degreeLabel);

		//Pais
		JLabel countryLabel1 = new JLabel("País: ");
		countryLabel1.setForeground(Color.BLACK);
		countryLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		countryLabel1.setBounds(10, 404, 70, 14);
		countryLabel1.setPreferredSize(new Dimension(70, 14));
		panel.add(countryLabel1);

		JLabel countryLabel = new JLabel();
		countryLabel.setForeground(Color.BLACK);
		countryLabel.setFont(new Font("Arial", Font.BOLD, 15));
		countryLabel.setBounds(47, 404, 315, 14);
		countryLabel.setPreferredSize(new Dimension(315, 14));
		panel.add(countryLabel);

		//Ubicación
		JLabel ubicacionLabel1 = new JLabel("Ubicación: ");
		ubicacionLabel1.setForeground(Color.BLACK);
		ubicacionLabel1.setFont(new Font("Arial", Font.BOLD, 15));
		ubicacionLabel1.setBounds(85, 404, 110, 14);
		ubicacionLabel1.setPreferredSize(new Dimension(110, 14));
		panel.add(ubicacionLabel1);

		JLabel ubicacionLabel = new JLabel();
		ubicacionLabel.setForeground(Color.BLACK);
		ubicacionLabel.setFont(new Font("Arial", Font.BOLD, 15));
		ubicacionLabel.setBounds(163, 404, 315, 14);
		ubicacionLabel.setPreferredSize(new Dimension(315, 14));
		panel.add(ubicacionLabel);

		//fecha
		JLabel timeLabel = new JLabel();
		timeLabel.setForeground(Color.BLACK);
		timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
		timeLabel.setBounds(255, 280, 300, 14);
		timeLabel.setPreferredSize(new Dimension(300, 14));
		panel.add(timeLabel);

		//logo
		JLabel logo = new JLabel(new ImageIcon("Img/Logo.png"));
		logo.setBounds(10, 18, 46, 42);
		logo.setPreferredSize(new Dimension(46, 42));
		panel.add(logo);

		//logo UEM
		JLabel logoUEM = new JLabel(new ImageIcon("Img/UEM.png"));
		logoUEM.setBounds(378, 408, 46, 42);
		logoUEM.setPreferredSize(new Dimension(46, 42));
		panel.add(logoUEM);

		//nombre
		JLabel nombre1 = new JLabel("Eduardo MC");
		nombre1.setForeground(Color.BLACK);
		nombre1.setFont(new Font("Arial", Font.ITALIC, 12));
		nombre1.setBounds(10, 436, 68, 14);
		nombre1.setPreferredSize(new Dimension(68, 14));
		panel.add(nombre1);

		//nombre
		JLabel nombre2 = new JLabel("Jorge PD");
		nombre2.setForeground(Color.BLACK);
		nombre2.setFont(new Font("Arial", Font.ITALIC, 12));
		nombre2.setBounds(112, 436, 62, 14);
		nombre2.setPreferredSize(new Dimension(62, 14));
		panel.add(nombre2);

		//separador nombres
		JLabel slash = new JLabel("/");
		slash.setForeground(Color.BLACK);
		slash.setFont(new Font("Arial", Font.ITALIC, 12));
		slash.setBounds(92, 436, 16, 14);
		slash.setPreferredSize(new Dimension(16, 14));
		panel.add(slash);

		//Inicio boton
		JButton botonbuscar = new JButton(new ImageIcon("Img/lupa2.png"));
		botonbuscar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // cambia cursor a la mano
		botonbuscar.setBorder(null); // quita bordes
		botonbuscar.setContentAreaFilled(false); // quita bordes
		botonbuscar.setFocusPainted(false); // quita bordes
		botonbuscar.setBounds(230, 25, 30, 30);
		botonbuscar.setBackground(new Color(42, 50, 60));
		panel.add(botonbuscar);

		//desplegable torres
		JComboBox desplegable = new JComboBox(torres);
		desplegable.setBackground(new Color(177, 186, 186));
		desplegable.setFont(new Font("Arial", Font.BOLD, 12));
		desplegable.setForeground(Color.BLACK);        
		desplegable.setBounds(66, 29, 154, 25);
		desplegable.setBorder(null);
		panel.add(desplegable);


		JLabel fondo = new JLabel(new ImageIcon("Img/fondo1.jpg"));
		fondo.setBounds(0, 0, 450, 500);
		fondo.setPreferredSize(new Dimension(450, 500));
		panel.add(fondo);






		frame.setContentPane(panel); //Incluir el panel dentro del frame
		frame.setVisible(true); //visible el frame

		botonbuscar.addActionListener(new ActionListener() {
			String lugar = "";
			public void actionPerformed(ActionEvent e) {
				lugar = desplegable.getSelectedItem().toString();
				botonbuscar.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				llamada(panel,lugar,Titulo,Imagen, climaLabel, descriptionLabel, temperatureLabel, 
						humidityLabel, temperatureminLabel, temperaturemaxLabel, pressureLabel, feelslikeLabel,
						visibilityLabel, speedLabel, degreeLabel, countryLabel, timeLabel, ubicacionLabel,
						peligro,peligro1,peligro2,peligro8,peligro9); 
				botonbuscar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // cambia cursor a la mano

			}});

	}
}