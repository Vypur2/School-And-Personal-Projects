//MICHAEL AZZINARO, SCOTT CORBETT

package apps;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.event.*;

import Structures.Song;

public class SongLib extends JFrame {

	JPanel songPanel, infoPanel;
	JList songList;
	DefaultListModel model = new DefaultListModel();
	JLabel infoSong;									 //NOTE(mike):labels need to be set in constructor
	JLabel infoArtist = new JLabel("Not Yet Set");
	JLabel infoAlbum = new JLabel("Not Yet Set");
	JLabel infoYear = new JLabel("Not Yet Set");
	JLabel addError = new JLabel("Song already exists");
	JLabel editError = new JLabel("Song already exists");
	
	JButton addButton = new JButton("Add Song");
	JButton deleteButton = new JButton("Delete Song");
	JButton editButton = new JButton("Edit Song");
	JButton saveButton = new JButton("Save Track List");
	JButton addConfirmButton = new JButton("Confirm?");
	JButton editConfirmButton = new JButton("Confirm?");
	JButton addCancelButton = new JButton("Cancel");
	JButton editCancelButton = new JButton("Cancel");
	static int initial;
	public SongLib(String title,ArrayList<Song> Songs){
		//reads all songs
		//readSongs("SongList.txt");


		setLayout(new FlowLayout());
		
		JPanel songPanel = new JPanel();
		JPanel infoPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel editPanel = new JPanel();
		JPanel addPanel = new JPanel();
		int songNumbers = Songs.size();
		
		int fieldWidth = 15;

		JTextField addName = new JTextField("The Song's Name", fieldWidth);
		JTextField addArtist = new JTextField("The Song's Artist",fieldWidth);
		JTextField addAlbum = new JTextField("The Song's Album",fieldWidth);
		JTextField addYear = new JTextField("The Song's Year",fieldWidth);
		JTextField editName = new JTextField(fieldWidth);
		JTextField editArtist = new JTextField(fieldWidth);
		JTextField editAlbum = new JTextField(fieldWidth);
		JTextField editYear = new JTextField(fieldWidth);
	
		/*
		String[] songTitles = new String[Songs.size()];
		int i = 0;
		while (i < Songs.size()){
			songTitles[i] = Songs.get(i).name;
			i++;
		} 
		*/
		
		
		int i = 0;
		while (i < Songs.size()){
			model.addElement(Songs.get(i).name);
			i++;
		} 

		sort(Songs);
		
		songList = new JList(model);

		songList.setLayout(new FlowLayout(FlowLayout.LEADING));
		songList.setSelectedIndex(0);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		songList.setLayoutOrientation(JList.VERTICAL);
		songList.setPreferredSize(new Dimension(200, 230));
		songList.setVisibleRowCount(-1);
		
		JScrollPane songListScroll = new JScrollPane(songList);
		songListScroll.setPreferredSize(new Dimension(220, 240));
		songListScroll.setViewportView(songList);
		songListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		songPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		infoPanel.setLayout(new GridLayout(4,2));
		editPanel.setLayout(new GridLayout(6,2));
		addPanel.setLayout(new GridLayout(6,2));
		
		songPanel.setPreferredSize(new Dimension(240, 250));
		infoPanel.setPreferredSize(new Dimension(240, 250));
		editPanel.setPreferredSize(new Dimension(240, 250));
		addPanel.setPreferredSize(new Dimension(240, 250));
		songPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		editPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		songPanel.add(songListScroll);

		
		infoSong = new JLabel(model.get(songList.getSelectedIndex()).toString());
		infoPanel.add(new JLabel("Name :                     "));
		infoPanel.add(infoSong);
		infoPanel.add(new JLabel("Artist :                   "));
		infoArtist.setText(Songs.get(songList.getSelectedIndex()).artist);
		infoPanel.add(infoArtist);
		infoPanel.add(new JLabel("Album :                    "));
		infoAlbum.setText(Songs.get(songList.getSelectedIndex()).album);
		infoPanel.add(infoAlbum);
		infoYear.setText(Songs.get(songList.getSelectedIndex()).year);
		infoPanel.add(new JLabel("Year :                     "));
		infoPanel.add(infoYear);
		
		addPanel.add(new JLabel("Name :"));
		addPanel.add(addName);
		addPanel.add(new JLabel("Artist :"));
		addPanel.add(addArtist);
		addPanel.add(new JLabel("Album :"));
		addPanel.add(addAlbum);
		addPanel.add(new JLabel("Year :"));
		addPanel.add(addYear);
		addPanel.add(addConfirmButton);
		addPanel.add(addCancelButton);
		
		editPanel.add(new JLabel("Name :"));
		editPanel.add(editName);
		editPanel.add(new JLabel("Artist :"));
		editPanel.add(editArtist);
		editPanel.add(new JLabel("Album :"));
		editPanel.add(editAlbum);
		editPanel.add(new JLabel("Year :"));
		editPanel.add(editYear);
		editPanel.add(editConfirmButton);
		editPanel.add(editCancelButton);
		
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);
		buttonPanel.add(saveButton);
		
