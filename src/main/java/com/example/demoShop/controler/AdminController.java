package com.example.demoShop.controler;

import com.example.demoShop.entity.Order;
import com.example.demoShop.entity.OrderStatus;
import com.example.demoShop.entity.Product;
import com.example.demoShop.repository.OrderRepository;
import com.example.demoShop.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminController(ProductRepository productRepository,
                           OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // ── Дашборд ─────────────────────────────────────────────────────────────
    @GetMapping
    public String dashboard(Model model) {
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long doneOrders = orderRepository.countByStatus(OrderStatus.DONE);
        long cancelledOrders = orderRepository.countByStatus(OrderStatus.CANCELLED);
        long totalRevenue = orderRepository.sumRevenue();

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("doneOrders", doneOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("recentOrders", orderRepository.findTop5ByOrderByCreatedAtDesc());
        model.addAttribute("activePage", "dashboard");
        return "admin/dashboard";
    }

    // ── Товари ───────────────────────────────────────────────────────────────
    @GetMapping("/products")
    public String products(@RequestParam(defaultValue = "0") int page,
                           Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        model.addAttribute("productPage", productPage);
        model.addAttribute("activePage", "products");
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("activePage", "products");
        return "admin/product-form";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не знайдено"));
        model.addAttribute("product", product);
        model.addAttribute("activePage", "products");
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    // ── Замовлення ───────────────────────────────────────────────────────────
    @GetMapping("/orders")
    public String orders(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(required = false) String status,
                         @RequestParam(required = false) String search,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<Order> orderPage;

        if (status != null && !status.isEmpty()) {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            orderPage = (search != null && !search.isEmpty())
                    ? orderRepository.findByStatusAndClientNameContainingIgnoreCase(orderStatus, search, pageable)
                    : orderRepository.findByStatus(orderStatus, pageable);
        } else if (search != null && !search.isEmpty()) {
            orderPage = orderRepository.findByClientNameContainingIgnoreCase(search, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("search", search);
        model.addAttribute("activePage", "orders");
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Замовлення не знайдено"));
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }
}