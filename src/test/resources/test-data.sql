-- Clean up existing data
DELETE FROM prices;

-- Insert test data for the 5 scenarios from the requirements
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES
-- Price list 1: 2020-06-14 00:00:00 to 2020-12-31 23:59:59 (priority 0)
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),

-- Price list 2: 2020-06-14 15:00:00 to 2020-06-14 18:30:00 (priority 1 - higher)
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),

-- Price list 3: 2020-06-15 00:00:00 to 2020-06-15 11:00:00 (priority 1)
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),

-- Price list 4: 2020-06-15 16:00:00 to 2020-12-31 23:59:59 (priority 1)
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');