const CAT_LABELS = {
    bouquets:     'Букети',
    roses:        'Троянди',
    tulips:       'Тюльпани',
    exotic:       'Екзотика',
    plants:       'Рослини',
    compositions: 'Композиції',
};

function renderCard(p) {
    return `
    <div class="product-card" data-cat="${p.cat}">
      <div class="product-img">
        <img src="${p.img}" alt="${p.name}" loading="lazy">
      </div>
      ${p.badge ? `<div class="product-badge">${p.badge}</div>` : ''}
      <div class="product-body">
        <div class="product-cat">${CAT_LABELS[p.cat] || p.cat}</div>
        <div class="product-name">${p.name}</div>
        <div class="product-footer">
          <div class="product-price">${p.price.toLocaleString('uk')} грн</div>
          <button class="add-btn" data-id="${p.id}" title="В кошик"
            ${p.stock === 0 ? 'disabled' : ''}>+</button>
        </div>
      </div>
      ${p.stock === 0 ? '<div class="product-badge" style="background:#666">Немає в наявності</div>' : ''}
    </div>`;
}

async function loadProducts(category = 'all') {
    const grid = document.getElementById('catalog-products');
    if (!grid) return;

    try {
        const res = await fetch(`/api/products?category=${category}`);
        const products = await res.json();

        grid.innerHTML = products.map(renderCard).join('');

        // Кнопки "в кошик"
        grid.querySelectorAll('.add-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const id = Number(btn.dataset.id);
                const card = btn.closest('.product-card');
                const name  = card.querySelector('.product-name').textContent;
                const price = Number(card.querySelector('.product-price')
                    .textContent.replace(/\D/g, ''));
                const img   = card.querySelector('img').src;
                handleAddBtn(btn, id, name, price, img);
            });
        });

    } catch (e) {
        console.error('Помилка завантаження товарів:', e);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadProducts();

    document.querySelectorAll('.filter-pill').forEach(pill => {
        pill.addEventListener('click', () => {
            document.querySelectorAll('.filter-pill').forEach(p => p.classList.remove('active'));
            pill.classList.add('active');
            loadProducts(pill.dataset.cat);
        });
    });
});