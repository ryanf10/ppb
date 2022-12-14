package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaps.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button btnGo, btnCari;
    private EditText lat, lng, zoom, namaLokasi;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.sattelite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);
        zoom = findViewById(R.id.zoom);
        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                double latitude = Double.parseDouble(lat.getText().toString());
                double longitude = Double.parseDouble(lng.getText().toString());
                int z = Integer.parseInt(zoom.getText().toString());
                goToPeta(latitude, longitude, z);
            }
        });

        namaLokasi = findViewById(R.id.namaLokasi);
        btnCari = findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                goCari(namaLokasi.getText().toString());
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in ITS and move the camera
        LatLng its = new LatLng(-7.282518, 112.795026);
        mMap.addMarker(new MarkerOptions().position(its).title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(its, 16));
    }

    private void goCari(String namaLokasi){
        Geocoder g = new Geocoder(getBaseContext());
        try {
            List<Address> daftar =
                    g.getFromLocationName(namaLokasi, 1);
            if (daftar.size() > 0) {
                Address address = daftar.get(0);
                String nemuAlamat = address.getAddressLine(0);
                Double lintang = address.getLatitude();
                Double bujur = address.getLongitude();
                goToPeta(lintang, bujur, 16);


                lat.setText(lintang.toString());
                lng.setText(bujur.toString());
                Toast.makeText(getBaseContext(), "Location " + nemuAlamat, Toast.LENGTH_SHORT).show();
                hitungJarakDariIts(lintang, bujur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToPeta(double lat, double lng, int zoom) {
        LatLng newLoc = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(newLoc).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, zoom));
    }

    private void closeKeyboard(View v) {
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void hitungJarakDariIts(double latTujuan, double lngTujuan){
        hitungJarak(-7.282518, 112.795026, latTujuan, lngTujuan, "Jarak dari ITS");
    }

    private void hitungJarak(double latAsal, Double lngAsal,
                             double latTujuan, double lngTujuan, String keterangan) {
        Location asal = new Location("asal");
        Location tujuan = new Location("tujuan");
        tujuan.setLatitude(latTujuan);
        tujuan.setLongitude(lngTujuan);
        asal.setLatitude(latAsal);
        asal.setLongitude(lngAsal);

        float jarak = (float) asal.distanceTo(tujuan) / 1000;
        String jaraknya = String.valueOf(jarak);
        Toast.makeText(getBaseContext(), keterangan + ": " + jaraknya + " km ",
                Toast.LENGTH_SHORT).show();
    }

}