package com.myhappyisland;

import ch.hsr.geohash.GeoHash;
import com.google.gson.JsonElement;
import io.deepstream.DeepstreamClient;
import io.deepstream.Record;
import io.deepstream.RecordPathChangedCallback;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        DeepstreamClient ds = new DeepstreamClient( "localhost:6021" );
        ds.login();
        ds.event.emit( "app", "My first ever event!" );

        GeoHash geoHash = GeoHash.withCharacterPrecision(50.941303, 6.958286, 11);

        Record record = ds.record.getRecord(geoHash.toBase32());

        record.set("temperature", 22);

        record.subscribe("temperature", new RecordPathChangedCallback() {
            @Override
            public void onRecordPathChanged(String recordName, String path, JsonElement data ) {
                System.out.println( "The temperature in berlin is: " +  data.getAsString() );
            }
        });


    }
}
