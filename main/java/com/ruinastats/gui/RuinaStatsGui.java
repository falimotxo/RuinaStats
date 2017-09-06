package com.ruinastats.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.ruinastats.loader.ExcelLoader;
import com.ruinastats.process.StatsProcess;

public class RuinaStatsGui extends JFrame {

	/** Serial Version */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(RuinaStatsGui.class.getName());

	/** Carga de datos del excel */
	private ExcelLoader excelLoader;
	
	/** Área donde se mostrará la información de estadísticas */
	private JTextArea statsTextArea;
	
	public RuinaStatsGui(ExcelLoader excelLoader) {
		super();
		log.info("Iniciando creación de interfaz gráfica");
		this.excelLoader = excelLoader;
		configureWindow();
		initComponent();
		log.info("Interfaz gráfica creada correctamente");
	}

	private void configureWindow() {
		this.setTitle("RuinaStats");
		this.setSize(700, 410);
		this.setLocationRelativeTo(null); // centramos la ventana en la pantalla
		this.setLayout(null); // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
		this.setResizable(false);
		this.setDefaultCloseOperation(closeOperation());
	}

	private int closeOperation() {
		log.info("RuinaStats finalizado");
		return JFrame.EXIT_ON_CLOSE;
	}

	private void initComponent() {
		// creamos los componentes
		JLabel textLocal = new JLabel();
		textLocal.setText("Local");
		textLocal.setBounds(50, 50, 50, 25);
		this.add(textLocal);

		JComboBox<String> comboLocal = new JComboBox<String>();
		comboLocal.addItem("");
		comboLocal.setBounds(100, 50, 100, 25);
		this.add(comboLocal);

		JLabel textAway = new JLabel();
		textAway.setText("Visitante");
		textAway.setBounds(225, 50, 50, 25);
		this.add(textAway);

		JComboBox<String> comboAway = new JComboBox<String>();
		comboAway.addItem("");
		comboAway.setBounds(300, 50, 100, 25);
		this.add(comboAway);

		for (String team : excelLoader.getListTeam()) {
			comboLocal.addItem(team);
			comboAway.addItem(team);
		}
		
		JLabel textReferee = new JLabel();
		textReferee.setText("Árbitro");
		textReferee.setBounds(425, 50, 50, 25);
		this.add(textReferee);

		JComboBox<String> comboReferee = new JComboBox<String>();
		comboReferee.addItem("");
		comboReferee.setBounds(500, 50, 150, 25);
		this.add(comboReferee);
		
		for (String referee : excelLoader.getListReferee()) {
			comboReferee.addItem(referee);
		}
		
		JButton boton = new JButton();
		boton.setText("Mostrar Estadísticas");
		boton.setBounds(225, 100, 200, 30);
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String teamLocal = (String) comboLocal.getSelectedItem();
				String teamAway = (String) comboAway.getSelectedItem();
				String referee = (String) comboReferee.getSelectedItem();
				
				log.info("Se va a generar estadísticas para el partido: " + teamLocal + " - " + teamAway + " (" + referee + ")");
				
				statsTextArea.setText("");
				StatsProcess process = new StatsProcess(teamLocal, teamAway, referee, 
						excelLoader.getMapTeamCache(), excelLoader.getMapRefereeCache());
				statsTextArea.setText(process.getStats());
			}
		});
		
		this.add(boton);
		
		statsTextArea = new JTextArea();
		statsTextArea.setBounds(50, 150, 600, 200);
		this.add(statsTextArea);
	}
}
