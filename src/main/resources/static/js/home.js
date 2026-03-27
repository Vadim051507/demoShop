document.addEventListener('DOMContentLoaded', async () => {
    const grid = document.getElementById('home-products');
    if (!grid) return;

    try {
        const res = await fetch('/api/products?category=all');
        const products = await res.json();

        // Показуємо перші 6 товарів з badge (бестселери) або просто перші 6
        const bestsellers = [
            ...products.filter(p => p.badge === 'Хіт' || p.badge === 'Топ продажів'),
            ...products.filter(p => p.badge !== 'Хіт' && p.badge !== 'Топ продажів')
        ].slice(0, 6);

        grid.innerHTML = bestsellers.map(renderCard).join('');

        grid.querySelectorAll('.add-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const id = Number(btn.dataset.id);
                const card = btn.closest('.product-card');
                const name  = card.querySelector('.product-name').textContent;
                const price = Number(card.querySelector('.product-price').textContent.replace(/\D/g, ''));
                const img   = card.querySelector('img').src;
                handleAddBtn(btn, id, name, price, img);
            });
        });

    } catch (e) {
        console.error('Помилка завантаження бестселерів:', e);
    }
});