package org.spotifumtp37.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.spotifumtp37.model.SpotifUMData;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonDataParser {
    private final Gson gson;

    public JsonDataParser() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Desserializa todo o estado de SpotifUMData a partir de um ficheiro JSON.
     */
    public SpotifUMData fromJsonData(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, SpotifUMData.class);
        }
    }

    /**
     * Serializa todo o estado de SpotifUMData para um ficheiro JSON.
     */
    public void toJsonData(SpotifUMData data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        }
    }
}