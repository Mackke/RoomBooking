DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS room;

-- Create the Room table
CREATE TABLE room (
                      id VARCHAR(36) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      type VARCHAR(50),
                      beds INT,
                      price DOUBLE
);

-- Create the Booking table
CREATE TABLE booking (
                         id VARCHAR(36) PRIMARY KEY,
                         room_id VARCHAR(36),
                         start_date DATE,
                         end_date DATE,
                         archived DATE
);

ALTER TABLE booking
    ADD CONSTRAINT FK_ROOM FOREIGN KEY (room_id) REFERENCES room (id);