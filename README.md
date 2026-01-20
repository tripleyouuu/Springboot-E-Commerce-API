## E-Commerce Backend API (Assignment Version)

This Spring Boot project implements the minimal e‑commerce backend required for the assignment:

- Products can be created and listed.
- Users can manage a cart (add items, view, clear).
- Orders are created from the cart with totals and stock checks.
- Payments use a mock service and a webhook to update order status.

The stack is Spring Boot 3, Spring Data JPA, and PostgreSQL.

---

### Setup

- **Requirements**: Java 21, Maven, PostgreSQL.
- Create a PostgreSQL database named `ecommerce_db`.
- Configure DB user/password in `src/main/resources/application.yaml` (password via `DB_PASSWORD` env var).

Run the app:

```bash
mvn spring-boot:run
```

Server runs on `http://localhost:8080`.

---

### Main APIs (for Postman)

- **Products**
  - `POST /api/products` – create product (name, description, price, stock).
  - `GET /api/products` – list products.

- **Cart**
  - `POST /api/cart/add` – add item to cart (`userId`, `productId`, `quantity`), checks stock and merges quantity.
  - `GET /api/cart/{userId}` – get cart items for a user.
  - `DELETE /api/cart/{userId}/clear` – clear cart, returns `{ "message": "Cart cleared successfully" }`.

- **Orders**
  - `POST /api/orders` – create order from cart for a user (`{ "userId": ... }`), validates cart + stock, updates stock, and clears cart.
  - `GET /api/orders/{orderId}` – get order details, including items and status.

- **Payments (Mock)**
  - `POST /api/payments/create` – create payment for order (`{ "orderId": ..., "amount": ... }`); returns `paymentId`, `orderId`, `amount`, `status: PENDING`.
  - `POST /api/webhooks/payment` – simulate payment callback (`{ "orderId": ..., "status": "SUCCESS" | "FAILED" }`); updates `Payment` and `Order` status.

---

### Suggested Testing Flow

1. Create 1 user (insert into `users` table) and a few products using `POST /api/products`.
2. Add products to the user’s cart with `POST /api/cart/add`.
3. View the cart with `GET /api/cart/{userId}`.
4. Create an order with `POST /api/orders` and note the `orderId`.
5. Create a payment with `POST /api/payments/create` using that `orderId`.
6. Call `POST /api/webhooks/payment` with `{ "orderId": <id>, "status": "SUCCESS" }`.
7. Check the order using `GET /api/orders/{orderId}` – status should be `PAID`.
