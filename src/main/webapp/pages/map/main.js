// mapboxgl.accessToken = 'pk.eyJ1IjoiZG5zOTExIiwiYSI6ImNsZHl5ZHRzdTB3OG4zeXByNnI2ZWxtbGgifQ.M3WoLYPxjY5DlOm47HzxZA'; // public token
mapboxgl.accessToken = 'pk.eyJ1IjoiZG5zOTExIiwiYSI6ImNsZTJ3czVhcjAzOXczb21xdWVxbDFicTEifQ.jSPqucDHSinP-pEGUO2iCw';    //  token from my account
const map = new mapboxgl.Map({
    container: 'map',
    language: 'ru-RU',
// Choose from Mapbox's core styles, or make your own style with Mapbox Studio
    style: 'mapbox://styles/mapbox/streets-v12',
    center: [27.67, 53.95696140207383],
    zoom: 13
});

let nav = new mapboxgl.NavigationControl({
    showCompass: true,
    showZoom: true
})
map.addControl(nav,'bottom-right');

const geocoder_from = new MapboxGeocoder({
    accessToken: mapboxgl.accessToken,
    mapboxgl: mapboxgl, // Set the mapbox-gl instance
    // countries: 'by',
    language: 'ru-RU',
    marker: false, // Do not use the default marker style
    placeholder: 'Откуда?', // Placeholder text for the search bar
    bbox: [27.66, 53.922, 27.7, 53.97], // Boundary for NewBor
    proximity: {
        longitude: 27.1,
        latitude: 53.95
    }
});
document.getElementById('geocoder_from').appendChild(geocoder_from.onAdd(map));
window.glb_start=[];

const geocoder_to = new MapboxGeocoder({
    accessToken: mapboxgl.accessToken, // Set the access token
    mapboxgl: mapboxgl, // Set the mapbox-gl instance
    language: 'ru-RU',
    marker: false, // Do not use the default marker style
    placeholder: 'Куда?', // Placeholder text for the search bar
    bbox: [27.66, 53.922, 27.7, 53.97], // Boundary for Berkeley
    proximity: {
        longitude: 27.1,
        latitude: 53.95
    }
});
document.getElementById('geocoder_to').appendChild(geocoder_to.onAdd(map));
window.glb_end=[];



// After the map style has loaded on the page,
// add a source layer and default styling for a single point
map.on('load', () => {
    map.addSource('single-point-start', {
        'type': 'geojson',
        'data': {
            'type': 'FeatureCollection',
            'features': []
        }
    });
    map.addLayer({
        'id': 'point-start',
        'source': 'single-point-start',
        'type': 'circle',
        'paint': {
            'circle-radius': 10,
            'circle-color': '#ff8080'
        }
    });
// Listen for the `result` event from the Geocoder // `result` event is triggered when a user makes a selection
//  Add a marker at the result's coordinates
    geocoder_from.on('result', (event) => {
        map.getSource('single-point-start').setData(event.result.geometry);
        const start = event.result.geometry.coordinates;

        document.getElementById('start_street').value = event.result.text;
        document.getElementById('start_addr').value = event.result.address;
        document.getElementById('start_lng').value = start[0];
        document.getElementById('start_lat').value = start[1];

        window.glb_start=start;
        if (window.glb_end.length == 0) {
            getRoute(start, start)
        } else{
            getRoute(start, window.glb_end);
        }
    });
});
// After the map style has loaded on the page,
// add a source layer and default styling for a single point
map.on('load', () => {
    map.addSource('single-point-finish', {
        'type': 'geojson',
        'data': {
            'type': 'FeatureCollection',
            'features': []
        }
    });
    map.addLayer({
        'id': 'point-finish',
        'source': 'single-point-finish',
        'type': 'circle',
        'paint': {
            'circle-radius': 10,
            'circle-color': '#80aaff'
        }
    });
// Listen for the `result` event from the Geocoder // `result` event is triggered when a user makes a selection
//  Add a marker at the result's coordinates
    geocoder_to.on('result', (event) => {
        map.getSource('single-point-finish').setData(event.result.geometry);
        const end = event.result.geometry.coordinates;

        document.getElementById('finish_street').value = event.result.text;
        document.getElementById('finish_addr').value = event.result.address;
        document.getElementById('finish_lng').value = end[0];
        document.getElementById('finish_lat').value = end[1];

        window.glb_end = end;
        if (window.glb_start.length == 0) {
            getRoute(end, end)
        } else{
            getRoute(window.glb_start, end);
        }
    });
});

async function getRoute(start, end) {
    // make a directions request using cycling profile
    const query = await fetch(
        `https://api.mapbox.com/directions/v5/mapbox/driving/${start[0]},${start[1]};${end[0]},${end[1]}?steps=true&geometries=geojson&access_token=${mapboxgl.accessToken}`,
        { method: 'GET' }
    );
    const json = await query.json();
    const data = json.routes[0];
    const route = data.geometry.coordinates;
    document.getElementById('distance').value = data.distance; //getting dist (m) on page
    document.getElementById('duration').value = data.duration; //getting duration (sec) on page
    const geojson = {
        type: 'Feature',
        properties: {},
        geometry: {
            type: 'LineString',
            coordinates: route
        }
    };
    // if the route already exists on the map, we'll reset it using setData
    if (map.getSource('route')) {
        map.getSource('route').setData(geojson);
    }
    // otherwise, we'll make a new request
    else {
        map.addLayer({
            id: 'route',
            type: 'line',
            source: {
                type: 'geojson',
                data: geojson
            },
            layout: {
                'line-join': 'round',
                'line-cap': 'round'
            },
            paint: {
                'line-color': '#5267ab',
                'line-width': 5,
                'line-opacity': 0.8,
            }
        });
    }
}

