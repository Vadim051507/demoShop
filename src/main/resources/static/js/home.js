
// ─── Home page ───
document.addEventListener('DOMContentLoaded', () => {
  const grid = document.getElementById('home-products');
  if (!grid) return;

  // Render first 6 products
  grid.innerHTML = PRODUCTS.slice(0, 6).map(renderCard).join('');

  // Add to cart buttons
  grid.querySelectorAll('.add-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const id = Number(btn.dataset.id);
      addToCart(id);
      btn.textContent = '✓';
      btn.classList.add('added');
      setTimeout(() => {
        btn.textContent = '+';
        btn.classList.remove('added');
      }, 1800);
    });
  });
});
