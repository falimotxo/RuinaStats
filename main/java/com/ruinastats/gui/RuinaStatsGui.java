package com.ruinastats.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ruinastats.loader.ExcelLoader;
import com.ruinastats.process.StatsProcess;

public class RuinaStatsGui extends JFrame {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	private static final String FIRST_DIVISION = "1 División";
	private static final String SECOND_DIVISION = "2 División";
	
	private static final Logger log = Logger.getLogger(RuinaStatsGui.class.getName());

	/** Carga de datos del excel */
	private ExcelLoader excelLoader;

	/** Área donde se mostrará la información de estadísticas */
	private JTextArea statsTextArea;
	/** Combo de equipo local */
	private JComboBox<String> comboLocal;
	/** Combo de equipo visitante */
	private JComboBox<String> comboAway;
	/** Combo de árbitros */
	private JComboBox<String> comboReferee;

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
		// creamos los componentes
		JLabel textDivision = new JLabel();
		textDivision.setText("Competición");
		textDivision.setBounds(225, 10, 100, 25);
		this.add(textDivision);

		JComboBox<String> comboDivision = new JComboBox<String>();
		comboDivision.addItem(FIRST_DIVISION);
		comboDivision.addItem(SECOND_DIVISION);
		comboDivision.setBounds(300, 10, 100, 25);
		comboDivision.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String selected = (String) e.getItem();
				log.info("Consultar datos de " + selected);
				comboLocal.removeAllItems();
				comboAway.removeAllItems();
				comboReferee.removeAllItems();
				comboLocal.addItem("");
				comboAway.addItem("");
				comboReferee.addItem("");
				if(selected.equals(FIRST_DIVISION)) {
					for (String team : excelLoader.getListTeam1()) {
						comboLocal.addItem(team);
						comboAway.addItem(team);
					}
					for (String referee : excelLoader.getListReferee1()) {
						comboReferee.addItem(referee);
					}
				} else if(selected.equals(SECOND_DIVISION)) {
					for (String team : excelLoader.getListTeam2()) {
						comboLocal.addItem(team);
						comboAway.addItem(team);
					}
					for (String referee : excelLoader.getListReferee2()) {
						comboReferee.addItem(referee);
					}
				}
			}
		});
		this.add(comboDivision);

		JLabel textLocal = new JLabel();
		textLocal.setText("Local");
		textLocal.setBounds(50, 50, 50, 25);
		this.add(textLocal);

		comboLocal = new JComboBox<String>();
		comboLocal.addItem("");
		comboLocal.setBounds(100, 50, 100, 25);
		this.add(comboLocal);

		JLabel textAway = new JLabel();
		textAway.setText("Visitante");
		textAway.setBounds(225, 50, 50, 25);
		this.add(textAway);

		comboAway = new JComboBox<String>();
		comboAway.addItem("");
		comboAway.setBounds(300, 50, 100, 25);
		this.add(comboAway);

		for (String team : excelLoader.getListTeam1()) {
			comboLocal.addItem(team);
			comboAway.addItem(team);
		}

		JLabel textReferee = new JLabel();
		textReferee.setText("Árbitro");
		textReferee.setBounds(425, 50, 50, 25);
		this.add(textReferee);

		comboReferee = new JComboBox<String>();
		comboReferee.addItem("");
		comboReferee.setBounds(500, 50, 150, 25);
		this.add(comboReferee);

		for (String referee : excelLoader.getListReferee1()) {
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

				if(!teamLocal.equals("") && !teamAway.equals("") && !teamLocal.equals(teamAway)) {
					if(!referee.equals("")) {
						log.info("Se va a generar estadísticas para el partido: " + teamLocal + " - " + teamAway + " ("
								+ referee + ")");
						
						statsTextArea.setText("");
						StatsProcess process = new StatsProcess(teamLocal, teamAway, referee, excelLoader.getMapTeamCache(),
								excelLoader.getMapRefereeCache());
						statsTextArea.setText(process.getStats());
					} else {
						int opt = JOptionPane.showConfirmDialog(null, "No ha seleccionado árbitro, ¿deséa continuar?");
						if(opt == JOptionPane.YES_OPTION) {
							log.info("Se va a generar estadísticas para el partido: " + teamLocal + " - " + teamAway);
							statsTextArea.setText("");
							StatsProcess process = new StatsProcess(teamLocal, teamAway, null, excelLoader.getMapTeamCache(),
									excelLoader.getMapRefereeCache());
							statsTextArea.setText(process.getStats());
						}
					}
				} else if(!teamLocal.equals("") && !teamAway.equals("") && teamLocal.equals(teamAway)) {
					JOptionPane.showMessageDialog(null, "El equipo local y visitante es el mismo");
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un equipo local y un equipo visitante");
				}
				
			}
		});

		this.add(boton);

		statsTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(statsTextArea);
		scrollPane.setBounds(50, 150, 600, 200);
		this.add(scrollPane);
	}
}