		this.add(songPanel);
		this.add(infoPanel);
		this.add(buttonPanel);

		//on selection change

		
		ListSelectionListener ls = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if (songList.getSelectedIndex() < 0 ){
					songList.setSelectedIndex(0);
				} else if (songList.getSelectedIndex() >= Songs.size()){
					songList.setSelectedIndex(0);
				}
				infoSong.setText(Songs.get(songList.getSelectedIndex()).name);
				infoArtist.setText(Songs.get(songList.getSelectedIndex()).artist);
				infoAlbum.setText(Songs.get(songList.getSelectedIndex()).album);
				infoYear.setText(Songs.get(songList.getSelectedIndex()).year);

			}
		};

		songList.addListSelectionListener(ls);
		
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//model.addElement(Songs.get(1));

				//NOTE(mike): update song list
				
				//(Scott): On click, it removes infoPanel and adds addPanel
				remove(infoPanel);
				remove(buttonPanel);
				add(addPanel);
				add(buttonPanel);
				revalidate();
				repaint();
			}
		});

		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				int j = songList.getSelectedIndex();
				songList.setSelectedIndex(0);	
				model.removeElement(Songs.get(j));
				Songs.remove(j);
				String[] songTitles = new String[Songs.size()];
				int i = 0;
				while (i < Songs.size()){
					songTitles[i] = Songs.get(i).name;
					i++;
				} 
				songList.setListData(songTitles);
				songList.setSelectedIndex(0);
						
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//(Scott): On click, it removes infoPanel and adds editPanel
				
				editName.setText(songList.getSelectedValue().toString());
				editArtist.setText(Songs.get(songList.getSelectedIndex()).artist);
				editAlbum.setText(Songs.get(songList.getSelectedIndex()).album);
				editYear.setText(Songs.get(songList.getSelectedIndex()).year);
				
				remove(infoPanel);
				remove(buttonPanel);
				add(editPanel);
				add(buttonPanel);
				revalidate();
				repaint();
			}
		});


		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				writeSongs(Songs);
			}
		});	
		
		addConfirmButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				if(!addName.getText().isEmpty() && !addArtist.getText().isEmpty()){
					if(legalAddEdit(Songs, addName.getText(), addArtist.getText())){
					Song s = new Song(addName.getText(), addArtist.getText(), addAlbum.getText(), addYear.getText());
//finds location of ordered song.
					int y = 0;

					while (s.name.toLowerCase().compareTo(Songs.get(y).name.toLowerCase()) > 0){
						
						y++;
						if (y >= Songs.size()){
							break;
						}
					}
					
					Songs.add(y,s);
					model.add(y,s.name);
					songList.setSelectedValue(s,true);
					
					//(Scott): On click, it removes addPanel and adds infoPanel
					remove(addPanel);
					remove(buttonPanel);
					add(infoPanel);
					add(buttonPanel);
					addPanel.remove(addError);
					revalidate();
					repaint();
					songList.setSelectedIndex(y);
					}
				}
				
				else{
					addPanel.add(addError);
					revalidate();
					repaint();
				}
			}
		});
		
		addCancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				//(Scott): On click, it removes addPanel and adds infoPanel
				remove(addPanel);
				remove(buttonPanel);
				add(infoPanel);
				add(buttonPanel);
				addPanel.remove(addError);
				revalidate();
				repaint();
			}
		});
		
		editConfirmButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(legalAddEdit(Songs, editName.getText(), editArtist.getText())){

					Songs.get(songList.getSelectedIndex()).name = editName.getText();
					Songs.get(songList.getSelectedIndex()).artist = editArtist.getText();
					Songs.get(songList.getSelectedIndex()).album = editAlbum.getText();
					Songs.get(songList.getSelectedIndex()).year = editYear.getText();
					int selec = songList.getSelectedIndex();
					Song s = Songs.get(selec);

					Songs.remove(selec);
					model.remove(songList.getSelectedIndex());
					
					int y = 0;
					
					while (s.name.toLowerCase().compareTo(Songs.get(y).name.toLowerCase()) > 0){
						y++;
						if (y >= Songs.size()){
							break;
						}
					}		

					Songs.add(y,s);
					model.add(y,s.name);
					songList.setSelectedIndex(y);
					//(Scott): On click, it removes editPanel and adds infoPanel
					remove(editPanel);
					remove(buttonPanel);
					add(infoPanel);
					add(buttonPanel);
					editPanel.remove(editError);
					revalidate();
					repaint();
				}
				
				else{
					editPanel.add(editError);
					revalidate();
					repaint();
				}
			}
		});
		
		editCancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				//(Scott): On click, it removes editPanel and adds infoPanel
				remove(editPanel);
				remove(buttonPanel);
				add(infoPanel);
				add(buttonPanel);
				revalidate();
				repaint();
			}
		});
	}

	public static void main(String [ ] args){
		ArrayList<Song> Sng = new ArrayList<Song>();
		Sng = readSongs("SongList.txt");
		JFrame lib = new SongLib("My Song Library",Sng);
		lib.setSize(500,400);
		lib.setResizable(false);
		lib.setLocationRelativeTo(null);
		lib.setVisible(true);
		lib.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Song selectedSong = null;
	}
	
	public boolean legalAddEdit(ArrayList<Song> Songs, String name, String artist){
		
		for(int i=0; i<Songs.size(); i++){
			if(Songs.get(i).name.equals(name) && Songs.get(i).artist.equals(artist)){
				return false;
			}
		}
		return true;
	}
	
	public void saveSongList(String docName){
		
	}

	public void sort(ArrayList<Song> Songs){

		model.clear();

		int j = 0;
		int k = j+1;
		while (j < Songs.size()){
			while (k < Songs.size()){
				if(Songs.get(k).name.toLowerCase().compareTo(Songs.get(j).name.toLowerCase()) < 0){
					Song temp = Songs.get(j);
					Songs.set(j,Songs.get(k));
					Songs.set(k,temp);
				}
				k++;
			}
			j++;
			k=j+1;
		} 
	
		int i = 0;
		while (i < Songs.size()){
			model.addElement(Songs.get(i).name);
			i++;
		} 
	}

	public static ArrayList<Song> readSongs(String docName){
		
		String curLn = "";
		ArrayList<Song> sSongs = new ArrayList<Song>();
		if (initial != sSongs.size()){
				return sSongs;
		} else {
		try (BufferedReader br = new BufferedReader(new FileReader(docName)))
		{
			
			while ((curLn = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(curLn,"-/]");
				while (st.hasMoreElements())
				{
					String sname = st.nextToken();
					sname=sname.trim();
					String sartist = st.nextToken();
					sartist = sartist.trim();
					String salbum = st.nextToken();
					salbum = salbum.trim();
					String syear = st.nextToken();
					syear = syear.trim();
					Song s = new Song(sname, sartist,salbum,syear);
					sSongs.add(s);
					initial = initial + 1;
				}
			}

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	int i = 0;
	while (i< sSongs.size()){
		i++;
	}
	return sSongs;
/*
		StringTokenizer st = new StringTokenizer(curLn);
		while (st.hasNextElement()){
			System.out.println(st.nextToken());
		}
		*/
	}

	public void writeSongs(ArrayList<Song> Songs){
		try {
			String content = "this is a test";

			PrintWriter writer = new PrintWriter("SongList.txt","UTF-8");

			int i = 0;

			while (i < Songs.size()){
				writer.println(Songs.get(i).toString());
				i++;
			}		
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
