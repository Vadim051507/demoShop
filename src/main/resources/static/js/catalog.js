document.addEventListener('DOMContentLoaded', () => {
    const grid = document.getElementById('catalog-products');
    if (!grid) return;

    grid.innerHTML = PRODUCTS.map(renderCard).join('');

    document.querySelectorAll('.filter-pill').forEach(pill => {
        pill.addEventListener('click', () => {
            document.querySelectorAll('.filter-pill').forEach(p => p.classList.remove('active'));
            pill.classList.add('active');
            const cat = pill.dataset.cat;
            grid.querySelectorAll('.product-card').forEach(card => {
                const show = cat === 'all' || card.dataset.cat === cat;
                if (show) {
                    card.style.display = '';
                    requestAnimationFrame(() => {
                        card.style.transition = 'opacity .3s, transform .3s';
                        card.style.opacity = '1';
                        card.style.transform = '';
                        card.style.pointerEvents = '';
                    });
                } else {
                    card.style.transition = 'opacity .3s, transform .3s';
                    card.style.opacity = '0';
                    card.style.transform = 'scale(.95)';
                    card.style.pointerEvents = 'none';
                    setTimeout(() => { card.style.display = 'none'; }, 300);
                }
            });
        });
    });

    grid.querySelectorAll('.add-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = Number(btn.dataset.id);
            const product = PRODUCTS.find(p => p.id === id);
            if (!product) return;
            handleAddBtn(btn, product.id, product.name, product.price, product.img);
        });
    });
});