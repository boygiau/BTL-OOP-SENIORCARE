USE seniorcare;

CREATE TABLE Users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       full_name VARCHAR(255) NOT NULL,
                       date_of_birth DATE,
                       phone_number VARCHAR(20) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       hometown VARCHAR(255),
                       current_address TEXT,
                       profile_picture_url VARCHAR(255),
                       hobbies TEXT,
                       personal_story TEXT,
                       emergency_contact_phone VARCHAR(20),
                       emergency_contact_email VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE MedicalProfiles (
                                 profile_id INT AUTO_INCREMENT PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 primary_condition VARCHAR(255),
                                 medical_history TEXT,
                                 allergies TEXT,
                                 FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Doctors (
                         doctor_id INT AUTO_INCREMENT PRIMARY KEY,
                         full_name VARCHAR(255) NOT NULL,
                         specialization VARCHAR(255),
                         workplace VARCHAR(255),
                         position VARCHAR(100),
                         bio TEXT,
                         profile_picture_url VARCHAR(255)
);

CREATE TABLE Appointments (
                              appointment_id INT AUTO_INCREMENT PRIMARY KEY,
                              user_id INT NOT NULL,
                              doctor_id INT NOT NULL,
                              appointment_time DATETIME NOT NULL,
                              user_symptoms TEXT,
                              status VARCHAR(50) DEFAULT 'Pending',
                              doctor_notes TEXT,
                              FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE Prescriptions (
                               prescription_id INT AUTO_INCREMENT PRIMARY KEY,
                               appointment_id INT NOT NULL,
                               user_id INT NOT NULL,
                               doctor_id INT NOT NULL,
                               medication_name VARCHAR(255) NOT NULL,
                               dosage VARCHAR(100),
                               instructions TEXT,
                               start_date DATE NOT NULL,
                               duration_days INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                               FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE CASCADE
);

CREATE TABLE MedicationSchedules (
                                     schedule_id INT AUTO_INCREMENT PRIMARY KEY,
                                     user_id INT NOT NULL,
                                     prescription_id INT NOT NULL,
                                     scheduled_time DATETIME NOT NULL,
                                     status VARCHAR(50) DEFAULT 'Pending',

                                     FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (prescription_id) REFERENCES Prescriptions(prescription_id) ON DELETE CASCADE
);

CREATE TABLE HealthLogs (
                            log_id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT NOT NULL,
                            log_type VARCHAR(100) NOT NULL,
                            value VARCHAR(255) NOT NULL,
                            notes TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE AIChat (
                        log_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        session_id VARCHAR(255) NOT NULL,

                        chat_title VARCHAR(255),
                        chat_description TEXT,

                        start_time DATETIME,
                        end_time DATETIME,
                        media_type VARCHAR(50),
                        chat_summary TEXT,
                        detected_emotion VARCHAR(100),
                        storage_url VARCHAR(255),
                        FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE AbnormalAlerts (
                                alert_id INT AUTO_INCREMENT PRIMARY KEY,
                                user_id INT NOT NULL,
                                alert_type VARCHAR(100) NOT NULL,
                                description TEXT NOT NULL,
                                alert_level VARCHAR(50) DEFAULT 'Info',
                                status VARCHAR(50) DEFAULT 'Raised',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE UserConnections (
                                 connection_id INT AUTO_INCREMENT PRIMARY KEY,
                                 user_id_1 INT NOT NULL,
                                 user_id_2 INT NOT NULL,
                                 status VARCHAR(50) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (user_id_1) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (user_id_2) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 UNIQUE(user_id_1, user_id_2)
);

CREATE TABLE PrivateMessages (
                                 message_id INT AUTO_INCREMENT PRIMARY KEY,
                                 sender_id INT NOT NULL,
                                 receiver_id INT NOT NULL,
                                 message_body TEXT NOT NULL,
                                 sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 is_read TINYINT(1) DEFAULT 0,

                                 FOREIGN KEY (sender_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                                 FOREIGN KEY (receiver_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE BlogCategories (
                                category_id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(100) NOT NULL UNIQUE,
                                description TEXT
);

CREATE TABLE Blog (
                      content_id INT AUTO_INCREMENT PRIMARY KEY,
                      content_type VARCHAR(50) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      content_body_or_url TEXT NOT NULL,
                      thumbnail_url VARCHAR(255),
                      author VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                      category_id INT,
                      FOREIGN KEY (category_id) REFERENCES BlogCategories(category_id) ON DELETE SET NULL
);

CREATE TABLE ExerciseCategories (
                                    category_id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL UNIQUE,
                                    description TEXT
);

CREATE TABLE ExerciseLibrary (
                                 exercise_id INT AUTO_INCREMENT PRIMARY KEY,
                                 title VARCHAR(255) NOT NULL,
                                 description TEXT,
                                 master_video_url VARCHAR(255),
                                 difficulty_level VARCHAR(50),
                                 focus_area VARCHAR(100),
                                 category_id INT,

                                 FOREIGN KEY (category_id) REFERENCES ExerciseCategories(category_id) ON DELETE SET NULL
);

CREATE TABLE UserExerciseSessions (
                                      session_id INT AUTO_INCREMENT PRIMARY KEY,
                                      user_id INT NOT NULL,
                                      exercise_id INT NOT NULL,

                                      session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      duration_seconds INT,
                                      performance_score DECIMAL(5, 2),
                                      common_mistakes TEXT,
                                      ai_feedback TEXT,

                                      FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                                      FOREIGN KEY (exercise_id) REFERENCES ExerciseLibrary(exercise_id) ON DELETE CASCADE
);

CREATE TABLE Notifications (
                               notification_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id INT NOT NULL,
                               notification_type VARCHAR(100),
                               reference_id INT,
                               message TEXT NOT NULL,
                               is_read TINYINT(1) DEFAULT 0,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);