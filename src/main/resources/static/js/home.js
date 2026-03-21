document.addEventListener('DOMContentLoaded', () => {
    const grid = document.getElementById('home-products');
    if (!grid) return;

    grid.innerHTML = PRODUCTS.slice(0, 6).map(renderCard).join('');

    grid.querySelectorAll('.add-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = Number(btn.dataset.id);
            const product = PRODUCTS.find(p => p.id === id);
            if (!product) return;
            handleAddBtn(btn, product.id, product.name, product.price, product.img);
        });
    });
});