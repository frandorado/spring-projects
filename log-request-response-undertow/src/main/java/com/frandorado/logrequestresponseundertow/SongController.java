package com.frandorado.logrequestresponseundertow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.*;

@RestController
public class SongController {
    
    @PostMapping("/songs")
    public Song createSong(@RequestBody Song song) {
        song.setId(new Random().nextLong());
        
        return song;
    }
    
    @GetMapping("/songs")
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();

        songs.add(Song.builder().id(1L).name("name1").author("author2").build());
        songs.add(Song.builder().id(2L).name("name2").author("author2").build());

        return songs;
    }

}
