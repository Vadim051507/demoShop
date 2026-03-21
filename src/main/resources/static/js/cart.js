// Завантажуємо кошик з сервера при старті
async function loadCart() {
    const res = await fetch('/api/cart');
    const data = await res.json();
    renderCart(data.items, data.total, data.count);
}

// Додати товар
async function addToCart(productId, name, price, img) {
    const res = await fetch('/api/cart/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ productId, name, price, img })
    });
    const data = await res.json();
    updateCartBadge(data.count);
}

// Видалити товар
async function removeFromCart(productId) {
    const res = await fetch(`/api/cart/remove/${productId}`, {
        method: 'DELETE'
    });
    const data = await res.json();
    updateCartBadge(data.count);
    loadCart(); // перемалювати кошик
}

// Оновити лічильник в navbar
function updateCartBadge(count) {
    const badge = document.getElementById('cart-count');
    badge.textContent = count;
    badge.style.display = count > 0 ? 'flex' : 'none';
}

// Відмалювати вміст drawer
function renderCart(items, total, count) {
    updateCartBadge(count);
    const container = document.getElementById('cart-items');

    if (items.length === 0) {
        container.innerHTML = `
            <div class="cart-empty">
                <div class="cart-empty-icon">🌸</div>
                <p>Кошик порожній</p>
            </div>`;
        return;
    }

    container.innerHTML = items.map(item => `
        <div class="cart-item">
            <img class="cart-item-img" src="${item.img}" alt="${item.name}">
            <div>
                <div class="cart-item-name">${item.name}</div>
                <div class="cart-item-price">
                    ${(item.price * item.quantity).toLocaleString('uk')} грн
                    ${item.quantity > 1 ? '× ' + item.quantity : ''}
                </div>
            </div>
            <button class="cart-item-remove"
                    onclick="removeFromCart(${item.productId})">✕</button>
        </div>
    `).join('');

    document.getElementById('cart-total').textContent =
        total.toLocaleString('uk') + ' грн';
}

// Оновлюємо кнопки "В кошик" в каталозі
function handleAddBtn(btn, productId, name, price, img) {
    addToCart(productId, name, price, img);
    btn.textContent = '✓';
    btn.classList.add('added');
    setTimeout(() => {
        btn.textContent = '+';
        btn.classList.remove('added');
    }, 1800);
}

// Завантажити кошик при відкритті сторінки
document.addEventListener('DOMContentLoaded', loadCart);

document.getElementById('cart-overlay')
    ?.addEventListener('click', toggleCart);
document.getElementById('cart-close')
    ?.addEventListener('click', toggleCart);

function toggleCart() {
    document.getElementById('cart-drawer').classList.toggle('open');
    document.getElementById('cart-overlay').classList.toggle('open');
    document.body.style.overflow =
        document.getElementById('cart-drawer').classList.contains('open')
            ? 'hidden' : '';
}
