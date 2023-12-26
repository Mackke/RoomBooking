


-- Inserting data into the room table
INSERT INTO room (id, name, description, type, beds, price)
VALUES
    ('a1f3b736-5b7c-4d2a-bd3c-31f9c96bbf41', 'Standard Room', 'Cozy room with basic amenities', 'Standard', 2, 100.00),
    ('6e96d7bb-1a0c-4e95-bd3a-7a74e21f71ef', 'Deluxe Suite', 'Luxurious suite with a view', 'Deluxe', 3, 250.00),
    ('4f0a3a35-06a4-4a6f-9207-c8fe00a0e819', 'Family Room', 'Spacious room for families', 'Family', 4, 180.00),
    ('12d0d3d9-4200-4774-895a-0c7f75cedc26', 'Executive Suite', 'Executive level accommodations', 'Suite', 2, 300.00),
    ('bb7f64af-5d52-4de1-af60-b289a73da3b4', 'Single Room', 'Perfect for solo travelers', 'Standard', 1, 80.00),
    ('3c62da78-c4c2-4d1b-a947-5a1b9e0c95b2', 'Ocean View Suite', 'Suite with a breathtaking ocean view', 'Suite', 2, 350.00),
    ('ae8c05e8-4a2a-4774-89a9-7bc155e2d7a1', 'Budget Room', 'Economical room for cost-conscious travelers', 'Economy', 1, 60.00),
    ('b47f23d1-87b0-4f10-bca5-2c508376d0e5', 'Executive Penthouse', 'Top-floor penthouse with premium amenities', 'Penthouse', 3, 500.00),
    ('e505db94-cf8e-4c7d-ae96-53194fc5a255', 'Cottage Retreat', 'Quaint cottage for a peaceful getaway', 'Cottage', 2, 200.00),
    ('d39d3ef3-4ecf-430a-9cf5-5058c8bf0a3e', 'Business Class Room', 'Ideal for business travelers with workspace', 'Business', 1, 120.00);

INSERT INTO booking (id, room_id, start_date, end_date, archived)
VALUES
    ('1b91d84f-8ef3-4b35-9d2b-19b878b874c5', 'a1f3b736-5b7c-4d2a-bd3c-31f9c96bbf41', '2023-12-10', '2023-12-13', null),
    ('69f0d244-1c88-4d9f-b3c3-e17f7690d6fe', '6e96d7bb-1a0c-4e95-bd3a-7a74e21f71ef', '2023-12-05', '2023-12-15', null),
    ('8c18d712-6395-41ea-8a0c-0dd811c44e4e', '4f0a3a35-06a4-4a6f-9207-c8fe00a0e819', '2023-12-20', '2023-12-29', null),
    ('51c5636c-86dd-457c-9500-8197019fa984', '4f0a3a35-06a4-4a6f-9207-c8fe00a0e819', '2023-12-17', '2023-12-25', '2023-12-15');