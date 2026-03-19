
// ─── Shared product data ───
const PRODUCTS = [
  { id: 1, name: 'Букет троянд',      cat: 'roses',    price: 1100, badge: 'Хіт',     img: 'https://images.unsplash.com/photo-1518895949257-7621c3c786d7?w=600' },
  { id: 2, name: 'Весняні тюльпани',  cat: 'tulips',   price: 750,  badge: null,       img: 'https://images.unsplash.com/photo-1588671151395-b3f7f5363be7?w=600' },
  { id: 3, name: 'Польові квіти',     cat: 'bouquets', price: 640,  badge: null,       img: 'https://images.unsplash.com/photo-1490750967868-88aa4486c946?w=600' },
  { id: 4, name: 'Святковий мікс',    cat: 'roses',    price: 1300, badge: 'Новинка',  img: 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600' },
  { id: 5, name: 'Екзотичний букет',  cat: 'exotic',   price: 1450, badge: 'Преміум',  img: 'https://images.unsplash.com/photo-1524593166156-312f362cada0?w=600' },
  { id: 6, name: 'Романтичний букет', cat: 'bouquets', price: 980,  badge: null,       img: 'https://images.unsplash.com/photo-1487530811015-780780169993?w=600' },
  { id: 7, name: 'Червоні троянди',   cat: 'roses',    price: 1200, badge: 'Хіт',     img: 'https://images.unsplash.com/photo-1519378058457-4c29a0a2efac?w=600' },
  { id: 8, name: 'Ніжні тюльпани',   cat: 'tulips',   price: 700,  badge: null,       img: 'https://images.unsplash.com/photo-1508610048659-a06b669e3321?w=600' },
  { id: 9, name: 'Сонячний букет',    cat: 'roses',    price: 820,  badge: null,       img: 'https://images.unsplash.com/photo-1518895949257-7621c3c786d7?w=600' },
];

const CAT_LABELS = {
  roses: 'Троянди',
  tulips: 'Тюльпани',
  bouquets: 'Букети',
  exotic: 'Екзотика',
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
          <button class="add-btn" data-id="${p.id}" title="В кошик">+</button>
        </div>
      </div>
    </div>`;
}
