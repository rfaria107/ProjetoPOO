package org.spotifumtp37.util;

import com.google.gson.*;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.album.MultimediaSong;
import org.spotifumtp37.model.album.ExplicitSong;

import java.lang.reflect.Type;

public class SongTypeAdapter implements JsonSerializer<Song>, JsonDeserializer<Song> {
    @Override
    public Song deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        boolean isMultimedia = obj.has("multimedia") && obj.get("multimedia").getAsBoolean();
        boolean isExplicit = obj.has("explicit") && obj.get("explicit").getAsBoolean();

        if (isMultimedia) {
            MultimediaSong song = context.deserialize(json, MultimediaSong.class);
            if (obj.has("videoLink")) {
                song.setVideoLink(obj.get("videoLink").getAsString());
            }
            return song;
        } else if (isExplicit) {
            return context.deserialize(json, ExplicitSong.class);
        } else {
            return context.deserialize(json, Song.class);
        }
    }

    @Override
    public JsonElement serialize(Song src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = context.serialize(src, src.getClass()).getAsJsonObject();
        if (src instanceof MultimediaSong) {
            obj.addProperty("multimedia", true);
            String videoLink = ((MultimediaSong) src).getVideoLink();
            if (videoLink != null) {
                obj.addProperty("videoLink", videoLink);
            }
        } else if (src instanceof ExplicitSong) {
            obj.addProperty("explicit", true);
        }
        return obj;
    }
}