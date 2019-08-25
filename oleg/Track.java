package Playlist2;

/**
 * Java Bean класс Track, представляющий информацию о музыкальном произведении. Класс содержит следующие данные:
 * -- Название произведения (title)
 * -- Продолжительность в миллисекундах (length)
 * -- Имя исполнителя (artist)
 * -- Жанр (genre)
 * -- Рейтинг (вещественное число от 0 до 5) (rating)
 * <p>
 * Для класса переопределен метод toSrting():
 * этот метод возвращает строку в следующем формате:
 * <title> [mm:ss] - <artist> - <genre>, <rating>
 * Были добавлены два метода (equals(Track track), intersection(Track track))
 * именно в этот класс, для дальнейшего удобства в их применении.
 * Изначально их тут не было.
 *
 * @author Oleg Syrvachev
 * @version 2.2
 */
public class Track implements java.io.Serializable {

    private String title;
    private int length;
    private String artist, genre;
    private double rating;

    public Track() {
        this.title = this.genre = this.artist = "";
        this.length = 0;
        this.rating = 0.0;
    }

    public Track(String title, int length, String artist, String genre, double rating) {
        this.title = title;
        this.length = length;
        this.artist = artist;
        this.genre = genre;
        this.rating = rating;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    void setArtist(String artist) {
        this.artist = artist;
    }

    String getArtist() {
        return artist;
    }

    void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return this.title + " [" + (this.length / 60000) + ":" + ((this.length / 1000) % 60) + "] - "
                + this.artist + " - " + this.genre + ", " + this.rating;
    }

    // Два класса для сравнения треков. Оба возвращают boolean. Первый: equals(Track track) возвращает true,
    // если track равен текущему треку. Второй: intersection(Track track) возвращает true, если любой из атрибутов
    // равен соответсвующему атрибуту текущего.
    boolean equals(Track track) {
        if ((track == null) || (track == new Track("", 0, "", "", 0.0))) {
            return false;
        }
        return this.title.equals(track.getTitle())
                && this.artist.equals(track.getArtist())
                && (this.length == track.getLength())
                && this.genre.equals(track.getGenre())
                && (this.rating == track.getRating());
    }

    boolean intersection(Track track) {
        if ((track == null) || (track == new Track("", 0, "", "", 0.0))) {
            return false;
        }
        return this.getTitle().equals(track.getTitle())
                || (this.getLength() == track.getLength())
                || this.getArtist().equals(track.getArtist())
                || this.getGenre().equals(track.getGenre())
                || (this.getRating() == track.getRating());
    }
}
