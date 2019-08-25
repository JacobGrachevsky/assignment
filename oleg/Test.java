package Playlist2;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        Track track1 = new Track("AAA", 100000, "aaa", "aA", 4.1),
                track2 = new Track("BBB", 200000, "bbb", "bB", 4.2),
                track3 = new Track("CCC", 300000, "ccc", "cC", 4.3),
                track4 = new Track("DDD", 400000, "ddd", "dD", 4.4),
                track5 = new Track("EEE", 500000, "eee", "eE", 4.5),
                track6 = new Track("Papa", 600000, "Mama", "Pop", 4.6);
        Playlist playlist = new Playlist();
        ArrayList<Track> selected;

        playlist.addTrack(track1);
        playlist.addTrack(track2);
        playlist.addTrack(track3);
        playlist.addTrack(track4);
        playlist.addTrack(track5);
        playlist.addTrack(track6);
        System.out.println("_________");

        playlist.deleteTrack(track5);
        playlist.deleteTrack(track6);
        System.out.println("_________");

        selected = playlist.selectTrackByMinRating(4.3);
        selected.forEach(System.out::println);
        System.out.println("_________");

        selected = playlist.selectTrackByMinLength("3:00");
        selected.forEach(System.out::println);
        System.out.println("_________");

        selected = playlist.selectTrackByTitle("AAA");
        selected.forEach(System.out::println);
        System.out.println("_________");

        selected = playlist.selectTrackByArtist("bbb");
        selected.forEach(System.out::println);
        System.out.println("_________");

        selected = playlist.selectTrackByGenre("cC");
        selected.forEach(System.out::println);
        System.out.println("_________");

    }
}
