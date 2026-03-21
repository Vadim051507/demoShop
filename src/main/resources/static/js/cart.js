// ─── Cart API ───────────────────────────────────────────────

async function loadCart() {
    const res = await fetch('/api/cart');
    const data = await res.json();
    renderCart(data.items, data.total, data.count);
}

async function addToCart(productId, name, price, img) {
    const res = await fetch('/api/cart/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ productId, name, price, img })
    });
    const data = await res.json();
    updateCartBadge(data.count);
    await loadCart();
}

async function removeFromCart(productId) {
    const res = await fetch(`/api/cart/remove/${productId}`, { method: 'DELETE' });
    const data = await res.json();
    updateCartBadge(data.count);
    await loadCart();
}

async function clearCart() {
    await fetch('/api/cart/clear', { method: 'DELETE' });
    await loadCart();
}

// ─── UI ─────────────────────────────────────────────────────

function updateCartBadge(count) {
    const badge = document.getElementById('cart-count');
    if (!badge) return;
    badge.textContent = count;
    badge.style.display = count > 0 ? 'flex' : 'none';
}

function renderCart(items, total, count) {
    updateCartBadge(count);
    const container = document.getElementById('cart-items');
    if (!container) return;

    if (items.length === 0) {
        container.innerHTML = `
            <div class="cart-empty">
                <div class="cart-empty-icon">🌸</div>
                <p>Кошик порожній</p>
            </div>`;
        document.getElementById('cart-total').textContent = '0 грн';
        return;
    }

    container.innerHTML = items.map(item => `
        <div class="cart-item">
            <img class="cart-item-img" src="${item.img}" alt="${item.name}">
            <div style="flex:1;">
                <div class="cart-item-name">${item.name}</div>
                <div class="cart-item-price">
                    ${(item.price * item.quantity).toLocaleString('uk')} грн
                    ${item.quantity > 1 ? `<span style="color:var(--muted);font-size:.75rem;"> × ${item.quantity}</span>` : ''}
                </div>
            </div>
            <button class="cart-item-remove" onclick="removeFromCart(${item.productId})">✕</button>
        </div>
    `).join('');

    document.getElementById('cart-total').textContent = total.toLocaleString('uk') + ' грн';
}

async function handleAddBtn(btn, productId, name, price, img) {
    await addToCart(productId, name, price, img);
    const res = await fetch('/api/cart');
    const data = await res.json();
    const item = data.items.find(i => i.productId === productId);
    const qty = item ? item.quantity : 1;

    btn.textContent = qty;
    btn.classList.add('added');
    clearTimeout(btn._resetTimer);
    btn._resetTimer = setTimeout(() => {
        btn.textContent = '+';
        btn.classList.remove('added');
    }, 2000);
}

// ─── Cart drawer ─────────────────────────────────────────────

function toggleCart() {
    const drawer = document.getElementById('cart-drawer');
    const overlay = document.getElementById('cart-overlay');
    drawer.classList.toggle('open');
    overlay.classList.toggle('open');
    document.body.style.overflow = drawer.classList.contains('open') ? 'hidden' : '';
}

// ─── Checkout modal ──────────────────────────────────────────

function openCheckout() {
    // Збираємо поточний кошик для відображення в модалці
    fetch('/api/cart').then(r => r.json()).then(data => {
        if (data.count === 0) return;
        buildCheckoutModal(data.items, data.total);
        document.getElementById('checkout-modal').classList.add('open');
        document.body.style.overflow = 'hidden';
        // Закриваємо drawer
        document.getElementById('cart-drawer').classList.remove('open');
        document.getElementById('cart-overlay').classList.remove('open');
    });
}

function closeCheckout() {
    document.getElementById('checkout-modal').classList.remove('open');
    document.body.style.overflow = '';
}

