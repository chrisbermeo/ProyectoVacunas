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
    lateinit var btnHibrido: Button
    lateinit var btnNormal: Button
    lateinit var btnVolver: Button
    fun init(){
        btnHibrido = findViewById(R.id.btnHibrido)
        btnNormal = findViewById(R.id.btnNormal)
        btnVolver = findViewById(R.id.btnVolver)
        //pasar el id del usuario a la otra activity
        //para pasarle ese valor a la tabla de los turnos.
        //val id_usuario = intent.getStringExtra("id_usuario")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
        //Mapa satelital
        btnHibrido.setOnClickListener{
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        //Mpaa normal
        btnNormal.setOnClickListener{
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        btnVolver.setOnClickListener{
            val forma2 = Intent(this@MapsActivity, GeneracionTurnos::class.java)
            startActivity(forma2)
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
        val hospitalGuasmo = LatLng(-2.276769,-79.8975766)
        mMap.addMarker(MarkerOptions().position(hospitalGuasmo).title("Hospital Guasmo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospitalGuasmo))
        val hospitalCeibos = LatLng(-2.1745623,-79.9434742)
        mMap.addMarker(MarkerOptions().position(hospitalCeibos).title("Hospital Ceibos").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospitalCeibos))
        val MaternidadSotomayor = LatLng(-2.1986834,-79.8927948)
        mMap.addMarker(MarkerOptions().position(MaternidadSotomayor).title("Maternidad Sotomayor").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MaternidadSotomayor, 11.0f))
        val  CentroConvenciones= LatLng(-2.158733,-79.8895206)
        mMap.addMarker(MarkerOptions().position(CentroConvenciones).title("Centro de Convenciones").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CentroConvenciones))

    }
}

