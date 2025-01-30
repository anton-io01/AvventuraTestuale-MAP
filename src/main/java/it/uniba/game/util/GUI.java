// it/uniba/game/util/GUI.java
package it.uniba.game.util;

import it.uniba.game.action.AzioneGlobale;
import it.uniba.game.action.GestoreAzioni;
import it.uniba.game.database.DatabaseInitializer;
import it.uniba.game.database.dao.OggettoDAO;
import it.uniba.game.database.dao.StanzaDAO;
import it.uniba.game.entity.Giocatore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame {
    private JTextArea mainTextArea;
    private JPanel sidePanel;
    private JTextField inputField;
    private boolean isInventoryShowing = false;
    private boolean isMapShowing = false;
    private AzioneGlobale azioneGlobale;
    private JLabel timerLabel;    // Label per il timer
    private ScheduledExecutorService executor; //Gestione delle chiamate thread periodiche
    private int timeRemaining = 7200;  // 2 ore in seconds
    private GestoreAzioni gestoreAzioni;
    private Giocatore giocatore;
    private Parser parser;
    private StanzaDAO stanzaDAO;
    private OggettoDAO oggettoDAO;


    public GUI() {
        super("Gioco Avventura Testuale");
        setupFrame();
        setupComponents();
        initializeGame();




    }



    private void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BorderLayout());


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setupComponents() {
        Color darkBg = new Color(15, 23, 42);
        Color panelBg = new Color(30, 41, 59);
        Color textColor = new Color(203, 213, 225);
        Color accentRed = new Color(239, 68, 68);
        Color accentOrange = new Color(251, 146, 60);
        Color accentGreen = new Color(74, 222, 128);
        Color accentPurple = new Color(192, 132, 252);



        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("Nuova Partita");
        JMenuItem saveItem = new JMenuItem("Salva");
        JMenuItem exitItem = new JMenuItem("Esci");

        fileMenu.add(newGameItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        newGameItem.addActionListener(e -> {
            resetGame();
        });
        saveItem.addActionListener(e -> {
            String output = (String) processCommand("salva");
            if(output !=null){
                appendToMainText(output);

            }
        });


        exitItem.addActionListener(e -> System.exit(0));


        // Top panel with BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(panelBg);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));


        // Timer panel for left side (Center Alignment VERTICAL)
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        timerPanel.setBackground(panelBg);
        timerLabel = new JLabel(" ");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        timerPanel.add(timerLabel);


        // Buttons panel for right side
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonsPanel.setBackground(panelBg);


        JButton helpButton = new JButton("AIUTO");
        helpButton.setForeground(accentRed);
        helpButton.setFont(new Font("Consolas", Font.BOLD, 14));
        JButton mapButton = new JButton("MAPPA");
        mapButton.setForeground(accentOrange);
        mapButton.setFont(new Font("Consolas", Font.BOLD, 14));

        JButton invButton = new JButton("INVENTARIO");
        invButton.setForeground(accentGreen);
        invButton.setFont(new Font("Consolas", Font.BOLD, 14));
        buttonsPanel.add(helpButton);
        buttonsPanel.add(mapButton);
        buttonsPanel.add(invButton);



        // Add panels to topPanel
        topPanel.add(timerPanel, BorderLayout.WEST);
        topPanel.add(buttonsPanel, BorderLayout.EAST);


        add(topPanel, BorderLayout.NORTH);
        // Main text area
        mainTextArea = new JTextArea();
        mainTextArea.setBackground(darkBg);
        mainTextArea.setForeground(textColor);
        mainTextArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(mainTextArea);
        scrollPane.setBackground(darkBg);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        // Side panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(panelBg);
        sidePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        sidePanel.setPreferredSize(new Dimension(0, 0));
        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(darkBg);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(sidePanel, BorderLayout.EAST);
        add(contentPanel, BorderLayout.CENTER);

        // Bottom Panel (Input)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(panelBg);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(panelBg);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        inputField = new JTextField();
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputField.setBackground(new Color(51, 65, 85));
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JButton sendButton = new JButton("INVIA");
        sendButton.setFont(new Font("Consolas", Font.BOLD, 14));
        sendButton.setForeground(accentPurple);


        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.add(inputPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        mapButton.addActionListener(e -> {
            String output = (String) processCommand("mappa");
            if(output!= null){
                appendToMainText(output);
                isMapShowing = false;
                isInventoryShowing = false;
                updateSidePanel();
            }
        });

        invButton.addActionListener(e -> {
            isInventoryShowing = !isInventoryShowing;
            isMapShowing = false;
            updateSidePanel();
        });

        helpButton.addActionListener(e -> {
            String output = (String) processCommand("aiuto");
            if (output != null) {
                appendToMainText(output);
                isMapShowing = false;
                isInventoryShowing = false;
                updateSidePanel();

            }
        });

        ActionListener sendAction = e -> {
            String text = inputField.getText().trim().toLowerCase();
            if (!text.isEmpty()) {
                appendToMainText("> " + text + "\n\n");
                Object output = processCommand(text);
                handleCommandOutput(text, output);
                inputField.setText("");

            }
        };
        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);
    }
    private void handleCommandOutput(String text, Object output) {
        if (text.startsWith("inventario")) {
            isInventoryShowing = true;
            isMapShowing = false;
            updateSidePanel();
        }else if (text.startsWith("mappa")) {
            if (output instanceof String) {
                appendToMainText((String) output);
                isMapShowing = false;
                isInventoryShowing = false;
                updateSidePanel();
            }
        }
        else if(output instanceof String){
            appendToMainText((String) output);
            isMapShowing = false;
            isInventoryShowing = false;
            updateSidePanel();
        } else{
            isInventoryShowing = false;
            isMapShowing = false;
            updateSidePanel();

        }
    }
    private void initializeGame(){
        // Inizializzazione del database
        try{
            DatabaseInitializer.initializeDatabase(false);
            //istanzio i DAO
            stanzaDAO = new StanzaDAO();
            oggettoDAO = new OggettoDAO();
            gestoreAzioni = new GestoreAzioni();
            this.parser = new Parser();


            //Creazione del giocatore di default all'avvio del game.
            giocatore = new Giocatore();
            //Imposto posizione iniziale
            if(giocatore.getPosizioneAttuale()==null) { // set posizione default / lista oggetti
                giocatore.setPosizioneAttuale(stanzaDAO.getStanzaById("02"));
                giocatore.aggiungiOggetto(oggettoDAO.getOggettoById("01"));
            }
        }catch (Exception e){
            System.err.println("Errore durante l'inizializzazione del gioco:");
            e.printStackTrace();
        }
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            timeRemaining--;

            String hours = String.format("%02d", timeRemaining / 3600);
            String minutes = String.format("%02d", (timeRemaining % 3600) / 60);
            String seconds = String.format("%02d", timeRemaining % 60);



            if(timeRemaining <=0) {
                timerLabel.setText("Tempo scaduto. GAME OVER");
                executor.shutdown();

            }   else{

                timerLabel.setText("Timer: " + hours + ":" + minutes + ":" + seconds);

            }
        }, 0, 1, TimeUnit.SECONDS);
        appendToMainText("L'oscurità di Neo Tokyo nasconde sempre dei segreti. Quella di questa notte è particolarmente minacciosa.\n\n");
        appendToMainText("Sei un agente della polizia di Neo Tokyo, abituato alle notti insonni e ai vicoli malfamati. " +
                "Tuttavia, la chiamata di stanotte ha una nota diversa: l'ospedale centrale ha segnalato una morte sospetta.\n\n");
        appendToMainText("Un paziente, apparentemente in ottima salute, è stato stroncato da un improvviso arresto cardiaco. " +
                "Il referto parla di malfunzionamento del pacemaker, ma una serie di decessi simili nei giorni scorsi hanno allertato l'ospedale.\n\n");
        appendToMainText("Una macchia d'ombra si allunga su questi casi, e la tua esperienza ti dice che non si tratta di semplici coincidenze. " +
                "L'ospedale ha deciso di non tacere e ha chiamato la polizia, sperando di trovare una risposta alla radice di questi eventi.\n\n");
        appendToMainText("Ti trovi nell'ingresso dell'ospedale di Neo Tokyo, l'odore di disinfettante si mescola con la tensione nell'aria.\n\n");
        appendToMainText("La tua auto è parcheggiata fuori dall'edificio, pronta a portarti dove l'indagine ti condurrà. " +
                "Per raggiungerla, ti basterà uscire dall'ospedale e digitare il comando 'usa auto'. (Puoi usare l'auto per spostarti tra i vari edifici, ma solo se ne conosci la posizione).\n\n");
        appendToMainText("Premi il pulsante 'AIUTO' o digita 'aiuto' per scoprire i comandi disponibili e iniziare a svelare questo mistero.\n\n");


        setLocationRelativeTo(null);
        this.setVisible(true); //imposto il frame come visibile solo al termine dell'inizializzazione.

    }
    private void resetGame() {
        DatabaseInitializer.initializeDatabase(true);

        try {
            //istanzio i DAO
            stanzaDAO = new StanzaDAO();
            oggettoDAO = new OggettoDAO();
            gestoreAzioni = new GestoreAzioni();

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.parser = new Parser();//istanza parser qui in modo tale che il metodo processCommand non dia eccezione dopo il click
        timeRemaining = 7200;  // Reset del timer a 2 ore in secondi
        mainTextArea.setText("");    // Pulisci l'area di testo principale
        this.giocatore = new Giocatore();    // Crea un nuovo giocatore
        if(giocatore.getPosizioneAttuale()==null) { // set posizione default / lista oggetti
            try {
                giocatore.setPosizioneAttuale(stanzaDAO.getStanzaById("02"));
                giocatore.aggiungiOggetto(oggettoDAO.getOggettoById("01"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        //riavvio il timer con gli stessi dati
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            timeRemaining--;

            String hours = String.format("%02d", timeRemaining / 3600);
            String minutes = String.format("%02d", (timeRemaining % 3600) / 60);
            String seconds = String.format("%02d", timeRemaining % 60);


            if (timeRemaining <= 0) {
                timerLabel.setText("Tempo scaduto. GAME OVER");
                executor.shutdown();

            } else {
                timerLabel.setText("Timer: " + hours + ":" + minutes + ":" + seconds);

            }
        }, 0, 1, TimeUnit.SECONDS);
        appendToMainText("L'oscurità di Neo Tokyo nasconde sempre dei segreti. Quella di questa notte è particolarmente minacciosa.\n\n");
        appendToMainText("Sei un agente della polizia di Neo Tokyo, abituato alle notti insonni e ai vicoli malfamati. " +
                "Tuttavia, la chiamata di stanotte ha una nota diversa: l'ospedale centrale ha segnalato una morte sospetta.\n\n");
        appendToMainText("Un paziente, apparentemente in ottima salute, è stato stroncato da un improvviso arresto cardiaco. " +
                "Il referto parla di malfunzionamento del pacemaker, ma una serie di decessi simili nei giorni scorsi hanno allertato l'ospedale.\n\n");
        appendToMainText("Una macchia d'ombra si allunga su questi casi, e la tua esperienza ti dice che non si tratta di semplici coincidenze. " +
                "L'ospedale ha deciso di non tacere e ha chiamato la polizia, sperando di trovare una risposta alla radice di questi eventi.\n\n");
        appendToMainText("Ti trovi nell'ingresso dell'ospedale di Neo Tokyo, l'odore di disinfettante si mescola con la tensione nell'aria.\n\n");
        appendToMainText("La tua auto è parcheggiata fuori dall'edificio, pronta a portarti dove l'indagine ti condurrà. " +
                "Per raggiungerla, ti basterà uscire dall'ospedale e digitare il comando 'usa auto'. (Puoi usare l'auto per spostarti tra i vari edifici, ma solo se ne conosci la posizione).\n\n");
        appendToMainText("Premi il pulsante 'AIUTO' o digita 'aiuto' per scoprire i comandi disponibili e iniziare a svelare questo mistero.\n\n");

        isInventoryShowing = false;
        isMapShowing = false;

        updateSidePanel();



    }
    private void updateSidePanel() {
        sidePanel.removeAll();
        if (!isMapShowing && !isInventoryShowing) {
            sidePanel.setPreferredSize(new Dimension(0, 0));

        }   else {
            sidePanel.setPreferredSize(new Dimension(250, 0));

            if (isInventoryShowing) {
                showInventory();
            }else if (isMapShowing) {
                showMap();
            }
        }
        sidePanel.revalidate();
        sidePanel.repaint();
        this.revalidate();
    }

    private void showInventory() {
        JLabel titleLabel = new JLabel("Inventario");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(titleLabel);
        sidePanel.add(Box.createVerticalStrut(10));


        String output = (String) processCommand("inventario");
        if (output != null) {

            String[] items = output.split("\n");
            for (String item : items) {
                if (!item.trim().isEmpty()) {
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(new GridBagLayout());
                    itemPanel.setBackground(new Color(51, 65, 85));
                    itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    itemPanel.setMaximumSize(new Dimension(230, 50));
                    itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel itemLabel = new JLabel(item.replace("- ", ""));
                    Font currentFont = itemLabel.getFont();
                    Font newFont = currentFont.deriveFont(currentFont.getSize() + 2f);

                    itemLabel.setForeground(Color.WHITE);
                    itemLabel.setFont(newFont);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 1.0;
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    itemPanel.add(itemLabel, gbc);
                    sidePanel.add(itemPanel);
                    sidePanel.add(Box.createVerticalStrut(5));

                }

            }

        }
    }

    private void showMap() {
        JLabel titleLabel = new JLabel("Mappa");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(30, 41, 59));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(titleLabel);
        sidePanel.add(titlePanel);
        sidePanel.add(Box.createVerticalStrut(10));
        String output = (String) processCommand("mappa");

        JTextArea textArea = new JTextArea(output);
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 41, 59));
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(textArea);

    }


    private void appendToMainText(String text) {
        try {
            String utf8Text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            mainTextArea.append(utf8Text);
            mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());

        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object processCommand(String input) {
        return gestoreAzioni.esegui(giocatore, parser.parseInput(input));
    }
}