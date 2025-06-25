CREATE TABLE IF NOT EXISTS products (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    price_in_cents INTEGER NOT NULL
    );

CREATE TABLE IF NOT EXISTS carts (
                                     id SERIAL PRIMARY KEY,
                                     total_in_cents INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS cart_items (
                                          id SERIAL PRIMARY KEY,
                                          cart_id INTEGER NOT NULL,
                                          product_id INTEGER NOT NULL,
                                          CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
    );

CREATE TABLE IF NOT EXISTS recipes (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS recipe_ingredients (
                                                  id SERIAL PRIMARY KEY,
                                                  recipe_id INTEGER NOT NULL,
                                                  product_id INTEGER NOT NULL,
                                                  CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    CONSTRAINT fk_product2 FOREIGN KEY (product_id) REFERENCES products(id)
    );
