package com.test.musicplayer;

public class SongInfo {
    String songName;
    String artistName;
    String albumName;
    String websiteURL;

    public SongInfo() {

    }

    public SongInfo(String song, String artist, String album, String websiteURL) {
        songName = song;
        artistName = artist;
        albumName = album;
        this.websiteURL = websiteURL;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getURL() {
        return websiteURL;
    }
}
