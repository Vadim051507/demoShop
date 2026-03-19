// ─── Cart ───
let cart = [];

function addToCart(id) {
  const product = PRODUCTS.find(p => p.id === id);
  if (!product) return;
  const existing = cart.find(p => p.id === id);
  if (existing) existing.qty++;
  else cart.push({ ...product, qty: 1 });
  updateCartUI();
}

function removeFromCart(id) {
  cart = cart.filter(p => p.id !== id);
  updateCartUI();
}

function updateCartUI() {
  const count = cart.reduce((s, p) => s + p.qty, 0);
  const total = cart.reduce((s, p) => s + p.price * p.qty, 0);

  const countEl = document.getElementById('cart-count');
  if (countEl) {
    countEl.textContent = count;
    countEl.style.display = count > 0 ? 'flex' : 'none';
  }

  const totalEl = document.getElementById('cart-total');
  if (totalEl) totalEl.textContent = total.toLocaleString('uk') + ' грн';

  const itemsEl = document.getElementById('cart-items');
  if (!itemsEl) return;

  if (cart.length === 0) {
    itemsEl.innerHTML = `
      <div class="cart-empty">
        <div class="cart-empty-icon">🌸</div>
        <p>Кошик порожній</p>
      </div>`;
    return;
  }

  itemsEl.innerHTML = cart.map(item => `
    <div class="cart-item">
      <img class="cart-item-img" src="${item.img}" alt="${item.name}">
      <div>
        <div class="cart-item-name">${item.name}</div>
        <div class="cart-item-price">
          ${(item.price * item.qty).toLocaleString('uk')} грн
          ${item.qty > 1 ? '× ' + item.qty : ''}
        </div>
      </div>
      <button class="cart-item-remove" data-remove="${item.id}">✕</button>
    </div>
  `).join('');

  itemsEl.querySelectorAll('[data-remove]').forEach(btn => {
    btn.addEventListener('click', () => removeFromCart(Number(btn.dataset.remove)));
  });
}

function toggleCart() {
  const drawer = document.getElementById('cart-drawer');
  const overlay = document.getElementById('cart-overlay');
  if (!drawer) return;
  drawer.classList.toggle('open');
  overlay.classList.toggle('open');
  document.body.style.overflow = drawer.classList.contains('open') ? 'hidden' : '';
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('cart-overlay')?.addEventListener('click', toggleCart);
  document.getElementById('cart-close')?.addEventListener('click', toggleCart);
});
