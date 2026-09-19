package com.example.citiwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webViewMap);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());

        String html = "<!DOCTYPE html>"
            + "<html><head>"
            + "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0'>"
            + "<link rel='stylesheet' href='https://unpkg.com/leaflet@1.9.4/dist/leaflet.css'/>"
            + "<script src='https://unpkg.com/leaflet@1.9.4/dist/leaflet.js'></script>"
            + "<style>"
            + "  html,body{width:100%;height:100%;margin:0;padding:0;overflow:hidden;}"
            + "  #map{width:100%;height:100%;}"
            + "</style>"
            + "</head><body>"
            + "<div id='map'></div>"
            + "<script>"
            // Initialize map on Bacolod City
            + "var map = L.map('map', {zoomControl:true}).setView([10.6765, 122.9509], 14);"
            // Satellite tile layer (ESRI World Imagery like the design)
            + "L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {"
            + "  attribution: '&copy; Esri, Maxar, Earthstar Geographics',"
            + "  maxZoom: 19"
            + "}).addTo(map);"
            // Icon helper functions
            + "function makeIcon(color) {"
            + "  return new L.Icon({"
            + "    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-' + color + '.png',"
            + "    iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [1, -34]"
            + "  });"
            + "}"
            // Red markers (Emergency)
            + "L.marker([10.6840, 122.9420], {icon: makeIcon('red')})"
            + "  .addTo(map)"
            + "  .bindPopup('<b>Broken Street Light</b><br/>📍 Barangay 6<br/><span style=\"color:#E05215;font-weight:bold\">🚨 Emergency</span>');"
            + "L.marker([10.6780, 122.9565], {icon: makeIcon('red')})"
            + "  .addTo(map)"
            + "  .bindPopup('<b>Flooded Crossroad</b><br/>📍 Villamonte<br/><span style=\"color:#E05215;font-weight:bold\">🚨 Emergency</span>');"
            // Blue marker (Reported)
            + "L.marker([10.6700, 122.9672], {icon: makeIcon('blue')})"
            + "  .addTo(map)"
            + "  .bindPopup('<b>Road Damage</b><br/>📍 Barangay 41<br/><span style=\"color:#1565C0;font-weight:bold\">📋 Reported</span>');"
            // Green marker (Resolved)
            + "L.marker([10.6625, 122.9390], {icon: makeIcon('green')})"
            + "  .addTo(map)"
            + "  .bindPopup('<b>Illegal Dumping</b><br/>📍 Barangay 40<br/><span style=\"color:#2E7D32;font-weight:bold\">✅ Resolved</span>');"
            + "</script>"
            + "</body></html>";

        webView.loadDataWithBaseURL(
            "https://leafletjs.com", html, "text/html", "utf-8", null);
    }
}