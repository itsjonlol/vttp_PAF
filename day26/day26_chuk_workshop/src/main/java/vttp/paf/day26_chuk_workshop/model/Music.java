package vttp.paf.day26_chuk_workshop.model;

public class Music {
    
    private String artists;
    private String trackName;

    public Music() {

    }

   

    

    @Override
    public String toString() {
        return "Music [artists=" + artists + ", trackName=" + trackName + "]";
    }





    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

   

    
    
    
    
}