function buildCheckoutModal(items, total) {
    const summary = document.getElementById('checkout-summary');
    summary.innerHTML = items.map(item => `
        <div class="co-item">
            <span class="co-item-name">${item.name}</span>
            <span class="co-item-qty">${item.quantity > 1 ? '× ' + item.quantity : ''}</span>
            <span class="co-item-price">${(item.price * item.quantity).toLocaleString('uk')} грн</span>
        </div>
    `).join('');
    document.getElementById('checkout-total-price').textContent = total.toLocaleString('uk') + ' грн';
}

async function submitOrder(e) {
    e.preventDefault();
    const btn = document.getElementById('checkout-submit');
    btn.disabled = true;
    btn.textContent = 'Обробка...';

    // Симулюємо відправку (тут потім підключиш реальний POST /api/orders)
    await new Promise(r => setTimeout(r, 1200));

    // Очищаємо кошик
    await clearCart();

    // Показуємо success стан
    document.getElementById('checkout-form-wrap').style.display = 'none';
    document.getElementById('checkout-success').style.display = 'flex';
}

// ─── Init ────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', () => {
    loadCart();
    document.getElementById('cart-overlay')?.addEventListener('click', toggleCart);
    document.getElementById('cart-close')?.addEventListener('click', toggleCart);
    document.querySelector('.checkout-btn')?.addEventListener('click', openCheckout);

    // Вставляємо модальне вікно в DOM
    document.body.insertAdjacentHTML('beforeend', buildCheckoutHTML());

    document.getElementById('checkout-form')?.addEventListener('submit', submitOrder);
});

function buildCheckoutHTML() {
    return `
    <div class="checkout-modal" id="checkout-modal">
        <div class="checkout-backdrop" onclick="closeCheckout()"></div>
        <div class="checkout-box">

            <button class="checkout-close" onclick="closeCheckout()">✕</button>

            <div id="checkout-form-wrap">
                <div class="checkout-left">
                    <div class="co-eyebrow">Ваше замовлення</div>
                    <h2 class="co-title">Підтвердження</h2>
                    <div class="checkout-summary" id="checkout-summary"></div>
                    <div class="co-divider"></div>
                    <div class="co-total-row">
                        <span>До сплати</span>
                        <span id="checkout-total-price" class="co-total-val">0 грн</span>
                    </div>
                </div>

                <div class="checkout-right">
                    <div class="co-eyebrow">Доставка</div>
                    <h2 class="co-title">Ваші дані</h2>
                    <form id="checkout-form">
                        <div class="co-field">
                            <label>Ім'я</label>
                            <input type="text" placeholder="Ваше ім'я" required>
                        </div>
                        <div class="co-field">
                            <label>Телефон</label>
                            <input type="tel" placeholder="+380" required>
                        </div>
                        <div class="co-field">
                            <label>Адреса доставки</label>
                            <input type="text" placeholder="вул. Хрещатик, 1, кв. 5" required>
                        </div>
                        <div class="co-field">
                            <label>Тип доставки</label>
                            <select>
                                <option>⚡ Експрес — 1–2 год (від 150 грн)</option>
                                <option>🕐 Стандартна — 2–4 год (від 80 грн)</option>
                                <option>📅 Запланована (від 80 грн)</option>
                                <option>🏪 Самовивіз (безкоштовно)</option>
                            </select>
                        </div>
                        <div class="co-field">
                            <label>Коментар</label>
                            <textarea placeholder="Побажання до букету або доставки..." rows="3"></textarea>
                        </div>
                        <button type="submit" class="co-submit" id="checkout-submit">
                            Оформити замовлення
                        </button>
                    </form>
                </div>
            </div>

            <div class="checkout-success" id="checkout-success">
                <div class="cs-icon">✦</div>
                <h2>Замовлення прийнято!</h2>
                <p>Ми зателефонуємо вам найближчим часом для підтвердження.</p>
                <p class="cs-sub">Дякуємо, що обрали Florist Boutique</p>
                <button class="co-submit" onclick="closeCheckout()" style="margin-top:32px;width:auto;padding:14px 40px;">
                    Чудово!
                </button>
            </div>

        </div>
    </div>`;
}