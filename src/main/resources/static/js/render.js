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