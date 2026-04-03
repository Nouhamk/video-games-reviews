// PixelAvis — app.js

// Slider de note : sync au chargement
document.addEventListener('DOMContentLoaded', () => {
  const slider = document.getElementById('note');
  const display = document.getElementById('noteDisplay');
  if (slider && display) {
    display.textContent = parseFloat(slider.value).toFixed(1);
    slider.addEventListener('input', () => {
      display.textContent = parseFloat(slider.value).toFixed(1);
    });
  }

  // Auto-dismiss flash messages
  document.querySelectorAll('.flash').forEach(el => {
    setTimeout(() => {
      el.style.transition = 'opacity .5s';
      el.style.opacity = '0';
      setTimeout(() => el.remove(), 500);
    }, 4000);
  });
});
