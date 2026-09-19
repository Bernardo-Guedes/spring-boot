searchInput = document.getElementById('searchInput');

searchInput.addEventListener('input', function() {
    const searchTerm = searchInput.value.toLowerCase();
    const radioStations = document.querySelectorAll('.radio-station');

    radioStations.forEach(function(station) {
        const stationName = station.querySelector('p').textContent.toLowerCase();
        if (stationName.includes(searchTerm)) {
            station.style.display = 'flex';
        } else {
            station.style.display = 'none';
        }
    });
});