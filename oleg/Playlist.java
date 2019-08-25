package Playlist2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Playlist {

    private ArrayList<Track> playlist, selectedTracks;

    public Playlist(){
        playlist = new ArrayList<>();
        selectedTracks = new ArrayList<>();
    }

    void addTrack(Track track){
        boolean add = true;
        for (Track tr : playlist) {
            if ((track.getArtist().equals(tr.getArtist())) && (tr.getTitle().equals(track.getTitle()))) {
                add = false;
                System.out.println("Уже есть такой трек у такого исполнителя");
                break;
            }
        }
        if (add) {
            playlist.add(track);
            System.out.println("Трек: " + track.toString() + " - добавлен.");
        } else {
            System.out.println("Трек: " + track.toString() + " - не добавлен.");
        }
    }

    void deleteTrack(Track track){
        boolean delete = false;
        for (Track tr : playlist) {
            if (tr.equals(track)) {
                playlist.remove(tr);
                delete = true;
                break;
            }
        }
        if (delete) {
            System.out.println("Трек: " + track.toString() + " - удален.");
        } else {
            System.out.println("Трек: " + track.toString() + " - не удален, потому что не найден.");
        }
    }

    // "мягкий" поиск - неполные совпадения
    ArrayList<Track> selectTrackByTitle(String title) {
        selectedTracks.clear();
        for (Track t : playlist) {
            if (t.getTitle().contains(title)) {
                selectedTracks.add(t);
            }
        }
        return selectedTracks;
    }

    // "жесткий поиск" - полное совпадение
    ArrayList<Track> selectTrackByArtist(String artist) {
        selectedTracks.clear();
        for (Track t : playlist) {
            if (t.getArtist().equals(artist)) {
                selectedTracks.add(t);
            }
        }
        return selectedTracks;
    }

    // "жесткий" поиск - поленое совпадение
    ArrayList<Track> selectTrackByGenre(String genre) {
        selectedTracks.clear();
        for (Track t : playlist) {
            if (t.getGenre().equals(genre)) {
                selectedTracks.add(t);
            }
        }
        return selectedTracks;
    }

    // >= rating
    ArrayList<Track> selectTrackByMinRating(double rating) {
        selectedTracks.clear();
        for (Track t : playlist) {
            if (t.getRating() >= rating) {
                selectedTracks.add(t);
            }
        }
        return selectedTracks;
    }

    // >= length
    ArrayList<Track> selectTrackByMinLength(@NotNull String notFormattedLength) {
        selectedTracks.clear();
        String[] formattedLength = notFormattedLength.split(":");
        int length = (Integer.parseInt(formattedLength[0]) * 60 + Integer.parseInt(formattedLength[1])) * 1000;
        for (Track t : playlist) {
            if (t.getLength() >= length) {
                selectedTracks.add(t);
            }
        }
        return selectedTracks;
    }

    ArrayList<Track> getPlaylist(){
        return playlist;
    }

    void setPlaylist(ArrayList<Track> arrayList){
        this.playlist = arrayList;
    }

}
