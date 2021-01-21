package com.example.proyectovacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var btnHibrido: Button
    private lateinit var btnNormal: Button
    private lateinit var btnVolver: Button
    fun init(){
        btnHibrido = findViewById(R.id.btnHibrido)
        btnNormal = findViewById(R.id.btnNormal)
        btnVolver = findViewById(R.id.btnVolver)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()

        btnHibrido.setOnClickListener{
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        }

        btnNormal.setOnClickListener{
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        btnVolver.setOnClickListener{
            /*val forma2 = Intent(this@MapsActivity, GeneracionTurnos::class.java)
            startActivity(forma2)*/
            this.finish()
        }

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        CentrosAcopio(googleMap)
    }
    fun CentrosAcopio(googleMap: GoogleMap){
        mMap = googleMap
        val objetoIntent2 : Intent = intent
        var ubicacion= objetoIntent2.getStringExtra("centro_acopio")
        if (ubicacion == "Hospital los Ceibos"){
            val hospitalCeibos = LatLng(-2.1745623,-79.9434742)
            mMap.addMarker(MarkerOptions().position(hospitalCeibos).title("Hospital Ceibos").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospitalCeibos, 15.0f))
        }
        if (ubicacion == "Centro de Convenciones"){
            val  CentroConvenciones= LatLng(-2.158733,-79.8895206)
            mMap.addMarker(MarkerOptions().position(CentroConvenciones).title("Centro de Convenciones").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CentroConvenciones, 15.0f))
        }
        if (ubicacion == "Maternidad Enrique Sotomayor"){
            val MaternidadSotomayor = LatLng(-2.1986834,-79.8927948)
            mMap.addMarker(MarkerOptions().position(MaternidadSotomayor).title("Maternidad Sotomayor").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MaternidadSotomayor, 15.0f))
        }
        if (ubicacion == "Hospital del Guasmo Sur"){
            val hospitalGuasmo = LatLng(-2.276769,-79.8975766)
            mMap.addMarker(MarkerOptions().position(hospitalGuasmo).title("Hospital Guasmo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospitalGuasmo, 15.0f))
        }
    }
}

