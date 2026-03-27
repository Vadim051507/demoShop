
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