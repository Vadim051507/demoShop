/* Кнопка "В кошик" */

const buttons = document.querySelectorAll(".buy-btn");

buttons.forEach(btn => {

btn.addEventListener("click", () => {

btn.classList.add("added");

setTimeout(() => {

btn.classList.remove("added");

},1500);

});

});


/* Фільтр каталогу */

const filterButtons = document.querySelectorAll(".filter-btn");
const products = document.querySelectorAll(".product-card");

filterButtons.forEach(button => {

button.addEventListener("click", () => {

document.querySelector(".filter-btn.active").classList.remove("active");
button.classList.add("active");

let filter = button.dataset.filter;

products.forEach(product => {

if(filter === "all" || product.dataset.category === filter){

product.style.display = "block";

}else{

product.style.display = "none";

}

});

});

});