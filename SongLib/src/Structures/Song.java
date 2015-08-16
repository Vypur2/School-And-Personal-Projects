//MICHAEL AZZINARO, SCOTT CORBETT

package Structures;

public class Song //implements Comparable<Song>
{
	public String name;
	public String artist;
	public String album;
	public String year;
	public Song(String name,String artist,String album, String year){
		this.name = name;
		this.album = album;
		this.artist = artist;
		this.year = year;
	}

	public String toString(){
		return (this.name + " - " + this.artist + " - " + this.album + " - " + this.year + " ]");
	}

	//@Override
	//public int compareTo(Song other){
	//	int num = this.name.compareTo(other.name);
	//	return num;
	//}
}