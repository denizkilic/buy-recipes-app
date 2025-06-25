-- Insert products and capture IDs
WITH inserted_products AS (
INSERT INTO products (name, price_in_cents) VALUES
    ('Tomato', 150),
    ('Pasta', 300),
    ('Olive Oil', 500),
    ('Garlic', 100),
    ('Basil', 120),
    ('Chicken Breast', 700),
    ('Lettuce', 200),
    ('Parmesan', 400)
    RETURNING id, name
    ),

-- Insert recipes and capture IDs
    inserted_recipes AS (
INSERT INTO recipes (name) VALUES
    ('Spaghetti Pomodoro'),
    ('Chicken Caesar Salad')
    RETURNING id, name
    ),

-- Insert recipe_ingredients using the returned IDs
    insert_recipe_ingredients AS (
INSERT INTO recipe_ingredients (recipe_id, product_id)
SELECT
    r.id AS recipe_id,
    p.id AS product_id
FROM
    inserted_recipes r
    JOIN inserted_products p
ON
    (r.name = 'Spaghetti Pomodoro' AND p.name IN ('Tomato', 'Pasta', 'Olive Oil', 'Garlic', 'Basil'))
    OR
    (r.name = 'Chicken Caesar Salad' AND p.name IN ('Chicken Breast', 'Lettuce', 'Garlic', 'Parmesan'))
    )

-- Insert carts with different total_in_cents values
INSERT INTO carts (total_in_cents) VALUES
    (0),
    (1070),
    (2150);