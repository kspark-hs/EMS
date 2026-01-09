function toggleTemperatureHumidityDetail(btn) {
    const card = btn.closest('.bg-white');
    if (!card) return;

    const detail = card.querySelector('.temperature-humidity-detail');
    if (!detail) return;

    detail.classList.toggle('hidden');
}

function toggleAirConditionerDetail(btn) {
    const card = btn.closest('.bg-white');
    if (!card) return;

    const detail = card.querySelector('.air-conditioner-detail');
    if (!detail) return;

    detail.classList.toggle('hidden');
}

